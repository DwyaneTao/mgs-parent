package com.mgs.dis.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品售价
 */
@Data
public class ProductSaleIncreaseDTO {

    /**
     * 产品id
     */
    private String productId;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * redisKey
     */
    private String redisKey;

    /**
     * 售卖日期
     */
    private String saleDate;

    /**
     * B2B调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer b2bAdjustmentType;

    /**
     * B2B调整金额
     */
    private BigDecimal b2bModifiedAmt;

    /**
     * B2C调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer b2cAdjustmentType;

    /**
     * B2C调整金额
     */
    private BigDecimal b2cModifiedAmt;

    /**
     * 携程调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer ctripAdjustmentType;

    /**
     * 携程调整方式调整金额
     */
    private BigDecimal ctripModifiedAmt;

    /**
     * 美团调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer meituanAdjustmentType;

    /**
     * 美团调整金额
     */
    private BigDecimal meituanModifiedAmt;


    /**
     * 飞猪调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer feizhuAdjustmentType;

    /**
     * 飞猪调整金额
     */
    private BigDecimal feizhuModifiedAmt;


    /**
     * 同程艺龙调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer tcylAdjustmentType;

    /**
     * 同程艺龙调整金额
     */
    private BigDecimal tcylModifiedAmt;

    /**
     * 去哪儿调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer qunarAdjustmentType;

    /**
     * 去哪儿调整金额
     */
    private BigDecimal qunarModifiedAmt;
}
