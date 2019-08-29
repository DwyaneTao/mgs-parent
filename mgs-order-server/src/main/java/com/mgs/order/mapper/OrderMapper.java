package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.response.ChannelOrderQtyDTO;
import com.mgs.order.remote.response.OnTimeOrderDTO;
import com.mgs.order.remote.response.OrderSimpleDTO;
import com.mgs.order.remote.response.OrderStatisticsDTO;
import com.mgs.order.remote.response.SupplyOrderAmt;
import feign.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderMapper extends MyMapper<OrderPO> {

    List<OrderSimpleDTO> queryOrderList(QueryOrderListDTO request);

    OrderStatisticsDTO queryOrderStatistics(QueryOrderStatisticsDTO request);

    List<ChannelOrderQtyDTO> queryChannelOrderQty(QueryOrderStatisticsDTO request);

    List<OnTimeOrderDTO> queryOnTimeOrderList(QueryOnTimeOrderListDTO request);

    List<String> queryBetweenDate(Map<String,String> request);

    Integer  querySettlementStatus(@Param("orderId")Integer orderId);

    List<SupplyOrderAmt> querySupplyOrderAmt(List request);
}