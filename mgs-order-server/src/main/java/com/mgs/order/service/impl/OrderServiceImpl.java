package com.mgs.order.service.impl;

import com.mgs.common.Response;
import com.mgs.dis.dto.HotelOrderOperateRequestDTO;
import com.mgs.dis.dto.HotelOrderOperateResponseDTO;
import com.mgs.dis.remote.DisOperateOrderRemote;
import com.mgs.enums.ChannelEnum;
import com.mgs.enums.DltOperateTypeEnum;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.RemarkEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SettlementTypeEnum;
import com.mgs.finance.enums.CheckStatusEnum;
import com.mgs.order.domain.GuestPO;
import com.mgs.order.domain.OrderAttachmentPO;
import com.mgs.order.domain.OrderConfirmRecordPO;
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.domain.OrderLogPO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.OrderProductPricePO;
import com.mgs.order.domain.OrderRemarkPO;
import com.mgs.order.domain.OrderRequestPO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.domain.SupplyProductPO;
import com.mgs.order.enums.ConfirmationStatusEnum;
import com.mgs.order.mapper.GuestMapper;
import com.mgs.order.mapper.OrderAttachmentMapper;
import com.mgs.order.mapper.OrderConfirmRecordMapper;
import com.mgs.order.mapper.OrderFinanceMapper;
import com.mgs.order.mapper.OrderLogMapper;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.mapper.OrderProductPriceMapper;
import com.mgs.order.mapper.OrderRemarkMapper;
import com.mgs.order.mapper.OrderRequestMapper;
import com.mgs.order.mapper.SupplyOrderFinanceMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.mapper.SupplyProductMapper;
import com.mgs.order.remote.request.AddOrderRequestDTO;
import com.mgs.order.remote.request.AddRemarkDTO;
import com.mgs.order.remote.request.CancelOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderDTO;
import com.mgs.order.remote.request.HandleOrderRequestDTO;
import com.mgs.order.remote.request.LockOrderDTO;
import com.mgs.order.remote.request.MarkOrderDTO;
import com.mgs.order.remote.request.ModifyGuestDTO;
import com.mgs.order.remote.request.ModifyOrderRoomDTO;
import com.mgs.order.remote.request.ModifyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifyRoomDTO;
import com.mgs.order.remote.request.ModifySalePriceDTO;
import com.mgs.order.remote.request.ModifySpecialRequirementDTO;
import com.mgs.order.remote.request.OrderAttachmentIdDTO;
import com.mgs.order.remote.request.PriceRequestDTO;
import com.mgs.order.remote.request.SaveOrderAttachmentDTO;
import com.mgs.order.service.OrderService;
import com.mgs.order.service.common.OrderCommonService;
import com.mgs.organization.remote.AgentRemote;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mgs.util.DateUtil.hour_format;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private OrderConfirmRecordMapper orderConfirmRecordMapper;

    @Autowired
    private OrderProductPriceMapper orderProductPriceMapper;

    @Autowired
    private OrderAttachmentMapper orderAttachmentMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private OrderRequestMapper orderRequestMapper;

    @Autowired
    private OrderRemarkMapper orderRemarkMapper;

    @Autowired
    private SupplyProductMapper supplyProductMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    @Autowired
    private OrderFinanceMapper orderFinanceMapper;

    @Autowired
    private AgentRemote agentRemote;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SupplyOrderFinanceMapper supplyOrderFinanceMapper;

    @Autowired
    private  DisOperateOrderRemote disOperateOrderRemote;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Override
    @Transactional
    public Response confirmOrder(ConfirmOrderDTO request) {
        log.info("confirmOrder param: {}", request);


        Response response = new Response();
        // 1. 检查并更新订单状态
        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());
        if (orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CANCELED.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("订单已取消！");
            return response;
        }


        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setOrderConfirmationStatus(ConfirmationStatusEnum.CONFIRMED.key);
        if (!StringUtil.isValidString(request.getConfirmationCode())) {
            orderUpdate.setConfirmationCode(request.getConfirmationCode());
        }
        orderUpdate.setModifiedBy(request.getOperator());
        orderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);
        OrderConfirmRecordPO orderConfirmRecordPO = new OrderConfirmRecordPO();
        orderConfirmRecordPO.setOrderId(request.getOrderId());
        orderConfirmRecordPO.setConfirmationCode(request.getConfirmationCode());
        orderConfirmRecordPO.setConfirmationContent(request.getConfirmationContent());
        orderConfirmRecordPO.setCreatedBy(request.getOperator());
        orderConfirmRecordPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderConfirmRecordMapper.insert(orderConfirmRecordPO);

        if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key) {
            OrderFinancePO orderFinanceQuery = new OrderFinancePO();
            orderFinanceQuery.setOrderId(request.getOrderId());
            OrderFinancePO orderFinancePO = orderFinanceMapper.selectOne(orderFinanceQuery);
            OrderFinancePO orderFinanceUpdate = new OrderFinancePO();
            orderFinanceUpdate.setId(orderFinancePO.getId());

            //非单结更新对账状态
            if (orderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                if (BigDecimal.ZERO.compareTo(orderFinancePO.getUnreceivedAmt()) == 0) {
                    //如果未收金额为0，则改为已对账
                    orderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                } else {
                    //如果未收金额不为0，则改为可出账
                    orderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                }
                orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
            }
        }

        // 2. 发消息
        // TODO: 2019/7/11 发消息通知客户
        stringRedisTemplate.convertAndSend(request.getCompanyCode() + "9", "all/订单号：" + orderPO.getOrderCode() + "<br>" + "订单以确认请尽快处理！");

        // 3. OTA操作订单（确认、取消）
        //携程代理通渠道
        StringBuffer confirmSB = new StringBuffer();
        try {
            if (orderPO.getChannelCode().equals(ChannelEnum.CTRIP.key)
                    || orderPO.getChannelCode().equals(ChannelEnum.CTRIP_B2B.key)
                    || orderPO.getChannelCode().equals(ChannelEnum.CTRIP_CHANNEL_A.key)
                    || orderPO.getChannelCode().equals(ChannelEnum.QUNAR.key)) {
                HotelOrderOperateRequestDTO hotelOrderOperateRequestDTO = new HotelOrderOperateRequestDTO();
                hotelOrderOperateRequestDTO.setOrderCode(orderPO.getOrderCode());
                hotelOrderOperateRequestDTO.setOrderState(orderPO.getOrderConfirmationStatus().toString());
                hotelOrderOperateRequestDTO.setChannelCode(orderPO.getChannelCode());
                hotelOrderOperateRequestDTO.setMerchantCode(orderPO.getCompanyCode());
                hotelOrderOperateRequestDTO.setCustomerOrderCode(orderPO.getChannelOrderCode());
                hotelOrderOperateRequestDTO.setOperator("商家");
                if (StringUtil.isValidString(orderPO.getConfirmationCode())) {
                    hotelOrderOperateRequestDTO.setConfirmNo(orderPO.getConfirmationCode());
                }
                if (!orderPO.getChannelCode().equals(ChannelEnum.QUNAR.key)) {
                    hotelOrderOperateRequestDTO.setOperateType( DltOperateTypeEnum.CTRIPACCEPT.no);//确认订单
                    hotelOrderOperateRequestDTO.setConfirmType(1);//operaterType=0时必传1：按入住人姓名；2：按确认号
                }else {
                    hotelOrderOperateRequestDTO.setOperateType(DltOperateTypeEnum.QUNARHAVEROOMANDACCEPT.no);//确认订单
                    hotelOrderOperateRequestDTO.setConfirmType(1);//operaterType=0时必传1：按入住人姓名；2：按确认号
                }

                HotelOrderOperateResponseDTO hotelOrderOperateResponseDTO = disOperateOrderRemote.operateOrder(hotelOrderOperateRequestDTO);
                if (null == hotelOrderOperateResponseDTO || !hotelOrderOperateResponseDTO.getIsSuccess().equals(ResultCodeEnum.SUCCESS.code)) {
                    response.setFailCode(ErrorCodeEnum.CONFIRM_OTA_ORDER_ERROR.errorCode);
                    response.setFailReason(hotelOrderOperateResponseDTO.getFailureReason());
                    confirmSB.append("代理通确认订单失败");
                }else {
                    confirmSB.append("代理通确认订单成功");
                }
            }
        }catch (Exception e) {
            log.error("确认OTA订单失败",e);
            confirmSB.append("代理通确认订单失败");
            response.setFailCode(ErrorCodeEnum.CONFIRM_OTA_ORDER_ERROR.errorCode);
            response.setFailReason(ErrorCodeEnum.CONFIRM_OTA_ORDER_ERROR.errorDesc);
        }


        // 4. 记日志
        String content = "确认客人成功";
        if (StringUtil.isValidString(request.getConfirmationCode())) {
            content = content + "，确认号：" + request.getConfirmationCode();
        }
        content = content + "；" + confirmSB.toString();
        orderCommonService.saveOrderLog(
                request.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response cancelOrder(CancelOrderDTO request) {
        log.info("cancelOrder param: {}", request);
        Response response = new Response();
        // 1. 检查订单状态
        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());
        if (orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CANCELED.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("订单已取消！");
            return response;
        }

        // 2. 检查供货单状态：没有已确认的供货单才能取消
        SupplyOrderPO supplyOrderQuery = new SupplyOrderPO();
        supplyOrderQuery.setOrderId(request.getOrderId());
        List<SupplyOrderPO> supplyOrderPOList = supplyOrderMapper.select(supplyOrderQuery);
        BigDecimal supplySum = BigDecimal.ZERO;
        for (SupplyOrderPO supplyOrderPO : supplyOrderPOList) {
            supplySum = supplySum.add(supplyOrderPO.getRefundFee().multiply(supplyOrderPO.getRate()));
            if (supplyOrderPO.getConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                response.setResult(ResultCodeEnum.FAILURE.code);
                response.setFailReason("供货单（" + supplyOrderPO.getSupplyOrderCode() + "）已确认，请先取消！");
                return response;
            }
        }

        // 3. 更新订单状态
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setOrderConfirmationStatus(ConfirmationStatusEnum.CANCELED.key);
        orderUpdate.setCancelledReason(request.getCancelledReason());
        BigDecimal orderSum = BigDecimal.ZERO;
        if (request.getRefundFee() != null) {
            orderUpdate.setRefundFee(request.getRefundFee());
            orderSum = request.getRefundFee();
        }
        orderUpdate.setOrderAmt(orderSum);
        orderUpdate.setProfit(orderSum.subtract(supplySum));
        orderUpdate.setModifiedBy(request.getOperator());
        orderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        // 4. 更新未收金额和结算状态
        OrderFinancePO orderFinanceQuery = new OrderFinancePO();
        orderFinanceQuery.setOrderId(request.getOrderId());
        OrderFinancePO orderFinancePO = orderFinanceMapper.selectOne(orderFinanceQuery);
        OrderFinancePO orderFinanceUpdate = new OrderFinancePO();
        orderFinanceUpdate.setId(orderFinancePO.getId());
        orderFinanceUpdate.setUnreceivedAmt(orderSum.subtract(orderFinancePO.getReceivedAmt()));
        if (orderFinancePO.getReceivedAmt().compareTo(orderSum) == 0 ){
            orderFinanceUpdate.setSettlementStatus(1);
        } else {
            orderFinanceUpdate.setSettlementStatus(0);
        }
        orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);

        if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key) {
            //非单结更新对账状态
            if (orderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                if (BigDecimal.ZERO.compareTo(orderFinanceUpdate.getUnreceivedAmt()) == 0) {
                    //如果未收金额为0，则改为已对账
                    orderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                } else {
                    //如果未收金额不为0，则改为可出账
                    orderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                }
                orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
            }

            //非单结自动退额度
            // TODO: 2019/7/12 调分销商信用账户明细接口，查询已挂账金额
            AgentCreditLineDTO agentCreditLineDTO = new AgentCreditLineDTO();
            agentCreditLineDTO.setAgentCode(orderPO.getAgentCode());
            agentCreditLineDTO.setOrderCode(orderPO.getOrderCode());
            agentCreditLineDTO.setCreatedBy(request.getOperator());
            agentCreditLineDTO.setDeductRefundCreditLine("+" + orderPO.getOrderAmt().toPlainString());
            List<AgentCreditLineDTO> agentCreditLineDTOS = new ArrayList<>();
            agentCreditLineDTOS.add(agentCreditLineDTO);
            Response creditLine = agentRemote.modifyDeductRefundCreditLine(agentCreditLineDTOS);
            if (creditLine.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                response.setFailCode(creditLine.getFailCode());
                response.setFailReason(creditLine.getFailReason());
                return response;
            }
        }

        // 5. 如果申请id不为空，则更新订单申请状态
        if (request.getOrderRequestId() != null && request.getOrderRequestId() != 0) {
            OrderRequestPO orderRequestUpdate = new OrderRequestPO();
            orderRequestUpdate.setId(request.getOrderRequestId());
            orderRequestUpdate.setModifiedBy(request.getOperator());
            orderRequestUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
            orderRequestUpdate.setHandleResult(1);
            orderRequestMapper.updateByPrimaryKeySelective(orderRequestUpdate);
        }

        // 6. OTA操作订单（确认、取消）
        //携程代理通渠道
        StringBuffer confirmSB = new StringBuffer();
        try {
            if (orderPO.getChannelCode().equals(ChannelEnum.CTRIP.key)
                    || orderPO.getChannelCode().equals(ChannelEnum.CTRIP_B2B.key)
                    || orderPO.getChannelCode().equals(ChannelEnum.CTRIP_CHANNEL_A.key)
                    || orderPO.getChannelCode().equals(ChannelEnum.QUNAR.key)) {
                HotelOrderOperateRequestDTO hotelOrderOperateRequestDTO = new HotelOrderOperateRequestDTO();
                hotelOrderOperateRequestDTO.setOrderCode(orderPO.getOrderCode());
                hotelOrderOperateRequestDTO.setChannelCode(orderPO.getChannelCode());
                hotelOrderOperateRequestDTO.setMerchantCode(orderPO.getCompanyCode());
                hotelOrderOperateRequestDTO.setOrderState(orderPO.getOrderConfirmationStatus().toString());
                hotelOrderOperateRequestDTO.setCustomerOrderCode(orderPO.getChannelOrderCode());
                hotelOrderOperateRequestDTO.setOperator("商家");
                if (StringUtil.isValidString(orderPO.getConfirmationCode())) {
                    hotelOrderOperateRequestDTO.setConfirmNo(orderPO.getConfirmationCode());
                }
                if (!orderPO.getChannelCode().equals(ChannelEnum.QUNAR.key)) {
                    hotelOrderOperateRequestDTO.setOperateType(DltOperateTypeEnum.CTRIPREFUSE.no);//取消订单
                    hotelOrderOperateRequestDTO.setConfirmType(1);//operaterType=0时必传1：按入住人姓名；2：按确认号
                }else {
                    hotelOrderOperateRequestDTO.setOperateType(DltOperateTypeEnum.QUNARREFUSEUNSUBSCRIBE.no);//取消订单
                    hotelOrderOperateRequestDTO.setConfirmType(1);//operaterType=0时必传1：按入住人姓名；2：按确认号
                }

                HotelOrderOperateResponseDTO hotelOrderOperateResponseDTO = disOperateOrderRemote.operateOrder(hotelOrderOperateRequestDTO);
                if (null == hotelOrderOperateResponseDTO || !hotelOrderOperateResponseDTO.getIsSuccess().equals(ResultCodeEnum.SUCCESS.code)) {
                    response.setFailCode(ErrorCodeEnum.CONFIRM_OTA_ORDER_ERROR.errorCode);
                    response.setFailReason(hotelOrderOperateResponseDTO.getFailureReason());
                    confirmSB.append("通知代理通取消订单失败");
                }else {
                    confirmSB.append("通知代理通取消订单成功");
                }
            }
        }catch (Exception e) {
            log.error("取消OTA订单失败",e);
            confirmSB.append("通知代理通取消订单失败");
            response.setFailCode(ErrorCodeEnum.CONFIRM_OTA_ORDER_ERROR.errorCode);
            response.setFailReason(ErrorCodeEnum.CONFIRM_OTA_ORDER_ERROR.errorDesc);
        }

        // 7. 记日志
        String content = "取消订单";
        if (request.getRefundFee() != null && BigDecimal.ZERO.compareTo(request.getRefundFee()) < 0) {
            content = content + "，退改费：" + request.getRefundFee();
        }
        if (StringUtil.isValidString(request.getCancelledReason())) {
            content = content + "，取消原因：" + request.getCancelledReason();
        }
        content = content + ";" + confirmSB.toString();
        orderCommonService.saveOrderLog(
                request.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response modifyGuest(ModifyGuestDTO request) {
        log.info("modifyGuest param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());
        String guestNew = StringUtil.listToString(request.getGuestList(), ",");
        if (orderPO.getGuest().equals(guestNew)) {
            response.setResult(ResultCodeEnum.SUCCESS.code);
            return response;
        }

        // 1.更新订单表
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setGuest(guestNew);
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        // 2.更新入住人表
        GuestPO guestDelete = new GuestPO();
        guestDelete.setOrderId(request.getOrderId());
        guestMapper.delete(guestDelete);
        List<GuestPO> guestList = new ArrayList<>();
        for (String guest : request.getGuestList()) {
            GuestPO guestPO = new GuestPO();
            guestPO.setOrderId(request.getOrderId());
            guestPO.setName(guest);
            guestPO.setCreatedBy(request.getOperator());
            guestPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
            guestList.add(guestPO);
        }
        guestMapper.insertList(guestList);

        // 3. 更新供货产品关联入住人
        SupplyProductPO supplyProductUpdate = new SupplyProductPO();
        supplyProductUpdate.setOrderId(request.getOrderId());
        supplyProductUpdate.setGuest(guestNew);
        Example supplyProductExample = new Example(SupplyProductPO.class);
        Example.Criteria supplyProductCriteria = supplyProductExample.createCriteria();
        supplyProductCriteria.andEqualTo("orderId", request.getOrderId());
        supplyProductMapper.updateByExampleSelective(supplyProductUpdate, supplyProductExample);

        // 3. 记日志
        orderCommonService.saveOrderLog(
                request.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                "修改客人：由(" + (StringUtil.isValidString(orderPO.getGuest()) ? orderPO.getGuest() : "--") + ")变更为(" + guestNew + ")"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改房型
     * @param request
     * @return
     */
    @Override
    public int modifyRoom(ModifyRoomDTO request) {
        OrderPO orderPO = new OrderPO();
        orderPO.setId(request.getOrderId());
        OrderPO beforeOrder = orderMapper.selectOne(orderPO);

        orderPO.setRoomId(request.getRoomId());
        orderPO.setRoomName(request.getRoomName());
        int i = orderMapper.insert(orderPO);

        orderCommonService.saveOrderLog(request.getOrderId(), request.getOperator(),
                request.getLoginName(), beforeOrder.getOrderCode(), "将"+ beforeOrder.getRoomName()+ "修改为"+ orderPO.getRoomName());
        return i;
    }

    @Override
    @Transactional
    public Response modifySpecialRequirement(ModifySpecialRequirementDTO request) {
        log.info("modifySpecialRequirement param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());

        // 1. 保存特殊要求
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setSpecialRequest(request.getSpecialRequest());
        orderUpdate.setIsShowOnSupplyOrder(request.getIsShownOnSupplyOrder());
        orderUpdate.setModifiedBy(request.getOperator());
        orderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        // 2. 记日志
        String content = "修改特殊要求："
                + request.getSpecialRequest()
                + ";" + (request.getIsShownOnSupplyOrder() == 1 ? "显示在供货单" : "不显示在供货单");
        orderCommonService.saveOrderLog(
                request.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response modifySalePrice(ModifySalePriceDTO request) {
        log.info("modifySalePrice param: {}", request);
        Response response = new Response();
        StringBuilder logSb = new StringBuilder("修改售价");
        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());
        BigDecimal salePriceTotalAmt = BigDecimal.ZERO;

        Boolean isChange = false;
        OrderProductPricePO orderProductPriceQuery = new OrderProductPricePO();
        orderProductPriceQuery.setOrderId(request.getOrderId());
        List<OrderProductPricePO> orderProductPricePOList = orderProductPriceMapper.select(orderProductPriceQuery);
        for (PriceRequestDTO priceDTO : request.getSalePriceList()) {
            for (OrderProductPricePO orderProductPricePO : orderProductPricePOList) {
                if (DateUtil.compare(DateUtil.stringToDate(priceDTO.getSaleDate()), orderProductPricePO.getSaleDate()) == 0) {
                    if (priceDTO.getSalePrice().compareTo(orderProductPricePO.getSalePrice()) != 0) {
                        logSb.append(",").append(priceDTO.getSaleDate())
                                .append("由").append(orderProductPricePO.getSalePrice())
                                .append("变更为").append(priceDTO.getSalePrice());

                        orderProductPricePO.setSalePrice(priceDTO.getSalePrice());
                        orderProductPriceMapper.updateByPrimaryKeySelective(orderProductPricePO);
                        isChange = true;
                    }
                    break;
                }
            }
            salePriceTotalAmt = salePriceTotalAmt.add(priceDTO.getSalePrice().multiply(BigDecimal.valueOf(orderPO.getRoomQty().doubleValue())));
        }
        if (!isChange) {
            response.setResult(ResultCodeEnum.SUCCESS.code);
            return response;
        }
        BigDecimal changeAmt = salePriceTotalAmt.subtract(orderPO.getSalePrice());
        if (orderPO.getSalePrice().compareTo(salePriceTotalAmt) != 0) {
            logSb.append(",订单总金额由").append(orderPO.getOrderAmt())
                    .append("变更为").append(orderPO.getOrderAmt().add(changeAmt));
        }
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setOrderAmt(orderPO.getOrderAmt().add(changeAmt));
        orderUpdate.setSalePrice(salePriceTotalAmt);
        orderUpdate.setProfit(orderPO.getProfit().add(changeAmt));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        if (changeAmt.compareTo(BigDecimal.ZERO) != 0) {
            //更新订单未结算金额和结算状态
            OrderFinancePO orderFinanceQuery = new OrderFinancePO();
            orderFinanceQuery.setOrderId(request.getOrderId());
            OrderFinancePO orderFinancePO = orderFinanceMapper.selectOne(orderFinanceQuery);
            OrderFinancePO orderFinanceUpdate = new OrderFinancePO();
            orderFinanceUpdate.setId(orderFinancePO.getId());
            orderFinanceUpdate.setUnreceivedAmt(orderFinancePO.getUnreceivedAmt().add(changeAmt));
            if (BigDecimal.ZERO.compareTo(orderFinanceUpdate.getUnreceivedAmt()) == 0) {
                orderFinanceUpdate.setSettlementStatus(1);
            } else {
                orderFinanceUpdate.setSettlementStatus(0);
            }
            orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);

            if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key
                    && orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                //非单结更新对账状态
                if (orderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                    if (BigDecimal.ZERO.compareTo(orderFinanceUpdate.getUnreceivedAmt()) == 0) {
                        //如果未收金额为0，则改为已对账
                        orderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                    } else {
                        //如果未收金额不为0，则改为可出账
                        orderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                    }
                    orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
                }

                // 非单结订单扣退额度
                // TODO: 2019/7/12 调分销商信用账户明细接口，查询已挂账金额
                AgentCreditLineDTO agentCreditLineDTO = new AgentCreditLineDTO();
                agentCreditLineDTO.setAgentCode(orderPO.getAgentCode());
                agentCreditLineDTO.setOrderCode(orderPO.getOrderCode());
                //原订单金额减去修改后金额等于扣退额度i

                if ((orderPO.getOrderAmt().subtract(orderPO.getOrderAmt().add(changeAmt)).compareTo(BigDecimal.ZERO) < 0)) {
                    agentCreditLineDTO.setDeductRefundCreditLine("-" + (orderPO.getOrderAmt().subtract(orderPO.getOrderAmt().add(changeAmt)).toPlainString()));
                } else {
                    agentCreditLineDTO.setDeductRefundCreditLine("+" + (orderPO.getOrderAmt().subtract(orderPO.getOrderAmt().add(changeAmt)).toPlainString()));

                }
                List<AgentCreditLineDTO> agentCreditLineDTOS = new ArrayList<AgentCreditLineDTO>();
                agentCreditLineDTOS.add(agentCreditLineDTO);
                Response creditLine = agentRemote.modifyDeductRefundCreditLine(agentCreditLineDTOS);
                if (creditLine.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                    response.setFailCode(creditLine.getFailCode());
                    response.setFailReason(creditLine.getFailReason());
                    return response;
                }

            }
        }

        //记日志
        orderCommonService.saveOrderLog(
                request.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                logSb.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response addRemark(AddRemarkDTO request) {
        log.info("addRemark param: {}", request);
        Response response = new Response();
        OrderRemarkPO orderRemarkInsert = new OrderRemarkPO();
        BeanUtils.copyProperties(request, orderRemarkInsert);
        if (2 != request.getRemarkType()) {
            orderRemarkInsert.setReceiver(RemarkEnum.getremark(request.getRemarkType()));
        }
        orderRemarkInsert.setCreatedBy(request.getOperator());
        orderRemarkInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderRemarkMapper.insert(orderRemarkInsert);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response saveOrderAttachment(SaveOrderAttachmentDTO request) {
        log.info("saveOrderAttachment param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());

        OrderAttachmentPO orderAttachmentInsert = new OrderAttachmentPO();
        BeanUtils.copyProperties(request, orderAttachmentInsert);
        orderAttachmentInsert.setCreatedBy(request.getOperator());
        orderAttachmentInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderAttachmentMapper.insert(orderAttachmentInsert);

        //记日志(new Date
        orderCommonService.saveOrderLog(
                request.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                "上传订单附件（" + request.getName() + "）成功"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response deleteOrderAttachment(OrderAttachmentIdDTO request) {
        log.info("deleteOrderAttachment param: {}", request);
        Response response = new Response();

        OrderAttachmentPO orderAttachmentPO = orderAttachmentMapper.selectByPrimaryKey(request.getOrderAttachmentId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(orderAttachmentPO.getOrderId());
        orderAttachmentMapper.deleteByPrimaryKey(request.getOrderAttachmentId());

        //记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                "删除订单附件（" + orderAttachmentPO.getName() + "）成功"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response addOrderRequest(AddOrderRequestDTO request) {
        log.info("addOrderRequest param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());

        // 1. 校验
        if (request.getRequestType() == 0) {
            // 1.1 如果有未处理的取消申请，不重复添加取消申请
            OrderRequestPO orderRequestQuery = new OrderRequestPO();
            orderRequestQuery.setOrderId(request.getOrderId());
            orderRequestQuery.setRequestType(request.getRequestType());
            orderRequestQuery.setHandleResult(0);
            List<OrderRequestPO> orderRequestDOS = orderRequestMapper.select(orderRequestQuery);
            if (!CollectionUtils.isEmpty(orderRequestDOS)) {
                response.setResult(ResultCodeEnum.SUCCESS.code);
                return response;
            }
        }

        //修改订单状态
        if(null != orderPO){
            OrderPO orderPOUpdate = new OrderPO();
            orderPOUpdate.setId(orderPO.getId());
            orderPOUpdate.setModificationStatus(request.getRequestType()+1);//订单状态 1取消，2修改，所以加1
            orderMapper.updateByPrimaryKeySelective(orderPOUpdate);
        }


        // 2. 添加订单申请
        OrderRequestPO orderRequestInsert = new OrderRequestPO();
        orderRequestInsert.setOrderId(request.getOrderId());
        orderRequestInsert.setRequestType(request.getRequestType());
        orderRequestInsert.setHandleResult(0);
        orderRequestInsert.setCreatedBy(request.getOperator());
        orderRequestInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderRequestMapper.insert(orderRequestInsert);

        // 3. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                "",
                orderPO.getOrderCode(),
                "添加" + (request.getRequestType().intValue() == 0 ? "取消申请" : "修改申请")
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response handleOrderRequest(HandleOrderRequestDTO request) {
        log.info("handleOrderRequest param: {}", request);
        Response response = new Response();
        OrderRequestPO orderRequestPO = orderRequestMapper.selectByPrimaryKey(request.getOrderRequestId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(orderRequestPO.getOrderId());

        OrderRequestPO orderRequestUpdate = new OrderRequestPO();
        orderRequestUpdate.setId(request.getOrderRequestId());
        orderRequestUpdate.setModifiedBy(request.getOperator());
        orderRequestUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        orderRequestUpdate.setHandleResult(request.getHandledResult());
        orderRequestUpdate.setRemark(request.getRemark());
        orderRequestMapper.updateByPrimaryKeySelective(orderRequestUpdate);

        //记日志
        StringBuilder content = new StringBuilder();
        content.append("处理订单申请：申请类型：" + (orderRequestPO.getRequestType() == 1 ? "取消单申请" : "修改单申请") + "，处理结果：" + (orderRequestPO.getHandleResult() == 1 ? "同意申请" : "拒绝申请"));
        if (StringUtil.isValidString(request.getRemark())) {
            content.append("，备注：" + request.getRemark());
        }
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response modifyOrderRoom(ModifyOrderRoomDTO request) {
        log.info("modifyOrderRoom param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());

        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setRoomName(request.getRoomName());
        orderUpdate.setModifiedBy(request.getOperator());
        orderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        //记日志
        StringBuilder content = new StringBuilder();
        content.append("更新订单房型：由(")
                .append(orderPO.getRoomName())
                .append(")改为(")
                .append(request.getRoomName())
                .append(")");
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response modifyOrderSettlementType(ModifyOrderSettlementTypeDTO request) {
        log.info("modifyOrderSettlementType param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());
        // 1. 校验
        if (orderPO.getSettlementType() == request.getSettlementType()) {
            response.setResult(ResultCodeEnum.SUCCESS.code);
            return response;
        }
        if (request.getSettlementType() != SettlementTypeEnum.SINGLE.key) {
            //单结订单不能改为非单结，非单结只能修改为单结
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason(SettlementTypeEnum.getValueByKey(orderPO.getSettlementType()) + "不能改为" + SettlementTypeEnum.getValueByKey(request.getSettlementType()));
            return response;
        }
        if (orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("订单已确认不能修改结算方式");
            return response;
        }

        // 2. 更新订单结算方式
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setSettlementType(request.getSettlementType());
        orderUpdate.setModifiedBy(request.getOperator());
        orderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        // 3. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                "修改订单结算方式：由(" + SettlementTypeEnum.getValueByKey(orderPO.getSettlementType())
                        + ")改为(" + SettlementTypeEnum.getValueByKey(request.getSettlementType()) + ")"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response lockOrder(LockOrderDTO request) {
        log.info("lockOrder param: {}", request);
        Response response = new Response();

        // 1. 校验
        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());
        if (StringUtil.isValidString(orderPO.getLockUser()) && request.getLockType() == 1) {
            if (orderPO.getLockUser().equals(request.getOperator())) {
                response.setResult(ResultCodeEnum.SUCCESS.code);
                return response;
            } else {
                response.setResult(ResultCodeEnum.FAILURE.code);
                response.setFailReason("订单已被" + orderPO.getLockName() + "锁定");
                return response;
            }
        }

        // 2. 加解锁
        if (request.getLockType() == 1) {
            OrderPO orderUpdate = new OrderPO();
            orderUpdate.setId(request.getOrderId());
            orderUpdate.setLockUser(request.getOperatorUser());
            orderUpdate.setLockName(request.getOperator());
            orderUpdate.setLockTime(new Date());
            orderMapper.updateByPrimaryKeySelective(orderUpdate);
        } else {
            orderPO.setLockUser(null);
            orderPO.setLockName(null);
            orderPO.setLockTime(null);
            orderMapper.updateByPrimaryKey(orderPO);
        }

        // 3. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                request.getLockType() == 1 ? "订单已锁定" : "订单已解锁"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response markOrder(MarkOrderDTO request) {
        log.info("markOrder param: {}", request);
        Response response = new Response();

        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(request.getOrderId());
        orderUpdate.setMarkedStatus(request.getMarkedStatus());
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response modifyChannelOrderCode(Map<String, String> request) {
        log.info("modifyChannelOrderCode param: {}", request);
        Response response = new Response();

        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(Integer.parseInt(request.get("orderId")));
        orderUpdate.setChannelOrderCode(request.get("channelOrderCode"));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
