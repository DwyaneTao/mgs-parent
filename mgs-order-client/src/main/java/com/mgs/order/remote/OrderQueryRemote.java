package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.request.QueryConfirmOrderInfoDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderRemarkDTO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.request.SupplyOrderIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-order-server")
public interface OrderQueryRemote {

    /**
     * 查询订单列表
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderList")
    Response queryOrderList(@RequestBody QueryOrderListDTO request);

    /**
     * 查询订单列表统计
     * @return
     */
    @PostMapping("/order/queryOrderStatistics")
    Response queryOrderStatistics(@RequestBody QueryOrderStatisticsDTO request);

    /**
     * 查询订单详情
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderDetail")
    Response queryOrderDetail(@RequestBody OrderCodeDTO request);

    /**
     * 查询订单备注
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderRemark")
    Response queryOrderRemark(@RequestBody QueryOrderRemarkDTO request);

    /**
     * 查询订单日志
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderLog")
    Response queryOrderLog(@RequestBody OrderIdDTO request);

    /**
     * 查询订单申请
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderRequest")
    Response queryOrderRequest(@RequestBody OrderIdDTO request);

    /**
     * 查询订单确认信息
     * @param request
     * @return
     */
    @PostMapping("/order/queryConfirmOrderInfo")
    Response queryConfirmOrderInfo(@RequestBody QueryConfirmOrderInfoDTO request);

    /**
     * 查询订单价格明细
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderPriceItem")
    Response queryOrderPriceItem(@RequestBody OrderIdDTO request);

    /**
     * 供货单预览
     * @param request
     * @return
     */
    @PostMapping("/order/previewSupplyOrder")
    Response previewSupplyOrder(@RequestBody SupplyOrderIdDTO request);

    /**
     * 查询供货单结果
     * @param request
     * @return
     */
    @PostMapping("/order/querySupplyOrderResult")
    Response querySupplyOrderResult(@RequestBody SupplyOrderIdDTO request);

    /**
     * 查询供货单产品详情
     * @param request
     * @return
     */
    @PostMapping("/order/querySupplyProduct")
    Response querySupplyProduct(@RequestBody SupplyProductIdDTO request);
}
