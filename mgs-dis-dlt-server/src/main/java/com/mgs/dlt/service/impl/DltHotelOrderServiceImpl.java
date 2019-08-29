package com.mgs.dlt.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.common.enums.DltChannelEnum;
import com.mgs.common.enums.DltChannelOrderStateEnum;
import com.mgs.common.enums.DltOrderOperateEnum;
import com.mgs.common.enums.OrderStateEnum;
import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisBaseQueryDTO;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.remote.DisAgentRemote;
import com.mgs.dis.remote.DisMappingRemote;
import com.mgs.dlt.domain.DltOrderCancelRulesPO;
import com.mgs.dlt.domain.DltOrderDayPricePO;
import com.mgs.dlt.domain.DltOrderDetailPO;
import com.mgs.dlt.domain.DltOrderPO;
import com.mgs.dlt.mapper.DltOrderCancelRulesMapper;
import com.mgs.dlt.mapper.DltOrderDayPriceMapper;
import com.mgs.dlt.mapper.DltOrderDetailMapper;
import com.mgs.dlt.mapper.DltOrderMapper;
import com.mgs.dis.dto.HotelOrderOperateRequestDTO;
import com.mgs.dlt.response.dto.CancelRule;
import com.mgs.dlt.response.dto.DltOrderInfo;
import com.mgs.dlt.response.dto.DltOrderRoomPrice;
import com.mgs.dis.dto.HotelOrderOperateResponseDTO;
import com.mgs.dlt.service.DltHotelOrderOperateService;
import com.mgs.dlt.service.DltHotelOrderService;
import com.mgs.enums.OrderStatusEnum;
import com.mgs.order.remote.BookingRemote;
import com.mgs.order.remote.OrderQueryRemote;
import com.mgs.order.remote.OrderRemote;
import com.mgs.order.remote.request.AddManualOrderDTO;
import com.mgs.order.remote.request.AddOrderRequestDTO;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.PriceRequestDTO;
import com.mgs.order.remote.response.OrderDTO;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *   2018/3/12.
 */
@Service("dltHotelOrderService")
public class DltHotelOrderServiceImpl implements DltHotelOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(DltHotelOrderServiceImpl.class);

    @Autowired
    private DisAgentRemote disAgentRemote;

    @Autowired
    private DisMappingRemote dltMapRoomPOMapper;

    @Autowired
    private DltOrderMapper dltOrderPOMapper;

    @Autowired
    private DltOrderDetailMapper dltOrderDetailPOMapper;

    @Autowired
    private DltOrderCancelRulesMapper dltOrderCancelRulesPOMapper;

    @Autowired
    private DltOrderDayPriceMapper dltOrderDayPricePOMapper;

    @Autowired
    private DltHotelOrderOperateService dltHotelOrderOperateServiceImpl;

    @Autowired
    private OrderQueryRemote orderQueryRemote;

    @Autowired
    private OrderRemote orderRemote;

    @Autowired
    private BookingRemote bookingRemote;

    @Override
    public void createOrder(DltOrderInfo dltOrderInfo, String merchantCode) {

        if (null == dltOrderInfo || null == dltOrderInfo.getDltOrderId()) {
            LOG.error("创建代理通订单参数错误，dltOrderInfo=" + dltOrderInfo);
            return;
        }

        if (null == dltOrderInfo.getRoomId()) {
            LOG.error("创建代理通订单参数错误，roomid=null");
            return;
        }

        StringBuilder orderModifyLog = new StringBuilder();
        String dltOrderId = dltOrderInfo.getDltOrderId();
        String mOrderId = null;
        boolean checkInDateChangeFlag = false;
        boolean checkOutDateChangeFlag = false;
        boolean guestNameChangeFlag = false;
        boolean roomNumChangeFlag = false;
        boolean orderStatusChangeFlag = false;
        StringBuilder orderChangeSB = new StringBuilder();

        // 将订单信息保存到本地库
        try {
            DltOrderDetailPO dltOrderDetailPO = this.copyOrderDetail(dltOrderInfo);

            Example example = new Example(DltOrderDetailPO.class);
            example.createCriteria().andEqualTo("dltOrderId",dltOrderId);
            List<DltOrderDetailPO> dltOrderDetailPOList = dltOrderDetailPOMapper.selectByExample(example);

            // 订单不存在，直接插入
            if (CollectionUtils.isEmpty(dltOrderDetailPOList)) {
                dltOrderDetailPOMapper.insert(dltOrderDetailPO);
            } else {
                // 订单存在，需要对比，看看哪里发生变化了，需要提示客户
                DltOrderDetailPO existsOrderDetailPo = dltOrderDetailPOList.get(0);
                mOrderId = existsOrderDetailPo.getMOrderCode();
                LOG.info("订单存在，mOrderId:"+mOrderId);
                orderChangeSB = this.compareOrderDetail(existsOrderDetailPo, dltOrderDetailPO);
                checkInDateChangeFlag = this.compareDate(existsOrderDetailPo.getCheckInDate(),dltOrderDetailPO.getCheckInDate());
                checkOutDateChangeFlag = this.compareDate(existsOrderDetailPo.getCheckOutDate(),dltOrderDetailPO.getCheckOutDate());
                guestNameChangeFlag = this.compareString(existsOrderDetailPo.getCustomerName(),dltOrderDetailPO.getCustomerName());
                roomNumChangeFlag = this.compareInteger(existsOrderDetailPo.getRoomNum(),dltOrderDetailPO.getRoomNum());
                orderStatusChangeFlag = this.compareString(existsOrderDetailPo.getOrderStatus(),dltOrderDetailPO.getOrderStatus());
                // 如果没有发生变化，不需要更新db
                if (orderChangeSB.length() > 0) {
                    orderModifyLog.append(orderChangeSB);

                    dltOrderDetailPO.setId(existsOrderDetailPo.getId());
                    dltOrderDetailPO.setModifiedBy("system");
                    dltOrderDetailPO.setModifiedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
                    dltOrderDetailPOMapper.updateByPrimaryKeySelective(dltOrderDetailPO);
                }
            }
        } catch (Exception e) {
            LOG.error("插入或更新代理通订单详细信息至数据库失败", e);
            this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "插入或更新代理通订单详细信息至数据库失败");
            return;
        }


        // 将订单取消政策保存到本地库
        try {
            List<DltOrderCancelRulesPO> dltOrderCancelRulesPOList = this.copyOrderCancelRules(dltOrderInfo);
            // 查询档期库里面的取消政策
            Example example = new Example(DltOrderCancelRulesPO.class);
            example.createCriteria().andEqualTo("dltOrderId",dltOrderId);
            List<DltOrderCancelRulesPO> existsOrderCancelRulesPOList = dltOrderCancelRulesPOMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(dltOrderCancelRulesPOList)) {

                if (CollectionUtils.isEmpty(existsOrderCancelRulesPOList)) {
                    orderModifyLog.append("\n新增了订单取消规则");
                    dltOrderCancelRulesPOMapper.insertList(dltOrderCancelRulesPOList);
                } else {

                    StringBuilder sb = this.compareOrderCancelRules(existsOrderCancelRulesPOList, dltOrderCancelRulesPOList);
                    if (sb.length() > 0) {
                        orderModifyLog.append(sb);

                        dltOrderCancelRulesPOMapper.deleteByExample(example);
                        dltOrderCancelRulesPOMapper.insertList(dltOrderCancelRulesPOList);
                    }
                }
            } else {
                if (!CollectionUtils.isEmpty(existsOrderCancelRulesPOList)) {
                    orderModifyLog.append("\n删除了订单取消规则");
                }
            }
        } catch (Exception e) {
            LOG.error("插入代理通订单取消规则详细信息至数据库失败", e);
            this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "插入代理通订单取消规则详细信息至数据库失败");
            return;
        }

        // 将订单每日价格保存到本地库
        try {
            List<DltOrderDayPricePO> dltOrderDayPricePOList = this.copyOrderDayPrices(dltOrderInfo);

            if (!CollectionUtils.isEmpty(dltOrderDayPricePOList)) {

                Example example = new Example(DltOrderDayPricePO.class);
                example.createCriteria().andEqualTo("dltOrderId",dltOrderId);
                List<DltOrderDayPricePO> existsOrderDayPriceList = dltOrderDayPricePOMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(existsOrderDayPriceList)) {
                    orderModifyLog.append("订单新增了每日价格信息；");
                    dltOrderDayPricePOMapper.insertList(dltOrderDayPricePOList);
                } else {

                    StringBuilder sb = this.compareOrderDayPrice(existsOrderDayPriceList, dltOrderDayPricePOList);
                    if (sb.length() > 0) {
                        orderModifyLog.append(sb);

                        dltOrderDayPricePOMapper.deleteByExample(example);
                        dltOrderDayPricePOMapper.insertList(dltOrderDayPricePOList);
                    }
                }
            } else {
                LOG.error("代理通订单每日价格详细信息缺失, dltOrderId:" + dltOrderInfo.getDltOrderId());
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "代理通订单每日价格详细信息缺失");
                return;
            }
        } catch (Exception e) {
            LOG.error("插入代理通订单每日价格详细信息至数据库失败", e);
            this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "插入代理通订单每日价格详细信息至数据库失败");
            return;
        }

        String channelCode = this.getChannel(dltOrderInfo.getChannel());
        if (null == channelCode) {
            LOG.error("代理通订单渠道无法识别，channel:" + dltOrderInfo.getChannel() + ", dltOrderId: " + dltOrderInfo.getDltOrderId());
            this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false,
                    "代理通订单渠道无法识别，channel:" + dltOrderInfo.getChannel() + ", dltOrderId: " + dltOrderInfo.getDltOrderId());
            return;
        }
        String orderState = this.transferOrderState(dltOrderInfo.getFormType());


        OrderDTO orderDetailResponseDTO = null;
        if(StringUtil.isValidString(mOrderId)) {
            OrderCodeDTO orderCodeDTO = new OrderCodeDTO();
            orderCodeDTO.setOrderCode(mOrderId);
            Response response = orderQueryRemote.queryOrderDetail(orderCodeDTO);
            if (null != response && null != response.getModel()) {
                orderDetailResponseDTO = JSON.parseObject(JSON.toJSONString(response.getModel()),OrderDTO.class);
            }
        }

        /**
         * 处理取消申请
         * 申请取消的如果已经确认或者已经取消，就直接调一次接口拒绝掉，不影响内部订单状态，否则就改为申请取消中，拒绝后变为处理中
         */
        if (OrderStateEnum.APPLYING_CANCEL.code.equals(orderState)) {
            if (null == orderDetailResponseDTO) {
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "未查询到订单记录");
                return;
            }

            // 订单已经是取消状态
            //mongoso订单状态（0：待确认 1：已确认 2：已取消）
            if (OrderStatusEnum.CANCELED.no == orderDetailResponseDTO.getConfirmationStatus()) {
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), true, "订单取消成功");
                return;
            }

            // 订单已经确认或者已经拒绝，则不可申请取消，直接发一个拒绝回去
//            if (OrderStatusEnum.TRADED.key == orderDetailResponseDTO.getOrderStatus()
//                    || OrderStatusEnum.CANCELED.key == orderDetailResponseDTO.getOrderStatus()) {
//                Boolean bln = this.refuse(orderDetailResponseDTO, dltOrderInfo);
//                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), bln, "拒绝取消" + (bln ? "成功" : "失败"));
//                return;
//            }

            //订单是非取消状态，发送取消申请到订单系统
            AddOrderRequestDTO addOrderRequestDTO = new AddOrderRequestDTO();
            addOrderRequestDTO.setOrderId(orderDetailResponseDTO.getOrderId());
            addOrderRequestDTO.setRequestType(0);
            addOrderRequestDTO.setOperator(channelCode);
            addOrderRequestDTO.setRemark("渠道客人发起取消");
            Response response = orderRemote.addOrderRequest(addOrderRequestDTO);

            if (null == response || null == response.getResult()
                    || 0 == response.getResult() || null == response.getModel()) {
                LOG.error("调用订单服务发送订单取消申请失败，参数：" + orderDetailResponseDTO.getOrderId());
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, response.getFailReason());
                return;
            } else {
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), true, "订单取消申请发送成功");
                return;
            }
        } else {

            // 向订单系统发送订单
            //申请修改的如果已经确认或者已经取消，就直接调一次接口拒绝掉，不影响内部订单状态，否则就直接修改订单信息，确认的时候提示订单状态处于修改申请中，请再次确认订单信息
            Response response = null;
            AddManualOrderDTO addManualOrderDTO;
            try {
                addManualOrderDTO = this.buildCreateOrderRequestDTO(dltOrderInfo, channelCode,merchantCode);
//                createOrderRequestDTO.setRemark(orderModifyLog.toString());
//                createOrderRequestDTO.setOrderState(orderState);
                addManualOrderDTO.setChannelCode(channelCode);
            } catch (Exception e) {
                String msg = "构建下单请求失败";
                LOG.error(msg);
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, msg);
                return;
            }

            try {
                if (OrderStateEnum.NEW.code.equals(orderState)) {
                    if(null==orderDetailResponseDTO) {
                        response = bookingRemote.addOTAOrder(addManualOrderDTO);
                        if(null != response && null != response.getModel()) {
                            mOrderId = response.getModel().toString();
                            LOG.info("下单成功，mOrderId:"+mOrderId);
                        }
                    }else {
                        this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "订单已经存在");
                    }

                } else if (OrderStateEnum.APPLYING_MODIFY.code.equals(orderState)) {
                    if (null == orderDetailResponseDTO) {
                        this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "未查询到订单记录");
                        return;
                    }

                    // 已经取消，则不可申请修改，直接发一个拒绝回去
                    // 代理通发起修改，但是订单内容没有变化
                    if (OrderStatusEnum.CANCELED.no == orderDetailResponseDTO.getConfirmationStatus()
                            || orderChangeSB.length() <= 0) {
                        Boolean bln = this.refuse(orderDetailResponseDTO, dltOrderInfo);
                        this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), bln, "拒绝修改" + (bln ? "成功" : "失败"));
                        return;
                    }

//                    //订单是非取消状态（所有产品默认不能修改）
//                    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
//                    orderRequestDTO.setOrderId(orderDetailResponseDTO.getOrderId());
//                    orderRequestDTO.setRequestType((byte)2);
//                    orderRequestDTO.setCreator(channelCode);
//                    //入住日期发生变更
//                    if(checkInDateChangeFlag) {
//                        orderRequestDTO.setCheckinDate(dltOrderInfo.getCheckinDate());
//                    }
//                    //离店日期发生变更
//                    if(checkOutDateChangeFlag) {
//                        orderRequestDTO.setCheckoutDate(dltOrderInfo.getCheckoutDate());
//                    }
//                    //客人姓名发生变更
//                    if(guestNameChangeFlag) {
//                        orderRequestDTO.setGuestNames(dltOrderInfo.getCustomerName());
//                    }
//                    //房间数发生变更
//                    if(roomNumChangeFlag) {
//                        orderRequestDTO.setRoomNum(dltOrderInfo.getRoomnum());
//                    }
//
//                    orderRequestDTO.setNote(orderChangeSB.toString());
//                    ResponseDTO modifyOrderResponseDTO = OrderInterfaceInvoker.addOrderRequest(JSON.toJSONString(orderRequestDTO));
//
//
//                    if (null == modifyOrderResponseDTO || null == modifyOrderResponseDTO.getResult()
//                            || 0 == modifyOrderResponseDTO.getResult() || null == modifyOrderResponseDTO.getModel()) {
//                        LOG.error("调用订单服务发送订单修改申请失败，参数：" + orderDetailResponseDTO.getOrderId());
//                        this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, modifyOrderResponseDTO.getFailReason());
//                        return;
//                    } else {
//                        this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), true, "订单修改申请发送成功");
//                        return;
//                    }
                } else {
                    LOG.error("订单操作类型无效，无法操作订单：");
                    this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "订单操作类型无效");
                    return;
                }

                if(!this.saveOrUpdateChannelOrderStatus(dltOrderInfo.getChannel(),Integer.valueOf(dltOrderInfo.getOrderStatus()),merchantCode,mOrderId)) {
                    LOG.error("更新渠道订单状态失败");
                }
            } catch (Exception e) {
                LOG.error("向订单系统下单或修改单失败", e);
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false, "向订单系统下单或修改单失败");
                return;
            }

            if (null != response  && null != response.getResult() && response.getResult() == 1) {
                DltOrderDetailPO po = new DltOrderDetailPO();
               po.setMOrderCode(response.getModel().toString());
                po.setModifiedBy("system");
                po.setModifiedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));

                Example example = new Example(DltOrderDetailPO.class);
                example.createCriteria().andEqualTo("dltOrderId",dltOrderId);
                dltOrderDetailPOMapper.updateByExampleSelective(po, example);
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), true, "订单处理成功");
            } else {
                LOG.error("向订单系统下单或修改单失败，下单参数：" + JSON.toJSONString(addManualOrderDTO));
                this.updateOrderHandleResult(dltOrderInfo.getDltOrderId(), false,
                        null == response ? "向订单系统下单或修改单失败" :response.getFailReason());
                return;
            }
        }
    }

    private Boolean refuse(OrderDTO orderDetailResponseDTO, DltOrderInfo dltOrderInfo) {
        try {
            HotelOrderOperateRequestDTO requestDTO = new HotelOrderOperateRequestDTO();
            requestDTO.setChannelCode(orderDetailResponseDTO.getChannelCode());
            requestDTO.setOrderCode(orderDetailResponseDTO.getOrderCode());
            requestDTO.setCustomerOrderCode(orderDetailResponseDTO.getChannelOrderCode());
            requestDTO.setOrderState(transferOrderStateToDlt(orderDetailResponseDTO.getConfirmationStatus()));


            String orderState = this.transferOrderState(dltOrderInfo.getFormType());
            String operateType = DltOrderOperateEnum.CTRIP_REFUSE.code;
            if (requestDTO.getChannelCode().startsWith("ctrip")) {
                operateType = OrderStateEnum.APPLYING_CANCEL.code.equals(orderState) ? DltOrderOperateEnum.CTRIP_REFUSE_CANCEL.code : DltOrderOperateEnum.CTRIP_REFUSE.code;
            } else if (requestDTO.getChannelCode().startsWith("qunar")) {
                operateType = OrderStateEnum.APPLYING_CANCEL.code.equals(orderState) ? DltOrderOperateEnum.QUNAR_REFUSE_UNSUBSCRIBE.code : DltOrderOperateEnum.CTRIP_REFUSE.code;
            }
            requestDTO.setOperateType(operateType);
            requestDTO.setRefuseType(3);
            HotelOrderOperateResponseDTO responseDTO = dltHotelOrderOperateServiceImpl.operateOrder(requestDTO);
            return (null != responseDTO && 1 == responseDTO.getIsSuccess());
        } catch (Exception e) {
            LOG.error("拒绝取消或拒绝修改操作失败", e);
        }
        return false;
    }

//    private CreateOrderRequestDTO buildCreateOrderRequestDTO(DltOrderInfo dltOrderInfo, String channelCode) {
//        CreateOrderRequestDTO request = new CreateOrderRequestDTO();
//        request.setCustomerOrderCode(dltOrderInfo.getOrderId());// orderId才是代理通订单号，可以在代理通后台搜索的
//        request.setPayMethod(PayMethodEnum.PREPAYALL.code);
//        request.setSaleCurrency(null == dltOrderInfo.getOrderCurrency() ? CurrencyEnum.CNY.code : dltOrderInfo.getOrderCurrency());
//        request.setSalePrice(dltOrderInfo.getOrderPrice());
//        request.setChildChannelCode(dltOrderInfo.getChannel());
//        request.setChannelState(this.transferChannelState(dltOrderInfo.getOrderStatus(), channelCode));
//        request.setGuestName(dltOrderInfo.getCustomerName());
//        request.setCheckinDate(dltOrderInfo.getCheckinDate());
//        request.setCheckoutDate(dltOrderInfo.getCheckoutDate());
//
//        ChannelAgentPOExample example = new ChannelAgentPOExample();
//        example.createCriteria().andChannelCodeEqualTo(channelCode);
//        List<ChannelAgentPO> channelAgentPOList = channelAgentPOMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(channelAgentPOList)) {
//            throw new ServiceException("未查询到客户和渠道关联关系，请检查t_channel_agent表的配置, channel=" + channelCode);
//        }
//        request.setAgentCode(channelAgentPOList.get(0).getAgentCode());
//        request.setAgentName(channelAgentPOList.get(0).getAgentName());
//        request.setCreator(channelAgentPOList.get(0).getAgentName());
//
//        DltMapRoomPOExample roomPOExample = new DltMapRoomPOExample();
//        roomPOExample.createCriteria().andDltRoomIdEqualTo(Long.valueOf(dltOrderInfo.getRoomId()));
//        List<DltMapRoomPO> dltMapRoomPOList = dltMapRoomPOMapper.selectByExample(roomPOExample);
//        if (CollectionUtils.isEmpty(dltMapRoomPOList)) {
//            throw new ServiceException("未查询到代理通售卖房型和本地价格计划的匹配信息，roomId=" + dltOrderInfo.getRoomId());
//        }
//        DltMapRoomPO dltMapRoomPO = dltMapRoomPOList.get(0);
//
//        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
//        OrderProductDTO product = new OrderProductDTO();
//        product.setHotelId(dltMapRoomPO.getZhHotelId());
//        product.setRoomTypeId(dltMapRoomPO.getZhRoomId());
//        product.setPriceplanId(dltMapRoomPO.getZhRpId());
//        product.setCheckinDate(dltOrderInfo.getCheckinDate());
//        product.setCheckoutDate(dltOrderInfo.getCheckoutDate());
//        product.setProductType(OrderProductTypeEnum.ROOM.code);
//
//        List<OrderDayPriceDTO> orderDayPriceDTOList = new ArrayList<>();
//        for (DltOrderRoomPrice dorp : dltOrderInfo.getRoomPriceList()) {
//            OrderDayPriceDTO dayPrice = new OrderDayPriceDTO();
//            dayPrice.setPriceplanId(dltMapRoomPO.getZhRpId());
//            dayPrice.setSaleDate(dorp.getEffectDate());
//            dayPrice.setSaleBCurrency(dorp.getCurrency());
//            dayPrice.setSaleBPrice(dorp.getPrice());
//            dayPrice.setBreakfastNum(BreakfastEnum.getKeyByNum(dorp.getBreakfastNum()));//床位早如何表示？
//            dayPrice.setRooms(dltOrderInfo.getRoomnum());
//            orderDayPriceDTOList.add(dayPrice);
//        }
//
//        product.setOrderDayPriceDTOList(orderDayPriceDTOList);
//        orderProductDTOList.add(product);
//        request.setOrderProductDTOList(orderProductDTOList);
//        return request;
//    }

    /**
     * 构建下单参数
     * @param dltOrderInfo
     * @param channelCode
     * @return
     */
    private AddManualOrderDTO buildCreateOrderRequestDTO(DltOrderInfo dltOrderInfo, String channelCode,String merchantCode) {
        AddManualOrderDTO addManualOrderDTO = new AddManualOrderDTO();
        addManualOrderDTO.setChannelOrderCode(dltOrderInfo.getOrderId());// orderId才是代理通订单号，可以在代理通后台搜索的
        addManualOrderDTO.setChannelCode(dltOrderInfo.getChannel());
        addManualOrderDTO.setOperator("代理通");
        List<String> guestNameLst = new ArrayList<>();
        guestNameLst.add(dltOrderInfo.getCustomerName());
        addManualOrderDTO.setGuestList(guestNameLst);
        addManualOrderDTO.setCompanyCode(merchantCode);
        addManualOrderDTO.setBaseCurrency(0);//币种默认为人民币
        addManualOrderDTO.setSaleCurrency(0);
        addManualOrderDTO.setOrderAmt(dltOrderInfo.getOrderPrice());

        DisBaseQueryDTO disBaseQueryDTO = new DisBaseQueryDTO();
        disBaseQueryDTO.setChannelCode(channelCode);
        disBaseQueryDTO.setCompanyCode(merchantCode);
        List<DisAgentDTO> disAgentDTOList = disAgentRemote.queryAgentListByParam(disBaseQueryDTO);
        if (CollectionUtils.isEmpty(disAgentDTOList)) {
            throw new RuntimeException("未查询到客户和渠道关联关系，请检查t_channel_agent表的配置, channel=" + channelCode);

        }
        addManualOrderDTO.setAgentCode(disAgentDTOList.get(0).getAgentCode());
        addManualOrderDTO.setAgentName(disAgentDTOList.get(0).getAgentName());
        DisMappingQueryDTO disMappingQueryDTO = new DisMappingQueryDTO();
        disMappingQueryDTO.setDisRoomId(dltOrderInfo.getRoomId());
        List<DisProductMappingDTO> disProductMappingDTOList = dltMapRoomPOMapper.queryProductMapping(disMappingQueryDTO);
        DisProductMappingDTO disProductMappingDTO = null;
        if (!CollectionUtils.isEmpty(disProductMappingDTOList)) {
            disProductMappingDTO = disProductMappingDTOList.get(0);
        }else {
            throw new RuntimeException("未查询到代理通售卖房型和本地价格计划的匹配信息，roomId=" + dltOrderInfo.getRoomId());
        }
        addManualOrderDTO.setHotelId(disProductMappingDTO.getHotelId());
        addManualOrderDTO.setRoomId(disProductMappingDTO.getRoomId());
        addManualOrderDTO.setRoomName(disProductMappingDTO.getRoomName());
        addManualOrderDTO.setProductId(disProductMappingDTO.getProductId());
        addManualOrderDTO.setStartDate(DateUtil.dateToString(dltOrderInfo.getCheckinDate()));
        addManualOrderDTO.setEndDate(DateUtil.dateToString(dltOrderInfo.getCheckoutDate()));
        addManualOrderDTO.setRoomQty(dltOrderInfo.getRoomnum());
        //早餐转换
        addManualOrderDTO.setBreakfastQty(dltOrderInfo.getRoomPriceList().get(0).getBreakfastNum());

        /**
         * 价格列表
         */
        List<PriceRequestDTO> priceList = new ArrayList<>();

        for (DltOrderRoomPrice dorp : dltOrderInfo.getRoomPriceList()) {
            PriceRequestDTO dayPrice = new PriceRequestDTO();
            dayPrice.setSaleDate(DateUtil.dateToString(dorp.getEffectDate()));
            dayPrice.setSalePrice(dorp.getPrice());
            priceList.add(dayPrice);
        }

        addManualOrderDTO.setPriceList(priceList);
        return addManualOrderDTO;
    }

    private String getChannel(String channel) {

        if (StringUtils.isEmpty(channel)) {
            LOG.error("代理通渠道或子渠道为空");
            return null;
        }

        if (channel.toLowerCase().equals(DltChannelEnum.QUNAR.key)) {
            return DltChannelEnum.QUNAR.key;
        }

        if (channel.toLowerCase().equals("ebk")) {
            return DltChannelEnum.CTRIP.key;
        }

        if (channel.toLowerCase().equals("b2b") || channel.toLowerCase().equals("b2boffline")) {
            return DltChannelEnum.CTRIP_B2B.key;
        }

        if (channel.toLowerCase().equals("tc")) {
            return DltChannelEnum.CTRIP_CHANNEL_A.key;
        }

        return null;
    }

    private String transferChannelState(String statusCode, String channel) {
        return DltChannelOrderStateEnum.getOrderStateName(channel, statusCode);
    }

    private boolean saveOrUpdateChannelOrderStatus(String channel,Integer orderStatus,String merchantCode,String mOrderId) {
        boolean flag = false;
//        try {
//            if(StringUtil.isValidString(channel) && null!=orderStatus && StringUtil.isValidString(merchantCode) && null!=mOrderId ) {
//                ChangeChannelOrderCodeRequestDTO changeChannelOrderCodeRequestDTO = new ChangeChannelOrderCodeRequestDTO();
//                changeChannelOrderCodeRequestDTO.setOrderId(mOrderId);
//                changeChannelOrderCodeRequestDTO.setCreator(channel);
//                changeChannelOrderCodeRequestDTO.setModifier(channel);
//                changeChannelOrderCodeRequestDTO.setChannelOrderStatus(orderStatus+":"+ChannelOrderStatusEnum.getValueByChannelAndStatus(channel,orderStatus));
//                Response response = OrderInterfaceInvoker.changeOrderStatus(JSON.toJSONString(changeChannelOrderCodeRequestDTO));
//                if(response.getResult().equals(ResultCodeEnum.SUCCESS.code)) {
//                    flag = true;
//                    LOG.info("更新订单状态成功，mOrderId:"+mOrderId);
//                }
//            }
//        }catch (Exception e) {
//            LOG.error("更新渠道订单状态失败",e);
//        }

        return flag;
    }

    private String transferOrderState(String formType) {
        switch (formType) {
            case "N" : return OrderStateEnum.NEW.code;
            case "M" : return OrderStateEnum.APPLYING_MODIFY.code;
            case "C" : return OrderStateEnum.APPLYING_CANCEL.code;
            case "D" : return OrderStateEnum.APPLYING_MODIFY.code;
            default : return OrderStateEnum.APPLYING_CANCEL.code;
        }
    }

    private String transferOrderStateToDlt(Integer mOrderStatus) {
        switch (mOrderStatus) {
            case 0 : return OrderStateEnum.PROCESSING.code;
            case 1 : return OrderStateEnum.CONFIRMED.code;
            case 2 : return OrderStateEnum.CANCELED.code;
            default : return OrderStateEnum.CANCELED.code;
        }
    }

    private DltOrderDetailPO copyOrderDetail(DltOrderInfo dltOrderInfo) {
        String updateTimeStr = dltOrderInfo.getUpdateTime();
        Date updateTime = null;
        if (!StringUtils.isEmpty(updateTimeStr)) {
            dltOrderInfo.setUpdateTime(null);//dlt订单这个字段格式有问题，需要手动转化为时间类型
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                updateTime = df.parse(updateTimeStr);
            } catch (ParseException e) {
                LOG.error("时间解析失败", e);
            }
        }

        DltOrderDetailPO dltOrderDetailPO = BeanUtil.transformBean(dltOrderInfo, DltOrderDetailPO.class);
        dltOrderDetailPO.setUpdateTime(updateTime);
        dltOrderDetailPO.setConfirmNo(dltOrderInfo.getConfirmno());
        dltOrderDetailPO.setCheckInDate(dltOrderInfo.getCheckinDate());
        dltOrderDetailPO.setCheckOutDate(dltOrderInfo.getCheckoutDate());
        dltOrderDetailPO.setCityEname(dltOrderInfo.getCityEName());
        dltOrderDetailPO.setHotelEname(dltOrderInfo.getHotelEName());
        dltOrderDetailPO.setRoomEname(dltOrderInfo.getRoomEName());
        dltOrderDetailPO.setRoomNum(dltOrderInfo.getRoomnum());
        dltOrderDetailPO.setCreatedBy("system");
        dltOrderDetailPO.setModifiedBy("system");
        dltOrderDetailPO.setCreatedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
        dltOrderDetailPO.setModifiedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
        return dltOrderDetailPO;
    }

    private List<DltOrderCancelRulesPO> copyOrderCancelRules(DltOrderInfo dltOrderInfo) {
        List<DltOrderCancelRulesPO> dltOrderCancelRulesPOList = new ArrayList<>();

        List<CancelRule> cancelRules = dltOrderInfo.getCancelRules();
        if (!CollectionUtils.isEmpty(cancelRules)) {
            for (CancelRule cr : cancelRules) {
                DltOrderCancelRulesPO po = BeanUtil.transformBean(cr, DltOrderCancelRulesPO.class);
                po.setDltOrderId(dltOrderInfo.getDltOrderId());
                po.setCreatedBy("system");
                po.setModifiedBy("system");
                po.setCreatedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
                po.setModifiedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
                dltOrderCancelRulesPOList.add(po);
            }
        }

        return dltOrderCancelRulesPOList;
    }

    private List<DltOrderDayPricePO> copyOrderDayPrices(DltOrderInfo dltOrderInfo) {
        List<DltOrderDayPricePO> dltOrderDayPricePOList = new ArrayList<>();

        List<DltOrderRoomPrice> dltOrderRoomPriceList = dltOrderInfo.getRoomPriceList();
        if (!CollectionUtils.isEmpty(dltOrderRoomPriceList))
            for (DltOrderRoomPrice roomPrice : dltOrderRoomPriceList)
            { DltOrderDayPricePO po = BeanUtil.transformBean(roomPrice, DltOrderDayPricePO.class);
                po.setDltOrderId(dltOrderInfo.getDltOrderId());
                po.setCreatedBy("system");
                po.setModifiedBy("system");
                po.setCreatedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
                po.setModifiedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
                dltOrderDayPricePOList.add(po); }
        return dltOrderDayPricePOList; }

        private StringBuilder compareOrderDetail(DltOrderDetailPO a, DltOrderDetailPO b) {
            StringBuilder sb = new StringBuilder();
            try { if (!compareString(a.getDltOrderId(), b.getDltOrderId())) sb.append("代理通订单号变化；");
                if (!compareString(a.getChannel(), b.getChannel())) sb.append("代理通订单渠道变化；");
                if (!compareString(a.getChildChannel(), b.getChildChannel())) sb.append("代理通订单子渠道变化；");
                if (!compareDateTime(a.getOrderDate(), b.getOrderDate())) sb.append("代理通订单时间变化；");
                if (!compareString(a.getOrderCurrency(), b.getOrderCurrency())) sb.append("代理通订单币种变化；");
                if (!compareBigDecimal(a.getOrderPrice(), b.getOrderPrice())) sb.append("代理通订单金额变化；");
                if (!compareString(a.getOrderStatus(), b.getOrderStatus())) sb.append("代理通订单状态变化；");
                if (!compareDate(a.getCheckInDate(), b.getCheckInDate())) sb.append("代理通订单入住时间变化；");
                if (!compareDate(a.getCheckOutDate(), b.getCheckOutDate())) sb.append("代理通订单入住时间变化；");
                if (!compareString(a.getHotelId(), b.getHotelId())) sb.append("代理通订单酒店变化；");
                if (!compareString(a.getRoomId(), b.getRoomId())) sb.append("代理通订单房型变化；");
                if (!compareString(a.getBedType(), b.getBedType())) sb.append("代理通订单床型变化；");
                if (!compareString(a.getCustomerName(), b.getCustomerName())) sb.append("代理通订单客人姓名变化；");
                if (!compareString(a.getCustomerDid(), b.getCustomerDid())) sb.append("代理通订单客人联系方式变化；");
                if (!compareString(a.getSpecialMemo(), b.getSpecialMemo())) sb.append("代理通订单特殊要求变化；");
            } catch (Exception e) {
                String errorMsg = "已存在的订单详细信息出错，请详细查看订单是否有变化";
                LOG.error(errorMsg, e); sb.append(errorMsg); } if (sb.length() > 0) sb.insert(0, "\n"); return sb; }

        private Boolean compareString(String a, String b) { return null == a ? null == b : null != b && a.equals(b); }
        private Boolean compareDate(Date a, Date b) { return null == a ? null == b : null != b && 0 == DateUtil.compare(a, b); }
        private Boolean compareDateTime(Date a, Date b) { return null == a ? null == b : null != b && a.getTime() == b.getTime(); }
        private Boolean compareBigDecimal(BigDecimal a, BigDecimal b) { return null == a ? null == b : null != b && 0 == a.compareTo(b); }
        private Boolean compareInteger(Integer a, Integer b) { return null == a ? null == b : null != b && a.equals(b); }
        private StringBuilder compareOrderCancelRules(List<DltOrderCancelRulesPO> listA, List<DltOrderCancelRulesPO> listB) {
        StringBuilder sb = new StringBuilder();
        if (!(CollectionUtils.isEmpty(listA) ? CollectionUtils.isEmpty(listB) : null != listB && listA.size() != listB.size())) {
            sb.append("\n代理通取消政策发生变化；");
            return sb;
        }

        Iterator<DltOrderCancelRulesPO> iteratorListA =  listA.iterator();
        while (iteratorListA.hasNext()) {
            DltOrderCancelRulesPO poa = iteratorListA.next();
            Boolean isChanged = true;

            Iterator<DltOrderCancelRulesPO> iteratorListB =  listB.iterator();
            while (iteratorListB.hasNext()) {
                DltOrderCancelRulesPO pob = iteratorListB.next();
                if (poa.toString().equals(pob.toString())) {
                    isChanged = false;
                    iteratorListB.remove();
                    break;
                }
            }

            if (isChanged) {
                sb.append("\n代理通取消政策发生变化；");
                return sb;
            }
        }
        return sb;
    }

    private StringBuilder compareOrderDayPrice(List<DltOrderDayPricePO> listA, List<DltOrderDayPricePO> listB) {
        StringBuilder sb = new StringBuilder();

        if (CollectionUtils.isEmpty(listA) && CollectionUtils.isEmpty(listB)) {
            return sb;
        }

        if (CollectionUtils.isEmpty(listA) || CollectionUtils.isEmpty(listB) || listA.size() != listB.size()) {
            sb.append("\n代理通每日价格或早餐信息发生变化；");
            return sb;
        }

        Iterator<DltOrderDayPricePO> iteratorListA =  listA.iterator();
        while (iteratorListA.hasNext()) {
            DltOrderDayPricePO poa = iteratorListA.next();
            Boolean isChanged = true;

            Iterator<DltOrderDayPricePO> iteratorListB =  listB.iterator();
            while (iteratorListB.hasNext()) {
                DltOrderDayPricePO pob = iteratorListB.next();
                if (poa.toString().equals(pob.toString())) {
                    isChanged = false;
                    iteratorListB.remove();
                    break;
                }
            }

            if (isChanged) {
                sb.append("\n代理通每日价格或早餐信息发生变化；");
                return sb;
            }
        }

        return sb;
    }

    public void updateOrderHandleResult(String dltOrderId, Boolean success, String msg) {
        try {
            Example example = new Example(DltOrderPO.class);
            example.createCriteria().andEqualTo("dltOrderId",dltOrderId);

            List<DltOrderPO> dltOrderPOList = dltOrderPOMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(dltOrderPOList)) {
                LOG.error("未查询到订单记录，dltOrderId:" + dltOrderId);
                return;
            }

            DltOrderPO dop = dltOrderPOList.get(0);
            dop.setHandleDate(DateUtil.dateToString(new Date(),DateUtil.hour_format));
            dop.setId(null);
            dop.setCreatedDt(null);
            if (success) {
                dop.setIsHandled(0);//处理成功归0
                dop.setHandleResult("success");
                dop.setHandleRemark(null == msg ? "处理成功" : msg);
            } else {
                dop.setIsHandled(dop.getIsHandled() + 1);//处理失败的次数加1
                dop.setHandleResult("failure");
                dop.setHandleRemark(null == msg ? "查询订单详情接口，返回失败" : msg);
            }

            dltOrderPOMapper.updateByExampleSelective(dop, example);
        } catch (Exception e) {
            LOG.error("更新订单详情查询失败次数失败，下次将继续处理该订单", e);
        }
    }
}
