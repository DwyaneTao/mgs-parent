package com.mgs.order.service;

import com.mgs.common.Response;
import com.mgs.order.remote.request.CancelOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderWorkOrderDTO;

public interface OrderFinanceHandle {

    /**
     * 确认工单
     */
    Response confirmOrderWorkOrder(ConfirmOrderWorkOrderDTO request);

    /**
     * 取消工单
     */
    Response cancelOrderWorkOrder(CancelOrderWorkOrderDTO request);
}
