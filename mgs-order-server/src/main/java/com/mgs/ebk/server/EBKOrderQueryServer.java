package com.mgs.ebk.server;

import com.mgs.common.Response;
import com.mgs.ebk.order.remote.request.CancelSupplyOrderDTO;
import com.mgs.ebk.order.remote.request.ConfirmSupplyOrderDTO;
import com.mgs.ebk.order.remote.request.QuerySupplyOrderListDTO;
import com.mgs.ebk.service.EBKOrderQueryService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Slf4j
public class EBKOrderQueryServer {

    @Autowired
    private EBKOrderQueryService supplyOrderQueryService;



    /**
     * 查询订单列表
     * @param request
     * @return
     */
    @PostMapping("ebk/order/queryOrderList")
    Response queryOrderList(@RequestBody QuerySupplyOrderListDTO request){
        Response response = new Response();
        try{
            response =supplyOrderQueryService.querySupplyOrderList(request);
        } catch (Exception e) {
            log.error("queryOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 查询订单列表
     * @param requestMap
     * @return
     */
    @PostMapping("ebk/order/queryOrderDetail")
    Response queryOrderDetail(@RequestBody Map<String,String>  requestMap){
        Response response = new Response();
        try{
            response =supplyOrderQueryService.querySupplyOrderDetail(requestMap);
        } catch (Exception e) {
            log.error("queryOrderDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 确认订单
     * @param request
     * @return
     */
    @PostMapping("ebk/order/confirmOrder")
    Response confirmSupplyOrder(@RequestBody ConfirmSupplyOrderDTO request){
        Response response = new Response();
        try{
            response =supplyOrderQueryService.confirmSupplyOrder(request);
        } catch (Exception e) {
            log.error("confirmSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 取消供货单
     * @param request
     * @return
     */
    @PostMapping("ebk/order/cancelOrder")
    Response cancelSupplyOrder(@RequestBody CancelSupplyOrderDTO request){
        Response response = new Response();
        try{
            response =supplyOrderQueryService.cancelSupplyOrder(request);
        } catch (Exception e) {
            log.error("confirmSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单日志
     * @param request
     * @return
     */
    @PostMapping("ebk/order/queryOrderLog")
    Response querySupplyOrderLog(@RequestBody Map<String,String> request){
        Response response = new Response();
        try{
            response =supplyOrderQueryService.querySupplyOrderLog(request);
        } catch (Exception e) {
            log.error("querySupplyOrderLog server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单统计
     * @param request
     * @return
     */
    @PostMapping("ebk/order/queryOrderStatistics")
    Response queryOrderStatistics(@RequestBody Map<String,String> request){
        Response response = new Response();
        try{
            response =supplyOrderQueryService.queryOrderStatistics(request);
        } catch (Exception e) {
            log.error("queryOrderStatistics server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

}
