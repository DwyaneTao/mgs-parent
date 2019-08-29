package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyProductPriceDTO implements Serializable{

    /**
     * 售卖日期
     */
    private String saleDate;

    /**
     * 底价
     */
    private BigDecimal basePrice;

    /**
     * 扣配额数
     */
    private Integer debuctedQuota;

    /**
     * 剩余配额数
     */
    private Integer remainingQuota;
}
