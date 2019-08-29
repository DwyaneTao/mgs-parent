package com.mgs.finance.remote.statement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierStatementDetailDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 账单编码
     */
    private String statementCode;

    /**
     * 账单状态：0未对账，1对账中，2已确定，3已取消
     */
    private Integer statementStatus;
    /**
     * 账单信息
     */
    private String statementStatusStr;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 联系人信息
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTel;

    /**
     * 账单名称
     */
    private String statementName;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 账单金额
     */
    private BigDecimal statementAmt;

    /**
     * 结算状态：0未结算，1已结算
     */
    private Integer settlementStatus;
    /**
     * 结算信息
     */
    private  String settlementStatusStr;

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

    /**
     * 结算日期
     */
    private Date settlementDate;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 新增明细数
     */
    private Integer newOrderQty;

    /**
     * 更新明细数
     */
    private Integer updatedOrderQty;

    /**
     * 确认或者取消人
     */
    private String modifyBy;

    /**
     * 确认或者取消时间
     */
    private String modifyDt;
}
