package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.CancelSupplyOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmSupplyOrderWorkOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-order-server")
public interface SupplyOrderFinanceHandleRemote {

    /**
     * 确认工单
     */
    @PostMapping("/order/finance/confirmSupplyOrderWorkOrder")
    Response confirmSupplyOrderWorkOrder(@RequestBody ConfirmSupplyOrderWorkOrderDTO request);

    /**
     * 取消工单
     */
    @PostMapping("/order/finance/cancelSupplyOrderWorkOrder")
    Response cancelSupplyOrderWorkOrder(@RequestBody CancelSupplyOrderWorkOrderDTO request);
}
