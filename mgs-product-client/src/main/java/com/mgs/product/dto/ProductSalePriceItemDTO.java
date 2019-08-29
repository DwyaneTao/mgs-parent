package com.mgs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 01:47
 * @Description: 售价详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class   ProductSalePriceItemDTO implements Serializable {

    /**
     * 日期
     */
    private String saleDate;

    /**
     * 底价
     */
    private BigDecimal basePrice;
    /**
     *折合低价
     */
    private  BigDecimal equivalentBasePrice;
    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 总配额数
     */
    private Integer quota;

    /**
     * 剩余配额数
     */
    private Integer remainingQuota;
    /**
     * 售罄设置（0不可超， 1可超）
     */
    private  Integer overDraftStatus;
    /**
     * 关房设置（1开房 0关房）
     */
    private  Integer  roomStatus;
}
