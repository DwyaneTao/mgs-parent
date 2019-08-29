package com.mgs.dis.dto;

import lombok.Data;

/**
 * 销售价格
 */
@Data
public class SalePriceDTO {

    /**
     * 加幅id
     */
    private Integer dayIncreaseId;


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
    private String b2bModifiedAmt;

    /**
     * B2C调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer b2cAdjustmentType;

    /**
     * B2C调整金额
     */
    private String b2cModifiedAmt;

    /**
     * 携程调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer ctripAdjustmentType;

    /**
     * 携程调整方式调整金额
     */
    private String ctripModifiedAmt;

    /**
     * 美团调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer meituanAdjustmentType;

    /**
     * 美团调整金额
     */
    private String meituanModifiedAmt;


    /**
     * 飞猪调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer feizhuAdjustmentType;

    /**
     * 飞猪调整金额
     */
    private String feizhuModifiedAmt;


    /**
     * 同程艺龙调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer tcylAdjustmentType;

    /**
     * 同程艺龙调整金额
     */
    private String tcylModifiedAmt;

    /**
     * 去哪儿调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer qunarAdjustmentType;

    /**
     * 去哪儿调整金额
     */
    private String qunarModifiedAmt;

}
