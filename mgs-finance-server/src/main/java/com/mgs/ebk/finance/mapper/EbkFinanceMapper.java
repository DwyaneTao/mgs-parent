package com.mgs.ebk.finance.mapper;

import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;

import java.util.List;

public interface EbkFinanceMapper {

    /**
     * 查询账单列表
     * @param request
     * @return
     */
    List<SupplierStatementListResponseDTO> queryEBKStatementList(QuerySupplierStatementListDTO request);
}
