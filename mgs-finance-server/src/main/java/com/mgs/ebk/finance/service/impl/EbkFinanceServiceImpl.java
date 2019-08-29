package com.mgs.ebk.finance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.ebk.finance.mapper.EbkFinanceMapper;
import com.mgs.ebk.finance.service.EbkFinanceService;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbkFinanceServiceImpl implements EbkFinanceService {

    @Autowired
    private EbkFinanceMapper ebkFinanceMapper;

    /**
     * 查询供应端账单列表
     * @param request
     * @return
     */
    @Override
    public PageInfo<SupplierStatementListResponseDTO> queryStatementList(QuerySupplierStatementListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<SupplierStatementListResponseDTO> list = ebkFinanceMapper.queryEBKStatementList(request);
        PageInfo<SupplierStatementListResponseDTO> page = new PageInfo<SupplierStatementListResponseDTO>(list);
        return page;
    }
}
