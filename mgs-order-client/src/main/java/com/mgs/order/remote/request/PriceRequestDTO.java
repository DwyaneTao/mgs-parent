package com.mgs.order.remote.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class

PriceRequestDTO implements Serializable {

    /**
     * 售卖日期
     */
    private String saleDate;

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 底价
     */
    private BigDecimal basePrice;
}
