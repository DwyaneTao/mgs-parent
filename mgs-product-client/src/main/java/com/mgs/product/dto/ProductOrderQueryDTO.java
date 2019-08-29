package com.mgs.product.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author py
 * @date 2019/7/17 9:34
 * 订单所需产品列表
 **/
@Data
public class ProductOrderQueryDTO {
    /**
     * 产品Id
     */
    private  Integer productId;
    /**
     * 产品名称
     */
    private  String productName;
    /**
     * 房型Id
     */
    private  Integer roomId;
    /**
     * 房型名称
     */
    private  String roomName;
    /**
     * 床型
     */
    private  String bedTypes;
    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购方式（0自签协议房 1自签包房）
     */
    private Integer purchaseType;
    /**
     * 间数
     */
    private  Integer roomQty;
    /**
     * 底价总和，计算逻辑：sum（每日价格）*roomQty
     */
    private BigDecimal totalAmt;
    /**
     * 早餐数
     */
    private Integer breakfastQty;
    /**
     * 币种
     */
    private Integer currency;

}
