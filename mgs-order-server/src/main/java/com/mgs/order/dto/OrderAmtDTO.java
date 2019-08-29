package com.mgs.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderAmtDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 订单财务id
     */
    private Integer orderFinanceId;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 已收金额
     */
    private BigDecimal receivedAmt;

    /**
     * 未收金额
     */
    private BigDecimal unreceivedAmt;

    /**
     * 收款未确认
     */
    private BigDecimal unconfirmedReceivedAmt;

    /**
     * 付款未确认
     */
    private BigDecimal unconfirmedPaidAmt;
}
