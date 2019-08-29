package com.mgs.ebk.remote;

import com.mgs.common.Response;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mgs-finance-server")
public interface EbkFinanceRemote {

    /**
     * 已出账单查询
     */
    @PostMapping("/ebk/finance/queryStatementList")
    Response queryStatementList(@RequestBody QuerySupplierStatementListDTO request);
}
