package com.mgs.order.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderListDTO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.request.SupplyOrderIdListDTO;
import com.mgs.order.remote.response.OnTimeSupplyOrderDTO;

public interface SupplyOrderFinanceService {

    /**
     * 单结订单查询
     */
    PaginationSupportDTO<OnTimeSupplyOrderDTO> queryOnTimeSupplyOrderList(QueryOnTimeSupplyOrderListDTO request);

    /**
     * 通知收款
     */
    Response notifyCollectionOfSupplyOrder(NotifyCollectionOfSupplyOrderDTO request);

    /**
     * 通知付款
     */
    Response notifyPaymentOfSupplyOrder(NotifyPaymentOfSupplyOrderDTO request);

    /**
     * 合并通知财务收款
     */
    Response notifyCollectionOfSupplyOrderList(NotifyCollectionOfSupplyOrderListDTO request);

    /**
     * 合并通知财务付款
     */
    Response notifyPaymentOfSupplyOrderList(NotifyPaymentOfSupplyOrderListDTO request);

    /**
     * 合并通知财务收款预览
     */
    Response notifyCollectionPreviewOfSupplyOrderList(SupplyOrderIdListDTO request);

    /**
     * 合并通知财务付款预览
     */
    Response notifyPaymentPreviewOfSupplyOrderList(SupplyOrderIdListDTO request);
}
