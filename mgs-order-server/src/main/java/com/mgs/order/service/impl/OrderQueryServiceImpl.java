package com.mgs.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ChanneSortEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.order.constant.OrderTemplate;
import com.mgs.order.domain.GuestPO;
import com.mgs.order.domain.OrderAttachmentPO;
import com.mgs.order.domain.OrderConfirmRecordPO;
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.domain.OrderLogPO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.OrderProductPricePO;
import com.mgs.order.domain.OrderRemarkPO;
import com.mgs.order.domain.OrderRequestPO;
import com.mgs.order.domain.SupplyAttachmentPO;
import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.domain.SupplyProductPO;
import com.mgs.order.domain.SupplyProductPricePO;
import com.mgs.order.domain.SupplyRequestPO;
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
import com.mgs.order.mapper.SupplyAttachmentMapper;
import com.mgs.order.mapper.SupplyOrderFinanceMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.mapper.SupplyProductMapper;
import com.mgs.order.mapper.SupplyProductPriceMapper;
import com.mgs.order.mapper.SupplyRequestMapper;
import com.mgs.order.remote.response.PriceResponseDTO;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.request.QueryConfirmOrderInfoDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderRemarkDTO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.request.SupplyOrderIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.ChannelOrderQtyDTO;
import com.mgs.order.remote.response.OrderAttachmentDTO;
import com.mgs.order.remote.response.OrderDTO;
import com.mgs.order.remote.response.OrderLogDTO;
import com.mgs.order.remote.response.OrderRemarkDTO;
import com.mgs.order.remote.response.OrderRequestCountDTO;
import com.mgs.order.remote.response.OrderRequestDTO;
import com.mgs.order.remote.response.OrderSimpleDTO;
import com.mgs.order.remote.response.OrderStatisticsDTO;
import com.mgs.order.remote.response.SupplyAttachmentDTO;
import com.mgs.order.remote.response.SupplyGuestDTO;
import com.mgs.order.remote.response.SupplyOrderAmt;
import com.mgs.order.remote.response.SupplyOrderDTO;
import com.mgs.order.remote.response.SupplyOrderPreviewDTO;
import com.mgs.order.remote.response.SupplyProductDTO;
import com.mgs.order.remote.response.SupplyProductDetailDTO;
import com.mgs.order.remote.response.SupplyProductPreviewDTO;
import com.mgs.order.remote.response.SupplyProductPriceDTO;
import com.mgs.order.remote.response.SupplyResultDTO;
import com.mgs.order.service.OrderQueryService;
import com.mgs.order.util.StringTemplateUtil;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderConfirmRecordMapper orderConfirmRecordMapper;

    @Autowired
    private OrderProductPriceMapper orderProductPriceMapper;

    @Autowired
    private OrderAttachmentMapper orderAttachmentMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private SupplyProductMapper supplyProductMapper;

    @Autowired
    private SupplyProductPriceMapper supplyProductPriceMapper;

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private OrderRequestMapper orderRequestMapper;

    @Autowired
    private OrderRemarkMapper orderRemarkMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private SupplyRequestMapper supplyRequestMapper;

    @Autowired
    private SupplyAttachmentMapper supplyAttachmentMapper;

    @Autowired
    private HotelRemote hotelRemote;

    @Autowired
    private SupplyOrderFinanceMapper supplyOrderFinanceMapper;

    @Autowired
    private OrderFinanceMapper orderFinanceMapper;

    @Override
    public PaginationSupportDTO<OrderSimpleDTO> queryOrderList(QueryOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<OrderSimpleDTO> list =orderMapper.queryOrderList(request);
        PageInfo<OrderSimpleDTO> page = new PageInfo<OrderSimpleDTO>(list);
        List orderIdList= new ArrayList();
        Map<Integer,BigDecimal> map = new HashMap<>();
        for(OrderSimpleDTO rderSimpleDTO:list){
            orderIdList.add( rderSimpleDTO.getOrderId());
        }

            List<SupplyOrderAmt> baseList = orderMapper.querySupplyOrderAmt(orderIdList);
            for (SupplyOrderAmt supplyOrderAmt : baseList) {
                map.put(supplyOrderAmt.getOrderId(), supplyOrderAmt.getAmt().stripTrailingZeros());
            }

        // 组装响应对象
        Map<Integer,OrderSimpleDTO> orderSimpleDTOMap=new HashMap();
        for (OrderSimpleDTO orderSimpleDTO : list) {
            if (request.getOperator().equals(orderSimpleDTO.getLockName())) {
                orderSimpleDTO.setLockName(null);
            }

            if(null ==map.get(orderSimpleDTO.getOrderId())){
                orderSimpleDTO.setSupplyOrderAmt(BigDecimal.ZERO);
            }else{
                orderSimpleDTO.setSupplyOrderAmt(map.get(orderSimpleDTO.getOrderId()));
            }

            orderSimpleDTO.setOrderTagList(new ArrayList<>());
            if(Math.abs(DateUtil.getDay(DateUtil.stringToDate(orderSimpleDTO.getStartDate()),DateUtil.stringToDate(DateUtil.dateToString(new Date()))))<1){
                orderSimpleDTO.getOrderTagList().add("今");
            }
            orderSimpleDTOMap.put(orderSimpleDTO.getOrderId(),orderSimpleDTO);
        }
        if (orderSimpleDTOMap.size()>0){
            List<OrderRequestCountDTO> orderRequestCountDTOList=orderRequestMapper.queryOrderRequestCount(new ArrayList<>(orderSimpleDTOMap.keySet()));
            for (OrderRequestCountDTO orderRequestCountDTO:orderRequestCountDTOList){
                OrderSimpleDTO orderSimpleDTO=orderSimpleDTOMap.get(orderRequestCountDTO.getOrderId());
                if (orderRequestCountDTO.getCancelCount()>0){
                    orderSimpleDTO.getOrderTagList().add("取");
                }
                if (orderRequestCountDTO.getModifyCount()>0){
                    orderSimpleDTO.getOrderTagList().add("改");
                }
            }
        }

        PaginationSupportDTO<OrderSimpleDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public OrderStatisticsDTO queryOrderStatistics(QueryOrderStatisticsDTO request) {
        //获取未处理订单数、未处理供货单数、我的订单、标星订单数
        OrderStatisticsDTO orderStatisticsDTO=orderMapper.queryOrderStatistics(request);

        //渠道订单列表
        List<ChannelOrderQtyDTO> channelOrderQtyDTOList=orderMapper.queryChannelOrderQty(request);
        Map<String,ChannelOrderQtyDTO> channelOrderQtyDTOMap=new HashMap<>();
        int all=0;
        int ctripSum=0;
        for ( int i=0; i<channelOrderQtyDTOList.size();i++){
            channelOrderQtyDTOMap.put(channelOrderQtyDTOList.get(i).getChannelCode(),channelOrderQtyDTOList.get(i));
            all+=channelOrderQtyDTOList.get(i).getOrderQty();
            if("Ctrip_b2b".equals(channelOrderQtyDTOList.get(i).getChannelCode()) || "Ctrip_channel_a".equals(channelOrderQtyDTOList.get(i).getChannelCode())){
                ctripSum+=channelOrderQtyDTOList.get(i).getOrderQty();
                channelOrderQtyDTOList.remove(i--);
            }

        }

        channelOrderQtyDTOList.add(new ChannelOrderQtyDTO("All",0,all));
        List<String> channelList= Arrays.asList("B2B","B2C","Ctrip","Qunar","Meituan","Feizhu");
        for (String channel:channelList){
            if (!channelOrderQtyDTOMap.containsKey(channel)){
                channelOrderQtyDTOList.add(new ChannelOrderQtyDTO(
                        channel,
                        0,
                        0
                ));
            }
        }

        for(ChannelOrderQtyDTO channelOrderQtyDTO:channelOrderQtyDTOList){
             if("Ctrip".equals(channelOrderQtyDTO.getChannelCode())){
                 channelOrderQtyDTO.setOrderQty(channelOrderQtyDTO.getOrderQty()+ctripSum);
             }
            channelOrderQtyDTO.setChannelNo(ChanneSortEnum.getNoByKey(channelOrderQtyDTO.getChannelCode()));
        }
        java.util.Collections.sort(channelOrderQtyDTOList);
        //今日新单
        Example todayNewOrderQtyExample = new Example(OrderPO.class);
        Example.Criteria todayNewOrderQtyCriteria=todayNewOrderQtyExample.createCriteria();
        todayNewOrderQtyCriteria.andEqualTo("companyCode",request.getCompanyCode());
        todayNewOrderQtyCriteria.andBetween("createdDt", DateUtil.dateToString(new Date()), DateUtil.dateToString(new Date()) + " 23:59:59");
        todayNewOrderQtyCriteria.andIsNull("orderOwnerUser");
        int todayNewOrderQty=orderMapper.selectCountByExample(todayNewOrderQtyExample);

        //今日入住订单数
        Example checkInTodayOrderQtyExample = new Example(OrderPO.class);
        Example.Criteria checkInTodayOrderQtyCriteria=checkInTodayOrderQtyExample.createCriteria();
        checkInTodayOrderQtyCriteria.andEqualTo("companyCode",request.getCompanyCode());
        checkInTodayOrderQtyCriteria.andEqualTo("startDate",DateUtil.dateToString(new Date()));
        int checkInTodayOrderQty=orderMapper.selectCountByExample(checkInTodayOrderQtyExample);

        //明日入住订单数
        Example checkInTomorrowOrderQtyExample = new Example(OrderPO.class);
        Example.Criteria checkInTomorrowOrderQtyCriteria=checkInTomorrowOrderQtyExample.createCriteria();
        checkInTomorrowOrderQtyCriteria.andEqualTo("companyCode",request.getCompanyCode());
        checkInTomorrowOrderQtyCriteria.andEqualTo("startDate",DateUtil.dateToString(DateUtil.getDate(new Date(),1,0)));
        int checkInTomorrowOrderQty=orderMapper.selectCountByExample(checkInTomorrowOrderQtyExample);

        //取消申请数
//        Example cancelledRequestQtyExample = new Example(OrderPO.class);
//        Example.Criteria cancelledRequestQtyCriteria=cancelledRequestQtyExample.createCriteria();
//        cancelledRequestQtyCriteria.andEqualTo("companyCode",request.getCompanyCode());
//        int cancelledRequestQty=orderMapper.selectCountByExample(cancelledRequestQtyExample);
        OrderRequestCountDTO orderRequestCountDTO=orderRequestMapper.queryOrderRequestStatistics(request);
        if (orderRequestCountDTO!=null){
            orderStatisticsDTO.setCancelledRequestQty(orderRequestCountDTO.getCancelCount());
        }
        if(orderStatisticsDTO!=null){
        orderStatisticsDTO.setTodayNewOrderQty(todayNewOrderQty);
        orderStatisticsDTO.setCheckInTodayOrderQty(checkInTodayOrderQty);
//        orderStatisticsDTO.setCancelledRequestQty(cancelledRequestQty);
        orderStatisticsDTO.setCheckInTomorrowOrderQty(checkInTomorrowOrderQty);
        orderStatisticsDTO.setChannelList(channelOrderQtyDTOList);
        }
        return orderStatisticsDTO;
    }

    @Override
    public OrderDTO queryOrderDetail(OrderCodeDTO request) {
        OrderPO orderQuery=new OrderPO();
        orderQuery.setOrderCode(request.getOrderCode());
        OrderPO orderPO=orderMapper.selectOne(orderQuery);
        Integer   SettlementStatus= orderMapper.querySettlementStatus(orderPO.getId());
        if (orderPO!=null){
            OrderDTO orderDTO=new OrderDTO();
            BeanUtils.copyProperties(orderPO,orderDTO);
            orderDTO.setContactTel(orderPO.getContactPhone());
            orderDTO.setSettlementStatus(SettlementStatus);
            orderDTO.setIsShownOnSupplyOrder(orderPO.getIsShowOnSupplyOrder());
            orderDTO.setOrderId(orderPO.getId());
            orderDTO.setStartDate(DateUtil.dateToString(orderPO.getStartDate()));
            orderDTO.setEndDate(DateUtil.dateToString(orderPO.getEndDate()));
            orderDTO.setSupplyOrderTotalAmt(orderDTO.getOrderAmt().subtract(orderDTO.getProfit()));
            orderDTO.setNightQty((int)DateUtil.getDay(orderPO.getStartDate(),orderPO.getEndDate()));
            orderDTO.setConfirmationStatus(orderPO.getOrderConfirmationStatus());

            //查询以收未收，财务锁单状态
            OrderFinancePO  orderFinancePO = new OrderFinancePO();
            orderFinancePO.setOrderId(orderPO.getId());
            OrderFinancePO  orderFinancePO1=  orderFinanceMapper.selectOne(orderFinancePO);
            orderDTO.setUnconfirmedPaidAmt(orderFinancePO1.getUnconfirmedPaidAmt());
            orderDTO.setUnconfirmedReceivedAmt(orderFinancePO1.getUnconfirmedReceivedAmt());
            orderDTO.setUnreceivedAmt(orderFinancePO1.getUnreceivedAmt());
            orderDTO.setReceivedAmt(orderFinancePO1.getReceivedAmt());
            orderDTO.setFinanceLockStatus(orderFinancePO1.getFinanceLockStatus());


            //查询确认人
            Example orderConfirmRecordExample=new Example(OrderConfirmRecordPO.class);
            orderConfirmRecordExample.setOrderByClause("id desc ");
            Example.Criteria orderConfirmRecordCriteria = orderConfirmRecordExample.createCriteria();
            orderConfirmRecordCriteria.andEqualTo("orderId",orderDTO.getOrderId());
            OrderConfirmRecordPO orderConfirmRecordPO=orderConfirmRecordMapper.selectOneByExample(orderConfirmRecordExample);
            if (orderConfirmRecordPO!=null){
                orderDTO.setConfirmer(orderConfirmRecordPO.getCreatedBy());
                orderDTO.setConfirmTime(orderConfirmRecordPO.getCreatedDt());
                orderDTO.setConfirmTime(orderConfirmRecordPO.getCreatedDt());
            }

            //查询订单价格明细
            Example orderProductPriceExample=new Example(OrderProductPricePO.class);
            orderProductPriceExample.setOrderByClause("sale_date");
            Example.Criteria orderProductPriceCriteria = orderProductPriceExample.createCriteria();
            orderProductPriceCriteria.andEqualTo("orderId",orderDTO.getOrderId());
            List<OrderProductPricePO> orderProductPricePOList=orderProductPriceMapper.selectByExample(orderProductPriceExample);
            orderDTO.setSalePriceList(new ArrayList<>());
            orderProductPricePOList.forEach(orderProductPricePO -> {
                orderDTO.getSalePriceList().add(new PriceResponseDTO(
                        DateUtil.dateToString(orderProductPricePO.getSaleDate(),""),
                        orderProductPricePO.getSalePrice(),
                        null
                ));
            });

            //查询订单附件
            OrderAttachmentPO orderAttachmentQuery=new OrderAttachmentPO();
            orderAttachmentQuery.setOrderId(orderDTO.getOrderId());
            List<OrderAttachmentPO> orderAttachmentPOList=orderAttachmentMapper.select(orderAttachmentQuery);
            orderDTO.setOrderAttachmentList(new ArrayList<>());
            orderAttachmentPOList.forEach(orderAttachmentPO -> {
                orderDTO.getOrderAttachmentList().add(new OrderAttachmentDTO(
                        orderAttachmentPO.getId(),
                        orderAttachmentPO.getName(),
                        orderAttachmentPO.getUrl()
                ));
            });
            
            //查询供货单
            SupplyOrderPO supplyOrderQuery=new SupplyOrderPO();
            supplyOrderQuery.setOrderId(orderDTO.getOrderId());
            Example example = new Example(SupplyOrderPO.class);
            example.setOrderByClause("id DESC");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("orderId", orderDTO.getOrderId());
            List<SupplyOrderPO> supplyOrderPOList=supplyOrderMapper.selectByExample(example);

            orderDTO.setSupplyOrderList(new ArrayList<>());
            for (SupplyOrderPO supplyOrderPO:supplyOrderPOList){
                SupplyOrderDTO supplyOrderDTO=new SupplyOrderDTO();
                BeanUtils.copyProperties(supplyOrderPO,supplyOrderDTO);

                //查询结算状态
                SupplyOrderFinancePO supplyOrderFinancePO = new SupplyOrderFinancePO();
                supplyOrderFinancePO.setSupplyOrderId(supplyOrderPO.getId());
                SupplyOrderFinancePO  supplyOrderFinancePO1 =   supplyOrderFinanceMapper.selectOne(supplyOrderFinancePO);
                supplyOrderDTO.setSettlementStatus(supplyOrderFinancePO1.getSettlementStatus());
                supplyOrderDTO.setSupplyOrderId(supplyOrderPO.getId());
                supplyOrderDTO.setUnconfirmedPaidAmt(supplyOrderFinancePO1.getUnconfirmedPaidAmt());
                supplyOrderDTO.setUnconfirmedReceivedAmt(supplyOrderFinancePO1.getUnconfirmedReceivedAmt());
                supplyOrderDTO.setPaidAmt(supplyOrderFinancePO1.getPaidAmt());
                supplyOrderDTO.setUnpaidAmt(supplyOrderFinancePO1.getUnpaidAmt());
                supplyOrderDTO.setFinanceLockStatus(supplyOrderFinancePO1.getFinanceLockStatus());

                //查询供货单价格明细
                List<PriceResponseDTO> basePriceList=supplyProductPriceMapper.querySupplyOrderPriceList(supplyOrderDTO.getSupplyOrderId());
                supplyOrderDTO.setBasePriceList(basePriceList);

                //查询供货单产品
                SupplyProductPO supplyProductQuery=new SupplyProductPO();
                supplyProductQuery.setSupplyOrderId(supplyOrderDTO.getSupplyOrderId());
                List<SupplyProductPO> supplyProductPOList=supplyProductMapper.select(supplyProductQuery);
                supplyOrderDTO.setProductList(new ArrayList<>());
                for (SupplyProductPO supplyProductPO:supplyProductPOList){
                    SupplyProductDTO supplyProductDTO=new SupplyProductDTO();
                    BeanUtils.copyProperties(supplyProductPO,supplyProductDTO);
                    supplyProductDTO.setSupplyProductId(supplyProductPO.getId());
                    supplyProductDTO.setStartDate(DateUtil.dateToString(supplyProductPO.getStartDate()));
                    supplyProductDTO.setEndDate(DateUtil.dateToString(supplyProductPO.getEndDate()));
                    supplyProductDTO.setBasePrice(supplyProductPO.getBasePriceTotalAmt());
                    supplyProductDTO.setNightQty((int)DateUtil.getDay(DateUtil.dateFormat(supplyProductPO.getStartDate(),"yyyy-MM-dd"),DateUtil.dateFormat(supplyProductPO.getEndDate(),"yyyy-MM-dd")));
                    supplyOrderDTO.getProductList().add(supplyProductDTO);
                }
                orderDTO.getSupplyOrderList().add(supplyOrderDTO);
            }

            if (null != request.getOrderOwnerName() && null != orderPO.getOrderOwnerName()) {
                orderDTO.setIsMyOrder((request.getOrderOwnerName().equals(orderPO.getOrderOwnerName())) ? 1 : 0);
            }
            return orderDTO;
        }
        return null;
    }

    @Override
    public List<OrderRemarkDTO> queryOrderRemark(QueryOrderRemarkDTO request) {
        List<OrderRemarkDTO> orderRemarkDTOList=new ArrayList<>();
        Example orderRemarkExample=new Example(OrderRemarkPO.class);
        orderRemarkExample.setOrderByClause("created_dt desc");
        Example.Criteria orderRemarkCriteria=orderRemarkExample.createCriteria();
        orderRemarkCriteria.andEqualTo("orderId",request.getOrderId());
        if (null != request.getRemarkType() && !request.getRemarkType().equals(-1)) {
            orderRemarkCriteria.andEqualTo("remarkType",request.getRemarkType());
        }
        List<OrderRemarkPO> orderRemarkPOList=orderRemarkMapper.selectByExample(orderRemarkExample);
        for (OrderRemarkPO orderRemarkPO:orderRemarkPOList){
            OrderRemarkDTO orderRemarkDTO=new OrderRemarkDTO();
            orderRemarkDTO.setRemark(orderRemarkPO.getRemark());
            orderRemarkDTO.setReceiver(orderRemarkPO.getReceiver());
            orderRemarkDTO.setCreatedBy(orderRemarkPO.getCreatedBy());
            orderRemarkDTO.setCreatedDt(DateUtil.dateToString(DateUtil.stringToDate(orderRemarkPO.getCreatedDt(),"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
            orderRemarkDTOList.add(orderRemarkDTO);
        }
        return orderRemarkDTOList;
    }

    @Override
    public List<OrderLogDTO> queryOrderLog(OrderIdDTO request) {
        List<OrderLogDTO> orderLogDTOList=new ArrayList<>();
        Example orderLogExample=new Example(OrderLogPO.class);
        orderLogExample.setOrderByClause("created_dt");
        Example.Criteria orderLogCriteria=orderLogExample.createCriteria();
        orderLogCriteria.andEqualTo("orderId",request.getOrderId());
        orderLogExample.setOrderByClause("id desc");
        List<OrderLogPO> orderLogPOList=orderLogMapper.selectByExample(orderLogExample);
        for (OrderLogPO orderLogPO:orderLogPOList){
            OrderLogDTO orderLogDTO=new OrderLogDTO();
            orderLogDTO.setContent(orderLogPO.getContent());
            orderLogDTO.setTarget(orderLogPO.getTarget());
            orderLogDTO.setCreatedBy(orderLogPO.getCreatedBy());
            orderLogDTO.setCreatedDt(orderLogPO.getCreatedDt());
            orderLogDTOList.add(orderLogDTO);
        }
        return orderLogDTOList;
    }

    @Override
    public List<OrderRequestDTO> queryOrderRequest(OrderIdDTO request) {
        OrderPO orderPO=orderMapper.selectByPrimaryKey(request.getOrderId());
        List<OrderRequestDTO> orderRequestDTOList=new ArrayList<>();
        Example orderRequestExample=new Example(OrderRequestPO.class);
        orderRequestExample.setOrderByClause("created_dt");
        Example.Criteria orderRequestCriteria=orderRequestExample.createCriteria();
        orderRequestCriteria.andEqualTo("orderId",request.getOrderId());
        List<OrderRequestPO> orderRequestPOList=orderRequestMapper.selectByExample(orderRequestExample);
        for (OrderRequestPO orderRequestPO:orderRequestPOList){
            OrderRequestDTO orderRequestDTO=new OrderRequestDTO();
            orderRequestDTO.setOrderRequestId(orderRequestPO.getId());
            orderRequestDTO.setRequestType(orderRequestPO.getRequestType());
            orderRequestDTO.setHandledResult(orderRequestPO.getHandleResult());
            orderRequestDTO.setRemark(orderRequestPO.getRemark());
            orderRequestDTO.setAgentName(orderPO.getAgentName());
            orderRequestDTO.setCreatedBy(orderRequestPO.getCreatedBy());
            orderRequestDTO.setCreatedDt(orderRequestPO.getCreatedDt());
            orderRequestDTO.setModifiedBy(orderRequestPO.getModifiedBy());
            orderRequestDTO.setModifiedDt(orderRequestPO.getModifiedDt());
            orderRequestDTOList.add(orderRequestDTO);
        }
        return orderRequestDTOList;
    }

    @Override
    public String queryConfirmOrderInfo(QueryConfirmOrderInfoDTO request) {
        OrderPO orderPO=orderMapper.selectByPrimaryKey(request.getOrderId());
        Map map = new HashMap();
        map.put("orderCode", orderPO.getOrderCode());
        map.put("startDate", DateUtil.dateToString(orderPO.getStartDate()));
        map.put("endDate", DateUtil.dateToString(orderPO.getEndDate()));
        map.put("hotelName",orderPO.getHotelName());
        map.put("roomQty",orderPO.getRoomQty());
        map.put("roomName",orderPO.getRoomName());

        //确认模板
        if (request.getConfirmType()==0){
            //查询酒店基本信息接口
            // TODO: 2019/5/16 调酒店基本信息接口

            Map<String, String>  hotel = new HashMap<>();
            hotel.put("hotelId",orderPO.getHotelId().toString());
            Response hotelResponse=  hotelRemote.queryHotelDetail(hotel);
            if (hotelResponse.getResult().equals(ResultCodeEnum.SUCCESS.code)&& null!=hotelResponse.getModel()){
                BasicHotelInfoDTO basicHotelInfoDTO =(BasicHotelInfoDTO) JSON.parseObject(JSONObject.toJSONString(hotelResponse.getModel()),BasicHotelInfoDTO.class);
                map.put("hotelAddress",basicHotelInfoDTO.getHotelAddress());
                map.put("hotelPhone",basicHotelInfoDTO.getHotelTel());
            }

            return StringTemplateUtil.processFreemarker(OrderTemplate.ConfirmedTemplate,map);
        }
        //取消模板
        else if (request.getConfirmType()==1){
            return StringTemplateUtil.processFreemarker(OrderTemplate.CanceledTemplate,map);
        }
        return null;
    }



    @Override
    public List<PriceResponseDTO> queryOrderPriceItem(OrderIdDTO request) {
        List<PriceResponseDTO> priceDTOList=new ArrayList<>();

        Example orderProductPriceExample=new Example(OrderProductPricePO.class);
        orderProductPriceExample.setOrderByClause("sale_date");
        Example.Criteria orderProductPriceCriteria=orderProductPriceExample.createCriteria();
        orderProductPriceCriteria.andEqualTo("orderId",request.getOrderId());
        List<OrderProductPricePO> orderProductPricePOList=orderProductPriceMapper.selectByExample(orderProductPriceExample);
        for (OrderProductPricePO orderProductPricePO:orderProductPricePOList){
            PriceResponseDTO priceDTO=new PriceResponseDTO();
            priceDTO.setSaleDate(DateUtil.dateToString(orderProductPricePO.getSaleDate(),"yyyy-MM-dd HH:mm:ss"));
            priceDTO.setSalePrice(orderProductPricePO.getSalePrice());
            priceDTOList.add(priceDTO);
        }
        return priceDTOList;
    }

    @Override
    public SupplyOrderPreviewDTO previewSupplyOrder(SupplyOrderIdDTO request) {
        SupplyOrderPreviewDTO supplyOrderPreviewDTO=new SupplyOrderPreviewDTO();

        //查询供货单
        SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
        BeanUtils.copyProperties(supplyOrderPO,supplyOrderPreviewDTO);
        supplyOrderPreviewDTO.setSupplyOrderId(request.getSupplyOrderId());

        //查询酒店基本信息
        // TODO: 2019/5/8 调酒店基本信息接口

        Map<String, String>  hotel = new HashMap<>();
        hotel.put("hotelId",supplyOrderPO.getHotelId().toString());
        Response hotelResponse=  hotelRemote.queryHotelDetail(hotel);
        if (hotelResponse.getResult().equals(ResultCodeEnum.SUCCESS.code)&& null!=hotelResponse.getModel()){
            BasicHotelInfoDTO basicHotelInfoDTO =(BasicHotelInfoDTO) JSON.parseObject(JSONObject.toJSONString(hotelResponse.getModel()),BasicHotelInfoDTO.class);
            supplyOrderPreviewDTO.setHotelAddress(basicHotelInfoDTO.getHotelAddress());
            supplyOrderPreviewDTO.setHotelTel(basicHotelInfoDTO.getHotelTel());
        }

        //查询特殊要求
        OrderPO orderPO=orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());
        if (null!=orderPO && orderPO.getIsShowOnSupplyOrder()==1){
            supplyOrderPreviewDTO.setSpecialRequest(orderPO.getSpecialRequest());
        }

        //查询产品
        Set<String> guestSet=new HashSet<>();
        Map<Integer,SupplyProductPO> supplyProductPOMap=new HashMap<>();
        SupplyProductPO supplyProductQuery=new SupplyProductPO();
        supplyProductQuery.setSupplyOrderId(request.getSupplyOrderId());
        List<SupplyProductPO> supplyProductPOList=supplyProductMapper.select(supplyProductQuery);
        for (SupplyProductPO supplyProductPO:supplyProductPOList){
            supplyProductPOMap.put(supplyProductPO.getId(),supplyProductPO);
            //过滤重复的入住人
            if (StringUtil.isValidString(supplyProductPO.getGuest())){
                if (supplyProductPO.getGuest().indexOf("、")!=-1){
                    guestSet.addAll(Collections.arrayToList(supplyProductPO.getGuest().split("、")));
                }else{
                    guestSet.add(supplyProductPO.getGuest());
                }
            }
        }
        supplyOrderPreviewDTO.setGuest(StringUtil.listToString(guestSet,","));

        //查询价格明细
        supplyOrderPreviewDTO.setProductList(new ArrayList<>());
        Example supplyProductPriceExample=new Example(SupplyProductPricePO.class);
        supplyProductPriceExample.setOrderByClause("supply_product_id,sale_date");
        Example.Criteria supplyProductPriceCriteria=supplyProductPriceExample.createCriteria();
        supplyProductPriceCriteria.andEqualTo("supplyOrderId",request.getSupplyOrderId());
        List<SupplyProductPricePO> supplyProductPricePOList=supplyProductPriceMapper.selectByExample(supplyProductPriceExample);
        Integer lastSupplyProductId=null;
        BigDecimal lastBasePrice=BigDecimal.ZERO;
        Integer nightQty=null;
        SupplyProductPreviewDTO currSupplyProductPreviewDTO=null;
        for (SupplyProductPricePO supplyProductPricePO:supplyProductPricePOList){
            if (supplyProductPricePO.getSupplyProductId()!=lastSupplyProductId
                    || supplyProductPricePO.getBasePrice().compareTo(lastBasePrice)!=0){
                nightQty=1;
                SupplyProductPO supplyProductPO=supplyProductPOMap.get(supplyProductPricePO.getSupplyProductId());
                SupplyProductPreviewDTO supplyProductPreviewDTO=new SupplyProductPreviewDTO();
                supplyProductPreviewDTO.setRoomName(supplyProductPO.getRoomName());
                supplyProductPreviewDTO.setProductName(supplyProductPO.getProductName());
                supplyProductPreviewDTO.setStartDate(DateUtil.dateToString(supplyProductPricePO.getSaleDate()));
                supplyProductPreviewDTO.setEndDate(DateUtil.dateToString(supplyProductPricePO.getSaleDate()));
                supplyProductPreviewDTO.setNightQty(nightQty);
                supplyProductPreviewDTO.setRoomQty(supplyProductPO.getRoomQty());
                supplyProductPreviewDTO.setBasePrice(supplyProductPricePO.getBasePrice());
                supplyProductPreviewDTO.setTotalAmt(supplyProductPricePO.getBasePrice()
                        .multiply(BigDecimal.valueOf(supplyProductPO.getRoomQty()))
                        .multiply(BigDecimal.valueOf(nightQty)));
                supplyOrderPreviewDTO.getProductList().add(supplyProductPreviewDTO);
                currSupplyProductPreviewDTO=supplyProductPreviewDTO;
            }else {
                nightQty++;
                currSupplyProductPreviewDTO.setEndDate(DateUtil.dateToString(DateUtil.getDate(DateUtil.stringToDate(currSupplyProductPreviewDTO.getStartDate()),1,0)));
                currSupplyProductPreviewDTO.setNightQty(nightQty);
                currSupplyProductPreviewDTO.setTotalAmt(currSupplyProductPreviewDTO.getBasePrice()
                        .multiply(BigDecimal.valueOf(currSupplyProductPreviewDTO.getRoomQty()))
                        .multiply(BigDecimal.valueOf(nightQty)));
            }
            lastSupplyProductId=supplyProductPricePO.getSupplyProductId();
            lastBasePrice=supplyProductPricePO.getBasePrice();
        }
        return supplyOrderPreviewDTO;
    }

    @Override
    public SupplyResultDTO querySupplyOrderResult(SupplyOrderIdDTO request) {
        //查询最新供货结果
        Example supplyRequestExample=new Example(SupplyRequestPO.class);
        supplyRequestExample.setOrderByClause("created_dt desc");
        Example.Criteria supplyRequestCriteria=supplyRequestExample.createCriteria();
        supplyRequestCriteria.andEqualTo("supplyOrderId",request.getSupplyOrderId());
        supplyRequestCriteria.andIsNotNull("thisConfirmationStatus");
        List<SupplyRequestPO> supplyRequestPOList=supplyRequestMapper.selectByExample(supplyRequestExample);
        SupplyResultDTO supplyResultDTO=new SupplyResultDTO();
        supplyResultDTO.setSupplyOrderId(request.getSupplyOrderId());
        if (supplyRequestPOList.size()==0){
            SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
            supplyResultDTO.setConfirmationStatus(supplyOrderPO.getConfirmationStatus());
            supplyResultDTO.setConfirmationCode(supplyOrderPO.getConfirmationCode());
            supplyResultDTO.setSupplierConfirmer(supplyOrderPO.getSupplierConfirmer());
            supplyResultDTO.setRefundFee(supplyOrderPO.getRefundFee());
            supplyResultDTO.setRefusedReason(supplyOrderPO.getRefusedReason());
            supplyResultDTO.setRemark(supplyOrderPO.getConfirmationRemark());
            supplyResultDTO.setOperatedBy(supplyOrderPO.getModifiedBy());
            supplyResultDTO.setOperatedTime(supplyOrderPO.getModifiedDt());
            //查询供货单附件
            supplyResultDTO.setSupplyAttachmentList(new ArrayList<>());
            SupplyAttachmentPO supplyAttachmentQuery=new SupplyAttachmentPO();
            supplyAttachmentQuery.setSupplyOrderId(request.getSupplyOrderId());
            List<SupplyAttachmentPO> supplyAttachmentPOList=supplyAttachmentMapper.select(supplyAttachmentQuery);
            for (SupplyAttachmentPO supplyAttachmentPO:supplyAttachmentPOList){
                SupplyAttachmentDTO supplyAttachmentDTO=new SupplyAttachmentDTO();
                supplyAttachmentDTO.setSupplyAttachmentId(supplyAttachmentPO.getId());
                supplyAttachmentDTO.setName(supplyAttachmentPO.getName());
                supplyAttachmentDTO.setUrl(supplyAttachmentPO.getUrl());
                supplyResultDTO.getSupplyAttachmentList().add(supplyAttachmentDTO);
            }
            return supplyResultDTO;
        }
        supplyResultDTO.setConfirmationStatus(supplyRequestPOList.get(0).getThisConfirmationStatus());
        supplyResultDTO.setConfirmationCode(supplyRequestPOList.get(0).getThisConfirmationCode());
        supplyResultDTO.setSupplierConfirmer(supplyRequestPOList.get(0).getThisSupplierConfirmer());
        supplyResultDTO.setRefundFee(supplyRequestPOList.get(0).getThisRefundFee());
        supplyResultDTO.setRefusedReason(supplyRequestPOList.get(0).getThisRefusedReason());
        supplyResultDTO.setRemark(supplyRequestPOList.get(0).getThisConfirmationRemark());
        supplyResultDTO.setOperatedBy(supplyRequestPOList.get(0).getModifiedBy());
        supplyResultDTO.setOperatedTime(supplyRequestPOList.get(0).getModifiedDt());

        //查询供货单附件
        supplyResultDTO.setSupplyAttachmentList(new ArrayList<>());
        SupplyAttachmentPO supplyAttachmentQuery=new SupplyAttachmentPO();
        supplyAttachmentQuery.setSupplyOrderId(request.getSupplyOrderId());
        List<SupplyAttachmentPO> supplyAttachmentPOList=supplyAttachmentMapper.select(supplyAttachmentQuery);
        for (SupplyAttachmentPO supplyAttachmentPO:supplyAttachmentPOList){
            SupplyAttachmentDTO supplyAttachmentDTO=new SupplyAttachmentDTO();
            supplyAttachmentDTO.setSupplyAttachmentId(supplyAttachmentPO.getId());
            supplyAttachmentDTO.setName(supplyAttachmentPO.getName());
            supplyAttachmentDTO.setUrl(supplyAttachmentPO.getUrl());
            supplyResultDTO.getSupplyAttachmentList().add(supplyAttachmentDTO);
        }
        return supplyResultDTO;
    }

    @Override
    public SupplyProductDetailDTO querySupplyProduct(SupplyProductIdDTO request) {
        SupplyProductDetailDTO supplyProductDetailDTO=new SupplyProductDetailDTO();
        SupplyProductPO supplyProductPO=supplyProductMapper.selectByPrimaryKey(request.getSupplyProductId());
        BeanUtils.copyProperties(supplyProductPO,supplyProductDetailDTO);
        supplyProductDetailDTO.setSupplyProductId(request.getSupplyProductId());
        supplyProductDetailDTO.setBasePrice(supplyProductPO.getBasePriceTotalAmt());
        supplyProductDetailDTO.setStartDate(DateUtil.dateToString(supplyProductPO.getStartDate()));
        supplyProductDetailDTO.setEndDate(DateUtil.dateToString(supplyProductPO.getEndDate()));
        SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectByPrimaryKey(supplyProductPO.getSupplyOrderId());
        supplyProductDetailDTO.setSupplierName(supplyOrderPO.getSupplierName());
        supplyProductDetailDTO.setSettlementCurrency(supplyOrderPO.getBaseCurrency());
        supplyProductDetailDTO.setBaseCurrency(supplyOrderPO.getBaseCurrency());
        supplyProductDetailDTO.setExchangeBasePrice(supplyProductPO.getBasePriceTotalAmt().multiply(supplyOrderPO.getRate()));

        //查询入住人
        supplyProductDetailDTO.setGuestList(new ArrayList<>());
        GuestPO guestQuery=new GuestPO();
        guestQuery.setOrderId(supplyOrderPO.getOrderId());
        List<GuestPO> guestPOList=guestMapper.select(guestQuery);
        for (GuestPO guestPO:guestPOList){
            SupplyGuestDTO supplyGuestDTO=new SupplyGuestDTO();
            supplyGuestDTO.setGuest(guestPO.getName());
            if (supplyProductPO.getGuest().indexOf(guestPO.getName())!=-1){
                supplyGuestDTO.setSelected(1);
            }
            supplyProductDetailDTO.getGuestList().add(supplyGuestDTO);
        }

        //查询产品明细
        SupplyProductPricePO supplyProductPriceQuery=new SupplyProductPricePO();
        supplyProductPriceQuery.setSupplyProductId(request.getSupplyProductId());
        List<SupplyProductPricePO> supplyProductPricePOList=supplyProductPriceMapper.select(supplyProductPriceQuery);
        supplyProductDetailDTO.setPriceList(new ArrayList<>());
        for (SupplyProductPricePO supplyProductPricePO:supplyProductPricePOList){
            SupplyProductPriceDTO supplyProductPriceDTO=new SupplyProductPriceDTO();
            BeanUtils.copyProperties(supplyProductPricePO,supplyProductPriceDTO);
            supplyProductPriceDTO.setSaleDate(DateUtil.dateToString(supplyProductPricePO.getSaleDate()));
            supplyProductDetailDTO.getPriceList().add(supplyProductPriceDTO);
        }
        return supplyProductDetailDTO;
    }
}
