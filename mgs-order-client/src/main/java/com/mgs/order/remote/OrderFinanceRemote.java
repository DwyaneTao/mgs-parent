package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.NotifyCollectionOfOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-order-server")
public interface OrderFinanceRemote {

    /**
     * 单结订单查询
     */
    @PostMapping("/order/finance/queryOnTimeOrderList")
    Response queryOnTimeOrderList(@RequestBody QueryOnTimeOrderListDTO request);

    /**
     * 通知收款
     */
    @PostMapping("/order/finance/notifyCollectionOfOrder")
    Response notifyCollectionOfOrder(@RequestBody NotifyCollectionOfOrderDTO request);

    /**
     * 通知付款
     */
    @PostMapping("/order/finance/notifyPaymentOfOrder")
    Response notifyPaymentOfOrder(@RequestBody NotifyPaymentOfOrderDTO request);

    /**
     * 合并通知财务收款
     */
    @PostMapping("/order/finance/notifyCollectionOfOrderList")
    Response notifyCollectionOfOrderList(@RequestBody NotifyCollectionOfOrderListDTO request);

    /**
     * 合并通知财务付款
     */
    @PostMapping("/order/finance/notifyPaymentOfOrderList")
    Response notifyPaymentOfOrderList(@RequestBody NotifyPaymentOfOrderListDTO request);

    /**
     * 合并通知财务收款预览
     */
    @PostMapping("/order/finance/notifyCollectionPreviewOfOrderList")
    Response notifyCollectionPreviewOfOrderList(@RequestBody OrderIdListDTO request);

    /**
     * 合并通知财务付款预览
     */
    @PostMapping("/order/finance/notifyPaymentPreviewOfOrderList")
    Response notifyPaymentPreviewOfOrderList(@RequestBody OrderIdListDTO request);
}
