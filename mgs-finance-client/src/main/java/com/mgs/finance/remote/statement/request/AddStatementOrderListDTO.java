package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddStatementOrderListDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 订单idList
     */
    private List<Integer> orderIdList;

    /**
     * 操作人
     */
    private String operator;
}
