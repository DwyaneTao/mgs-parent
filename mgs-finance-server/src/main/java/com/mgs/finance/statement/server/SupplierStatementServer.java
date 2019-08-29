package com.mgs.finance.statement.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
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
import com.mgs.finance.remote.statement.response.SupplierStatementDetailDTO;
import com.mgs.finance.statement.service.SupplierStatementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SupplierStatementServer {
    
    @Autowired
    private SupplierStatementService supplierStatementService;

    /**
     * 已出账单查询
     */
    @PostMapping("/finance/supplier/queryStatementList")
    public Response queryStatementList(@RequestBody QuerySupplierStatementListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=supplierStatementService.queryStatementList(request);
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
    @PostMapping("/finance/supplier/queryUncheckOutSupplierList")
    public Response queryUncheckOutSupplierList(@RequestBody QueryUncheckOutSupplierListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=supplierStatementService.queryUncheckOutSupplierList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryUncheckOutSupplierList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 创建账单
     */
    @PostMapping("/finance/supplier/createStatement")
    public Response createStatement(@RequestBody CreateSupplierStatementDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.createStatement(request);
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
    @PostMapping("/finance/supplier/queryStatementDetail")
    public Response queryStatementDetail(@RequestBody StatementIdDTO request){
        Response response = new Response();
        try{
            SupplierStatementDetailDTO supplierStatementDetailDTO=supplierStatementService.queryStatementDetail(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(supplierStatementDetailDTO);
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
    @PostMapping("/finance/supplier/queryStatementOrderList")
    public Response queryStatementOrderList(@RequestBody QueryStatementSupplyOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=supplierStatementService.queryStatementOrderList(request);
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
    @PostMapping("/finance/supplier/queryUnCheckOutSupplyOrder")
    public Response queryUnCheckOutSupplyOrder(@RequestBody QueryUnCheckOutSupplyOrderDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=supplierStatementService.queryUnCheckOutSupplyOrder(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryUnCheckOutSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 添加账单明细
     */
    @PostMapping("/finance/supplier/addStatementOrderList")
    public Response addStatementOrderList(@RequestBody AddStatementSupplyOrderListDTO request){
        Response response = new Response();
        try{
            if(request != null && request.getStatementId() != null && CollectionUtils.isNotEmpty(request.getSupplyOrderIdList())) {
                response = supplierStatementService.addStatementOrderList(request);
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
    @PostMapping("/finance/supplier/deleteStatementOrderList")
    public Response deleteStatementOrderList(@RequestBody DeleteStatementOrderListDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.deleteStatementOrderList(request);
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
    @PostMapping("/finance/supplier/modifyStatementStatus")
    public Response modifyStatementStatus(@RequestBody ModifyStatementStatusDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.modifyStatementStatus(request);
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
    @PostMapping("/finance/supplier/queryUpdatedStatementOrderList")
    public Response queryUpdatedStatementOrderList(@RequestBody QueryUpdatedStatementOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=supplierStatementService.queryUpdatedStatementOrderList(request);
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
    @PostMapping("/finance/supplier/updateStatementOrderList")
    public Response updateStatementOrderList(@RequestBody StatementIdDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.updateStatementOrderList(request);
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
    @PostMapping("/finance/supplier/notifyCollectionOfStatement")
    public Response notifyCollectionOfStatement(@RequestBody NotifyCollectionOfStatementDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.notifyCollectionOfStatement(request);
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
    @PostMapping("/finance/supplier/notifyPaymentOfStatement")
    public Response notifyPaymentOfStatement(@RequestBody NotifyPaymentOfStatementDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.notifyPaymentOfStatement(request);
        } catch (Exception e) {
            log.error("notifyCollectionOfStatement server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改账单名称
     */
    @PostMapping("/finance/supplier/modifyStatementName")
    public Response modifyStatementName(@RequestBody ModifyStatementNameDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.modifyStatementName(request);
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
    @PostMapping("/finance/supplier/modifySettlementDate")
    public Response modifySettlementDate(@RequestBody ModifySettlementDateDTO request){
        Response response = new Response();
        try{
            response=supplierStatementService.modifySettlementDate(request);
        } catch (Exception e) {
            log.error("modifySettlementDate server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
