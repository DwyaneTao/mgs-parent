package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.SupplyProductPricePO;
import com.mgs.order.remote.response.PriceResponseDTO;

import java.util.List;

public interface SupplyProductPriceMapper extends MyMapper<SupplyProductPricePO> {

    List<PriceResponseDTO> querySupplyOrderPriceList(Integer supplyOrderId);
}