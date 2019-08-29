package com.mgs.order.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderListDTO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.request.SupplyOrderIdListDTO;
import com.mgs.order.service.SupplyOrderFinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SupplyOrderFinanceServer {

    @Autowired
    private SupplyOrderFinanceService supplyOrderFinanceService;

    /**
     * 单结订单查询
     */
    @PostMapping("/order/finance/queryOnTimeSupplyOrderList")
    public Response queryOnTimeSupplyOrderList(@RequestBody QueryOnTimeSupplyOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=supplyOrderFinanceService.queryOnTimeSupplyOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryOnTimeSupplyOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知收款
     */
    @PostMapping("/order/finance/notifyCollectionOfSupplyOrder")
    public Response notifyCollectionOfSupplyOrder(@RequestBody NotifyCollectionOfSupplyOrderDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceService.notifyCollectionOfSupplyOrder(request);
        } catch (Exception e) {
            log.error("notifyCollectionOfSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @PostMapping("/order/finance/notifyPaymentOfSupplyOrder")
    public Response notifyPaymentOfSupplyOrder(@RequestBody NotifyPaymentOfSupplyOrderDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceService.notifyPaymentOfSupplyOrder(request);
        } catch (Exception e) {
            log.error("notifyPaymentOfSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款
     */
    @PostMapping("/order/finance/notifyCollectionOfSupplyOrderList")
    public Response notifyCollectionOfSupplyOrderList(@RequestBody NotifyCollectionOfSupplyOrderListDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceService.notifyCollectionOfSupplyOrderList(request);
        } catch (Exception e) {
            log.error("notifyCollectionOfSupplyOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款
     */
    @PostMapping("/order/finance/notifyPaymentOfSupplyOrderList")
    public Response notifyPaymentOfSupplyOrderList(@RequestBody NotifyPaymentOfSupplyOrderListDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceService.notifyPaymentOfSupplyOrderList(request);
        } catch (Exception e) {
            log.error("notifyPaymentOfSupplyOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款预览
     */
    @PostMapping("/order/finance/notifyCollectionPreviewOfSupplyOrderList")
    public Response notifyCollectionPreviewOfSupplyOrderList(@RequestBody SupplyOrderIdListDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceService.notifyCollectionPreviewOfSupplyOrderList(request);
        } catch (Exception e) {
            log.error("notifyCollectionPreviewOfSupplyOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款预览
     */
    @PostMapping("/order/finance/notifyPaymentPreviewOfSupplyOrderList")
    public Response notifyPaymentPreviewOfSupplyOrderList(@RequestBody SupplyOrderIdListDTO request){
        Response response = new Response();
        try{
            response=supplyOrderFinanceService.notifyPaymentPreviewOfSupplyOrderList(request);
        } catch (Exception e) {
            log.error("notifyPaymentPreviewOfSupplyOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
