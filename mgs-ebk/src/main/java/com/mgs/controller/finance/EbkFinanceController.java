package com.mgs.controller.finance;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.ebk.remote.EbkFinanceRemote;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.enums.StatementStatusEnum;
import com.mgs.finance.remote.statement.SupplierStatementRemote;
import com.mgs.finance.remote.statement.request.ModifyStatementStatusDTO;
import com.mgs.finance.remote.statement.request.QueryStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ebk/finance")
public class EbkFinanceController extends BaseController {

    @Autowired
    private EbkFinanceRemote ebkFinanceRemote;

    @Autowired
    private SupplierStatementRemote supplierStatementRemote;

    /**
     * 查询账单列表
     * @param request
     * @return
     */
    @PostMapping("/queryStatementList")
    public Response queryStatementList(@RequestBody QuerySupplierStatementListDTO request) {
        Response response = new Response();
        try {
            if (StringUtil.isValidString(request.getStatementStatus()) && request.getStatementStatus().equals("-1")) {
                request.setStatementStatus(null);
            }
            if (StringUtil.isValidString(request.getOverdueStatus()) && request.getOverdueStatus().equals("-1")) {
                request.setSettlementStatus(null);
            }
            request.setSupplierCode(getCompanyCode());
            response = ebkFinanceRemote.queryStatementList(request);
        }catch (Exception e){
            response.setResult(0);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
        }
        return response;
    }

    /**
     * 查询账单详情
     * @param request
     * @return
     */
    @PostMapping("/queryStatementDetail")
    public Response queryStatementDetail(@RequestBody StatementIdDTO request){
        Response response = new Response();
        try{
            response = supplierStatementRemote.queryStatementDetail(request);
        } catch (Exception e) {
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改账单状态
     */
    @RequestMapping(value = "/confirmStatement",produces = { "application/json;charset=UTF-8" })
    public Response confirmStatement(@RequestBody ModifyStatementStatusDTO request) {
        Response response=null;
        try {
            request.setStatementStatus(StatementStatusEnum.CONFIRMED.key);
            request.setOperator(super.getUserName());
            response= supplierStatementRemote.modifyStatementStatus(request);
        } catch (Exception e) {
            response=new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单明细
     */
    @RequestMapping(value = "/queryStatementOrderList",produces = { "application/json;charset=UTF-8" })
    public Response queryStatementOrderList(@RequestBody QueryStatementSupplyOrderListDTO request) {
        Response response=null;
        try {
            response= supplierStatementRemote.queryStatementOrderList(request);
        } catch (Exception e) {
            response=new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
