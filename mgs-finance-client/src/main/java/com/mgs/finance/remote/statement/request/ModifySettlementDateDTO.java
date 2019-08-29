package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifySettlementDateDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 结算日期
     */
    private String settlementDate;

    /**
     * 操作人
     */
    private String operator;
}
