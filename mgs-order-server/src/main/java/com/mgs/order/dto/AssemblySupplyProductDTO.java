package com.mgs.order.dto;

import com.mgs.order.domain.SupplyProductPO;
import com.mgs.order.domain.SupplyProductPricePO;
import lombok.Data;

import java.util.List;

@Data
public class AssemblySupplyProductDTO {

    /**
     * 供货产品
     */
    private SupplyProductPO supplyProduct;

    /**
     * 供货产品价格明细
     */
    private List<SupplyProductPricePO> supplyProductPriceList;
}
