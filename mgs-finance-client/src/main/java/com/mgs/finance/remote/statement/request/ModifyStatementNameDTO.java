package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyStatementNameDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 账单名称
     */
    private String statementName;

    /**
     * 操作人
     */
    private String operator;
}
