package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatementCodeDTO implements Serializable{

    /**
     * 账单编码
     */
    private String statementCode;
}
