package com.mgs.ebk.mapper;

import com.mgs.common.MyMapper;
import com.mgs.ebk.domain.EBKSupplyOrderPO;
import com.mgs.ebk.domain.EBKSupplyOrderRemarkPO;
import com.mgs.ebk.order.remote.request.QuerySupplyOrderListDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderDetailDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderListDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderStatistics;

import java.util.List;
import java.util.Map;

public interface EBKSupplyOrderMapper extends MyMapper<EBKSupplyOrderPO> {

    List<SupplyOrderListDTO> queryOrderList(QuerySupplyOrderListDTO request);

    SupplyOrderDetailDTO queryOrderDetail(Map<String, String> request);

    SupplyOrderStatistics queryOrderStatistics(Map<String, String> request);

    List<EBKSupplyOrderRemarkPO> querySupplyOrderRemak(Map<String, Integer> request);


}
