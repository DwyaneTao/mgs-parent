package com.mgs.controller.finance.workorder;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.workorder.FinanceNofityRemote;
import com.mgs.finance.remote.workorder.WorkOrderRemote;
import com.mgs.finance.remote.workorder.request.BusinessCodeDTO;
import com.mgs.finance.remote.workorder.request.ConfirmWorkOrderDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderIdDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/finance/workorder")
public class WorkOrderController extends BaseController {

    @Autowired
    private WorkOrderRemote workOrderRemote;

    @Autowired
    private FinanceNofityRemote financeNofityRemote;

    /**
     * 财务工单查询
     */
    @RequestMapping(value = "/queryWorkOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryWorkOrderList(@RequestBody QueryWorkOrderListDTO request) {
        Response response=null;
        try {
            if(StringUtil.isValidString(request.getFinanceType()) && request.getFinanceType().equals("-1")){
                request.setFinanceType(null);
            }

            if(StringUtil.isValidString(request.getWorkOrderStatus()) && request.getWorkOrderStatus().equals("-1")){
                request.setWorkOrderStatus(null);
            }
            request.setCompanyCode(super.getCompanyCode());
            response=workOrderRemote.queryWorkOrderList(request);
            log.info("workOrderRemote.queryWorkOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("workOrderRemote.queryWorkOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 工单详情
     */
    @RequestMapping(value = "/queryWorkOrderDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryWorkOrderDetail(@RequestBody WorkOrderIdDTO request) {
        Response response=null;
        try {
            response=workOrderRemote.queryWorkOrderDetail(request);
            log.info("workOrderRemote.queryWorkOrderDetail result:"+response.getResult());
        } catch (Exception e) {
            log.error("workOrderRemote.queryWorkOrderDetail 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 确认工单
     */
    @RequestMapping(value = "/confirmWorkOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response confirmWorkOrder(@RequestBody ConfirmWorkOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(getUserName());
            response=workOrderRemote.confirmWorkOrder(request);
            log.info("workOrderRemote.confirmWorkOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("workOrderRemote.confirmWorkOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除工单
     */
    @RequestMapping(value = "/deleteWorkOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteWorkOrder(@RequestBody WorkOrderIdDTO request) {
        Response response=null;
        try {
            request.setOperator(getUserName());
            response=workOrderRemote.deleteWorkOrder(request);
            log.info("workOrderRemote.deleteWorkOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("workOrderRemote.deleteWorkOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知记录查询
     */
    @RequestMapping(value = "/financeNotificationLogList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response financeNotificationLogList(@RequestBody BusinessCodeDTO request) {
        Response response=null;
        try {
            response=financeNofityRemote.financeNotificationLogList(request);
            log.info("financeNofityRemote.financeNotificationLogList result:"+response.getResult());
        } catch (Exception e) {
            log.error("financeNofityRemote.financeNotificationLogList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
