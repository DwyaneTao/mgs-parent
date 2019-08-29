package com.mgs.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.mapper.OrderFinanceMapper;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.remote.request.CancelOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderWorkOrderDTO;
import com.mgs.order.service.OrderFinanceHandle;
import com.mgs.order.service.common.OrderCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class OrderFinanceHandleImpl implements OrderFinanceHandle {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderFinanceMapper orderFinanceMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    @Transactional
    @Override
    public Response confirmOrderWorkOrder(ConfirmOrderWorkOrderDTO request) {
        log.info("confirmOrderWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        OrderPO orderQuery=new OrderPO();
        orderQuery.setOrderCode(request.getOrderCode());
        OrderPO orderPO=orderMapper.selectOne(orderQuery);

        OrderFinancePO orderFinanceQuery=new OrderFinancePO();
        orderFinanceQuery.setOrderCode(request.getOrderCode());
        OrderFinancePO orderFinancePO=orderFinanceMapper.selectOne(orderFinanceQuery);

        // 1.更新订单结算状态及结算金额
        OrderFinancePO orderFinanceUpdate=orderFinanceMapper.selectByPrimaryKey(orderFinancePO.getId());
        orderFinanceUpdate.setReceivedAmt(orderFinancePO.getReceivedAmt().add(request.getConfirmAmt()));
        orderFinanceUpdate.setUnreceivedAmt(orderFinancePO.getUnreceivedAmt().subtract(request.getConfirmAmt()));
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            orderFinanceUpdate.setUnconfirmedReceivedAmt(orderFinancePO.getUnconfirmedReceivedAmt().subtract(request.getNotifyAmt()));
        }else{
            orderFinanceUpdate.setUnconfirmedPaidAmt(orderFinancePO.getUnconfirmedPaidAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        if (orderPO.getOrderAmt().compareTo(orderFinanceUpdate.getReceivedAmt())==0){
            orderFinanceUpdate.setRealSettlementDate(new Date());
            orderFinanceUpdate.setSettlementStatus(1);
        }else {
            orderFinanceUpdate.setRealSettlementDate(null);
            orderFinanceUpdate.setSettlementStatus(0);
        }
        orderFinanceUpdate.setFinanceLockStatus(0);
        orderFinanceMapper.updateByPrimaryKey(orderFinanceUpdate);

        // 2.记日志
        StringBuilder content = new StringBuilder();
        content.append("订单财务回传结果，");
        if (request.getConfirmAmt().compareTo(BigDecimal.ZERO)>0){
            content.append("确认收款金额：").append(request.getConfirmAmt());
        }else{
            content.append("确认付款金额：").append(BigDecimal.ZERO.subtract(request.getConfirmAmt()));
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
    public Response cancelOrderWorkOrder(CancelOrderWorkOrderDTO request) {
        log.info("cancelOrderWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        OrderPO orderQuery=new OrderPO();
        orderQuery.setOrderCode(request.getOrderCode());
        OrderPO orderPO=orderMapper.selectOne(orderQuery);

        OrderFinancePO orderFinanceQuery=new OrderFinancePO();
        orderFinanceQuery.setOrderCode(request.getOrderCode());
        OrderFinancePO orderFinancePO=orderFinanceMapper.selectOne(orderFinanceQuery);

        // 1.更新已通知金额
        OrderFinancePO orderFinanceUpdate=new OrderFinancePO();
        orderFinanceUpdate.setId(orderFinancePO.getId());
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            orderFinanceUpdate.setUnconfirmedReceivedAmt(orderFinancePO.getUnconfirmedReceivedAmt().subtract(request.getNotifyAmt()));
        }else{
            orderFinanceUpdate.setUnconfirmedPaidAmt(orderFinancePO.getUnconfirmedPaidAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);

        // 2.记日志
        StringBuilder content = new StringBuilder();
        content.append("订单财务回传结果，");
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            content.append("取消通知收款金额：").append(request.getNotifyAmt());
        }else{
            content.append("取消通知付款金额：").append(BigDecimal.ZERO.subtract(request.getNotifyAmt()));
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
