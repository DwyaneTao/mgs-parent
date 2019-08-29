package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "o_order_finance")
public class OrderFinancePO  {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 订单编码
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 已收金额
     */
    @Column(name = "received_amt")
    private BigDecimal receivedAmt;

    /**
     * 未收金额
     */
    @Column(name = "unreceived_amt")
    private BigDecimal unreceivedAmt;

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
     * 实际结算时间
     */
    private Date realSettlementDate;

}