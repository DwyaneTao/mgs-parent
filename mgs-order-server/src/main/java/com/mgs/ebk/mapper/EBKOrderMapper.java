package com.mgs.ebk.mapper;

import com.mgs.common.MyMapper;
import com.mgs.ebk.order.remote.request.QuerySupplyOrderListDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderDetailDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderListDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderStatistics;
import com.mgs.order.domain.SupplyOrderPO;

import java.util.List;
import java.util.Map;

public interface EBKOrderMapper extends MyMapper<SupplyOrderPO> {

    SupplyOrderDetailDTO queryOrderDetail(Map<String, String> request);


}
