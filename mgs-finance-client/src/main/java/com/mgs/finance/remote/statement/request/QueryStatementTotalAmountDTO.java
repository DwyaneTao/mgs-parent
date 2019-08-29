package com.mgs.finance.remote.statement.request;

import lombok.Data;

@Data
public class QueryStatementTotalAmountDTO {

    private Integer statementId;

    private String currency;

    private String amount;
}
