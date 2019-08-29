package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryUpdatedStatementOrderListDTO extends BaseRequest{

    /**
     * 账单id
     */
    private Integer statementId;
}
