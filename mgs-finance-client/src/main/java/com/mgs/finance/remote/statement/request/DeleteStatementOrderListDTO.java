package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeleteStatementOrderListDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 账单明细idList
     */
    private List<Integer> statementOrderIdList;

    /**
     * 操作人
     */
    private String operator;
}
