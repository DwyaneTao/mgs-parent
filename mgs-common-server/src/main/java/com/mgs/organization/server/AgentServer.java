package com.mgs.organization.server;

import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.*;
import com.mgs.organization.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author py
 * @date 2019/6/26 20:39
 **/
@RestController
@Slf4j
@RequestMapping("/agent")
public class AgentServer {
    @Autowired
    private AgentService agentService;
    /**
     * 新增客户
     */
    @PostMapping("/addAgent")
    Response addAgent(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            response= agentService.addAgent(request);

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
    @PostMapping("/modifyAgentStatus")
    Response modifyAgentStatus(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{

            response = agentService.modifyAgentStatus(request);
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
    @PostMapping("/modifyAgent")
    Response modifyAgent(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            response= agentService.modifyAgent(request);
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
     */
    @PostMapping("/queryAgentDetail")
    Response queryAgentDetail(@RequestBody AgentAddDTO request){
        Response response = new Response();
        try{
            response= agentService.queryAgentDetail(request);
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
     */
    @PostMapping("/queryAgentList")
    Response queryAgentList(@RequestBody AgentListRequest request){
        Response response = new Response();
        try{
            PaginationSupportDTO<QueryAgentListDTO> queryAgentListDTOPaginationSupportDTO = agentService.queryAgentList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(queryAgentListDTOPaginationSupportDTO);
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
    @PostMapping("/modifyDeductRefundCreditLine")
    Response modifyDeductRefundCreditLine(@RequestBody List<AgentCreditLineDTO> agentCreditLineList){
        Response response = new Response();
        try{
            for (AgentCreditLineDTO list :agentCreditLineList){
                response = agentService.deductRefundCreditLine(list);
            }

            response.setResult(ResultCodeEnum.SUCCESS.code);
        } catch (Exception e) {
            log.error("modifyDeductRefundCreditLine server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
