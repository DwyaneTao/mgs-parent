package com.mgs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 00:13
 * @Description: 产品每日行情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleItemDTO implements Serializable {

    /**
     * 日期
     */
    private String saleDate;

    /**
     * 底价
     */
    private BigDecimal basePrice;

    /**
     * 总配额数
     */
    private Integer quota;

    /**
     * 剩余配额数
     */
    private Integer remainingQuota;
    /**
     * 已售配额数
     */
     private  Integer soldQuota;
    /**
     * 房态
     */
    private Integer roomStatus;

    /**
     * 可超状态（0不可超 1可超）
     */
    private Integer overDraftStatus;
}
