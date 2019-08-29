package com.mgs.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SupplyOrderAmtDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 供货单编码
     */
    private String supplyOrderCode;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供货单财务id
     */
    private Integer supplyOrderFinanceId;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 供货单金额
     */
    private BigDecimal supplyOrderAmt;

    /**
     * 实付
     */
    private BigDecimal paidAmt;

    /**
     * 未付
     */
    private BigDecimal unpaidAmt;

    /**
     * 收款未确认
     */
    private BigDecimal unconfirmedReceivedAmt;

    /**
     * 付款未确认
     */
    private BigDecimal unconfirmedPaidAmt;
}
