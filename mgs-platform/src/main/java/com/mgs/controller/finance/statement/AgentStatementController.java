package com.mgs.controller.finance.statement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.statement.AgentStatementRemote;
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
import com.mgs.finance.remote.statement.request.QueryStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutAgentListDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.AgentStatementDetailDTO;
import com.mgs.finance.remote.statement.response.StatementOrderDTO;
import com.mgs.finance.remote.statement.response.StatementSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementDetailDTO;
import com.mgs.organization.remote.BankRemote;
import com.mgs.organization.remote.CompanyRemote;
import com.mgs.organization.remote.dto.BankAddDTO;
import com.mgs.organization.remote.dto.BankSupplierDTO;
import com.mgs.organization.remote.dto.CompanyAddDTO;
import com.mgs.organization.remote.dto.CompanySelectDTO;
import com.mgs.util.DateUtil;
import com.mgs.util.ExcelHelper;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/finance/agent")
public class AgentStatementController extends BaseController {

    @Autowired
    private AgentStatementRemote agentStatementRemote;
    @Autowired
    private BankRemote bankRemote;

    @Autowired
    private CompanyRemote companyRemote;
    /**
     * 已出账单查询
     */
    @RequestMapping(value = "/queryStatementList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementList(@RequestBody QueryAgentStatementListDTO request) {
        Response response=null;
        try {
            if(StringUtil.isValidString(request.getSettlementStatus()) && request.getSettlementStatus().equals("-1")){
                request.setSettlementStatus(null);
            }
            if(StringUtil.isValidString(request.getOverdueStatus()) && request.getOverdueStatus().equals("-1")){
                request.setOverdueStatus(null);
            }
            if(StringUtil.isValidString(request.getStatementStatus()) && request.getStatementStatus().equals("-1")){
                request.setStatementStatus(null);
            }
            request.setCompanyCode(super.getCompanyCode());
            response=agentStatementRemote.queryStatementList(request);
            log.info("agentStatementRemote.queryStatementList result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.queryStatementList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 未出账查询
     */
    @RequestMapping(value = "/queryUncheckOutAgentList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUncheckOutAgentList(@RequestBody QueryUncheckOutAgentListDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            response=agentStatementRemote.queryUncheckOutAgentList(request);
            log.info("agentStatementRemote.queryUncheckOutAgentList result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.queryUncheckOutAgentList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 创建账单
     */
    @RequestMapping(value = "/createStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response createStatement(@RequestBody CreateAgentStatementDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            request.setOperator(super.getUserName());
            response=agentStatementRemote.createStatement(request);
            log.info("agentStatementRemote.createStatement result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.createStatement 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单详情
     */
    @RequestMapping(value = "/queryStatementDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementDetail(@RequestBody StatementIdDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            request.setOperator(super.getUserName());
            response=agentStatementRemote.queryStatementDetail(request);
            log.info("agentStatementRemote.queryStatementDetail result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.queryStatementDetail 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单明细
     */
    @RequestMapping(value = "/queryStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementOrderList(@RequestBody QueryStatementOrderListDTO request) {
        Response response=null;
        try {
            response=agentStatementRemote.queryStatementOrderList(request);
            log.info("agentStatementRemote.queryStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.queryStatementOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 未出账订单查询
     */
    @RequestMapping(value = "/queryUnCheckOutOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUnCheckOutOrder(@RequestBody QueryUnCheckOutOrderDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            response=agentStatementRemote.queryUnCheckOutOrder(request);
            log.info("agentStatementRemote.queryUnCheckOutOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.queryUnCheckOutOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 添加账单明细
     */
    @RequestMapping(value = "/addStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addStatementOrderList(@RequestBody AddStatementOrderListDTO request) {
        Response response=null;
        try {
            if(request != null && request.getStatementId() != null
              && CollectionUtils.isNotEmpty(request.getOrderIdList())) {
                request.setOperator(super.getUserName());
                response = agentStatementRemote.addStatementOrderList(request);
                log.info("agentStatementRemote.addStatementOrderList result:" + response.getResult());
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("agentStatementRemote.addStatementOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除账单明细
     */
    @RequestMapping(value = "/deleteStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteStatementOrderList(@RequestBody DeleteStatementOrderListDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.deleteStatementOrderList(request);
            log.info("agentStatementRemote.deleteStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.deleteStatementOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改账单状态
     */
    @RequestMapping(value = "/modifyStatementStatus",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyStatementStatus(@RequestBody ModifyStatementStatusDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.modifyStatementStatus(request);
            log.info("agentStatementRemote.modifyStatementStatus result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.modifyStatementStatus 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 导出账单
     */
    @RequestMapping(value = "/exportStatement", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public void exportAgentBillImport(@RequestBody StatementIdDTO requestDTO, HttpServletResponse response) throws Exception {
        log.info("exportStatement:"+ requestDTO);
        try {
            if (null != requestDTO) {
                QueryStatementOrderListDTO queryStatementOrderListDTO = new QueryStatementOrderListDTO();
                BankAddDTO bankAddDTO = new BankAddDTO();
                List<StatementOrderDTO> statementOrderDTOS = new ArrayList<>();
                List<BankSupplierDTO> bankList = new ArrayList<>();
                AgentStatementDetailDTO agentStatementDetailDTO = new AgentStatementDetailDTO();
                queryStatementOrderListDTO.setStatementId(requestDTO.getStatementId());
                Response response1 = agentStatementRemote.queryStatementDetail(requestDTO);
                Response response2 = agentStatementRemote.queryStatementOrderList(queryStatementOrderListDTO);
                bankAddDTO.setOrgCode(getCompanyCode());
                Response response3 = bankRemote.queryBankList(bankAddDTO);
                if (response2.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response2.getModel()) {
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response2.getModel()));
                    statementOrderDTOS = JSON.parseArray(jsonArray.getString("itemList"), StatementOrderDTO.class);
                }
                if (response1.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response1.getModel()) {
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response1));
                    agentStatementDetailDTO = JSON.parseObject(jsonArray.getString("model"), AgentStatementDetailDTO.class);
                }
                if (response3.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response3.getModel()) {
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response3));
                    bankList = JSON.parseArray(jsonArray.getString("model"), BankSupplierDTO.class);
                }
                if(!CollectionUtils.isEmpty(statementOrderDTOS)){
                    for (StatementOrderDTO statementOrder :statementOrderDTOS) {
                        if(statementOrder.getConfirmationStatus().equals(0)){
                            statementOrder.setConfirmationStatusStr("未确认");
                        }
                        if(statementOrder.getConfirmationStatus().equals(1)){
                            statementOrder.setConfirmationStatusStr("确认");
                        }
                        if(statementOrder.getConfirmationStatus().equals(2)){
                            statementOrder.setConfirmationStatusStr("已取消");
                        }
                    }
                    if(agentStatementDetailDTO.getStatementStatus().equals(0)){
                        agentStatementDetailDTO.setStatementStatusStr("未对账");
                    }
                    if(agentStatementDetailDTO.getStatementStatus().equals(1)){
                        agentStatementDetailDTO.setStatementStatusStr("对账中");
                    }
                    if(agentStatementDetailDTO.getStatementStatus().equals(2)){
                        agentStatementDetailDTO.setStatementStatusStr("已确定");
                    }
                    if(agentStatementDetailDTO.getStatementStatus().equals(3)){
                        agentStatementDetailDTO.setStatementStatusStr("已取消");
                    }
                    if(agentStatementDetailDTO.getSettlementStatus().equals(0)){
                        agentStatementDetailDTO.setSettlementStatusStr("未结算");
                    }
                    if(agentStatementDetailDTO.getSettlementStatus().equals(1)){
                        agentStatementDetailDTO.setSettlementStatusStr("已结算");
                    }

                    CompanyAddDTO companyAddDTO = new CompanyAddDTO();
                    companyAddDTO.setCompanyCode(getCompanyCode());
                    Response response4 = companyRemote.queryCompanyDetail(companyAddDTO);
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response4));
                    CompanySelectDTO companySelectDTO = JSON.parseObject(jsonArray.getString("model"), CompanySelectDTO.class);

                    Map map = new HashMap();
                    map.put("list",statementOrderDTOS);
                    map.put("agentName",agentStatementDetailDTO.getAgentName());
                    map.put("statementCode",agentStatementDetailDTO.getStatementCode());
                    map.put("statementName",agentStatementDetailDTO.getStatementName());
                    map.put("createdBy",agentStatementDetailDTO.getCreatedBy());
                    map.put("createdDt",DateUtil.dateToString(agentStatementDetailDTO.getCreatedDt(),DateUtil.hour_format));
                    map.put("statementStatusStr",agentStatementDetailDTO.getStatementStatusStr());
                    map.put("settlementStatusStr",agentStatementDetailDTO.getSettlementStatusStr());
                    map.put("settlementDate",DateUtil.dateToString(agentStatementDetailDTO.getSettlementDate(),DateUtil.defaultFormat));
                    map.put("overdueDays",agentStatementDetailDTO.getOverdueDays());
                    map.put("statementAmt",agentStatementDetailDTO.getStatementAmt());
                    map.put("receivedAmt",agentStatementDetailDTO.getReceivedAmt());
                    map.put("unreceivedAmt",agentStatementDetailDTO.getUnreceivedAmt());
                    map.put("bankList",bankList);
                    map.put("companyName", companySelectDTO.getCompanyName());
                    OutputStream output = null;
                    BufferedInputStream bis = null;
                    BufferedOutputStream bos = null;
                    try {
                        ByteArrayInputStream byteArrayInputStream = ExcelHelper.exportFromRemote(map, "http://39.108.73.131/mongohotel/template/agentStatementTemplate.xls");
                        //告诉浏览器用什么软件可以打开此文件
                        response.setContentType("application/vnd.ms-excel;charset=utf-8");
                        // 下载文件的默认名称
                        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("客户对账单.xls","UTF-8"));
                        output = response.getOutputStream();
                        bis = new BufferedInputStream(byteArrayInputStream);
                        bos = new BufferedOutputStream(output);
                        byte[] buff = new byte[2048];
                        int bytesRead;
                        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                            bos.write(buff, 0, bytesRead);
                        }
                    } catch (Exception e) {
                        log.error("exportStatement has error",e);
                    }finally {
                        if (null != bos) {
                            bos.close();
                        }
                        if (null != bis) {
                            bis.close();
                        }
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            }
        }catch(Exception e) {
            log.error("exportStatement error!",e);

        }

    }

    /**
     * 账单明细变更查询
     */
    @RequestMapping(value = "/queryUpdatedStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUpdatedStatementOrderList(@RequestBody QueryUpdatedStatementOrderListDTO request) {
        Response response=null;
        try {
            response=agentStatementRemote.queryUpdatedStatementOrderList(request);
            log.info("agentStatementRemote.queryUpdatedStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.queryUpdatedStatementOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 更新账单明细
     */
    @RequestMapping(value = "/updateStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response updateStatementOrderList(@RequestBody StatementIdDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.updateStatementOrderList(request);
            log.info("agentStatementRemote.updateStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.updateStatementOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知收款
     */
    @RequestMapping(value = "/notifyCollectionOfStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfStatement(@RequestBody NotifyCollectionOfStatementDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.notifyCollectionOfStatement(request);
            log.info("agentStatementRemote.notifyCollectionOfStatement result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.notifyCollectionOfStatement 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @RequestMapping(value = "/notifyPaymentOfStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfStatement(@RequestBody NotifyPaymentOfStatementDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.notifyPaymentOfStatement(request);
            log.info("agentStatementRemote.notifyPaymentOfStatement result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.notifyPaymentOfStatement 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改账单名称
     */
    @RequestMapping(value = "/modifyStatementName",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyStatementName(@RequestBody ModifyStatementNameDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.modifyStatementName(request);
            log.info("agentStatementRemote.modifyStatementName result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.modifyStatementName 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改结算日期
     */
    @RequestMapping(value = "/modifySettlementDate",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySettlementDate(@RequestBody ModifySettlementDateDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=agentStatementRemote.modifySettlementDate(request);
            log.info("agentStatementRemote.modifySettlementDate result:"+response.getResult());
        } catch (Exception e) {
            log.error("agentStatementRemote.modifySettlementDate 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
