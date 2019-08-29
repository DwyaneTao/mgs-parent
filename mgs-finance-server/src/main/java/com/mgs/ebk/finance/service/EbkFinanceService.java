package com.mgs.ebk.finance.service;

import com.github.pagehelper.PageInfo;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;


public interface EbkFinanceService {

    PageInfo<SupplierStatementListResponseDTO> queryStatementList(QuerySupplierStatementListDTO request);
}
