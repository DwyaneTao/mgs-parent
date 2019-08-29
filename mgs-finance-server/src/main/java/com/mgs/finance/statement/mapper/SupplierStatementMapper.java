package com.mgs.finance.statement.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutSupplierListDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.UncheckOutSupplierDTO;
import com.mgs.finance.statement.domain.SupplierStatementPO;

import java.util.List;

public interface SupplierStatementMapper extends MyMapper<SupplierStatementPO> {

    /**
     * 已出账单查询
     */
    List<SupplierStatementListResponseDTO> queryStatementList(QuerySupplierStatementListDTO request);

    /**
     * 未出账查询
     */
    List<UncheckOutSupplierDTO> queryUncheckOutSupplierList(QueryUncheckOutSupplierListDTO request);
}