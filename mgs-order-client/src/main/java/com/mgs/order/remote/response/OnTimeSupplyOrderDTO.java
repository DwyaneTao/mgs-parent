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
public class OnTimeSupplyOrderDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 供货单号
     */
    private String supplyOrderCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 应付
     */
    private BigDecimal payableAmt;

    /**
     * 实付
     */
    private BigDecimal paidAmt;

    /**
     * 未付
     */
    private BigDecimal unpaidAmt;

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
    private Integer baseCurrency;
}
