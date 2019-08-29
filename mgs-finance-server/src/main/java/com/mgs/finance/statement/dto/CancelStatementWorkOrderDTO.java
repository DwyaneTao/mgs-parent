package com.mgs.finance.statement.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CancelStatementWorkOrderDTO implements Serializable{

    /**
     * 账单编码
     */
    private String statementCode;

    /**
     * 通知金额
     */
    private BigDecimal notifyAmt;

    private String operator;
}
