package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.dto.SupplyOrderAmtDTO;
import com.mgs.order.remote.request.SupplyOrderIdListDTO;

import java.util.List;

public interface SupplyOrderFinanceMapper extends MyMapper<SupplyOrderFinancePO> {

    /**
     * 查询供货单金额
     */
    List<SupplyOrderAmtDTO> querySupplyOrderAmt(SupplyOrderIdListDTO request);
}