package com.mgs.finance.statement.dto;

import com.mgs.finance.remote.statement.request.CreateAgentStatementDTO;
import lombok.Data;

import java.util.List;

@Data
public class InsertStatementOrderDTO extends CreateAgentStatementDTO{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 订单号list
     */
    private List<Integer> orderIdList;
}
