package com.mgs.product.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 01:15
 * @Description: 产品单日行情
 */
@Data
public class ProductDayQuotationDTO extends BaseDTO {

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 配额账号
     */
    private Integer quotaAccountId;

    /**
     * 售卖日期
     */
    private String saleDate;

    /**
     * 底价
     */
    private BigDecimal modifiedBasePrice;

    /**
     * 房态(-1表示不变 0关房 1开房)
     */
    private Integer roomStatus;

    /**
     * 配额数
     */
    private Integer modifiedQuota;

    /**
     * 配额调整方式（0加 1减 2等于）
     */
    private Integer quotaAdjustmentType;

    /**
     * 底价调整方式（0加 1减 2等于）
     */
    private Integer basePriceAdjustmentType;

    /**
     * 售罄设置（1可超 0不可超 -1表示不变）
     */
    private Integer overDraftStatus;
}
