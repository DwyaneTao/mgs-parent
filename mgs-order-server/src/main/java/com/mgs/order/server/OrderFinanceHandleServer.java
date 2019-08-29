package com.mgs.order.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.CancelOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderWorkOrderDTO;
import com.mgs.order.service.OrderFinanceHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderFinanceHandleServer {

    @Autowired
    private OrderFinanceHandle orderFinanceHandle;

    /**
     * 确认工单
     */
    @PostMapping("/order/finance/handle/confirmOrderWorkOrder")
    public Response confirmOrderWorkOrder(@RequestBody ConfirmOrderWorkOrderDTO request){
        Response response = new Response();
        try{
            response=orderFinanceHandle.confirmOrderWorkOrder(request);
        } catch (Exception e) {
            log.error("confirmOrderWorkOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 取消工单
     */
    @PostMapping("/order/finance/handle/cancelOrderWorkOrder")
    public Response cancelOrderWorkOrder(@RequestBody CancelOrderWorkOrderDTO request){
        Response response = new Response();
        try{
            response=orderFinanceHandle.cancelOrderWorkOrder(request);
        } catch (Exception e) {
            log.error("cancelOrderWorkOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
