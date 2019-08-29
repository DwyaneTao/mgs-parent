package com.mgs.ebk.finance.server;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.ebk.finance.service.EbkFinanceService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EbkFinanceServer {

    @Autowired
    private EbkFinanceService ebkFinanceService;

    /**
     * 查询账单详情
     * @param request
     * @return
     */
    @PostMapping("/ebk/finance/queryStatementList")
    public Response queryStatementList(@RequestBody QuerySupplierStatementListDTO request){
        Response response = new Response();
        try{
            response.setResult(1);
            PageInfo<SupplierStatementListResponseDTO> pageInfo = ebkFinanceService.queryStatementList(request);
            if(pageInfo != null){
                PaginationSupportDTO<SupplierStatementListResponseDTO> supportDTO = new PaginationSupportDTO<SupplierStatementListResponseDTO>();
                supportDTO.copyProperties(pageInfo, SupplierStatementListResponseDTO.class);
                response.setModel(supportDTO);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
        }
        return response;
    }
}
