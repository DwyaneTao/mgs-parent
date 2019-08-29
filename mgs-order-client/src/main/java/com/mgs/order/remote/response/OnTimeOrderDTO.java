package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnTimeOrderDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 应收
     */
    private BigDecimal receivableAmt;

    /**
     * 实收
     */
    private BigDecimal receivedAmt;

    /**
     * 未收
     */
    private BigDecimal unreceivedAmt;

    /**
     * 收款待确认
     */
    private BigDecimal unconfirmedReceivedAmt;

    /**
     * 付款待确认
     */
    private BigDecimal unconfirmedPaidAmt;

    /**
     * 结算日期
     */
    private String settlementDate;

    /**
     * 逾期日期
     */
    private Integer overdueDays;

    /**
     * 结算状态
     */
    private Integer settlementStatus;

    /**
     * 币种
     */
    private Integer SaleCurrency;
}
