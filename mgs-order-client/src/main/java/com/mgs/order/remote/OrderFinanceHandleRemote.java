package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.CancelOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderWorkOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-order-server")
public interface OrderFinanceHandleRemote {

    /**
     * 确认工单
     */
    @PostMapping("/order/finance/handle/confirmOrderWorkOrder")
    Response confirmOrderWorkOrder(@RequestBody ConfirmOrderWorkOrderDTO request);

    /**
     * 取消工单
     */
    @PostMapping("/order/finance/handle/cancelOrderWorkOrder")
    Response cancelOrderWorkOrder(@RequestBody CancelOrderWorkOrderDTO request);
}
