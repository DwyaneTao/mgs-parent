package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyStatementStatusDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 账单状态：0未对账，1对账中，2已确定，3已取消
     */
    private Integer statementStatus;

    /**
     * 操作人
     */
    private String operator;
}
