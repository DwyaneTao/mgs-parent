package com.mgs.finance.statement.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.statement.request.AddStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.CreateAgentStatementDTO;
import com.mgs.finance.remote.statement.request.DeleteStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.ModifySettlementDateDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementNameDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementStatusDTO;
import com.mgs.finance.remote.statement.request.NotifyCollectionOfStatementDTO;
import com.mgs.finance.remote.statement.request.NotifyPaymentOfStatementDTO;
import com.mgs.finance.remote.statement.request.QueryAgentStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutAgentListDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.AgentStatementDetailDTO;
import com.mgs.finance.statement.service.AgentStatementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class AgentStatementServer {

    @Autowired
    private AgentStatementService agentStatementService;

    /**
     * 已出账单查询
     */
    @PostMapping("/finance/agent/queryStatementList")
    public Response queryStatementList(@RequestBody QueryAgentStatementListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=agentStatementService.queryStatementList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryStatementList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 未出账查询
     */
    @PostMapping("/finance/agent/queryUncheckOutAgentList")
    public Response queryUncheckOutAgentList(@RequestBody QueryUncheckOutAgentListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=agentStatementService.queryUncheckOutAgentList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryUncheckOutAgentList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 创建账单
     */
    @PostMapping("/finance/agent/createStatement")
    public Response createStatement(@RequestBody CreateAgentStatementDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.createStatement(request);
        } catch (Exception e) {
            log.error("createStatement server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单详情
     */
    @PostMapping("/finance/agent/queryStatementDetail")
    public Response queryStatementDetail(@RequestBody StatementIdDTO request){
        Response response = new Response();
        try{
            AgentStatementDetailDTO agentStatementDetailDTO=agentStatementService.queryStatementDetail(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(agentStatementDetailDTO);
        } catch (Exception e) {
            log.error("queryStatementDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单明细
     */
    @PostMapping("/finance/agent/queryStatementOrderList")
    public Response queryStatementOrderList(@RequestBody QueryStatementOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=agentStatementService.queryStatementOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryStatementOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 未出账订单查询
     */
    @PostMapping("/finance/agent/queryUnCheckOutOrder")
    public Response queryUnCheckOutOrder(@RequestBody QueryUnCheckOutOrderDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=agentStatementService.queryUnCheckOutOrder(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryUnCheckOutOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 添加账单明细
     */
    @PostMapping("/finance/agent/addStatementOrderList")
    public Response addStatementOrderList(@RequestBody AddStatementOrderListDTO request){
        Response response = new Response();
        try{
            if(request != null && CollectionUtils.isNotEmpty(request.getOrderIdList())
                    && request.getStatementId() != null) {
                response = agentStatementService.addStatementOrderList(request);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("addStatementOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除账单明细
     */
    @PostMapping("/finance/agent/deleteStatementOrderList")
    public Response deleteStatementOrderList(@RequestBody DeleteStatementOrderListDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.deleteStatementOrderList(request);
        } catch (Exception e) {
            log.error("deleteStatementOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改账单状态
     */
    @PostMapping("/finance/agent/modifyStatementStatus")
    public Response modifyStatementStatus(@RequestBody ModifyStatementStatusDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.modifyStatementStatus(request);
        } catch (Exception e) {
            log.error("modifyStatementStatus server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单明细变更查询
     */
    @PostMapping("/finance/agent/queryUpdatedStatementOrderList")
    public Response queryUpdatedStatementOrderList(@RequestBody QueryUpdatedStatementOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=agentStatementService.queryUpdatedStatementOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryUpdatedStatementOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 更新账单明细
     */
    @PostMapping("/finance/agent/updateStatementOrderList")
    public Response updateStatementOrderList(@RequestBody StatementIdDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.updateStatementOrderList(request);
        } catch (Exception e) {
            log.error("updateStatementOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知收款
     */
    @PostMapping("/finance/agent/notifyCollectionOfStatement")
    public Response notifyCollectionOfStatement(@RequestBody NotifyCollectionOfStatementDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.notifyCollectionOfStatement(request);
        } catch (Exception e) {
            log.error("notifyCollectionOfStatement server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @PostMapping("/finance/agent/notifyPaymentOfStatement")
    public Response notifyPaymentOfStatement(@RequestBody NotifyPaymentOfStatementDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.notifyPaymentOfStatement(request);
        } catch (Exception e) {
            log.error("notifyPaymentOfStatement server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改账单名称
     */
    @PostMapping("/finance/agent/modifyStatementName")
    public Response modifyStatementName(@RequestBody ModifyStatementNameDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.modifyStatementName(request);
        } catch (Exception e) {
            log.error("modifyStatementName server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改结算日期
     */
    @PostMapping("/finance/agent/modifySettlementDate")
    public Response modifySettlementDate(@RequestBody ModifySettlementDateDTO request){
        Response response = new Response();
        try{
            response=agentStatementService.modifySettlementDate(request);
        } catch (Exception e) {
            log.error("modifySettlementDate server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
