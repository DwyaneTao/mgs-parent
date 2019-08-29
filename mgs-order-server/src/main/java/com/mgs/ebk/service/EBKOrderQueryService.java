package com.mgs.ebk.service;

import com.mgs.common.Response;
import com.mgs.ebk.order.remote.request.CancelSupplyOrderDTO;
import com.mgs.ebk.order.remote.request.ConfirmSupplyOrderDTO;
import com.mgs.ebk.order.remote.request.QuerySupplyOrderListDTO;

import java.util.Map;

public interface EBKOrderQueryService {

    /**
     * 查询供货单
     * @param request
     * @return
     */
    Response querySupplyOrderList(QuerySupplyOrderListDTO request);


    /**
     * 查询供货单详情
     * @param request
     * @return
     */
    Response querySupplyOrderDetail(Map<String,String> request);


    /**
     * 确认供货单
     * @param request
     * @return
     */
    Response confirmSupplyOrder(ConfirmSupplyOrderDTO request);


    /**
     * 查询日志
     * @param request
     * @return
     */
    Response querySupplyOrderLog(Map<String,String> request);


    /**
     * 取消供货单
     * @param request
     * @return
     */
    Response cancelSupplyOrder(CancelSupplyOrderDTO request);


    /**
     * 订单统计
     * @param request
     * @return
     */
    Response queryOrderStatistics(Map<String,String> request);


}
