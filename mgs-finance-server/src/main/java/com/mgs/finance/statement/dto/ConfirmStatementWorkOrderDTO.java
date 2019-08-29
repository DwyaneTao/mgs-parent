package com.mgs.finance.statement.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ConfirmStatementWorkOrderDTO implements Serializable{

    /**
     * 账单编码
     */
    private String statementCode;

    /**
     * 通知金额
     */
    private BigDecimal notifyAmt;

    /**
     * 确认金额
     */
    private BigDecimal confirmAmt;

    private String operator;
}
