package com.mgs.ebk.order.remote.response;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SupplyOrderDetailDTO implements Serializable {



    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 订单单id
     */
    private Integer orderId;


    /**
     * 单号编码
     */
    private String orderCode;


    /**
     * 供货单状态：0待确认，1已确认，2已取消
     */
    private Integer orderConfirmationStatus;

    /**
     * 酒单名称
     */
    private String hotelName;


    /**
     * 酒单名称
     */
    private Integer hotelId;


    /**
     * 产品名称
     */
    private String productName;


    /**
     * 入店
     */
    private String startDate;

    /**
     * 离店
     */
    private String endDate;


    /**
     * 晚数
     */
    private Integer nightQty;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 入住人
     */
    private String guest;


    /**
     * 特殊要求
     */
    private String specialRequest;


    /**
     * 确认号
     */
    private String confirmationCode;


    /**
     * 创建时间
     */
    private String createdDt;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;


    /**
     * 售价
     */
    private BigDecimal salePrice;


    /**
     * 退订费
     */
    private BigDecimal refundFee;


    /**
     * 结算方式
     */
    private Integer settlementType;


    /**
     * 结算状态
     */
    private Integer settlementStatus;


    /**
     * 已收金额
     */
    private BigDecimal receivedAmt;


    /**
     * 未收金额
     */
    private BigDecimal unreceivedAmt;


    /**
     * 备注
     */
    private SupplyOrderRemark remark;



    /**
     * 申请内容
     */
    private String  applicationContent;


    /**
     * 申请类型
     */
    private Integer applicationType;


}
