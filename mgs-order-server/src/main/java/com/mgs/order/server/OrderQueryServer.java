package com.mgs.order.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.response.PriceResponseDTO;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.request.QueryConfirmOrderInfoDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderRemarkDTO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.request.SupplyOrderIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.OrderDTO;
import com.mgs.order.remote.response.OrderLogDTO;
import com.mgs.order.remote.response.OrderRemarkDTO;
import com.mgs.order.remote.response.OrderRequestDTO;
import com.mgs.order.remote.response.OrderSimpleDTO;
import com.mgs.order.remote.response.OrderStatisticsDTO;
import com.mgs.order.remote.response.SupplyOrderPreviewDTO;
import com.mgs.order.remote.response.SupplyProductDetailDTO;
import com.mgs.order.remote.response.SupplyResultDTO;
import com.mgs.order.service.OrderQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class OrderQueryServer {

    @Autowired
    private OrderQueryService orderQueryService;

    /**
     * 查询订单列表
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderList")
    Response queryOrderList(@RequestBody QueryOrderListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO<OrderSimpleDTO> paginationSupportDTO=orderQueryService.queryOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单列表统计
     * @return
     */
    @PostMapping("/order/queryOrderStatistics")
    Response queryOrderStatistics(@RequestBody QueryOrderStatisticsDTO request){
        Response response = new Response();
        try{
            OrderStatisticsDTO queryOrderStatistics=orderQueryService.queryOrderStatistics(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(queryOrderStatistics);
        } catch (Exception e) {
            log.error("queryOrderStatistics server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单详情
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderDetail")
    Response queryOrderDetail(@RequestBody OrderCodeDTO request){
        Response response = new Response();
        try{
            OrderDTO orderDTO=orderQueryService.queryOrderDetail(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(orderDTO);
        } catch (Exception e) {
            log.error("queryOrderDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单备注
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderRemark")
    Response queryOrderRemark(@RequestBody QueryOrderRemarkDTO request){
        Response response = new Response();
        try{
            List<OrderRemarkDTO> orderRemarkDTOList=orderQueryService.queryOrderRemark(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(orderRemarkDTOList);
        } catch (Exception e) {
            log.error("queryOrderRemark server error!",e);
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
    @PostMapping("/order/queryOrderLog")
    Response queryOrderLog(@RequestBody OrderIdDTO request){
        Response response = new Response();
        try{
            List<OrderLogDTO> orderLogDTOList=orderQueryService.queryOrderLog(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(orderLogDTOList);
        } catch (Exception e) {
            log.error("queryOrderLog server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单申请
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderRequest")
    Response queryOrderRequest(@RequestBody OrderIdDTO request){
        Response response = new Response();
        try{
            List<OrderRequestDTO> orderRequestDTOList=orderQueryService.queryOrderRequest(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(orderRequestDTOList);
        } catch (Exception e) {
            log.error("queryOrderRequest server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单确认信息
     * @param request
     * @return
     */
    @PostMapping("/order/queryConfirmOrderInfo")
    Response queryConfirmOrderInfo(@RequestBody QueryConfirmOrderInfoDTO request){
        Response response = new Response();
        try{
            String content=orderQueryService.queryConfirmOrderInfo(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(content);
        } catch (Exception e) {
            log.error("queryConfirmOrderInfo server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询订单价格明细
     * @param request
     * @return
     */
    @PostMapping("/order/queryOrderPriceItem")
    Response queryOrderPriceItem(@RequestBody OrderIdDTO request){
        Response response = new Response();
        try{
            List<PriceResponseDTO> priceDTOList=orderQueryService.queryOrderPriceItem(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(priceDTOList);
        } catch (Exception e) {
            log.error("queryOrderPriceItem server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 供货单预览
     * @param request
     * @return
     */
    @PostMapping("/order/previewSupplyOrder")
    Response previewSupplyOrder(@RequestBody SupplyOrderIdDTO request){
        Response response = new Response();
        try{
            SupplyOrderPreviewDTO supplyOrderPreviewDTO=orderQueryService.previewSupplyOrder(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(supplyOrderPreviewDTO);
        } catch (Exception e) {
            log.error("previewSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询供货单结果
     * @param request
     * @return
     */
    @PostMapping("/order/querySupplyOrderResult")
    Response querySupplyOrderResult(@RequestBody SupplyOrderIdDTO request){
        Response response = new Response();
        try{
            SupplyResultDTO supplyResultDTO=orderQueryService.querySupplyOrderResult(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(supplyResultDTO);
        } catch (Exception e) {
            log.error("querySupplyOrderResult server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询供货单产品详情
     * @param request
     * @return
     */
    @PostMapping("/order/querySupplyProduct")
    Response querySupplyProduct(@RequestBody SupplyProductIdDTO request){
        Response response = new Response();
        try{
            SupplyProductDetailDTO supplyProductDetailDTO=orderQueryService.querySupplyProduct(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(supplyProductDetailDTO);
        } catch (Exception e) {
            log.error("querySupplyProduct server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
