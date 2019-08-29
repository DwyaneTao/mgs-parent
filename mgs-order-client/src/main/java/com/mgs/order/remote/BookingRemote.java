package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.AddManualOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-order-server")
public interface BookingRemote {

    /**
     * 新建手工单
     * @param request
     * @return
     */
    @PostMapping("/order/addManualOrder")
    Response addManualOrder(@RequestBody AddManualOrderDTO request);

    /**
     * 渠道下单
     * @param request
     * @return
     */
    @PostMapping("/order/addOTAOrder")
    Response addOTAOrder(@RequestBody AddManualOrderDTO request);
}
