package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatementIdDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 账单编码
     */
    private String statementCode;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 公司编码
     */
    private String companyCode;
}
