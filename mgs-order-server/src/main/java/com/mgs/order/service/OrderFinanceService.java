package com.mgs.order.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.order.remote.request.NotifyCollectionOfOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import com.mgs.order.remote.response.OnTimeOrderDTO;

public interface OrderFinanceService {

    /**
     * 单结订单查询
     */
    PaginationSupportDTO<OnTimeOrderDTO> queryOnTimeOrderList(QueryOnTimeOrderListDTO request);

    /**
     * 通知收款
     */
    Response notifyCollectionOfOrder(NotifyCollectionOfOrderDTO request);

    /**
     * 通知付款
     */
    Response notifyPaymentOfOrder(NotifyPaymentOfOrderDTO request);

    /**
     * 合并通知财务收款
     */
    Response notifyCollectionOfOrderList(NotifyCollectionOfOrderListDTO request);

    /**
     * 合并通知财务付款
     */
    Response notifyPaymentOfOrderList(NotifyPaymentOfOrderListDTO request);

    /**
     * 合并通知财务收款预览
     */
    Response notifyCollectionPreviewOfOrderList(OrderIdListDTO request);

    /**
     * 合并通知财务付款预览
     */
    Response notifyPaymentPreviewOfOrderList(OrderIdListDTO request);
}
