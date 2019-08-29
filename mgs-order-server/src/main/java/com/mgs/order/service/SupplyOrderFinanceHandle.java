package com.mgs.order.service;

import com.mgs.common.Response;
import com.mgs.order.remote.request.CancelSupplyOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmSupplyOrderWorkOrderDTO;

public interface SupplyOrderFinanceHandle {

    /**
     * 确认工单
     */
    Response confirmSupplyOrderWorkOrder(ConfirmSupplyOrderWorkOrderDTO request);

    /**
     * 取消工单
     */
    Response cancelSupplyOrderWorkOrder(CancelSupplyOrderWorkOrderDTO request);
}
