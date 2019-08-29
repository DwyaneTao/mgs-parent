package com.mgs.product.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 00:19
 * @Description: 产品数据查询临时对象
 */
@Data
public class ProductTempDTO {

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 房型床型
     */
    private String roomBedTypes;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 早餐数
     */
    private Integer breakfastQty;

    /**
     * 床型
     */
    private String bedTypes;

    /**
     * 销售中渠道
     */
    private String onSaleChannels;

    /**
     * 仓库中渠道
     */
    private String offShelveChannels;

    /**
     * 包房款Id
     */
    private Integer advancePaymentId;

    /**
     * 包房款名称
     */
    private String advancePaymentName;

    /**
     * 币种
     */
    private Integer currency;

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

    /**
     * B2B售卖状态
     */
    private Integer b2bSaleStatus;

    /**
     * 携程售卖状态
     */
    private Integer ctripSaleStatus;

    /**
     * 美团售卖状态
     */
    private Integer meituanSaleStatus;

    /**
     * 飞猪售卖状态
     */
    private Integer feizhuSaleStatus;

    /**
     * 同程艺龙售卖状态
     */
    private Integer tcylSaleStatus;

    /**
     * B2C售卖状态
     */
    private Integer b2cSaleStatus;

    /**
     * 去哪儿售卖状态
     */
    private Integer qunarSaleStatus;

    /**
     * 停售状态
     */
    private Integer offShelveStatus;
}

