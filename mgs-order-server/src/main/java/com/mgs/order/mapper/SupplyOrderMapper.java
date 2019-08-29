package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.response.OnTimeSupplyOrderDTO;

import java.util.List;

public interface SupplyOrderMapper extends MyMapper<SupplyOrderPO> {

    /**
     * 单结订单查询
     */
    List<OnTimeSupplyOrderDTO> queryOnTimeSupplyOrderList(QueryOnTimeSupplyOrderListDTO request);
}