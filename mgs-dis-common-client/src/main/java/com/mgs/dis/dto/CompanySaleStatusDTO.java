package com.mgs.dis.dto;

import lombok.Data;

/**
 * 商家对应的渠道信息
 */
@Data
public class CompanySaleStatusDTO {

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 有效性
     */
    private Integer active;

    /**
     * B2B销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer b2bSaleStatus;

    /**
     * B2C销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer b2cSaleStatus;

    /**
     * 携程销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer ctripSaleStatus;

    /**
     * 美团销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer meituanSaleStatus;

    /**
     * 飞猪销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer feizhuSaleStatus;

    /**
     * 同城艺龙销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer tcylSaleStatus;

    /**
     * 去哪儿销售状态(1售卖中 0库存中 null库存中)
     */
    private Integer qunarSaleStatus;
}
