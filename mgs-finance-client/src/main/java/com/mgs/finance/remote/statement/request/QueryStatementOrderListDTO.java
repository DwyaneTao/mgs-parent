package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryStatementOrderListDTO extends BaseRequest{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 订单编码
     */
    private String orderCode;
}

