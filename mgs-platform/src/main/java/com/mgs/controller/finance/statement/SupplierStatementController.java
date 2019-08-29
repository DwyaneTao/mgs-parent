package com.mgs.controller.finance.statement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.statement.SupplierStatementRemote;
import com.mgs.finance.remote.statement.request.AddStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.CreateSupplierStatementDTO;
import com.mgs.finance.remote.statement.request.DeleteStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.ModifySettlementDateDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementNameDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementStatusDTO;
import com.mgs.finance.remote.statement.request.NotifyCollectionOfStatementDTO;
import com.mgs.finance.remote.statement.request.NotifyPaymentOfStatementDTO;
import com.mgs.finance.remote.statement.request.QueryStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutSupplyOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutSupplierListDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.StatementSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementDetailDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.organization.remote.CompanyRemote;
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
@RequestMapping(value = "/finance/supplier")
public class SupplierStatementController extends BaseController {

    @Autowired
    private SupplierStatementRemote supplierStatementRemote;

    @Autowired
    private CompanyRemote companyRemote;

    /**
     * 已出账单查询
     */
    @RequestMapping(value = "/queryStatementList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementList(@RequestBody QuerySupplierStatementListDTO request) {
        Response response=null;
        try {
            if(StringUtil.isValidString(request.getSettlementStatus()) && request.getSettlementStatus().equals("-1")){
                request.setSettlementStatus(null);
            }
            if(StringUtil.isValidString(request.getOverdueStatus()) && request.getOverdueStatus().equals("-1")){
                request.setSettlementStatus(null);
            }
            if(StringUtil.isValidString(request.getStatementStatus()) && request.getStatementStatus().equals("-1")){
                request.setStatementStatus(null);
            }
            request.setCompanyCode(super.getCompanyCode());
            response=supplierStatementRemote.queryStatementList(request);
            log.info("supplierStatementRemote.queryStatementList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.queryStatementList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 未出账查询
     */
    @RequestMapping(value = "/queryUncheckOutSupplierList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUncheckOutSupplierList(@RequestBody QueryUncheckOutSupplierListDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            response=supplierStatementRemote.queryUncheckOutSupplierList(request);
            log.info("supplierStatementRemote.queryUncheckOutSupplierList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.queryUncheckOutSupplierList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 创建账单
     */
    @RequestMapping(value = "/createStatement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response createStatement(@RequestBody CreateSupplierStatementDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            request.setOperator(super.getUserName());
            response=supplierStatementRemote.createStatement(request);
            log.info("supplierStatementRemote.createStatement result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.createStatement 接口异常",e);
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
            request.setCompanyCode(getCompanyCode());
            request.setOperator(super.getUserName());
            response=supplierStatementRemote.queryStatementDetail(request);
            log.info("supplierStatementRemote.queryStatementDetail result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.queryStatementDetail 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 账单明细
     */
    @RequestMapping(value = "/queryStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryStatementOrderList(@RequestBody QueryStatementSupplyOrderListDTO request) {
        Response response=null;
        try {
            response=supplierStatementRemote.queryStatementOrderList(request);
            log.info("supplierStatementRemote.queryStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.queryStatementOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 未出账订单查询
     */
    @RequestMapping(value = "/queryUnCheckOutSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryUnCheckOutSupplyOrder(@RequestBody QueryUnCheckOutSupplyOrderDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            response=supplierStatementRemote.queryUnCheckOutSupplyOrder(request);
            log.info("supplierStatementRemote.queryUnCheckOutOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.queryUnCheckOutSupplyOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 添加账单明细
     */
    @RequestMapping(value = "/addStatementOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addStatementOrderList(@RequestBody AddStatementSupplyOrderListDTO request) {
        Response response=null;
        try {
            if(request != null && request.getStatementId()!= null && CollectionUtils.isNotEmpty(request.getSupplyOrderIdList())) {
                request.setOperator(super.getUserName());
                response = supplierStatementRemote.addStatementOrderList(request);
                log.info("supplierStatementRemote.addStatementOrderList result:" + response.getResult());
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("supplierStatementRemote.addStatementOrderList 接口异常",e);
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
            response=supplierStatementRemote.deleteStatementOrderList(request);
            log.info("supplierStatementRemote.deleteStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.deleteStatementOrderList 接口异常",e);
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
            response=supplierStatementRemote.modifyStatementStatus(request);
            log.info("supplierStatementRemote.modifyStatementStatus result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.modifyStatementStatus 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 导出账单
     */
    @RequestMapping(value = "/exportStatement", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public void exportSupplierBillImport(@RequestBody StatementIdDTO requestDTO, HttpServletResponse response) throws Exception {
        log.info("exportStatement:"+ requestDTO);
        try {
            if (null != requestDTO) {
                QueryStatementSupplyOrderListDTO  queryStatementSupplyOrderListDTO = new QueryStatementSupplyOrderListDTO ();
                List<StatementSupplyOrderDTO> statementSupplyOrderList = new ArrayList<StatementSupplyOrderDTO>();
                SupplierStatementDetailDTO supplierStatementDetailDTO = new SupplierStatementDetailDTO();
                queryStatementSupplyOrderListDTO.setStatementId(requestDTO.getStatementId());
                Response response1 = supplierStatementRemote.queryStatementDetail(requestDTO);
                Response response2 = supplierStatementRemote.queryStatementOrderList(queryStatementSupplyOrderListDTO);
                if (response2.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response2.getModel()) {
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response2.getModel()));
                    statementSupplyOrderList = JSON.parseArray(jsonArray.getString("itemList"), StatementSupplyOrderDTO.class);
                }
                if (response1.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response1.getModel()) {
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response1));
                    supplierStatementDetailDTO = JSON.parseObject(jsonArray.getString("model"), SupplierStatementDetailDTO.class);
                }
                if(!CollectionUtils.isEmpty(statementSupplyOrderList)){
                    for (StatementSupplyOrderDTO StatementSupplyOrder :statementSupplyOrderList) {
                        if(StatementSupplyOrder.getConfirmationStatus().equals(0)){
                            StatementSupplyOrder.setConfirmationStatusStr("未确认");
                        }
                        if(StatementSupplyOrder.getConfirmationStatus().equals(1)){
                            StatementSupplyOrder.setConfirmationStatusStr("确认");
                        }
                        if(StatementSupplyOrder.getConfirmationStatus().equals(2)){
                            StatementSupplyOrder.setConfirmationStatusStr("已取消");
                        }

                    }
                    if(supplierStatementDetailDTO.getStatementStatus().equals(0)){
                        supplierStatementDetailDTO.setStatementStatusStr("未对账");
                    }
                    if(supplierStatementDetailDTO.getStatementStatus().equals(1)){
                        supplierStatementDetailDTO.setStatementStatusStr("对账中");
                    }
                    if(supplierStatementDetailDTO.getStatementStatus().equals(2)){
                        supplierStatementDetailDTO.setStatementStatusStr("已确定");
                    }
                    if(supplierStatementDetailDTO.getStatementStatus().equals(3)){
                        supplierStatementDetailDTO.setStatementStatusStr("已取消");
                    }
                    if(supplierStatementDetailDTO.getSettlementStatus().equals(0)){
                        supplierStatementDetailDTO.setSettlementStatusStr("未结算");
                    }
                    if(supplierStatementDetailDTO.getSettlementStatus().equals(1)){
                        supplierStatementDetailDTO.setSettlementStatusStr("已结算");
                    }

                    CompanyAddDTO companyAddDTO = new CompanyAddDTO();
                    companyAddDTO.setCompanyCode(getCompanyCode());
                    Response response4 = companyRemote.queryCompanyDetail(companyAddDTO);
                    JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response4));
                    CompanySelectDTO companySelectDTO = JSON.parseObject(jsonArray.getString("model"), CompanySelectDTO.class);

                    Map map = new HashMap();
                    map.put("list",statementSupplyOrderList);
                    map.put("supplierName",supplierStatementDetailDTO.getSupplierName());
                    map.put("statementCode",supplierStatementDetailDTO.getStatementCode());
                    map.put("statementName",supplierStatementDetailDTO.getStatementName());
                    map.put("createdBy",supplierStatementDetailDTO.getCreatedBy());
                    map.put("createdDt", DateUtil.dateToString(supplierStatementDetailDTO.getCreatedDt(),DateUtil.hour_format));
                    map.put("statementStatusStr",supplierStatementDetailDTO.getStatementStatusStr());
                    map.put("settlementStatusStr",supplierStatementDetailDTO.getSettlementStatusStr());
                    map.put("settlementDate",DateUtil.dateToString(supplierStatementDetailDTO.getSettlementDate(),DateUtil.defaultFormat));
                    map.put("overdueDays",supplierStatementDetailDTO.getOverdueDays());
                    map.put("statementAmt",supplierStatementDetailDTO.getStatementAmt());
                    map.put("paidAm",supplierStatementDetailDTO.getPaidAmt());
                    map.put("unpaidAmt",supplierStatementDetailDTO.getUnpaidAmt());
                    map.put("companyName", companySelectDTO.getCompanyName());
                    OutputStream output = null;
                    BufferedInputStream bis = null;
                    BufferedOutputStream bos = null;
                    try {
                        ByteArrayInputStream byteArrayInputStream = ExcelHelper.exportFromRemote(map, "http://39.108.73.131/mongohotel/template/supplierStatementTemplate.xls");
                        //告诉浏览器用什么软件可以打开此文件
                        response.setContentType("application/vnd.ms-excel;charset=utf-8");
                        // 下载文件的默认名称
                        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("供应商对账单Excel.xls","UTF-8"));

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
            log.error("exploreQueryFinanceList error!",e);

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
            response=supplierStatementRemote.queryUpdatedStatementOrderList(request);
            log.info("supplierStatementRemote.queryUpdatedStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.queryUpdatedStatementOrderList 接口异常",e);
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
            response=supplierStatementRemote.updateStatementOrderList(request);
            log.info("supplierStatementRemote.updateStatementOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.updateStatementOrderList 接口异常",e);
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
            response=supplierStatementRemote.notifyCollectionOfStatement(request);
            log.info("supplierStatementRemote.notifyCollectionOfStatement result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.notifyCollectionOfStatement 接口异常",e);
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
            response=supplierStatementRemote.notifyPaymentOfStatement(request);
            log.info("supplierStatementRemote.notifyPaymentOfStatement result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.notifyPaymentOfStatement 接口异常",e);
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
            response=supplierStatementRemote.modifyStatementName(request);
            log.info("supplierStatementRemote.modifyStatementName result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.modifyStatementName 接口异常",e);
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
            response=supplierStatementRemote.modifySettlementDate(request);
            log.info("supplierStatementRemote.modifySettlementDate result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplierStatementRemote.modifySettlementDate 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
