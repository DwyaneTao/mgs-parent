package com.mgs.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SettlementTypeEnum;
import com.mgs.finance.enums.CheckStatusEnum;
import com.mgs.finance.remote.ExchangeRateRemote;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.OrderRemarkPO;
import com.mgs.order.domain.SupplyAttachmentPO;
import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.domain.SupplyProductPO;
import com.mgs.order.domain.SupplyProductPricePO;
import com.mgs.order.domain.SupplyRequestPO;
import com.mgs.order.enums.ConfirmationStatusEnum;
import com.mgs.order.enums.RemarkTypeEnum;
import com.mgs.order.enums.SendingResultEnum;
import com.mgs.order.enums.SendingStatusEnum;
import com.mgs.order.enums.SendingTypeEnum;
import com.mgs.order.enums.SupplyOrderTypeEnum;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.mapper.OrderRemarkMapper;
import com.mgs.order.mapper.SupplyAttachmentMapper;
import com.mgs.order.mapper.SupplyOrderFinanceMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.mapper.SupplyProductMapper;
import com.mgs.order.mapper.SupplyProductPriceMapper;
import com.mgs.order.mapper.SupplyRequestMapper;
import com.mgs.order.remote.request.AddProductDTO;
import com.mgs.order.remote.request.ModifySupplierOrderCodeDTO;
import com.mgs.order.remote.request.ModifySupplyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifySupplyProductDTO;
import com.mgs.order.remote.request.PriceRequestDTO;
import com.mgs.order.remote.request.SaveSupplyAttachmentDTO;
import com.mgs.order.remote.request.SaveSupplyResultDTO;
import com.mgs.order.remote.request.SendToSupplierDTO;
import com.mgs.order.remote.request.SupplyAttachmentIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.PrintSupplyOrderDTO;
import com.mgs.order.remote.response.SupplyOrderListDTO;
import com.mgs.order.remote.response.SupplyProductPreviewDTO;
import com.mgs.order.service.SupplyOrderService;
import com.mgs.order.service.common.OrderCommonService;
import com.mgs.organization.remote.AgentRemote;
import com.mgs.organization.remote.SupplierRemote;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import com.mgs.organization.remote.dto.AgentSelectDTO;
import com.mgs.organization.remote.dto.SupplierAddDTO;
import com.mgs.organization.remote.dto.SupplierSelectDTO;
import com.mgs.product.dto.DebitedQuotaDTO;
import com.mgs.product.dto.QuotaDTO;
import com.mgs.product.remote.DebitedQuotaRemote;
import com.mgs.product.remote.QuotaRemote;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mgs.util.DateUtil.hour_format;

@Service
@Slf4j
public class SupplyOrderServiceImpl implements SupplyOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private SupplyProductMapper supplyProductMapper;

    @Autowired
    private SupplyProductPriceMapper supplyProductPriceMapper;

    @Autowired
    private SupplyRequestMapper supplyRequestMapper;

    @Autowired
    private SupplyAttachmentMapper supplyAttachmentMapper;

    @Autowired
    private OrderRemarkMapper orderRemarkMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    @Autowired
    private SupplyOrderFinanceMapper supplyOrderFinanceMapper;

    @Autowired
    private SupplierRemote supplierRemote;

    @Autowired
    private ExchangeRateRemote exchangeRateRemote;

    @Autowired
    private AgentRemote agentRemote;

    @Autowired
    private DebitedQuotaRemote debitedQuotaRemote;

    @Autowired
    private QuotaRemote quotaRemote;

    @Autowired
    private HotelRemote hotelRemote;

    @Override
    @Transactional
    public Response modifySupplyProduct(ModifySupplyProductDTO request) {
        log.info("modifySupplyProduct param: {}", request);
        Response response = new Response();
        StringBuilder logSb = new StringBuilder("修改供货单产品");
        Boolean isSupplyProductChange = false;
        Boolean isSaleDateChange = false;
        BigDecimal basePriceTotalAmt = BigDecimal.ZERO;

        SupplyProductPO supplyProductPO = supplyProductMapper.selectByPrimaryKey(request.getSupplyProductId());
        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(supplyProductPO.getSupplyOrderId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        // 1. 更新产品
        SupplyProductPO supplyProductUpdate = new SupplyProductPO();
        supplyProductUpdate.setId(request.getSupplyProductId());
        supplyProductUpdate.setModifiedBy(request.getOperator());
        supplyProductUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        if (DateUtil.compare(supplyProductPO.getStartDate(), DateUtil.stringToDate(request.getStartDate())) != 0) {
            supplyProductUpdate.setStartDate(DateUtil.stringToDate(request.getStartDate()));
            logSb.append(",入住日期由").append(DateUtil.dateToString(supplyProductPO.getStartDate()))
                    .append("变更为").append(request.getStartDate());
            isSaleDateChange = true;
            isSupplyProductChange = true;
        }
        if (DateUtil.compare(supplyProductPO.getEndDate(), DateUtil.stringToDate(request.getEndDate())) != 0) {
            supplyProductUpdate.setEndDate(DateUtil.stringToDate(request.getEndDate()));
            logSb.append(",离店日期由").append(DateUtil.dateToString(supplyProductPO.getEndDate()))
                    .append("变更为").append(request.getEndDate());
            isSaleDateChange = true;
            isSupplyProductChange = true;
        }
        if (supplyProductPO.getRoomQty() != request.getRoomQty()) {
            supplyProductUpdate.setRoomQty(request.getRoomQty());
            logSb.append(",房间数量由").append(supplyProductPO.getRoomQty())
                    .append("变更为").append(request.getRoomQty());
            isSupplyProductChange = true;
        }
        if (supplyProductPO.getGuest() == null && request.getGuest() != null
                || !supplyProductPO.getGuest().equals(request.getGuest())) {
            supplyProductUpdate.setGuest(request.getGuest());
            logSb.append(",入住人由").append(supplyProductPO.getGuest())
                    .append("变更为").append(request.getGuest());
            isSupplyProductChange = true;
        }

        // 2. 更新价格明细
        SupplyProductPricePO supplyProductPriceQuery = new SupplyProductPricePO();
        supplyProductPriceQuery.setSupplyProductId(request.getSupplyProductId());
        List<SupplyProductPricePO> supplyProductPricePOList = supplyProductPriceMapper.select(supplyProductPriceQuery);

        if (isSaleDateChange) {
            //如果入住离店日期变更，则先清空，再重新插入
            supplyProductPriceMapper.delete(supplyProductPriceQuery);

            List<SupplyProductPricePO> insertList = new ArrayList<>();
            for (PriceRequestDTO priceDTO : request.getPriceList()) {
                SupplyProductPricePO supplyProductPricePO = new SupplyProductPricePO();
                supplyProductPricePO.setSupplyProductId(supplyProductPO.getId());
                supplyProductPricePO.setSupplyOrderId(supplyOrderPO.getId());
                supplyProductPricePO.setSaleDate(DateUtil.stringToDate(priceDTO.getSaleDate()));
                supplyProductPricePO.setBasePrice(priceDTO.getBasePrice());
                insertList.add(supplyProductPricePO);

                basePriceTotalAmt = basePriceTotalAmt.add(priceDTO.getBasePrice().multiply(BigDecimal.valueOf(request.getRoomQty().doubleValue())));
            }
            supplyProductPriceMapper.insertList(insertList);

            logSb.append(",底价由(");
            for (SupplyProductPricePO supplyProductPricePO : supplyProductPricePOList) {
                logSb.append("日期").append(DateUtil.dateToString(supplyProductPricePO.getSaleDate()))
                        .append("价格").append(supplyProductPricePO.getBasePrice());
            }
            logSb.append(")变更为(");
            for (PriceRequestDTO priceDTO : request.getPriceList()) {
                logSb.append("日期").append(priceDTO.getSaleDate())
                        .append("价格").append(priceDTO.getBasePrice());
            }
            logSb.append(")");
        } else {
            logSb.append(",底价变更");
            for (PriceRequestDTO priceDTO : request.getPriceList()) {
                for (SupplyProductPricePO supplyProductPricePO : supplyProductPricePOList) {
                    if (DateUtil.compare(DateUtil.stringToDate(priceDTO.getSaleDate()), supplyProductPricePO.getSaleDate()) == 0) {
                        if (priceDTO.getBasePrice().compareTo(supplyProductPricePO.getBasePrice()) != 0) {
                            logSb.append(",").append(priceDTO.getSaleDate())
                                    .append("由").append(supplyProductPricePO.getBasePrice())
                                    .append("变更为").append(priceDTO.getBasePrice());

                            supplyProductPricePO.setBasePrice(priceDTO.getBasePrice());
                            supplyProductPriceMapper.updateByPrimaryKeySelective(supplyProductPricePO);
                            isSupplyProductChange = true;
                        }
                        break;
                    }
                }
                basePriceTotalAmt = basePriceTotalAmt.add(priceDTO.getBasePrice().multiply(BigDecimal.valueOf(request.getRoomQty().doubleValue())));
            }
        }
        //供货单产品没有变更
        if (isSupplyProductChange == false) {
            response.setResult(ResultCodeEnum.SUCCESS.code);
            return response;
        }
        BigDecimal changeAmt = basePriceTotalAmt.subtract(supplyProductPO.getBasePriceTotalAmt());
        if (supplyProductPO.getBasePriceTotalAmt().compareTo(basePriceTotalAmt) != 0) {
            logSb.append(",供货单总金额由").append(supplyOrderPO.getSupplyOrderAmt())
                    .append("变更为").append(supplyOrderPO.getSupplyOrderAmt().add(changeAmt));
        }
        supplyProductUpdate.setBasePriceTotalAmt(basePriceTotalAmt);
        supplyProductMapper.updateByPrimaryKeySelective(supplyProductUpdate);

        // 3. 更新供货单金额和状态
        SupplyOrderPO supplyOrderUpdate = new SupplyOrderPO();
        supplyOrderUpdate.setId(supplyOrderPO.getId());
        supplyOrderUpdate.setBasePrice(supplyOrderPO.getBasePrice().add(changeAmt));
        supplyOrderUpdate.setSupplyOrderAmt(supplyOrderUpdate.getBasePrice().add(null != supplyOrderPO.getRefundFee() ? supplyOrderPO.getRefundFee() : BigDecimal.ZERO));
        supplyOrderUpdate.setConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
        supplyOrderUpdate.setSendingStatus(SendingStatusEnum.UNSEND.key);
        supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);

        // 4. 更新订单金额
        SupplyOrderPO supplyOrderQuery = new SupplyOrderPO();
        supplyOrderQuery.setOrderId(supplyOrderPO.getOrderId());
        List<SupplyOrderPO> supplyOrderPOList = supplyOrderMapper.select(supplyOrderQuery);
        BigDecimal supplyOrderSum = BigDecimal.ZERO;
        for (SupplyOrderPO supplyOrder : supplyOrderPOList) {
            supplyOrderSum = supplyOrderSum.add(supplyOrder.getSupplyOrderAmt().multiply(supplyOrder.getRate()));
        }
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(orderPO.getId());
        orderUpdate.setSupplyOrderConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key.toString());
        orderUpdate.setProfit(orderPO.getOrderAmt().subtract(supplyOrderSum));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);


        try {
            // 5. 退扣配额
            // TODO: 2019/5/7 调退扣配额接口
            Map<String, Integer> map = new HashMap<>();
            map.put("productId", supplyProductPO.getProductId());
            map.put("orderId", orderPO.getId());
            map.put("type", 1);//查询以扣的配额
            Response debitedQuota = debitedQuotaRemote.queryDebitedQuota(map);
            List<DebitedQuotaDTO> debitedQuotaDTO = new ArrayList<>();
            if (debitedQuota.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != debitedQuota.getModel()) {
                debitedQuotaDTO = JSON.parseObject(JSONObject.toJSONString(debitedQuota.getModel()), new TypeReference<List<DebitedQuotaDTO>>() {
                });
            }
            //返还配额
            QuotaDTO quotaDTO = new QuotaDTO();
            quotaDTO.setOrderCode(debitedQuotaDTO.get(0).getOrderCode());
            quotaDTO.setOrderId(debitedQuotaDTO.get(0).getOrderId());
            quotaDTO.setProductId(debitedQuotaDTO.get(0).getProductId());
            quotaDTO.setQuota(-debitedQuotaDTO.get(0).getQuota());//以扣配额为负数，负负得正，加配额
            quotaDTO.setSupplyOrderCode(debitedQuotaDTO.get(0).getSupplyOrderCode());
            quotaDTO.setSupplyOrderId(debitedQuotaDTO.get(0).getSupplyOrderId());
            StringBuilder saleDate = new StringBuilder();
            for (DebitedQuotaDTO debitedQuotaDTOs : debitedQuotaDTO) {
                saleDate.append(DateUtil.dateToString(DateUtil.stringToDate(debitedQuotaDTOs.getSaleDate(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")).append(",");
            }
            quotaDTO.setSaleDate(saleDate.substring(0, saleDate.length() - 1));
            quotaRemote.modifyQuota(quotaDTO);

            //扣除配额
            Map<String, String> data = new HashMap<>();
            data.put("begin", request.getStartDate());
            data.put("end", request.getEndDate());
            List<String> list = orderMapper.queryBetweenDate(data);
            StringBuilder saleDate1 = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                saleDate1.append(list.get(i)).append(",");
            }
            quotaDTO.setSaleDate(saleDate1.substring(0, saleDate1.length() - 1));
            quotaDTO.setQuota(-request.getRoomQty());//减配额
            quotaRemote.modifyQuota(quotaDTO);
        } catch (Exception e) {
            log.error("扣配额异常！订单ID：" + orderPO.getId(), e);
        }


        if (changeAmt.compareTo(BigDecimal.ZERO) != 0) {
            //更新订单未结算金额和结算状态
            SupplyOrderFinancePO supplyOrderFinanceQuery = new SupplyOrderFinancePO();
            supplyOrderFinanceQuery.setSupplyOrderId(supplyOrderPO.getId());
            SupplyOrderFinancePO supplyOrderFinancePO = supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);
            SupplyOrderFinancePO supplyOrderFinanceUpdate = new SupplyOrderFinancePO();
            supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
            supplyOrderFinanceUpdate.setUnpaidAmt(supplyOrderFinancePO.getUnpaidAmt().add(changeAmt));
            if (BigDecimal.ZERO.compareTo(supplyOrderFinanceUpdate.getUnpaidAmt()) == 0) {
                supplyOrderFinanceUpdate.setSettlementStatus(1);
            } else {
                supplyOrderFinanceUpdate.setSettlementStatus(0);
            }
            supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);

            if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key
                    && orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                //非单结更新对账状态
                if (supplyOrderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                    if (BigDecimal.ZERO.compareTo(supplyOrderFinanceUpdate.getUnpaidAmt()) == 0) {
                        //如果未收金额为0，则改为已对账
                        supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                    } else {
                        //如果未收金额不为0，则改为可出账
                        supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                    }
                    supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
                }
            }
        }

        // 5. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                logSb.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response addProduct(AddProductDTO request) {
        log.info("addProduct param: {}", request);
        Response response = new Response();

        OrderPO orderPO = orderMapper.selectByPrimaryKey(request.getOrderId());

        // 1. 如果供应商对应的供货单不存在，则创建供货单
        SupplyOrderPO supplyOrderQuery = new SupplyOrderPO();
        supplyOrderQuery.setOrderId(request.getOrderId());
        supplyOrderQuery.setSupplierCode(request.getSupplierCode());
        List<SupplyOrderPO> supplyOrderPOList = supplyOrderMapper.select(supplyOrderQuery);
        SupplyOrderPO supplyOrderPO = null;
//        if (supplyOrderPOList.size()==0){
        supplyOrderPO = new SupplyOrderPO();
        BeanUtils.copyProperties(request, supplyOrderPO);
        supplyOrderPO.setCityCode(orderPO.getCityCode());
        supplyOrderPO.setCityName(orderPO.getCityName());
        supplyOrderPO.setHotelId(orderPO.getHotelId());
        supplyOrderPO.setHotelName(orderPO.getHotelName());
        supplyOrderPO.setSupplyOrderAmt(request.getBasePriceTotalAmt());
        supplyOrderPO.setBasePrice(request.getBasePriceTotalAmt());
        supplyOrderPO.setStartDate(DateUtil.stringToDate(request.getStartDate()));
        supplyOrderPO.setEndDate(DateUtil.stringToDate(request.getEndDate()));
        supplyOrderPO.setConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
        supplyOrderPO.setSendingStatus(SendingStatusEnum.UNSEND.key);
        supplyOrderPO.setCreatedBy(request.getOperator());
        supplyOrderPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));

        //查询供应商
        // TODO: 2019/5/6 调供应商接口

        SupplierAddDTO supplierAddDTO = new SupplierAddDTO();
        supplierAddDTO.setSupplierCode(request.getSupplierCode());
        response = supplierRemote.querySupplierDetail(supplierAddDTO);
        SupplierSelectDTO supplierSelectDTO = new SupplierSelectDTO();
        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response.getModel()) {
            supplierSelectDTO = (SupplierSelectDTO) JSON.parseObject(JSONObject.toJSONString(response.getModel()), SupplierSelectDTO.class);
            supplyOrderPO.setMerchantPm(supplierSelectDTO.getSupplierId().toString());
            supplyOrderPO.setSettlementType(supplierSelectDTO.getSettlementType());
        }

        //查询汇率
        // TODO: 2019/5/8 调汇率接口
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setCurrency(request.getBaseCurrency());
        if (request.getBaseCurrency() == 0) {//0为人民币
            supplyOrderPO.setRate(BigDecimal.ONE);
        } else {
            Response ExchangeRate = exchangeRateRemote.queryExchangeRate(exchangeRateDTO);
            if (ExchangeRate.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != ExchangeRate.getModel()) {
                JSONArray jsonArray = (JSONArray) JSON.parseArray(JSONObject.toJSONString(ExchangeRate.getModel()));
                Iterator<Object> it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject arrayObj = (JSONObject) it.next();
                    BigDecimal rate = new BigDecimal(arrayObj.get("reversedExchangeRate").toString());
                    supplyOrderPO.setRate(rate);
                }
            }
        }
        supplyOrderMapper.insert(supplyOrderPO);

        SupplyOrderPO supplyOrderNew = supplyOrderMapper.selectByPrimaryKey(supplyOrderPO.getId());
        SupplyOrderFinancePO supplyOrderFinanceInsert = new SupplyOrderFinancePO();
        supplyOrderFinanceInsert.setSupplyOrderId(supplyOrderNew.getId());
        supplyOrderFinanceInsert.setSupplyOrderCode(supplyOrderNew.getSupplyOrderCode());
        supplyOrderFinanceInsert.setPaidAmt(BigDecimal.ZERO);
        supplyOrderFinanceInsert.setUnpaidAmt(BigDecimal.ZERO);
        supplyOrderFinanceInsert.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        supplyOrderFinanceInsert.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        supplyOrderFinanceInsert.setSettlementStatus(0);
        supplyOrderFinanceInsert.setSettlementDate(DateUtil.stringToDate(DateUtil.dateToString(new Date())));
        supplyOrderFinanceInsert.setCheckStatus(CheckStatusEnum.NEW.key);
        supplyOrderFinanceInsert.setFinanceLockStatus(0);
        supplyOrderFinanceInsert.setCreatedBy(request.getOperator());
        supplyOrderFinanceInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyOrderFinanceMapper.insert(supplyOrderFinanceInsert);
//        }else{
//            supplyOrderPO=supplyOrderPOList.get(0);
//            //更新供货单金额
//            SupplyOrderPO supplyOrderUpdate=new SupplyOrderPO();
//            supplyOrderUpdate.setId(supplyOrderPO.getId());
//            supplyOrderUpdate.setBasePrice(supplyOrderPO.getBasePrice().add(request.getBasePriceTotalAmt()));
//            supplyOrderUpdate.setSupplyOrderAmt(supplyOrderPO.getSupplyOrderAmt().add(request.getBasePriceTotalAmt()));
//            supplyOrderUpdate.setConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key);
//            supplyOrderUpdate.setSendingStatus(SendingStatusEnum.UNSEND.key);
//            supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);
//        }

        // 2. 保存产品
        SupplyProductPO supplyProductPO = new SupplyProductPO();
        BeanUtils.copyProperties(request, supplyProductPO);
        supplyProductPO.setSupplyOrderId(supplyOrderPO.getId());
        supplyProductPO.setOrderId(orderPO.getId());
        supplyProductPO.setStartDate(DateUtil.stringToDate(request.getStartDate()));
        supplyProductPO.setEndDate(DateUtil.stringToDate(request.getEndDate()));
        supplyProductPO.setGuest(orderPO.getGuest());
        supplyProductPO.setBasePriceTotalAmt(request.getBasePriceTotalAmt());
        supplyProductPO.setCreatedBy(request.getOperator());
        supplyProductPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyProductMapper.insert(supplyProductPO);

        // 3. 保存价格明细
        List<SupplyProductPricePO> supplyProductPricePOList = new ArrayList<>();
        for (PriceRequestDTO priceDTO : request.getPriceList()) {
            SupplyProductPricePO supplyProductPricePO = new SupplyProductPricePO();
            supplyProductPricePO.setSupplyProductId(supplyProductPO.getId());
            supplyProductPricePO.setSupplyOrderId(supplyOrderPO.getId());
            supplyProductPricePO.setSaleDate(DateUtil.stringToDate(priceDTO.getSaleDate()));
            supplyProductPricePO.setBasePrice(priceDTO.getBasePrice());
            supplyProductPricePOList.add(supplyProductPricePO);
        }
        supplyProductPriceMapper.insertList(supplyProductPricePOList);

        // 4. 更新订单金额
        SupplyOrderPO supplyOrderParam = new SupplyOrderPO();
        supplyOrderParam.setOrderId(supplyOrderPO.getOrderId());
        List<SupplyOrderPO> supplyOrderList = supplyOrderMapper.select(supplyOrderParam);
        BigDecimal supplyOrderSum = BigDecimal.ZERO;
        for (SupplyOrderPO supplyOrder : supplyOrderList) {
            supplyOrderSum = supplyOrderSum.add(supplyOrder.getSupplyOrderAmt().multiply(supplyOrder.getRate()));
        }
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(orderPO.getId());
        orderUpdate.setSupplyOrderConfirmationStatus(ConfirmationStatusEnum.UNCONFIRM.key.toString());
        orderUpdate.setProfit(orderPO.getOrderAmt().subtract(supplyOrderSum));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);


        try {
            // 5. 扣配额
            // TODO: 2019/5/7 调扣配额接口
            Map<String, String> data = new HashMap<>();
            data.put("begin", request.getStartDate());
            data.put("end", request.getEndDate());
            List<String> list = orderMapper.queryBetweenDate(data);
            StringBuilder saleDate1 = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                saleDate1.append(list.get(i)).append(",");
            }
            QuotaDTO quotaDTO = new QuotaDTO();
            quotaDTO.setSaleDate(saleDate1.substring(0, saleDate1.length() - 1));
            quotaDTO.setQuota(-request.getRoomQty());//减配额
            quotaDTO.setOrderId(orderPO.getId());
            quotaDTO.setOrderCode(orderPO.getOrderCode());
            quotaDTO.setSupplyOrderId(supplyOrderPO.getId());
            quotaDTO.setSupplyOrderCode(supplyOrderPO.getSupplyOrderCode());
            quotaDTO.setSupplierCode(supplierSelectDTO.getSupplierCode());
            quotaDTO.setProductId(supplyProductPO.getProductId());
            quotaRemote.modifyQuota(quotaDTO);
        } catch (Exception e) {
            log.error("扣配额异常！订单ID：" + orderPO.getId(), e);
        }


        //if (supplyOrderPOList.size() != 0) {
            //更新订单未结算金额和结算状态
            SupplyOrderFinancePO supplyOrderFinanceQuery = new SupplyOrderFinancePO();
            supplyOrderFinanceQuery.setSupplyOrderId(supplyOrderPO.getId());
            SupplyOrderFinancePO supplyOrderFinancePO = supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);
            SupplyOrderFinancePO supplyOrderFinanceUpdate = new SupplyOrderFinancePO();
            supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
            supplyOrderFinanceUpdate.setUnpaidAmt(supplyOrderFinancePO.getUnpaidAmt().add(request.getBasePriceTotalAmt()));
            if (BigDecimal.ZERO.compareTo(supplyOrderFinanceUpdate.getUnpaidAmt()) == 0) {
                supplyOrderFinanceUpdate.setSettlementStatus(1);
            } else {
                supplyOrderFinanceUpdate.setSettlementStatus(0);
            }
            supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);

            if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key
                    && orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                //非单结更新对账状态
                if (supplyOrderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                    if (BigDecimal.ZERO.compareTo(supplyOrderFinanceUpdate.getUnpaidAmt()) == 0) {
                        //如果未收金额为0，则改为已对账
                        supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                    } else {
                        //如果未收金额不为0，则改为可出账
                        supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                    }
                    supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
                }
            }
        //}

        // 6. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                "添加客房（" + supplyProductPO.getRoomName() + "|" + supplyProductPO.getProductName() + "）成功"
        );
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response deleteProduct(SupplyProductIdDTO request) {
        log.info("deleteProduct param: {}", request);
        Response response = new Response();

        SupplyProductPO supplyProductPO = supplyProductMapper.selectByPrimaryKey(request.getSupplyProductId());
        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(supplyProductPO.getSupplyOrderId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        // 1. 删除供货价格表
        SupplyProductPricePO productPriceDelete = new SupplyProductPricePO();
        productPriceDelete.setSupplyProductId(request.getSupplyProductId());
        supplyProductPriceMapper.delete(productPriceDelete);
        // 2. 删除供货产品表
        SupplyProductPO supplyProductDelete = new SupplyProductPO();
        supplyProductDelete.setId(request.getSupplyProductId());
        supplyProductMapper.delete(supplyProductDelete);
        // 3. 如果供货单下面没产品， 删除供货单
        SupplyProductPO supplyProductQuery = new SupplyProductPO();
        supplyProductQuery.setSupplyOrderId(supplyOrderPO.getId());
        List<SupplyProductPO> supplyProductPOList = supplyProductMapper.select(supplyProductQuery);
        if (CollectionUtils.isEmpty(supplyProductPOList)) {
            supplyOrderMapper.deleteByPrimaryKey(supplyOrderPO.getId());
        } else {
            // 4. 如果供货单下面有产品， 更新供货单表总金额、冗余字段
            SupplyOrderPO supplyOrderUpdate = new SupplyOrderPO();
            supplyOrderUpdate.setId(supplyOrderPO.getId());
            supplyOrderUpdate.setBasePrice(supplyOrderPO.getBasePrice().subtract(supplyProductPO.getBasePriceTotalAmt()));
            supplyOrderUpdate.setSupplyOrderAmt(supplyOrderPO.getSupplyOrderAmt().subtract(supplyProductPO.getBasePriceTotalAmt()));
            // 更新供货单冗余的产品信息
            SupplyProductPO firstSupplyProductQuery = new SupplyProductPO();
            firstSupplyProductQuery.setSupplyOrderId(supplyOrderPO.getId());
            SupplyProductPO supplyFirstProduct = supplyProductMapper.select(firstSupplyProductQuery).get(0);
            supplyOrderUpdate.setStartDate(supplyFirstProduct.getStartDate());
            supplyOrderUpdate.setEndDate(supplyFirstProduct.getEndDate());
            supplyOrderUpdate.setRoomQty(supplyFirstProduct.getRoomQty());
            supplyOrderUpdate.setRoomName(supplyFirstProduct.getRoomName());
            supplyOrderUpdate.setProductName(supplyFirstProduct.getProductName());
            supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);
        }

        // 5. 更新订单金额
        SupplyOrderPO supplyOrderQuery = new SupplyOrderPO();
        supplyOrderQuery.setOrderId(supplyOrderPO.getOrderId());
        List<SupplyOrderPO> supplyOrderPOList = supplyOrderMapper.select(supplyOrderQuery);
        BigDecimal supplyOrderSum = BigDecimal.ZERO;
        for (SupplyOrderPO supplyOrder : supplyOrderPOList) {
            supplyOrderSum = supplyOrderSum.add(supplyOrder.getSupplyOrderAmt().multiply(supplyOrder.getRate()));
        }
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(orderPO.getId());
        orderUpdate.setProfit(orderPO.getOrderAmt().subtract(supplyOrderSum));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);


        try {
            // 6. 退配额
            // TODO: 2019/5/7 调退配额接口
            Map<String, String> data = new HashMap<>();
            data.put("begin", DateUtil.dateToString(supplyProductPO.getStartDate()));
            data.put("end", DateUtil.dateToString(supplyProductPO.getEndDate()));
            List<String> list = orderMapper.queryBetweenDate(data);
            StringBuilder saleDate1 = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                saleDate1.append(list.get(i)).append(",");
            }
            QuotaDTO quotaDTO = new QuotaDTO();
            quotaDTO.setSaleDate(saleDate1.substring(0, saleDate1.length() - 1));
            quotaDTO.setQuota(-supplyProductPO.getRoomQty());//减配额
            quotaDTO.setOrderId(orderPO.getId());
            quotaDTO.setOrderCode(orderPO.getOrderCode());
            quotaDTO.setSupplyOrderId(supplyOrderPO.getId());
            quotaDTO.setSupplyOrderCode(supplyOrderPO.getSupplyOrderCode());
            quotaDTO.setSupplierCode(supplyOrderPO.getSupplierCode());
            quotaDTO.setProductId(supplyProductPO.getProductId());
            quotaRemote.modifyQuota(quotaDTO);
        } catch (Exception e) {
            log.error("扣配额异常！订单ID：" + orderPO.getId(), e);
        }


        //更新订单未结算金额和结算状态
        if (!CollectionUtils.isEmpty(supplyProductPOList)) {
            SupplyOrderFinancePO supplyOrderFinanceQuery = new SupplyOrderFinancePO();
            supplyOrderFinanceQuery.setSupplyOrderId(supplyOrderPO.getId());
            SupplyOrderFinancePO supplyOrderFinancePO = supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);
            SupplyOrderFinancePO supplyOrderFinanceUpdate = new SupplyOrderFinancePO();
            supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
            supplyOrderFinanceUpdate.setUnpaidAmt(supplyOrderFinancePO.getUnpaidAmt().subtract(supplyProductPO.getBasePriceTotalAmt()));
            if (BigDecimal.ZERO.compareTo(supplyOrderFinanceUpdate.getUnpaidAmt()) == 0) {
                supplyOrderFinanceUpdate.setSettlementStatus(1);
            } else {
                supplyOrderFinanceUpdate.setSettlementStatus(0);
            }
            supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);

            if (orderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key
                    && orderPO.getOrderConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                //非单结更新对账状态
                if (supplyOrderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                    if (BigDecimal.ZERO.compareTo(supplyOrderFinanceUpdate.getUnpaidAmt()) == 0) {
                        //如果未收金额为0，则改为已对账
                        supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                    } else {
                        //如果未收金额不为0，则改为可出账
                        supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                    }
                    supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
                }
            }
        }

        // 7. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                "删除客房（" + supplyProductPO.getRoomName() + "|" + supplyProductPO.getProductName() + "）成功"
        );
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response sendToSupplier(SendToSupplierDTO request) {
        log.info("sendToSupplier param: {}", request);
        Response response = new Response();

        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());

        // 1. 校验：预订单只能发送一次， 供货单已确认不能重发预订单
        SupplyRequestPO supplyRequestQuery = new SupplyRequestPO();
        supplyRequestQuery.setSupplyOrderId(request.getSupplyOrderId());
        supplyRequestQuery.setSupplyOrderType(SupplyOrderTypeEnum.BOOK.key);
        supplyRequestQuery.setSendingResult(SendingResultEnum.SUCCESS.key);
        List<SupplyRequestPO> bookRequestPOList = supplyRequestMapper.select(supplyRequestQuery);
        if (request.getSupplyOrderType().equals(SupplyOrderTypeEnum.BOOK.key)) {
            if (!CollectionUtils.isEmpty(bookRequestPOList)) {
                response.setResult(ResultCodeEnum.FAILURE.code);
                response.setFailReason("预订单只能发送一次");
                return response;
            }
        }
        //已经有尚未处理的取消（修改）申请时，提示：有尚未处理的取消（修改）申请，无法发起新申请
        Example example = new Example(SupplyRequestPO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("supplyOrderId", request.getSupplyOrderId());
        criteria.andEqualTo("sendingResult", SendingResultEnum.SUCCESS.key);
        //criteria.andEqualTo("sendingType",SendingTypeEnum.EBK.key);
        criteria.andEqualTo("thisConfirmationStatus", ConfirmationStatusEnum.UNCONFIRM.key);
        List<Integer> supplyOrderTypeList = new ArrayList<>();
        supplyOrderTypeList.add(SupplyOrderTypeEnum.REVISE.key);
        supplyOrderTypeList.add(SupplyOrderTypeEnum.CANCEL.key);
        criteria.andIn("supplyOrderType", supplyOrderTypeList);
        List<SupplyRequestPO> supplyRequestPOList = supplyRequestMapper.selectByExample(example);
        boolean haveUndoneRequest = (request.getSupplyOrderType() == SupplyOrderTypeEnum.REVISE.key
                || request.getSupplyOrderType() == SupplyOrderTypeEnum.CANCEL.key)
                && !CollectionUtils.isEmpty(supplyRequestPOList);
        if (haveUndoneRequest) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("有尚未处理的取消（修改）申请，无法发起新申请");
            return response;
        }
        if (request.getSupplyOrderType() == SupplyOrderTypeEnum.REVISE.key
                && supplyOrderPO.getConfirmationStatus() != ConfirmationStatusEnum.CONFIRMED.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("供货单不是已确认，不能发修改单");
            return response;
        }
        if (CollectionUtils.isEmpty(bookRequestPOList)
                && request.getSupplyOrderType() != SupplyOrderTypeEnum.BOOK.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("没发过预订单，不能直接发" + SupplyOrderTypeEnum.getValueByKey(request.getSupplyOrderType()));
            return response;
        }
        if (supplyOrderPO.getConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key
                && request.getSupplyOrderType() == SupplyOrderTypeEnum.RESEND.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("供货单已确认，不能重发预订单");
            return response;
        }
        if (supplyOrderPO.getConfirmationStatus() == ConfirmationStatusEnum.CANCELED.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("供货单已取消，不能再发" + SupplyOrderTypeEnum.getValueByKey(request.getSupplyOrderType()));
            return response;
        }

        // 1. 插入发单申请表
        SupplyRequestPO supplyRequestPO = new SupplyRequestPO();
        supplyRequestPO.setSupplyOrderId(request.getSupplyOrderId());
        supplyRequestPO.setSupplyOrderType(request.getSupplyOrderType());
        supplyRequestPO.setSendingType(request.getSendingType());
        supplyRequestPO.setSendingResult(SendingResultEnum.UNSEND.key);
        supplyRequestPO.setMerchantRemark(request.getRemark());
        supplyRequestPO.setCreatedBy(request.getOperator());
        supplyRequestPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyRequestMapper.insertSelective(supplyRequestPO);

        // 2. 发单
        Response sendResponseDTO = new Response(ResultCodeEnum.FAILURE.code);
        try {
            if (request.getSendingType() == SendingTypeEnum.EBK.key) {
                // 发供货单类型：1：预定单，2:重发预订单， 3：修改单，4：取消单
                if (request.getSupplyOrderType() == SupplyOrderTypeEnum.BOOK.key
                        || request.getSupplyOrderType() == SupplyOrderTypeEnum.RESEND.key) {
                    // TODO: 2019/5/7 调ebk接口发送预订单
                } else if (request.getSupplyOrderType() == SupplyOrderTypeEnum.REVISE.key) {
                    // TODO: 2019/5/7 调ebk接口发修改单
                } else if (request.getSupplyOrderType() == SupplyOrderTypeEnum.CANCEL.key) {
                    //// TODO: 2019/5/7 调ebk接口发取消单
                }
                sendResponseDTO.setResult(ResultCodeEnum.SUCCESS.code);
            }
        } catch (Exception e) {
            log.error("sendSupplyRequest exception:{}", JSON.toJSONString(request), e);
            sendResponseDTO.setResult(ResultCodeEnum.FAILURE.code);
            sendResponseDTO.setFailReason(e.getMessage());
        }

        // 3. 记日志
        if (sendResponseDTO.getResult().equals(ResultCodeEnum.FAILURE.code)) {
            orderCommonService.saveOrderLog(
                    supplyOrderPO.getOrderId(),
                    request.getOperator(),
                    request.getOrderOwnerName(),
                    supplyOrderPO.getSupplyOrderCode(),
                    "发单给供应商（供货单：" + supplyOrderPO.getSupplyOrderCode()
                            + "，发送方式：" + SendingTypeEnum.getValueByKey(request.getSendingType())
                            + "，发单类型：" + SupplyOrderTypeEnum.getValueByKey(request.getSupplyOrderType()) + "）"
                            + ", 供应商返回:" + sendResponseDTO.getFailReason()
            );

            log.info("发单返回失败，记录日志。请求参数：{},返回参数:{}", JSON.toJSONString(request), JSON.toJSONString(sendResponseDTO));

            // 4. 更新供货请求为：发送失败
            SupplyRequestPO supplyRequestUpdate = new SupplyRequestPO();
            supplyRequestUpdate.setId(supplyRequestPO.getId());
            supplyRequestUpdate.setSendingResult(SendingResultEnum.FAILURE.key);
            supplyRequestMapper.updateByPrimaryKeySelective(supplyRequestUpdate);

            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason(sendResponseDTO.getFailReason());
            return response;
        } else {
            orderCommonService.saveOrderLog(
                    supplyOrderPO.getOrderId(),
                    request.getOperator(),
                    request.getOrderOwnerName(),
                    supplyOrderPO.getSupplyOrderCode(),
                    "发单给供应商（供货单：" + supplyOrderPO.getSupplyOrderCode()
                            + "，发送方式：" + SendingTypeEnum.getValueByKey(request.getSendingType()) +
                            "，发单类型：" + SupplyOrderTypeEnum.getValueByKey(request.getSupplyOrderType()) + "）"
            );

            // 4. 更新供货请求为：发送成功
            SupplyRequestPO supplyRequestUpdate = new SupplyRequestPO();
            supplyRequestUpdate.setId(supplyRequestPO.getId());
            supplyRequestUpdate.setSendingResult(SendingResultEnum.SUCCESS.key);
            supplyRequestMapper.updateByPrimaryKeySelective(supplyRequestUpdate);
        }

        // 5. 更新供货单状态
        SupplyOrderPO supplyOrderUpdate = new SupplyOrderPO();
        supplyOrderUpdate.setId(request.getSupplyOrderId());
        supplyOrderUpdate.setSendingStatus(SendingStatusEnum.getEnumBySupplyOrderType(request.getSupplyOrderType()).key);
        supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);

        // 6. 添加商家给供应商备注
        if (!StringUtil.isValidString(request.getRemark())) {
            OrderRemarkPO orderRemarkPO = new OrderRemarkPO();
            orderRemarkPO.setOrderId(supplyOrderPO.getOrderId());
            orderRemarkPO.setRemarkType(RemarkTypeEnum.SUPPLY_NOTE.key);
            orderRemarkPO.setReceiver(supplyOrderPO.getSupplierName());
            orderRemarkPO.setRemark(request.getRemark());
            orderRemarkPO.setCreatedBy(request.getOperator());
            orderRemarkPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
            orderRemarkMapper.insert(orderRemarkPO);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
//    @Transactional
    public Response saveSupplyResult(SaveSupplyResultDTO request) {
        log.info("saveSupplyResult param: {}", request);
        Response response = new Response();

        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        // 1. 更新更新供货请求结果
        Example supplyRequestExample = new Example(SupplyRequestPO.class);
        supplyRequestExample.setOrderByClause("created_dt desc");
        Example.Criteria supplyRequestCriteria = supplyRequestExample.createCriteria();
        supplyRequestCriteria.andEqualTo("supplyOrderId", request.getSupplyOrderId());
        List<SupplyRequestPO> supplyRequestPOList = supplyRequestMapper.selectByExample(supplyRequestExample);
        if (!CollectionUtils.isEmpty(supplyRequestPOList)) {
            SupplyRequestPO supplyRequestUpdate = new SupplyRequestPO();
            supplyRequestUpdate.setId(supplyRequestPOList.get(0).getId());
            supplyRequestUpdate.setThisConfirmationStatus(request.getConfirmationStatus());
            supplyRequestUpdate.setThisConfirmationCode(request.getConfirmationCode());
            supplyRequestUpdate.setThisSupplierConfirmer(request.getSupplierConfirmer());
            supplyRequestUpdate.setThisRefundFee(request.getRefundFee());
            supplyRequestUpdate.setThisRefusedReason(request.getRefusedReason());
            supplyRequestUpdate.setThisConfirmationRemark(request.getRemark());
            supplyRequestUpdate.setModifiedBy(request.getOperator());
            supplyRequestUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
            supplyRequestMapper.updateByPrimaryKeySelective(supplyRequestUpdate);
        }

        // 2. 更新供货单状态
        SupplyOrderPO supplyOrderUpdate = new SupplyOrderPO();
        supplyOrderUpdate.setId(request.getSupplyOrderId());
        supplyOrderUpdate.setConfirmationStatus(request.getConfirmationStatus());
        supplyOrderUpdate.setConfirmationCode(request.getConfirmationCode());
        supplyOrderUpdate.setSupplierConfirmer(request.getSupplierConfirmer());
        supplyOrderUpdate.setConfirmationRemark(request.getRemark());
        supplyOrderUpdate.setSupplyOrderAmt(supplyOrderPO.getSupplyOrderAmt());
        if (request.getRefundFee() != null && BigDecimal.ZERO.compareTo(request.getRefundFee()) != 0) {
            //如果退订费不为0，则更新供货单金额
            supplyOrderUpdate.setRefundFee(request.getRefundFee());
            supplyOrderUpdate.setSupplyOrderAmt(supplyOrderPO.getBasePrice().add(request.getRefundFee()));
        }
        if (request.getConfirmationStatus() == ConfirmationStatusEnum.CANCELED.key) {
            //供货单确认状态为否时，供货单金额为退改费
            supplyOrderUpdate.setSupplyOrderAmt(request.getRefundFee());

        }
        supplyOrderUpdate.setRefusedReason(request.getRefusedReason());
        supplyOrderUpdate.setModifiedBy(request.getOperator());
        supplyOrderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);

        // 4. 更新订单表冗余字段：订单确认号、供货单状态、订单总金额
        SupplyOrderPO supplyOrderQuery = new SupplyOrderPO();
        supplyOrderQuery.setOrderId(supplyOrderPO.getOrderId());
        List<SupplyOrderPO> supplyOrderPOList = supplyOrderMapper.select(supplyOrderQuery);
        BigDecimal supplyOrderSum = BigDecimal.ZERO;
        Set<String> supplyConfirmationCodeSet = new HashSet<>();
        Integer supplyConfirmationStatus = ConfirmationStatusEnum.CANCELED.key;
        for (SupplyOrderPO supplyOrder : supplyOrderPOList) {
            //有确认号，并且确认状态为待确认的时候，才把确认号写到订单中
            if (StringUtil.isValidString(supplyOrder.getConfirmationCode())
                    && null != supplyOrder.getConfirmationStatus()
                    && supplyOrder.getConfirmationStatus().equals(1)) {
                supplyConfirmationCodeSet.add(supplyOrder.getConfirmationCode());
            }
            if (supplyOrder.getConfirmationStatus() == ConfirmationStatusEnum.UNCONFIRM.key) {
                supplyConfirmationStatus = ConfirmationStatusEnum.UNCONFIRM.key;
            }
            if (supplyConfirmationStatus != ConfirmationStatusEnum.UNCONFIRM.key
                    && supplyOrder.getConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                supplyConfirmationStatus = ConfirmationStatusEnum.CONFIRMED.key;
            }
            supplyOrderSum = supplyOrderSum.add(supplyOrder.getSupplyOrderAmt().multiply(supplyOrder.getRate()));
            if (ConfirmationStatusEnum.CANCELED.key != supplyOrder.getConfirmationStatus() && null != supplyOrder.getRefundFee() && null != supplyOrder.getRate()) {
                supplyOrderSum = supplyOrderSum.add(supplyOrder.getRefundFee().multiply(supplyOrder.getRate()));
            }
        }
        OrderPO orderUpdate = new OrderPO();
        orderUpdate.setId(supplyOrderPO.getOrderId());
        orderUpdate.setConfirmationCode(StringUtil.listToString(supplyConfirmationCodeSet, ","));
        orderUpdate.setSupplyOrderConfirmationStatus(supplyConfirmationStatus.toString());
        orderUpdate.setProfit(orderPO.getOrderAmt().subtract(supplyOrderSum));
        orderMapper.updateByPrimaryKeySelective(orderUpdate);


        try {
            // 7. 退扣配额
            SupplyProductPO firstSupplyProductQuery = new SupplyProductPO();
            firstSupplyProductQuery.setSupplyOrderId(supplyOrderPO.getId());
            SupplyProductPO supplyFirstProduct = supplyProductMapper.select(firstSupplyProductQuery).get(0);
            Map<String, String> data = new HashMap<>();
            data.put("begin", DateUtil.dateToString(supplyOrderPO.getStartDate()));
            data.put("end", DateUtil.dateToString(supplyOrderPO.getEndDate()));
            List<String> list = orderMapper.queryBetweenDate(data);
            StringBuilder saleDate1 = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                saleDate1.append(list.get(i)).append(",");
            }
            QuotaDTO quotaDTO = new QuotaDTO();
            quotaDTO.setSaleDate(saleDate1.substring(0, saleDate1.length() - 1));
            quotaDTO.setOrderId(orderPO.getId());
            quotaDTO.setOrderCode(orderPO.getOrderCode());
            quotaDTO.setSupplyOrderId(supplyOrderPO.getId());
            quotaDTO.setSupplyOrderCode(supplyOrderPO.getSupplyOrderCode());
            quotaDTO.setSupplierCode(supplyOrderPO.getSupplierCode());
            quotaDTO.setProductId(supplyFirstProduct.getProductId());
            if (supplyOrderPO.getConfirmationStatus() != supplyOrderUpdate.getConfirmationStatus()) {
                if (supplyOrderUpdate.getConfirmationStatus() == ConfirmationStatusEnum.CANCELED.key) {
                    // TODO: 2019/5/7 调退配额接口
                    quotaDTO.setQuota(supplyOrderPO.getRoomQty());//加配额
                    quotaRemote.modifyQuota(quotaDTO);
                }
                if (supplyOrderPO.getConfirmationStatus() == ConfirmationStatusEnum.CANCELED.key
                        && supplyOrderUpdate.getConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
                    // TODO: 2019/5/7 调扣配额接口
                    quotaDTO.setQuota(-supplyOrderPO.getRoomQty());//减配额
                    quotaRemote.modifyQuota(quotaDTO);
                }
            }
        } catch (Exception e) {
            log.error("扣配额异常！订单ID：" + orderPO.getId(), e);
        }

        //如果是取消，更新供货单应付金额
        if (null != request.getConfirmationStatus() && request.getConfirmationStatus().equals(ConfirmationStatusEnum.CANCELED.key)) {
            SupplyOrderFinancePO supplyOrderFinanceQuerys = new SupplyOrderFinancePO();
            supplyOrderFinanceQuerys.setSupplyOrderId(supplyOrderPO.getId());
            SupplyOrderFinancePO supplyOrderFinancePOs = supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuerys);
            SupplyOrderFinancePO supplyOrderFinanceUpdates = new SupplyOrderFinancePO();
            supplyOrderFinanceUpdates.setId(supplyOrderFinancePOs.getId());
             if(supplyOrderFinancePOs.getPaidAmt()!=null && supplyOrderFinancePOs.getPaidAmt().compareTo(new BigDecimal(0))==1){
                 supplyOrderFinanceUpdates.setUnpaidAmt(supplyOrderFinancePOs.getPaidAmt().subtract(request.getRefundFee()));
             }else{
                 supplyOrderFinanceUpdates.setUnpaidAmt(request.getRefundFee());
             }
            supplyOrderFinanceUpdates.setCheckStatus(1);
            supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdates);
        }

        if (null != supplyOrderPO.getSettlementType() && supplyOrderPO.getSettlementType() != SettlementTypeEnum.SINGLE.key
                && request.getConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {

            SupplyOrderFinancePO supplyOrderFinanceQuery = new SupplyOrderFinancePO();
            supplyOrderFinanceQuery.setSupplyOrderId(supplyOrderPO.getId());
            SupplyOrderFinancePO supplyOrderFinancePO = supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);
            SupplyOrderFinancePO supplyOrderFinanceUpdate = new SupplyOrderFinancePO();
            supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
            //非单结更新对账状态
            if (supplyOrderFinancePO.getCheckStatus() != CheckStatusEnum.HOLD.key) {
                if (BigDecimal.ZERO.compareTo(supplyOrderFinancePO.getUnpaidAmt()) == 0) {
                    //如果未收金额为0，则改为已对账
                    supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CHECKED.key);
                } else {
                    //如果未收金额不为0，则改为可出账
                    supplyOrderFinanceUpdate.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
                }
                supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
            }
        }

        // 6. 记日志
        StringBuffer content = new StringBuffer();
        content.append("更新供货单确认状态，供货单：" + supplyOrderPO.getSupplyOrderCode() + "，"
                + "由" + ConfirmationStatusEnum.getValueByKey(supplyOrderPO.getConfirmationStatus()) + "变更为"
                + ConfirmationStatusEnum.getValueByKey(request.getConfirmationStatus()));
        if (StringUtil.isValidString(request.getConfirmationCode())) {
            content.append("，确认号：" + request.getConfirmationCode());
        }
        if (StringUtil.isValidString(request.getSupplierConfirmer())) {
            content.append("，确认人：" + request.getSupplierConfirmer());
        }
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                content.toString()
        );
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response modifySupplyOrderSettlementType(ModifySupplyOrderSettlementTypeDTO request) {
        log.info("modifySupplyOrderSettlementType param: {}", request);
        Response response = new Response();

        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        // 1. 校验
        if (supplyOrderPO.getSettlementType() == request.getSettlementType()) {
            response.setResult(ResultCodeEnum.SUCCESS.code);
            return response;
        }
        if (request.getSettlementType() != SettlementTypeEnum.SINGLE.key) {
            //单结订单不能改为非单结，非单结只能修改为单结
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason(SettlementTypeEnum.getValueByKey(supplyOrderPO.getSettlementType()) + "不能改为" + SettlementTypeEnum.getValueByKey(request.getSettlementType()));
            return response;
        }
        if (supplyOrderPO.getConfirmationStatus() == ConfirmationStatusEnum.CONFIRMED.key) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("供货单已确认不能修改结算方式");
            return response;
        }

        // 2. 更新订单结算方式
        SupplyOrderPO supplyOrderUpdate = new SupplyOrderPO();
        supplyOrderUpdate.setId(request.getSupplyOrderId());
        supplyOrderUpdate.setSettlementType(request.getSettlementType());
        supplyOrderUpdate.setModifiedBy(request.getOperator());
        supplyOrderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);

        // 3. 记日志
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                "修改供货单结算方式：由(" + SettlementTypeEnum.getValueByKey(orderPO.getSettlementType())
                        + ")改为(" + SettlementTypeEnum.getValueByKey(request.getSettlementType()) + ")"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response saveSupplyAttachment(SaveSupplyAttachmentDTO request) {
        log.info("saveSupplyAttachment param: {}", request);
        Response response = new Response();

        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());

        SupplyAttachmentPO supplyAttachmentInsert = new SupplyAttachmentPO();
        BeanUtils.copyProperties(request, supplyAttachmentInsert);
        supplyAttachmentInsert.setCreatedBy(request.getOperator());
        supplyAttachmentInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyAttachmentMapper.insert(supplyAttachmentInsert);

        //记日志
        orderCommonService.saveOrderLog(
                supplyOrderPO.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                "上传供货单附件（" + request.getName() + "）成功"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response deleteSupplyAttachment(SupplyAttachmentIdDTO request) {
        log.info("deleteSupplyAttachment param: {}", request);
        Response response = new Response();

        SupplyAttachmentPO supplyAttachmentPO = supplyAttachmentMapper.selectByPrimaryKey(request.getSupplyAttachmentId());
        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(supplyAttachmentPO.getSupplyOrderId());
        supplyAttachmentMapper.deleteByPrimaryKey(request.getSupplyAttachmentId());

        //记日志
        orderCommonService.saveOrderLog(
                supplyOrderPO.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                "删除供货单附件（" + supplyAttachmentPO.getName() + "）成功"
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response modifySupplierOrderCode(ModifySupplierOrderCodeDTO request) {
        log.info("modifySupplierOrderCode param: {}", request);
        Response response = new Response();

        SupplyOrderPO supplyOrderPO = supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
        OrderPO orderPO = orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        SupplyOrderPO supplyOrderUpdate = new SupplyOrderPO();
        supplyOrderUpdate.setId(request.getSupplyOrderId());
        supplyOrderUpdate.setSupplierOrderCode(request.getSupplierOrderCode());
        supplyOrderUpdate.setModifiedBy(request.getOperator());
        supplyOrderUpdate.setModifiedDt(DateUtil.dateToString(new Date(), hour_format));
        supplyOrderMapper.updateByPrimaryKeySelective(supplyOrderUpdate);

        //记日志
        StringBuilder content = new StringBuilder();
        content.append("更新供应商订单号为：")
                .append(request.getSupplierOrderCode());
        orderCommonService.saveOrderLog(
                supplyOrderPO.getOrderId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                supplyOrderPO.getSupplyOrderCode(),
                content.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    @Transactional
    public Response batchPrintSupplierOrder(Map<String, String> request) {
        log.info("batchPrintSupplierOrder param: {}", request);
        Response response = new Response();
        String[] supplyOrderCodes = request.get("supplyOrderCodes").split(",");
        PrintSupplyOrderDTO printSupplyOrderDTO = new PrintSupplyOrderDTO();
        List<SupplyOrderListDTO> supplyOrderListDTOS = new ArrayList<>();
        SupplyOrderPO supplyOrderPO = new SupplyOrderPO();
        supplyOrderPO.setSupplyOrderCode(supplyOrderCodes[0]);

        SupplyOrderPO supplyOrder = new SupplyOrderPO();
        supplyOrder = supplyOrderMapper.selectOne(supplyOrderPO);

        //查询酒店基本信息
        // TODO: 2019/5/8 调酒店基本信息接口
        Map<String, String> hotel = new HashMap<>();
        hotel.put("hotelId", supplyOrder.getHotelId().toString());
        Response hotelResponse = hotelRemote.queryHotelDetail(hotel);
        if (hotelResponse.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != hotelResponse.getModel()) {
            BasicHotelInfoDTO basicHotelInfoDTO = (BasicHotelInfoDTO) JSON.parseObject(JSONObject.toJSONString(hotelResponse.getModel()), BasicHotelInfoDTO.class);
            printSupplyOrderDTO.setHotelName(basicHotelInfoDTO.getHotelName());
            printSupplyOrderDTO.setHotelAddress(basicHotelInfoDTO.getHotelAddress());
            printSupplyOrderDTO.setHotelTel(basicHotelInfoDTO.getHotelTel());
        }

        //查询特殊要求
        OrderPO orderPO = orderMapper.selectByPrimaryKey(supplyOrder.getOrderId());
        if (null != orderPO && orderPO.getIsShowOnSupplyOrder() == 1) {
            printSupplyOrderDTO.setSpecialRequest(orderPO.getSpecialRequest());
        }

        String SupplierCode = "";
        for (int i = 0; i < supplyOrderCodes.length; i++) {
            SupplyOrderListDTO supplyOrderListDTO = new SupplyOrderListDTO();
            supplyOrderListDTO.setTotalAmt(new BigDecimal(0));
            SupplyOrderPO supplyOrderPOS = new SupplyOrderPO();
            supplyOrderPO.setSupplyOrderCode(supplyOrderCodes[i]);
            supplyOrderPOS = supplyOrderMapper.selectOne(supplyOrderPO);
            supplyOrderListDTO.setSupplyOrderCode(supplyOrderPOS.getSupplyOrderCode());
            supplyOrderListDTO.setCurrency(supplyOrderPOS.getBaseCurrency());
            if (!"".equals(SupplierCode) && !SupplierCode.equals(supplyOrderPOS.getSupplierCode())) {
                response.setResult(ResultCodeEnum.SUCCESS.code);
                response.setFailReason("只有供应商相同才能发单!");
                return response;
            }
            SupplierCode = supplyOrderPOS.getSupplierCode();

            Set<String> guestSet = new HashSet<>();
            Map<Integer, SupplyProductPO> supplyProductPOMap = new HashMap<>();
            SupplyProductPO supplyProductQuery = new SupplyProductPO();
            supplyProductQuery.setSupplyOrderId(supplyOrderPOS.getId());
            List<SupplyProductPO> supplyProductPOList = supplyProductMapper.select(supplyProductQuery);
            for (SupplyProductPO supplyProductPO : supplyProductPOList) {
                supplyProductPOMap.put(supplyProductPO.getId(), supplyProductPO);
                //过滤重复的入住人
                if (StringUtil.isValidString(supplyProductPO.getGuest())) {
                    if (supplyProductPO.getGuest().indexOf("、") != -1) {
                        guestSet.addAll(Collections.arrayToList(supplyProductPO.getGuest().split("、")));
                    } else {
                        guestSet.add(supplyProductPO.getGuest());
                    }
                }
            }
            printSupplyOrderDTO.setGuest(StringUtil.listToString(guestSet, ","));

            Example supplyProductPriceExample = new Example(SupplyProductPricePO.class);
            supplyProductPriceExample.setOrderByClause("supply_product_id,sale_date");
            Example.Criteria supplyProductPriceCriteria = supplyProductPriceExample.createCriteria();
            supplyProductPriceCriteria.andEqualTo("supplyOrderId", supplyOrderPOS.getId());
            List<SupplyProductPricePO> supplyProductPricePOList = supplyProductPriceMapper.selectByExample(supplyProductPriceExample);
            Integer lastSupplyProductId = null;
            Integer nightQty = null;
            BigDecimal lastBasePrice = BigDecimal.ZERO;
            List<SupplyProductPreviewDTO> list = new ArrayList<>();
            for (SupplyProductPricePO supplyProductPricePO : supplyProductPricePOList) {
                if (supplyProductPricePO.getSupplyProductId() != lastSupplyProductId
                        || supplyProductPricePO.getBasePrice().compareTo(lastBasePrice) != 0) {
                    nightQty = 1;
                    SupplyProductPO supplyProductPO = supplyProductPOMap.get(supplyProductPricePO.getSupplyProductId());
                    SupplyProductPreviewDTO supplyProductPreviewDTO = new SupplyProductPreviewDTO();
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
                    supplyOrderListDTO.setTotalAmt(supplyOrderListDTO.getTotalAmt().add(supplyProductPreviewDTO.getTotalAmt()));
                    list.add(supplyProductPreviewDTO);
                }
            }
            supplyOrderListDTO.setProductList(list);
            supplyOrderListDTOS.add(supplyOrderListDTO);
        }
        printSupplyOrderDTO.setSupplyOrderList(supplyOrderListDTOS);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(printSupplyOrderDTO);
        return response;
    }

}
