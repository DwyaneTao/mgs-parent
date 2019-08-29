package com.mgs.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgs.common.Response;
import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisBaseQueryDTO;
import com.mgs.dis.remote.DisAgentRemote;
import com.mgs.enums.ChannelEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SettlementTypeEnum;
import com.mgs.finance.enums.CheckStatusEnum;
import com.mgs.finance.remote.ExchangeRateRemote;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.order.domain.GuestPO;
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.OrderProductPricePO;
import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.domain.SupplyProductPO;
import com.mgs.order.domain.SupplyProductPricePO;
import com.mgs.order.dto.AssemblyOrderDTO;
import com.mgs.order.dto.AssemblySupplyOrderDTO;
import com.mgs.order.dto.AssemblySupplyProductDTO;
import com.mgs.order.enums.ConfirmationStatusEnum;
import com.mgs.order.enums.SendingStatusEnum;
import com.mgs.order.mapper.GuestMapper;
import com.mgs.order.mapper.OrderFinanceMapper;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.mapper.OrderProductPriceMapper;
import com.mgs.order.mapper.SupplyOrderFinanceMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.mapper.SupplyProductMapper;
import com.mgs.order.mapper.SupplyProductPriceMapper;
import com.mgs.order.remote.request.AddManualOrderDTO;
import com.mgs.order.service.BookingService;
import com.mgs.organization.remote.AgentRemote;
import com.mgs.organization.remote.SupplierRemote;
import com.mgs.organization.remote.dto.*;
import com.mgs.product.dto.ProductSalePriceDTO;
import com.mgs.product.dto.ProductSalePriceItemDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.QuotaDTO;
import com.mgs.product.remote.ProductSaleRemote;
import com.mgs.product.remote.QuotaRemote;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mgs.util.DateUtil.hour_format;


@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private OrderProductPriceMapper orderProductPriceMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private SupplyProductMapper supplyProductMapper;

    @Autowired
    private SupplyProductPriceMapper supplyProductPriceMapper;

    @Autowired
    private OrderFinanceMapper orderFinanceMapper;

    @Autowired
    private SupplyOrderFinanceMapper supplyOrderFinanceMapper;

    @Autowired
    private AgentRemote agentRemote;

    @Autowired
    private SupplierRemote supplierRemote;

    @Autowired
    private HotelRemote hotelRemote;

    @Autowired
    private ExchangeRateRemote exchangeRateRemote;

    @Autowired
    private QuotaRemote quotaRemote;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DisAgentRemote disAgentRemote;

    @Autowired
    private ProductSaleRemote productSaleRemote;


    @Override
    @Transactional
    public Response addManualOrder(AddManualOrderDTO request) {
        log.info("addManualOrder param: {}", JSON.toJSONString(request));
        Response response = new Response();
        //获取渠道客户信息
        if(!StringUtil.isValidString(request.getAgentCode())){
            DisBaseQueryDTO disBaseQueryDTO =new DisBaseQueryDTO();
            disBaseQueryDTO.setChannelCode(request.getChannelCode().toLowerCase());
            List<DisAgentDTO> disAgentDTO =  disAgentRemote.queryAgentListByParam(disBaseQueryDTO);
            if(0 == disAgentDTO.size()){
                response.setResult(ResultCodeEnum.SUCCESS.code);
                response.setFailReason(request.getChannelCode()+"此渠道暂未开通！");
                return  response;
            }
            request.setAgentCode(disAgentDTO.get(0).getAgentCode());
            request.setAgentName(disAgentDTO.get(0).getAgentName());
        }

        //查询分销商
        AgentAddDTO agentAddDTO = new AgentAddDTO();
        agentAddDTO.setAgentCode(request.getAgentCode());
        Response agentCompany = agentRemote.queryAgentDetail(agentAddDTO);
        AgentSelectDTO agentSelectDTO = new AgentSelectDTO();
        if (agentCompany.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != agentCompany.getModel()) {
            agentSelectDTO = (AgentSelectDTO) JSON.parseObject(JSONObject.toJSONString(agentCompany.getModel()), AgentSelectDTO.class);
            if (!ChannelEnum.B2C.key.equals(request.getChannelCode()) && SettlementTypeEnum.SINGLE.key != agentSelectDTO.getSettlementType()) {//单结,判断剩余额度是否足够
                BigDecimal balance = new BigDecimal(agentSelectDTO.getBalance());
                if (request.getOrderAmt().compareTo(balance) == 1) {//订单金额小于信用额，提示信用额不足
                    response.setResult(ResultCodeEnum.SUCCESS.code);
                    response.setFailReason("信用额度不足,无法下单！");
                    return response;
                }
            }
        }


        //组装订单数据
        AssemblyOrderDTO assemblyOrderDTO = this.assemblyOrderData(request, agentSelectDTO.getSettlementType());
        //保存订单
        Integer orderId = this.saveOrder(assemblyOrderDTO);
        //保配额
        try {
            // TODO: 2019/5/6 调扣配额接口
            if (request.getProductId() != null) {
                QuotaDTO quotaDTO = new QuotaDTO();
                quotaDTO.setOrderCode(assemblyOrderDTO.getOrder().getOrderCode());
                quotaDTO.setOrderId(orderId);
                quotaDTO.setProductId(request.getProductId());
                quotaDTO.setSupplierCode(request.getSupplierCode());
                quotaDTO.setQuota(-request.getRoomQty());//扣配额
                quotaDTO.setSupplyOrderCode(assemblyOrderDTO.getSupplyOrderList().get(0).getSupplyOrder().getSupplyOrderCode());
                quotaDTO.setSupplyOrderId(assemblyOrderDTO.getSupplyOrderList().get(0).getSupplyOrder().getId());
                Map<String, String> map = new HashMap<>();
                map.put("begin", request.getStartDate());
                map.put("end", request.getEndDate());
                List<String> list = orderMapper.queryBetweenDate(map);
                StringBuilder saleDate = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    saleDate.append(list.get(i)).append(",");
                }
                quotaDTO.setSaleDate(saleDate.substring(0, saleDate.length() - 1));
                Response quota = quotaRemote.modifyQuota(quotaDTO);
            }
        } catch (Exception e) {
            log.error("扣配额异常！订单ID："+orderId,e);
        }


        OrderPO orderPO = orderMapper.selectByPrimaryKey(orderId);

        // 非单结自动扣额度
        // TODO: 2019/7/14 扣额度
        if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key) {
            AgentCreditLineDTO agentCreditLineDTO = new AgentCreditLineDTO();
            agentCreditLineDTO.setAgentCode(orderPO.getAgentCode());
            agentCreditLineDTO.setOrderCode(orderPO.getOrderCode());
            agentCreditLineDTO.setDeductRefundCreditLine("-" + orderPO.getOrderAmt());
            agentCreditLineDTO.setCreatedBy(request.getOperator());
            List<AgentCreditLineDTO> agentCreditLineDTOS = new ArrayList<AgentCreditLineDTO>();
            agentCreditLineDTOS.add(agentCreditLineDTO);
            Response creditLine = agentRemote.modifyDeductRefundCreditLine(agentCreditLineDTOS);
            if (creditLine.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                response.setFailCode(creditLine.getFailCode());
                response.setFailReason(creditLine.getFailReason());
                return response;
            }
        }

        StringBuilder  message =new StringBuilder();
        message.append("all/订单号：").append(orderPO.getOrderCode()+"<br>");
        message.append("客户-").append(agentSelectDTO.getAgentName());
        message.append("下了一条").append(request.getStartDate()).append("入住").append(request.getHotelName());
        message.append(request.getRoomQty()).append("间").append(DateUtil.getDay(orderPO.getStartDate(),orderPO.getEndDate())).append("晚的订单,请尽快处理!");
        stringRedisTemplate.convertAndSend(request.getCompanyCode()+"9",message.toString());

        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderPO.getOrderCode());
        return response;
    }

    @Override
    @Transactional
    public Response addOTAOrder(AddManualOrderDTO request) {
        log.info("addOTAOrder param: {}", JSON.toJSONString(request));
        Response response = new Response();
        //获取渠道客户信息
        if(!StringUtil.isValidString(request.getAgentCode())){
            DisBaseQueryDTO disBaseQueryDTO =new DisBaseQueryDTO();
            disBaseQueryDTO.setChannelCode(request.getChannelCode().toLowerCase());
            List<DisAgentDTO> disAgentDTO =  disAgentRemote.queryAgentListByParam(disBaseQueryDTO);
            if(0 == disAgentDTO.size()){
                response.setResult(ResultCodeEnum.SUCCESS.code);
                response.setFailReason(request.getChannelCode()+"此渠道暂未开通！");
                return  response;
            }
            request.setAgentCode(disAgentDTO.get(0).getAgentCode());
            request.setAgentName(disAgentDTO.get(0).getAgentName());
        }

        //查询分销商
        AgentAddDTO agentAddDTO = new AgentAddDTO();
        agentAddDTO.setAgentCode(request.getAgentCode());
        Response agentCompany = agentRemote.queryAgentDetail(agentAddDTO);
        AgentSelectDTO agentSelectDTO = new AgentSelectDTO();
        if (agentCompany.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != agentCompany.getModel()) {
            agentSelectDTO = (AgentSelectDTO) JSON.parseObject(JSONObject.toJSONString(agentCompany.getModel()), AgentSelectDTO.class);
            if (!ChannelEnum.B2C.key.equals(request.getChannelCode()) && SettlementTypeEnum.SINGLE.key != agentSelectDTO.getSettlementType()) {//单结,判断剩余额度是否足够
                BigDecimal balance = new BigDecimal(agentSelectDTO.getBalance());
                if (request.getOrderAmt().compareTo(balance) == 1) {//订单金额小于信用额，提示信用额不足
                    response.setResult(ResultCodeEnum.SUCCESS.code);
                    response.setFailReason("信用额度不足,无法下单！");
                    return response;
                }
            }
        }

        //调用产品接口，查询底价
        QueryProductRequestDTO queryProductRequestDTO = new QueryProductRequestDTO();
        queryProductRequestDTO.setProductId(request.getProductId());
        queryProductRequestDTO.setStartDate(request.getStartDate());
        queryProductRequestDTO.setEndDate(DateUtil.dateToString(DateUtil.getDate(DateUtil.stringToDate(request.getEndDate()),-1,0)));
        queryProductRequestDTO.setCompanyCode(request.getCompanyCode());
        queryProductRequestDTO.setChannelCode(request.getChannelCode());
        Response productResponse = productSaleRemote.querySalePriceList(queryProductRequestDTO);
        ProductSalePriceDTO productSalePriceDTO = null;
        String supplierCode = null;
        if (null != productResponse && productResponse.getResult().equals(ResultCodeEnum.SUCCESS.code)
                && null != productResponse.getModel()) {
            productSalePriceDTO = JSON.parseObject(JSONObject.toJSONString(productResponse.getModel()), ProductSalePriceDTO.class);
            supplierCode = productSalePriceDTO.getSupplierCode();
        }else {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("产品不存在,无法下单！");
            return response;
        }

        //组装订单数据
        AssemblyOrderDTO assemblyOrderDTO = this.assemblyOTAOrderData(request,productSalePriceDTO, agentSelectDTO.getSettlementType());
        //保存订单
        Integer orderId = this.saveOrder(assemblyOrderDTO);
        //保配额
        try {
            // TODO: 2019/5/6 调扣配额接口
            if (request.getProductId() != null) {
                QuotaDTO quotaDTO = new QuotaDTO();
                quotaDTO.setOrderCode(assemblyOrderDTO.getOrder().getOrderCode());
                quotaDTO.setOrderId(orderId);
                quotaDTO.setProductId(request.getProductId());
                quotaDTO.setSupplierCode(supplierCode);
                quotaDTO.setQuota(-request.getRoomQty());//扣配额
                quotaDTO.setSupplyOrderCode(assemblyOrderDTO.getSupplyOrderList().get(0).getSupplyOrder().getSupplyOrderCode());
                quotaDTO.setSupplyOrderId(assemblyOrderDTO.getSupplyOrderList().get(0).getSupplyOrder().getId());
                Map<String, String> map = new HashMap<>();
                map.put("begin", request.getStartDate());
                map.put("end", request.getEndDate());
                List<String> list = orderMapper.queryBetweenDate(map);
                StringBuilder saleDate = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    saleDate.append(list.get(i)).append(",");
                }
                quotaDTO.setSaleDate(saleDate.substring(0, saleDate.length() - 1));
                Response quota = quotaRemote.modifyQuota(quotaDTO);
            }
        } catch (Exception e) {
            log.error("扣配额异常！订单ID："+orderId,e);
        }


        OrderPO orderPO = orderMapper.selectByPrimaryKey(orderId);

        // 非单结自动扣额度
        // TODO: 2019/7/14 扣额度
        if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key) {
            AgentCreditLineDTO agentCreditLineDTO = new AgentCreditLineDTO();
            agentCreditLineDTO.setAgentCode(orderPO.getAgentCode());
            agentCreditLineDTO.setOrderCode(orderPO.getOrderCode());
            agentCreditLineDTO.setDeductRefundCreditLine("-" + orderPO.getOrderAmt());
            agentCreditLineDTO.setCreatedBy(request.getOperator());
            List<AgentCreditLineDTO> agentCreditLineDTOS = new ArrayList<AgentCreditLineDTO>();
            agentCreditLineDTOS.add(agentCreditLineDTO);
            Response creditLine = agentRemote.modifyDeductRefundCreditLine(agentCreditLineDTOS);
            if (creditLine.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                response.setFailCode(creditLine.getFailCode());
                response.setFailReason(creditLine.getFailReason());
                return response;
            }
        }

        StringBuilder  message =new StringBuilder();
        message.append("all/订单号：").append(orderPO.getOrderCode()+"<br>");
        message.append("客户-").append(agentSelectDTO.getAgentName());
        message.append("下了一条").append(request.getStartDate()).append("入住").append(request.getHotelName());
        message.append(request.getRoomQty()).append("间").append(DateUtil.getDay(orderPO.getStartDate(),orderPO.getEndDate())).append("晚的订单,请尽快处理!");
        stringRedisTemplate.convertAndSend(request.getCompanyCode()+"9",message.toString());

        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderPO.getOrderCode());
        return response;
    }

    private AssemblyOrderDTO assemblyOrderData(AddManualOrderDTO request, Integer settlementType) {

        BigDecimal rate=null;
        //查询汇率
        // TODO: 2019/5/6 调汇率接口
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setCurrency(request.getBaseCurrency());
        if (request.getBaseCurrency() == 0) {//0为人民币
            rate=BigDecimal.ONE;
        } else {
            Response ExchangeRate = exchangeRateRemote.queryExchangeRate(exchangeRateDTO);
            if (ExchangeRate.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != ExchangeRate.getModel()) {
                JSONArray jsonArray = (JSONArray) JSON.parseArray(JSONObject.toJSONString(ExchangeRate.getModel()));
                Iterator<Object> it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject arrayObj = (JSONObject) it.next();
                    rate = new BigDecimal(arrayObj.get("reversedExchangeRate").toString()).setScale(2,BigDecimal.ROUND_CEILING);
                }
            }
        }


        //组装订单
        AssemblyOrderDTO assemblyOrderDTO = new AssemblyOrderDTO();
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(request, orderPO);
        orderPO.setStartDate(DateUtil.stringToDate(request.getStartDate()));
        orderPO.setEndDate(DateUtil.stringToDate(request.getEndDate()));
        orderPO.setIsManualOrder(1);
        orderPO.setIsSubstituted(1);
        orderPO.setIsShowOnSupplyOrder(1);
        orderPO.setOrderConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
        orderPO.setSupplyOrderConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key.toString());
        orderPO.setSalePrice(request.getOrderAmt());
        orderPO.setRefundFee(BigDecimal.ZERO);
        orderPO.setMarkedStatus(0);
        orderPO.setModificationStatus(0);
        orderPO.setCreatedBy(request.getOperator());
        orderPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderPO.setProfit(request.getOrderAmt().subtract(request.getSupplyOrderAmt().multiply(rate).setScale(2,BigDecimal.ROUND_UP)));
        orderPO.setInstantConfimationStatus(1);
        //查询酒店基本信息
        // TODO: 2019/5/6 调酒店基本信息接口
        Map<String, String> hotel = new HashMap<>();
        hotel.put("hotelId", request.getHotelId().toString());
        Response hotelResponse = hotelRemote.queryHotelDetail(hotel);
        if (hotelResponse.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != hotelResponse.getModel()) {
            BasicHotelInfoDTO basicHotelInfoDTO = (BasicHotelInfoDTO) JSON.parseObject(JSONObject.toJSONString(hotelResponse.getModel()), BasicHotelInfoDTO.class);
            orderPO.setCityCode(basicHotelInfoDTO.getCityCode());
            orderPO.setCityName(basicHotelInfoDTO.getCityName());
        }

        //查询分销商
        // TODO: 2019/5/6 调分销商接口
        if (ChannelEnum.B2C.key.equals(request.getChannelCode())) {
            orderPO.setSettlementType(SettlementTypeEnum.SINGLE.key);
        } else {
            if (settlementType != null) {
                orderPO.setSettlementType(settlementType);
            } else {
                orderPO.setSettlementType(SettlementTypeEnum.MONTH.key);
            }
        }

        AgentAddDTO agentAddDTO = new AgentAddDTO();
        agentAddDTO.setAgentCode(request.getAgentCode());
        Response response = agentRemote.queryAgentDetail(agentAddDTO);
        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response.getModel()) {
            AgentSelectDTO agentSelectDTO = (AgentSelectDTO) JSON.parseObject(JSONObject.toJSONString(response.getModel()), AgentSelectDTO.class);
            orderPO.setMerchantBm(agentSelectDTO.getSaleManagerId().toString());
            if (null != agentSelectDTO.getContactList()) {
                for (int i = 0; i < agentSelectDTO.getContactList().size(); i++) {
                    if (agentSelectDTO.getContactList().get(i).getContactRole().equals(1) || agentSelectDTO.getContactList().get(i).getContactRole().equals("0,1")) {
                        orderPO.setContactName(agentSelectDTO.getContactList().get(i).getContactName());
                        orderPO.setContactPhone(agentSelectDTO.getContactList().get(i).getContactTel());
                    } else {
                        orderPO.setContactName(agentSelectDTO.getContactList().get(0).getContactName());
                        orderPO.setContactPhone(agentSelectDTO.getContactList().get(0).getContactTel());
                    }
                }
            }else{
                orderPO.setContactPhone(agentSelectDTO.getAgentTel());
            }
        }

        assemblyOrderDTO.setOrder(orderPO);

        OrderFinancePO orderFinancePO = new OrderFinancePO();
        orderFinancePO.setReceivedAmt(BigDecimal.ZERO);
        orderFinancePO.setUnreceivedAmt(orderPO.getOrderAmt());
        orderFinancePO.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        orderFinancePO.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        orderFinancePO.setSettlementStatus(0);
        orderFinancePO.setSettlementDate(DateUtil.stringToDate(DateUtil.dateToString(new Date())));
        orderFinancePO.setCheckStatus(CheckStatusEnum.NEW.key);
        orderFinancePO.setFinanceLockStatus(0);
        orderFinancePO.setCreatedBy(request.getOperator());
        orderFinancePO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        assemblyOrderDTO.setOrderFinance(orderFinancePO);

        //设置入住人
        StringBuilder guestSb = new StringBuilder();
        if (request.getGuestList().size() > 0) {
            assemblyOrderDTO.setGuestList(new ArrayList<>());
            for (String guest : request.getGuestList()) {
                guestSb.append(guest).append("、");
                GuestPO guestPO = new GuestPO();
                guestPO.setName(guest);
                assemblyOrderDTO.getGuestList().add(guestPO);
            }
            guestSb.deleteCharAt(guestSb.length() - 1);
        }
        orderPO.setGuest(guestSb.toString());

        //组装供货单
        AssemblySupplyOrderDTO assemblySupplyOrderDTO = new AssemblySupplyOrderDTO();
        assemblyOrderDTO.setSupplyOrderList(Arrays.asList(assemblySupplyOrderDTO));
        SupplyOrderPO supplyOrderPO = new SupplyOrderPO();
        BeanUtils.copyProperties(request, supplyOrderPO);
        supplyOrderPO.setBasePrice(request.getSupplyOrderAmt());
        supplyOrderPO.setRefundFee(BigDecimal.ZERO);
        supplyOrderPO.setStartDate(orderPO.getStartDate());
        supplyOrderPO.setEndDate(orderPO.getEndDate());
        supplyOrderPO.setConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
        supplyOrderPO.setSendingStatus(SendingStatusEnum.UNSEND.key);
        supplyOrderPO.setCreatedBy(request.getOperator());
        supplyOrderPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
//        supplyOrderPO.setCityCode();
//        supplyOrderPO.setCityName();

        //查询供应商
        // TODO: 2019/5/6 调供应商接口
        SupplierAddDTO supplierAddDTO = new SupplierAddDTO();
        supplierAddDTO.setSupplierCode(request.getSupplierCode());
        response = supplierRemote.querySupplierDetail(supplierAddDTO);
        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response.getModel()) {
            SupplierSelectDTO supplierSelectDTO = (SupplierSelectDTO) JSON.parseObject(JSONObject.toJSONString(response.getModel()), SupplierSelectDTO.class);
            supplyOrderPO.setMerchantPm(supplierSelectDTO.getPurchaseManagerId().toString());
            supplyOrderPO.setSettlementType(supplierSelectDTO.getSettlementType());
        }

        supplyOrderPO.setRate(rate);

        assemblySupplyOrderDTO.setSupplyOrder(supplyOrderPO);

        SupplyOrderFinancePO supplyOrderFinancePO = new SupplyOrderFinancePO();
        supplyOrderFinancePO.setPaidAmt(BigDecimal.ZERO);
        supplyOrderFinancePO.setUnpaidAmt(supplyOrderPO.getSupplyOrderAmt());
        supplyOrderFinancePO.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        supplyOrderFinancePO.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        supplyOrderFinancePO.setSettlementStatus(0);
        supplyOrderFinancePO.setSettlementDate(DateUtil.stringToDate(DateUtil.dateToString(new Date())));
        supplyOrderFinancePO.setCheckStatus(CheckStatusEnum.NEW.key);
        supplyOrderFinancePO.setFinanceLockStatus(0);
        supplyOrderFinancePO.setCreatedBy(request.getOperator());
        supplyOrderFinancePO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        assemblySupplyOrderDTO.setSupplyOrderFinance(supplyOrderFinancePO);

        //组装供货产品
        AssemblySupplyProductDTO assemblySupplyProductDTO = new AssemblySupplyProductDTO();
        assemblySupplyOrderDTO.setSupplyProductList(Arrays.asList(assemblySupplyProductDTO));
        SupplyProductPO supplyProductPO = new SupplyProductPO();
        BeanUtils.copyProperties(request, supplyProductPO);
        supplyProductPO.setStartDate(orderPO.getStartDate());
        supplyProductPO.setEndDate(orderPO.getEndDate());
        supplyProductPO.setGuest(guestSb.toString());
        supplyProductPO.setBasePriceTotalAmt(request.getSupplyOrderAmt());
        supplyProductPO.setCreatedBy(request.getOperator());
        supplyProductPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        assemblySupplyProductDTO.setSupplyProduct(supplyProductPO);

        //组装价格
        assemblySupplyProductDTO.setSupplyProductPriceList(new ArrayList<>());
        assemblyOrderDTO.setOrderProductPriceList(new ArrayList<>());
        request.getPriceList().forEach(priceDTO -> {
            OrderProductPricePO orderProductPricePO = new OrderProductPricePO();
            orderProductPricePO.setSaleDate(DateUtil.stringToDate(priceDTO.getSaleDate()));
            orderProductPricePO.setSalePrice(priceDTO.getSalePrice());
            assemblyOrderDTO.getOrderProductPriceList().add(orderProductPricePO);

            SupplyProductPricePO supplyProductPricePO = new SupplyProductPricePO();
            supplyProductPricePO.setSaleDate(DateUtil.stringToDate(priceDTO.getSaleDate()));
            supplyProductPricePO.setBasePrice(priceDTO.getBasePrice());
            assemblySupplyProductDTO.getSupplyProductPriceList().add(supplyProductPricePO);
        });

        return assemblyOrderDTO;
    }

    private AssemblyOrderDTO assemblyOTAOrderData(AddManualOrderDTO request,ProductSalePriceDTO productSalePriceDTO,Integer settlementType) {

        BigDecimal rate=null;
        //查询汇率
        // TODO: 2019/5/6 调汇率接口
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setCurrency(request.getBaseCurrency());
        if (request.getBaseCurrency() == 0) {//0为人民币
            rate=BigDecimal.ONE;
        } else {
            Response ExchangeRate = exchangeRateRemote.queryExchangeRate(exchangeRateDTO);
            if (ExchangeRate.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != ExchangeRate.getModel()) {
                JSONArray jsonArray = (JSONArray) JSON.parseArray(JSONObject.toJSONString(ExchangeRate.getModel()));
                Iterator<Object> it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject arrayObj = (JSONObject) it.next();
                    rate = new BigDecimal(arrayObj.get("reversedExchangeRate").toString()).setScale(2,BigDecimal.ROUND_CEILING);
                }
            }
        }


        //组装订单
        AssemblyOrderDTO assemblyOrderDTO = new AssemblyOrderDTO();
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(request, orderPO);
        orderPO.setStartDate(DateUtil.stringToDate(request.getStartDate()));
        orderPO.setEndDate(DateUtil.stringToDate(request.getEndDate()));
        orderPO.setIsManualOrder(0);
        orderPO.setIsSubstituted(0);
        orderPO.setIsShowOnSupplyOrder(1);
        orderPO.setOrderConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
        orderPO.setSupplyOrderConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key.toString());
        orderPO.setSalePrice(request.getOrderAmt());
        orderPO.setRefundFee(BigDecimal.ZERO);
        orderPO.setMarkedStatus(0);
        orderPO.setModificationStatus(0);
        orderPO.setCreatedBy(request.getOperator());
        orderPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));

        //查询酒店基本信息
        // TODO: 2019/5/6 调酒店基本信息接口
        Map<String, String> hotel = new HashMap<>();
        hotel.put("hotelId", request.getHotelId().toString());
        Response hotelResponse = hotelRemote.queryHotelDetail(hotel);
        if (hotelResponse.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != hotelResponse.getModel()) {
            BasicHotelInfoDTO basicHotelInfoDTO = (BasicHotelInfoDTO) JSON.parseObject(JSONObject.toJSONString(hotelResponse.getModel()), BasicHotelInfoDTO.class);
            orderPO.setCityCode(basicHotelInfoDTO.getCityCode());
            orderPO.setCityName(basicHotelInfoDTO.getCityName());
        }
        orderPO.setHotelName(productSalePriceDTO.getHotelName());
        orderPO.setRoomName(productSalePriceDTO.getRoomName());

        if (ChannelEnum.B2C.key.equals(request.getChannelCode())) {
            orderPO.setSettlementType(SettlementTypeEnum.SINGLE.key);
        } else {
            if (settlementType != null) {
                orderPO.setSettlementType(settlementType);
            } else {
                orderPO.setSettlementType(SettlementTypeEnum.MONTH.key);
            }
        }

        //查询分销商(查询联系人信息)
        AgentAddDTO agentAddDTO = new AgentAddDTO();
        agentAddDTO.setAgentCode(request.getAgentCode());
        Response response = agentRemote.queryAgentDetail(agentAddDTO);
        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response.getModel()) {
            AgentSelectDTO agentSelectDTO = (AgentSelectDTO) JSON.parseObject(JSONObject.toJSONString(response.getModel()), AgentSelectDTO.class);
            orderPO.setMerchantBm(agentSelectDTO.getSaleManagerId().toString());
            if (null != agentSelectDTO.getContactList()) {
                for (int i = 0; i < agentSelectDTO.getContactList().size(); i++) {
                    if (agentSelectDTO.getContactList().get(i).getContactRole().equals(1) || agentSelectDTO.getContactList().get(i).getContactRole().equals("0,1")) {
                        orderPO.setContactName(agentSelectDTO.getContactList().get(i).getContactName());
                        orderPO.setContactPhone(agentSelectDTO.getContactList().get(i).getContactTel());
                    } else {
                        orderPO.setContactName(agentSelectDTO.getContactList().get(0).getContactName());
                        orderPO.setContactPhone(agentSelectDTO.getContactList().get(0).getContactTel());
                    }
                }
            }else{
                orderPO.setContactPhone(agentSelectDTO.getAgentTel());
            }
        }

        OrderFinancePO orderFinancePO = new OrderFinancePO();
        orderFinancePO.setReceivedAmt(BigDecimal.ZERO);
        orderFinancePO.setUnreceivedAmt(orderPO.getOrderAmt());
        orderFinancePO.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        orderFinancePO.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        orderFinancePO.setSettlementStatus(0);
        orderFinancePO.setSettlementDate(DateUtil.stringToDate(DateUtil.dateToString(new Date())));
        orderFinancePO.setCheckStatus(CheckStatusEnum.NEW.key);
        orderFinancePO.setFinanceLockStatus(0);
        orderFinancePO.setCreatedBy(request.getOperator());
        orderFinancePO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        assemblyOrderDTO.setOrderFinance(orderFinancePO);

        //设置入住人
        StringBuilder guestSb = new StringBuilder();
        if (request.getGuestList().size() > 0) {
            assemblyOrderDTO.setGuestList(new ArrayList<>());
            for (String guest : request.getGuestList()) {
                guestSb.append(guest).append("、");
                GuestPO guestPO = new GuestPO();
                guestPO.setName(guest);
                assemblyOrderDTO.getGuestList().add(guestPO);
            }
            guestSb.deleteCharAt(guestSb.length() - 1);
        }
        orderPO.setGuest(guestSb.toString());

        //组装供货单
        //计算供应商价格
        BigDecimal supplyOrderAmt = BigDecimal.ZERO;
        Map<String,BigDecimal> basePriceMap = new HashMap<>();
        for (ProductSalePriceItemDTO productSalePriceItemDTO : productSalePriceDTO.getPriceList()) {
            supplyOrderAmt = supplyOrderAmt.add(productSalePriceItemDTO.getBasePrice().multiply(new BigDecimal(request.getRoomQty().toString())));
            basePriceMap.put(productSalePriceItemDTO.getSaleDate(),productSalePriceItemDTO.getBasePrice());
        }
        orderPO.setProductName(productSalePriceDTO.getProductName());
        orderPO.setProfit(request.getOrderAmt().subtract(supplyOrderAmt.multiply(rate).setScale(2,BigDecimal.ROUND_UP)));
        assemblyOrderDTO.setOrder(orderPO);

        AssemblySupplyOrderDTO assemblySupplyOrderDTO = new AssemblySupplyOrderDTO();
        assemblyOrderDTO.setSupplyOrderList(Arrays.asList(assemblySupplyOrderDTO));
        SupplyOrderPO supplyOrderPO = new SupplyOrderPO();
        BeanUtils.copyProperties(request, supplyOrderPO);
        supplyOrderPO.setSupplierCode(productSalePriceDTO.getSupplierCode());
        supplyOrderPO.setSupplierName(productSalePriceDTO.getSupplierName());
        supplyOrderPO.setBaseCurrency(productSalePriceDTO.getCurrency());
        supplyOrderPO.setSupplyOrderAmt(supplyOrderAmt);
        supplyOrderPO.setProductName(productSalePriceDTO.getProductName());
        supplyOrderPO.setBasePrice(supplyOrderAmt);
        supplyOrderPO.setRefundFee(BigDecimal.ZERO);
        supplyOrderPO.setStartDate(orderPO.getStartDate());
        supplyOrderPO.setEndDate(orderPO.getEndDate());
        supplyOrderPO.setConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
        supplyOrderPO.setSendingStatus(SendingStatusEnum.UNSEND.key);
        supplyOrderPO.setCreatedBy(request.getOperator());
        supplyOrderPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));

        //查询供应商
        // TODO: 2019/5/6 调供应商接口
        SupplierAddDTO supplierAddDTO = new SupplierAddDTO();
        supplierAddDTO.setSupplierCode(productSalePriceDTO.getSupplierCode());
        response = supplierRemote.querySupplierDetail(supplierAddDTO);
        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response.getModel()) {
            SupplierSelectDTO supplierSelectDTO = (SupplierSelectDTO) JSON.parseObject(JSONObject.toJSONString(response.getModel()), SupplierSelectDTO.class);
            supplyOrderPO.setMerchantPm(supplierSelectDTO.getPurchaseManagerId().toString());
            supplyOrderPO.setSettlementType(supplierSelectDTO.getSettlementType());
        }

        supplyOrderPO.setRate(rate);

        assemblySupplyOrderDTO.setSupplyOrder(supplyOrderPO);

        SupplyOrderFinancePO supplyOrderFinancePO = new SupplyOrderFinancePO();
        supplyOrderFinancePO.setPaidAmt(BigDecimal.ZERO);
        supplyOrderFinancePO.setUnpaidAmt(supplyOrderPO.getSupplyOrderAmt());
        supplyOrderFinancePO.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        supplyOrderFinancePO.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        supplyOrderFinancePO.setSettlementStatus(0);
        supplyOrderFinancePO.setSettlementDate(DateUtil.stringToDate(DateUtil.dateToString(new Date())));
        supplyOrderFinancePO.setCheckStatus(CheckStatusEnum.NEW.key);
        supplyOrderFinancePO.setFinanceLockStatus(0);
        supplyOrderFinancePO.setCreatedBy(request.getOperator());
        supplyOrderFinancePO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        assemblySupplyOrderDTO.setSupplyOrderFinance(supplyOrderFinancePO);

        //组装供货产品
        AssemblySupplyProductDTO assemblySupplyProductDTO = new AssemblySupplyProductDTO();
        assemblySupplyOrderDTO.setSupplyProductList(Arrays.asList(assemblySupplyProductDTO));
        SupplyProductPO supplyProductPO = new SupplyProductPO();
        BeanUtils.copyProperties(request, supplyProductPO);
        supplyProductPO.setStartDate(orderPO.getStartDate());
        supplyProductPO.setEndDate(orderPO.getEndDate());
        supplyProductPO.setGuest(guestSb.toString());
        supplyProductPO.setBasePriceTotalAmt(supplyOrderAmt);
        supplyProductPO.setCreatedBy(request.getOperator());
        supplyProductPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        assemblySupplyProductDTO.setSupplyProduct(supplyProductPO);

        //组装价格
        assemblySupplyProductDTO.setSupplyProductPriceList(new ArrayList<>());
        assemblyOrderDTO.setOrderProductPriceList(new ArrayList<>());
        request.getPriceList().forEach(priceDTO -> {
            OrderProductPricePO orderProductPricePO = new OrderProductPricePO();
            orderProductPricePO.setSaleDate(DateUtil.stringToDate(priceDTO.getSaleDate()));
            orderProductPricePO.setSalePrice(priceDTO.getSalePrice());
            assemblyOrderDTO.getOrderProductPriceList().add(orderProductPricePO);

            SupplyProductPricePO supplyProductPricePO = new SupplyProductPricePO();
            supplyProductPricePO.setSaleDate(DateUtil.stringToDate(priceDTO.getSaleDate()));
            supplyProductPricePO.setBasePrice(basePriceMap.get(priceDTO.getSaleDate()));
            assemblySupplyProductDTO.getSupplyProductPriceList().add(supplyProductPricePO);
        });

        return assemblyOrderDTO;
    }

    private Integer saveOrder(AssemblyOrderDTO orderDTO) {
        orderMapper.insert(orderDTO.getOrder());
        Integer orderId = orderDTO.getOrder().getId();
        OrderPO orderPO = orderMapper.selectByPrimaryKey(orderId);
        orderDTO.getOrderFinance().setOrderId(orderId);
        orderDTO.getOrderFinance().setOrderCode(orderPO.getOrderCode());
        orderFinanceMapper.insert(orderDTO.getOrderFinance());
        orderDTO.getOrderProductPriceList().forEach(orderProductPricePO -> {
            orderProductPricePO.setOrderId(orderId);
        });
        orderProductPriceMapper.insertList(orderDTO.getOrderProductPriceList());
        orderDTO.getGuestList().forEach(guestPO -> {
            guestPO.setOrderId(orderId);
        });
        guestMapper.insertList(orderDTO.getGuestList());
        for (AssemblySupplyOrderDTO supplyOrderDTO : orderDTO.getSupplyOrderList()) {
            supplyOrderDTO.getSupplyOrder().setOrderId(orderId);
            supplyOrderMapper.insert(supplyOrderDTO.getSupplyOrder());
            Integer supplyOrderId = supplyOrderDTO.getSupplyOrder().getId();
            SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(supplyOrderId);
            supplyOrderDTO.getSupplyOrderFinance().setSupplyOrderId(supplyOrderId);
            supplyOrderDTO.getSupplyOrderFinance().setSupplyOrderCode(supplyOrderPO.getSupplyOrderCode());
            supplyOrderFinanceMapper.insert(supplyOrderDTO.getSupplyOrderFinance());
            for (AssemblySupplyProductDTO supplyProductDTO : supplyOrderDTO.getSupplyProductList()) {
                supplyProductDTO.getSupplyProduct().setSupplyOrderId(supplyOrderId);
                supplyProductDTO.getSupplyProduct().setOrderId(orderId);
                supplyProductMapper.insert(supplyProductDTO.getSupplyProduct());
                Integer supplyProductId = supplyProductDTO.getSupplyProduct().getId();

                supplyProductDTO.getSupplyProductPriceList().forEach(supplyProductPricePO -> {
                    supplyProductPricePO.setSupplyOrderId(supplyOrderId);
                    supplyProductPricePO.setSupplyProductId(supplyProductId);
                });
                supplyProductPriceMapper.insertList(supplyProductDTO.getSupplyProductPriceList());
            }
        }
        return orderDTO.getOrder().getId();
    }
}
