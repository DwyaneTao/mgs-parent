package com.mgs.order.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.NotifyCollectionOfOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import com.mgs.order.service.OrderFinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderFinanceServer {

    @Autowired
    private OrderFinanceService orderFinanceService;

    /**
     * 单结订单查询
     */
    @PostMapping("/order/finance/queryOnTimeOrderList")
    public Response queryOnTimeOrderList(@RequestBody QueryOnTimeOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=orderFinanceService.queryOnTimeOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryOnTimeOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知收款
     */
    @PostMapping("/order/finance/notifyCollectionOfOrder")
    public Response notifyCollectionOfOrder(@RequestBody NotifyCollectionOfOrderDTO request){
        Response response = new Response();
        try{
            response=orderFinanceService.notifyCollectionOfOrder(request);
        } catch (Exception e) {
            log.error("notifyCollectionOfOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @PostMapping("/order/finance/notifyPaymentOfOrder")
    public Response notifyPaymentOfOrder(@RequestBody NotifyPaymentOfOrderDTO request){
        Response response = new Response();
        try{
            response=orderFinanceService.notifyPaymentOfOrder(request);
        } catch (Exception e) {
            log.error("notifyPaymentOfOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款
     */
    @PostMapping("/order/finance/notifyCollectionOfOrderList")
    public Response notifyCollectionOfOrderList(@RequestBody NotifyCollectionOfOrderListDTO request){
        Response response = new Response();
        try{
            response=orderFinanceService.notifyCollectionOfOrderList(request);
        } catch (Exception e) {
            log.error("notifyCollectionOfOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款
     */
    @PostMapping("/order/finance/notifyPaymentOfOrderList")
    public Response notifyPaymentOfOrderList(@RequestBody NotifyPaymentOfOrderListDTO request){
        Response response = new Response();
        try{
            response=orderFinanceService.notifyPaymentOfOrderList(request);
        } catch (Exception e) {
            log.error("notifyPaymentOfOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款预览
     */
    @PostMapping("/order/finance/notifyCollectionPreviewOfOrderList")
    public Response notifyCollectionPreviewOfOrderList(@RequestBody OrderIdListDTO request){
        Response response = new Response();
        try{
            response=orderFinanceService.notifyCollectionPreviewOfOrderList(request);
        } catch (Exception e) {
            log.error("notifyCollectionPreviewOfOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款预览
     */
    @PostMapping("/order/finance/notifyPaymentPreviewOfOrderList")
    public Response notifyPaymentPreviewOfOrderList(@RequestBody OrderIdListDTO request){
        Response response = new Response();
        try{
            response=orderFinanceService.notifyPaymentPreviewOfOrderList(request);
        } catch (Exception e) {
            log.error("notifyPaymentPreviewOfOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
