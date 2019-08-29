package com.mgs.finance.statement.dto;

import com.mgs.finance.remote.statement.request.CreateSupplierStatementDTO;
import lombok.Data;

import java.util.List;

@Data
public class InsertStatementSupplyOrderDTO extends CreateSupplierStatementDTO {

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 供货单号list
     */
    private List<Integer> supplyOrderIdList;
}
