package com.mgs.controller.organization;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.BankRemote;
import com.mgs.organization.remote.dto.BankAddDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author py
 * @date 2019/7/2 11:40
 **/
@RestController
@Slf4j
@RequestMapping(value = "/bank")
public class BankController extends BaseController {
    @Autowired
    private BankRemote bankRemote;
    /**
     * 新增银行卡信息
     */
    @RequestMapping(value = "/addBank",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response addBank(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{

            if (StringUtil.isValidString(request.getBankName())
                    &&request.getOrgType()!=null
                    &&StringUtil.isValidString(request.getAccountName())
                    &&StringUtil.isValidString(request.getAccountNumber())
            ){
                request.setCreatedBy(getUserName());
                if (request.getOrgType().equals(2)){
                    request.setOrgCode(getCompanyCode());
                }
                response= bankRemote.addBank(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("addBank server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改银行卡信息
     */
    @RequestMapping(value = "/modifyBank",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifyBank(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getBankName())
                    &&request.getBankId()!=null
                    &&StringUtil.isValidString(request.getAccountName())
                    &&StringUtil.isValidString(request.getAccountNumber())
            ){
                if (!StringUtil.isValidString(request.getOrgCode())) {
                    request.setOrgCode(null);
                }
                request.setModifiedBy(getUserName());
                response = bankRemote.modifyBank(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("modifyBank server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除银行卡
     */
    @RequestMapping(value = "/deleteBank",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response deleteBank(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{
            if (request.getBankId()!=null
            ){
                request.setModifiedBy(getUserName());
                response = bankRemote.deleteBank(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("deleteBank server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 银行卡列表
     */
    @PostMapping("/queryBankList")
    Response queryBankList(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{
            if (null != request && !StringUtil.isValidString(request.getOrgCode())) {
                request.setOrgCode(getCompanyCode());
            }
            response = bankRemote.queryBankList(request);
        } catch (Exception e) {
            log.error("queryBankList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
