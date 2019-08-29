package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryStatementSupplyOrderListDTO extends BaseRequest{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 供货单编码
     */
    private String supplyOrderCode;
}

