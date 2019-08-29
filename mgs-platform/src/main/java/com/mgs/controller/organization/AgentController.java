package com.mgs.controller.organization;

import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.AgentRemote;
import com.mgs.organization.remote.dto.AgentAddDTO;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import com.mgs.organization.remote.dto.AgentListRequest;
import com.mgs.organization.remote.dto.QueryAgentListDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author py
 * @date 2019/7/1 21:15
 **/
@RestController
@Slf4j
@RequestMapping(value = "/agent")
public class AgentController  extends BaseController {
    @Autowired
    private AgentRemote agentRemote;
    /**
     * 新增客户
     */
    @RequestMapping(value = "/addAgent",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response addAgent(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            if (request.getAgentType()!=null
            ){
                if (request.getAgentType() == 0) {
                        if (StringUtil.isValidString(request.getAgentName())
                                && StringUtil.isValidString(request.getAdminName())
                                && StringUtil.isValidString(request.getAdminAccount())
                                && StringUtil.isValidString(request.getAdminTel())
                                && request.getSaleManagerId() != null
                                && request.getSettlementType() != null
                        ) {
                            request.setOrgDomain(getOrgDomain());
                            request.setCreatedBy(getUserName());
                            request.setCompanyCode(getCompanyCode());
                            response = agentRemote.addAgent(request);
                        } else {
                            response.setResult(0);
                            response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                            response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                        }
                    }
               else {
                   if (StringUtil.isValidString(request.getAgentName())
                           &&StringUtil.isValidString(request.getAgentTel())
                           &&request.getSettlementType()!=null
                   ){
                       request.setCreatedBy(getUserName());
                       request.setCompanyCode(getCompanyCode());
                       response= agentRemote.addAgent(request);
                   }
                   else{
                       response.setResult(0);
                       response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                       response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                   }
               }
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }


        } catch (Exception e) {
            log.error("addAgent server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改客户启用状态
     */
    @RequestMapping(value = "/modifyAgentStatus",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifyAgentStatus(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            if (request.getAgentId()!=null
                    &&request.getAvailableStatus()!=null
                    &&request.getAgentId()!=null
            ){
                request.setModifiedBy(getUserName());
                response = agentRemote.modifyAgentStatus(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("modifyAgentStatus server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改客户信息
     */
    @RequestMapping(value = "/modifyAgent",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifyAgent(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            request.setCompanyCode(getCompanyCode());
            if (request.getAgentType()!=null
            ){
                if (request.getAgentType()==0){
                    if (request.getAgentId()!=null
                            &&StringUtil.isValidString(request.getAgentName())
                            &&StringUtil.isValidString(request.getAdminName())
                            &&StringUtil.isValidString(request.getAdminAccount())
                            &&StringUtil.isValidString(request.getAdminTel())
                            &&request.getSettlementType()!=null
                            &&StringUtil.isValidString(request.getCreditLine())
                            &&request.getSaleManagerId()!=null
                    ){
                        request.setModifiedBy(getUserName());
                        request.setCreatedBy(getUserName());
                        response= agentRemote.modifyAgent(request);
                    }
                    else{
                        response.setResult(0);
                        response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                        response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                    }
                }
                else {
                    if (request.getAgentId()!=null
                            &&StringUtil.isValidString(request.getAgentName())
                            &&StringUtil.isValidString(request.getAgentTel())
                            &&request.getSettlementType()!=null
                    ){
                        request.setModifiedBy(getUserName());
                        request.setCreatedBy(getUserName());
                        response= agentRemote.modifyAgent(request);
                    }
                    else{
                        response.setResult(0);
                        response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                        response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                    }
                }
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("modifyAgent server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 客户详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryAgentDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryAgentDetail(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getAgentCode())){
                response= agentRemote.queryAgentDetail(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("queryAgentDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询客户列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryAgentList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryAgentList(@RequestBody AgentListRequest request){
        Response response = new Response();
        try{
            request.setCompanyCode(getCompanyCode());
            if(request.getAvailableStatus()!=null){
                if(request.getAvailableStatus()==-1){
                    request.setAvailableStatus(null);
                }}
            if(request.getAgentType()!=null){
                if(request.getAgentType()==-1){
                    request.setAgentType(null);
                }
            }
            response = agentRemote.queryAgentList(request);
        } catch (Exception e) {
            log.error("queryAgentList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
}
/**
 * 扣退额度
 */
@RequestMapping(value = "/modifyDeductRefundCreditLine",produces = { "application/json;charset=UTF-8" })
@ResponseBody
Response modifyDeductRefundCreditLine(@RequestBody List<AgentCreditLineDTO> request){
    Response response = new Response();
    try{
            response = agentRemote.modifyDeductRefundCreditLine(request);
    } catch (Exception e) {
        log.error("modifyDeductRefundCreditLine server error!",e);
        response.setResult(ResultCodeEnum.FAILURE.code);
        response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
        response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
    }
    return response;
}
}
