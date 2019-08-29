package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyOrderPreviewDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 供货单编码
     */
    private String supplyOrderCode;

    /**
     * 发单状态：0未发单，1已发预订单，2已重发预订单，3已发修改单，4已发取消单
     */
    private Integer sendingStatus;

    /**
     * 确认状态：0未确认，1确认
     */
    private Integer confirmationStatus;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 酒店地址
     */
    private String hotelAddress;

    /**
     * 酒店电话
     */
    private String hotelTel;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 特殊要求
     */
    private String specialRequest;

    /**
     * 供货单金额
     */
    private BigDecimal supplyOrderAmt;

    /**
     * 底价币种
     */
    private Integer baseCurrency;

    /**
     * 产品列表
     */
    private List<SupplyProductPreviewDTO> productList;
}
