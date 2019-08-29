package com.mgs.organization.server;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.BankAddDTO;
import com.mgs.organization.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/6/21 22:36
 **/
@RestController
@Slf4j
@RequestMapping("/bank")
public class BankServer {
    @Autowired
    private BankService bankService;
    /**
     * 新增银行卡信息
     */
    @PostMapping("/addBank")
    Response addBank(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{

            response= bankService.addBank(request);
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
    @PostMapping("/modifyBank")
    Response modifyBank(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{
            response = bankService.modifyBank(request);
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
    @PostMapping("/deleteBank")
    Response deleteBank(@RequestBody BankAddDTO request){
        Response response = new Response();
        try{
            response = bankService.deleteBank(request);
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
            response = bankService.QueryBankList(request);
        } catch (Exception e) {
            log.error("queryBankList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
