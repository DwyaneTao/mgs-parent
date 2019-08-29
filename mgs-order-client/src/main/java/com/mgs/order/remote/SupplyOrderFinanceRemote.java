package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderListDTO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.request.SupplyOrderIdListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-order-server")
public interface SupplyOrderFinanceRemote {

    /**
     * 单结订单查询
     */
    @PostMapping("/order/finance/queryOnTimeSupplyOrderList")
    Response queryOnTimeSupplyOrderList(@RequestBody QueryOnTimeSupplyOrderListDTO request);

    /**
     * 通知收款
     */
    @PostMapping("/order/finance/notifyCollectionOfSupplyOrder")
    Response notifyCollectionOfSupplyOrder(@RequestBody NotifyCollectionOfSupplyOrderDTO request);

    /**
     * 通知付款
     */
    @PostMapping("/order/finance/notifyPaymentOfSupplyOrder")
    Response notifyPaymentOfSupplyOrder(@RequestBody NotifyPaymentOfSupplyOrderDTO request);

    /**
     * 合并通知财务收款
     */
    @PostMapping("/order/finance/notifyCollectionOfSupplyOrderList")
    Response notifyCollectionOfSupplyOrderList(@RequestBody NotifyCollectionOfSupplyOrderListDTO request);

    /**
     * 合并通知财务付款
     */
    @PostMapping("/order/finance/notifyPaymentOfSupplyOrderList")
    Response notifyPaymentOfSupplyOrderList(@RequestBody NotifyPaymentOfSupplyOrderListDTO request);

    /**
     * 合并通知财务收款预览
     */
    @PostMapping("/order/finance/notifyCollectionPreviewOfSupplyOrderList")
    Response notifyCollectionPreviewOfSupplyOrderList(@RequestBody SupplyOrderIdListDTO request);

    /**
     * 合并通知财务付款预览
     */
    @PostMapping("/order/finance/notifyPaymentPreviewOfSupplyOrderList")
    Response notifyPaymentPreviewOfSupplyOrderList(@RequestBody SupplyOrderIdListDTO request);
}
