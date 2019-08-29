package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.OrderRequestPO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.response.OrderRequestCountDTO;

import java.util.List;

public interface OrderRequestMapper extends MyMapper<OrderRequestPO> {

    List<OrderRequestCountDTO> queryOrderRequestCount(List<Integer> orderIdList);

    OrderRequestCountDTO queryOrderRequestStatistics(QueryOrderStatisticsDTO request);
}