package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddStatementSupplyOrderListDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 供货单idList
     */
    private List<Integer> supplyOrderIdList;

    /**
     * 操作人
     */
    private String operator;
}
