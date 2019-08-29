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
public class AgentStatementListResponseDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 账单编码
     */
    private String statementCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 账单名称
     */
    private String statementName;

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
     * 币种
     */
    private Integer currency;

    /**
     * 账单状态：0未对账，1对账中，2已确定，3已取消
     */
    private Integer statementStatus;

    /**
     * 结算日期
     */
    private Date settlementDate;

    /**
     * 逾期日期
     */
    private Integer overdueDays;

    /**
     * 结算状态：0未结算，1已结算
     */
    private Integer settlementStatus;
}
