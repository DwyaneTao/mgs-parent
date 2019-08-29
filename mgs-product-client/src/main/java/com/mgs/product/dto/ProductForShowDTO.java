package com.mgs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 00:10
 * @Description: 产品显示接口
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForShowDTO implements Serializable {

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
     * 当前渠道销售状态（1销售中 0仓库中）
     */
    private Integer saleStatus;

    /**
     * 采购方式（0自签协议房 1自签包房）
     */
    private Integer purchaseType;

    /**
     * 供货方式（0运营商自建 1供应商EBK录入 2api对接）
     */
    private Integer supplyType;

    /**
     * 床型
     */
    private String bedTypes;

    /**
     * 是否选中
     */
    private Integer selected;

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
    private Integer soldQuota;

    /**
     * 渠道售卖状态（0未推送 1已推送）
     */
    private Integer channelSaleStatus;

    /**
     * 价格列表
     */
    private List<ProductSaleItemDTO> saleItemList;
}
