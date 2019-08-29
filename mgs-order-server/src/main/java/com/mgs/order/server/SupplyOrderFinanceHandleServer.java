package com.mgs.order.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.CancelSupplyOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmSupplyOrderWorkOrderDTO;
import com.mgs.order.service.SupplyOrderFinanceHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SupplyOrderFinanceHandleServer {

    @Autowired
    private SupplyOrderFinanceHandle supplyOrderFinanceHandle;

    /**
     * 确认工单
     */
    @PostMapping("/order/finance/confirmSupplyOrderWorkOrder")
    public Response confirmSupplyOrderWorkOrder(@RequestBody ConfirmSupplyOrderWorkOrderDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceHandle.confirmSupplyOrderWorkOrder(request);
        } catch (Exception e) {
            log.error("confirmSupplyOrderWorkOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 取消工单
     */
    @PostMapping("/order/finance/cancelSupplyOrderWorkOrder")
    public Response cancelSupplyOrderWorkOrder(@RequestBody CancelSupplyOrderWorkOrderDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceHandle.cancelSupplyOrderWorkOrder(request);
        } catch (Exception e) {
            log.error("cancelSupplyOrderWorkOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
