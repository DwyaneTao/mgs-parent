package com.mgs.order.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.order.remote.response.PriceResponseDTO;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.request.QueryConfirmOrderInfoDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderRemarkDTO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.request.SupplyOrderIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.OrderDTO;
import com.mgs.order.remote.response.OrderLogDTO;
import com.mgs.order.remote.response.OrderRemarkDTO;
import com.mgs.order.remote.response.OrderRequestDTO;
import com.mgs.order.remote.response.OrderSimpleDTO;
import com.mgs.order.remote.response.OrderStatisticsDTO;
import com.mgs.order.remote.response.SupplyOrderPreviewDTO;
import com.mgs.order.remote.response.SupplyProductDetailDTO;
import com.mgs.order.remote.response.SupplyResultDTO;

import java.util.List;

public interface OrderQueryService {

    /**
     * 查询订单列表
     * @param request
     * @return
     */
    PaginationSupportDTO<OrderSimpleDTO> queryOrderList(QueryOrderListDTO request);

    /**
     * 查询订单列表统计
     * @return
     */
    OrderStatisticsDTO queryOrderStatistics(QueryOrderStatisticsDTO request);

    /**
     * 查询订单详情
     * @param request
     * @return
     */
    OrderDTO queryOrderDetail(OrderCodeDTO request);

    /**
     * 查询订单备注
     * @param request
     * @return
     */
    List<OrderRemarkDTO> queryOrderRemark(QueryOrderRemarkDTO request);

    /**
     * 查询订单日志
     * @param request
     * @return
     */
    List<OrderLogDTO> queryOrderLog(OrderIdDTO request);

    /**
     * 查询订单申请
     * @param request
     * @return
     */
    List<OrderRequestDTO> queryOrderRequest(OrderIdDTO request);

    /**
     * 查询订单确认信息
     * @param request
     * @return
     */
    String queryConfirmOrderInfo(QueryConfirmOrderInfoDTO request);

    /**
     * 查询订单价格明细
     * @param request
     * @return
     */
    List<PriceResponseDTO> queryOrderPriceItem(OrderIdDTO request);

    /**
     * 供货单预览
     * @param request
     * @return
     */
    SupplyOrderPreviewDTO previewSupplyOrder(SupplyOrderIdDTO request);

    /**
     * 查询供货单结果
     * @param request
     * @return
     */
    SupplyResultDTO querySupplyOrderResult(SupplyOrderIdDTO request);

    /**
     * 查询供货单产品详情
     * @param request
     * @return
     */
    SupplyProductDetailDTO querySupplyProduct(SupplyProductIdDTO request);
}
