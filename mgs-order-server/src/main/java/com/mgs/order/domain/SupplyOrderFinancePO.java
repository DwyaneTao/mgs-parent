package com.mgs.order.domain;

import com.mgs.common.BasePO;
import com.sun.xml.internal.rngom.parse.host.Base;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "o_supply_order_finance")
public class SupplyOrderFinancePO   {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供货单id
     */
    @Column(name = "supply_order_id")
    private Integer supplyOrderId;

    /**
     * 供货单编码
     */
    @Column(name = "supply_order_code")
    private String supplyOrderCode;

    /**
     * 已付金额
     */
    @Column(name = "paid_amt")
    private BigDecimal paidAmt;

    /**
     * 未付金额
     */
    @Column(name = "unpaid_amt")
    private BigDecimal unpaidAmt;

    /**
     * 收款未确认
     */
    @Column(name = "unconfirmed_received_amt")
    private BigDecimal unconfirmedReceivedAmt;

    /**
     * 付款未确认
     */
    @Column(name = "unconfirmed_paid_amt")
    private BigDecimal unconfirmedPaidAmt;

    /**
     * 结算状态：0未结算，1已结算
     */
    @Column(name = "settlement_status")
    private Integer settlementStatus;

    /**
     * 结算日期
     */
    @Column(name = "settlement_date")
    private Date settlementDate;

    /**
     * 对账状态：0新建，1可出账，2已纳入账单，3已对账
     */
    @Column(name = "check_status")
    private Integer checkStatus;

    /**
     * 财务锁单状态，0：未锁定，1：已锁定
     */
    @Column(name = "finance_lock_status")
    private Integer financeLockStatus;

    private String createdBy;

    private String createdDt;

    /**
     * 实际结算日期
     */
    private Date realSettlementDate;
}