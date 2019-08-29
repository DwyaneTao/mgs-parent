package com.mgs.order.dto;

import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.domain.SupplyOrderPO;
import lombok.Data;

import java.util.List;

@Data
public class AssemblySupplyOrderDTO {

    /**
     * 供货单
     */
    private SupplyOrderPO supplyOrder;

    /**
     * 供货单财务信息
     */
    private SupplyOrderFinancePO supplyOrderFinance;

    /**
     * 供货产品
     */
    private List<AssemblySupplyProductDTO> supplyProductList;
}
