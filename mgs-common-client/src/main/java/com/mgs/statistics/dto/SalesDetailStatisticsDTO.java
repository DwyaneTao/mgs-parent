package com.mgs.statistics.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesDetailStatisticsDTO {

    /**
     * 订单编码
     */
    private  String orderCode;

    /**
     * 酒店名称
     */
    private  String hotelName;

    /**
     * 房型名称
     */
    private  String roomName;

    /**
     * 产品名称
     */
    private String productName;


    /**
     * 下单日期
     */
    private  String createdDt;

    /**
     * 入住日期
     */
    private  String startDate;

    /**
     * 离店日期
     */
    private  String endDate;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 间夜
     */
    private  Integer NightQty;

    /**
     * 入住人
     */
    private  String guest;

    /**
     * 订单确认状态
     */
    private  Integer orderConfirmationStatus;

    /**
     * 订单结算方式
     */
    private Integer orderSettlementType;

    /**
     * 下单人
     */
    private  String createdBy;

    /**
     * 归属人
     */
    private  String orderOwnerName;

    /**
     * 销售经理
     */
    private  String saleManagerName;

    /**
     * 客户类型
     */
    private String agentType;


    /**
     * 客户名
     */
    private  String agentName;

    /**
     * 客户编码
     */
    private  String agentCode;

    /**
     * 渠道单号
     */
    private  String channelOrderCode;

    /**
     * 销售币种
     */
    private Integer saleCurrency;


    /**
     * 应收金额
     */
    private  BigDecimal receivableAmt;

    /**
     * 实收金额
     */
    private  BigDecimal receivedAmt;

    /**
     * 未收金额
     */
    private  BigDecimal unreceivedAmt;







    /**
     * 供货单编码
     */
    private String supplyOrderCode;

    /**
     * 房型名称
     */
    private  String supplyOrderRoomName;

    /**
     * 产品名称
     */
    private String supplyOrderProductName;


    /**
     * 入住日期
     */
    private  String supplyOrderStartDate;

    /**
     * 离店日期
     */
    private  String supplyOrderEndDate;

    /**
     * 间数
     */
    private  Integer supplyOrderRoomQty;


    /**
     * 间数
     */
    private  Integer supplyOrderNightQty;

    /**
     * 入住人
     */
    private String supplyOrderGuest;


    /**
     * 供货单确认状态
     */
    private  Integer supplyOrderConfirmationStatus;

    /**
     * 采购类型
     */
    private  Integer purchaseType;

    /**
     * 供货单结算方式
     */
    private  Integer supplyOrderSettlementType;


    /**
     * 采购经理
     */
    private  String purchaseManagerName;

    /**
     * 供应商名称
     */
    private String supplierName;


    /**
     * 供应商编码
     */
    private  String supplierCode;



    /**
     * 供货方单号
     */
    private  String supplierOrderCode;

    /**
     * 酒店确认号
     */
    private  String confirmationCode;


    /**
     * 采购币种
     */
    private  Integer baseCurrency;

    /**
     * 应付金额
     */
    private BigDecimal payableAmt;


    /**
     * 实付金额
     */
    private  BigDecimal paidAmt;

    /**
     * 未付金额
     */
    private  BigDecimal unpaidAmt;

    /**
     * 折合为人民币
     */
    private  BigDecimal  equivalentCny;

    /**
     * 利润
     */
    private  BigDecimal profit;




}
