package com.mgs.finance.workorder.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.workorder.request.ConfirmWorkOrderDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderIdDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderDTO;
import com.mgs.finance.workorder.service.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WorkOrderServer {

    @Autowired
    private WorkOrderService workOrderService;

    /**
     * 财务工单查询
     */
    @PostMapping("/finance/workorder/queryWorkOrderList")
    public Response queryWorkOrderList(@RequestBody QueryWorkOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=workOrderService.queryWorkOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryWorkOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 工单详情
     */
    @PostMapping("/finance/workorder/queryWorkOrderDetail")
    public Response queryWorkOrderDetail(@RequestBody WorkOrderIdDTO request){
        Response response = new Response();
        try{
            WorkOrderDTO workOrderDTO=workOrderService.queryWorkOrderDetail(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(workOrderDTO);
        } catch (Exception e) {
            log.error("queryWorkOrderDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 确认工单
     */
    @PostMapping("/finance/workorder/confirmWorkOrder")
    public Response confirmWorkOrder(@RequestBody ConfirmWorkOrderDTO request){
        Response response = new Response();
        try{
            response=workOrderService.confirmWorkOrder(request);
        } catch (Exception e) {
            log.error("queryOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除工单
     */
    @PostMapping("/finance/workorder/deleteWorkOrder")
    public Response deleteWorkOrder(@RequestBody WorkOrderIdDTO request){
        Response response = new Response();
        try{
            response=workOrderService.deleteWorkOrder(request);
        } catch (Exception e) {
            log.error("deleteWorkOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
