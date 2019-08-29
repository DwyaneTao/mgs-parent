package com.mgs.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.mapper.SupplyOrderFinanceMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.remote.request.CancelSupplyOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmSupplyOrderWorkOrderDTO;
import com.mgs.order.service.SupplyOrderFinanceHandle;
import com.mgs.order.service.common.OrderCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class SupplyOrderFinanceHandleImpl implements SupplyOrderFinanceHandle {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private SupplyOrderFinanceMapper supplyOrderFinanceMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    @Transactional
    @Override
    public Response confirmSupplyOrderWorkOrder(ConfirmSupplyOrderWorkOrderDTO request) {
        log.info("confirmSupplyOrderWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        SupplyOrderPO supplyOrderQuery=new SupplyOrderPO();
        supplyOrderQuery.setSupplyOrderCode(request.getSupplyOrderCode());
        SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectOne(supplyOrderQuery);

        OrderPO orderPO=orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        SupplyOrderFinancePO supplyOrderFinanceQuery=new SupplyOrderFinancePO();
        supplyOrderFinanceQuery.setSupplyOrderCode(request.getSupplyOrderCode());
        SupplyOrderFinancePO supplyOrderFinancePO=supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);

        // 1.更新订单结算状态及结算金额
        SupplyOrderFinancePO supplyOrderFinanceUpdate=supplyOrderFinanceMapper.selectByPrimaryKey(supplyOrderFinancePO.getId());
        supplyOrderFinanceUpdate.setPaidAmt(supplyOrderFinancePO.getPaidAmt().add(request.getConfirmAmt()));
        supplyOrderFinanceUpdate.setUnpaidAmt(supplyOrderFinancePO.getUnpaidAmt().subtract(request.getConfirmAmt()));
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            supplyOrderFinanceUpdate.setUnconfirmedPaidAmt(supplyOrderFinancePO.getUnconfirmedPaidAmt().subtract(request.getNotifyAmt()));
        }else{
            supplyOrderFinanceUpdate.setUnconfirmedReceivedAmt(supplyOrderFinancePO.getUnconfirmedReceivedAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        if (supplyOrderPO.getSupplyOrderAmt().compareTo(supplyOrderFinanceUpdate.getPaidAmt())==0){
            supplyOrderFinanceUpdate.setRealSettlementDate(new Date());
            supplyOrderFinanceUpdate.setSettlementStatus(1);
        }else {
            supplyOrderFinanceUpdate.setSettlementStatus(0);
            supplyOrderFinanceUpdate.setRealSettlementDate(null);
        }
        supplyOrderFinanceUpdate.setFinanceLockStatus(0);
        supplyOrderFinanceMapper.updateByPrimaryKey(supplyOrderFinanceUpdate);

        // 2.记日志
        StringBuilder content = new StringBuilder();
        content.append("供货单").append(supplyOrderPO.getSupplyOrderCode()).append("财务回传结果，");
        if (request.getConfirmAmt().compareTo(BigDecimal.ZERO)>0){
            content.append("确认付款金额：").append(request.getConfirmAmt());
        }else{
            content.append("确认收款金额：").append(BigDecimal.ZERO.subtract(request.getConfirmAmt()));
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

    @Transactional
    @Override
    public Response cancelSupplyOrderWorkOrder(CancelSupplyOrderWorkOrderDTO request) {
        log.info("cancelSupplyOrderWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        SupplyOrderPO supplyOrderQuery=new SupplyOrderPO();
        supplyOrderQuery.setSupplyOrderCode(request.getSupplyOrderCode());
        SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectOne(supplyOrderQuery);

        OrderPO orderPO=orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        SupplyOrderFinancePO supplyOrderFinanceQuery=new SupplyOrderFinancePO();
        supplyOrderFinanceQuery.setSupplyOrderCode(request.getSupplyOrderCode());
        SupplyOrderFinancePO supplyOrderFinancePO=supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);

        // 1.更新已通知金额
        SupplyOrderFinancePO supplyOrderFinanceUpdate=new SupplyOrderFinancePO();
        supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            supplyOrderFinanceUpdate.setUnconfirmedPaidAmt(supplyOrderFinancePO.getUnconfirmedPaidAmt().subtract(request.getNotifyAmt()));
        }else{
            supplyOrderFinanceUpdate.setUnconfirmedReceivedAmt(supplyOrderFinancePO.getUnconfirmedReceivedAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);

        // 2.记日志
        StringBuilder content = new StringBuilder();
        content.append("供货单").append(supplyOrderPO.getSupplyOrderCode()).append("财务回传结果，");
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            content.append("取消通知付款金额：").append(request.getNotifyAmt());
        }else{
            content.append("取消通知收款金额：").append(BigDecimal.ZERO.subtract(request.getNotifyAmt()));
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
}
