package com.mgs.controller.order;

import com.alibaba.fastjson.JSON;
import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.OrderQueryRemote;
import com.mgs.order.remote.OrderRemote;
import com.mgs.order.remote.request.LockOrderDTO;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.request.QueryConfirmOrderInfoDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderRemarkDTO;
import com.mgs.order.remote.request.QueryOrderStatisticsDTO;
import com.mgs.order.remote.request.SupplyOrderIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.OrderSimpleDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/order")
public class OrderQueryController extends BaseController {

    @Autowired
    private OrderQueryRemote orderQueryRemote;

    @Autowired
    private OrderRemote orderRemote;

    @RequestMapping(value = "/queryOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderList(@RequestBody QueryOrderListDTO request) {
        Response response=null;
        try {
            if (null != request) {

                if (null !=  request.getChannelCode() &&  request.getChannelCode().equals("All")) {
                    request.setChannelCode(null);
                }

                if (null !=  request.getOrderSettlementType() &&  request.getOrderSettlementType().equals(-1)) {
                    request.setOrderSettlementType(null);
                }

                if (null != request.getMarkedStatus() && request.getMarkedStatus().equals(-1)) {
                    request.setMarkedStatus(null);
                }
                if (null != request.getOrderConfirmationStatus() && request.getOrderConfirmationStatus().equals(-1)) {
                    request.setOrderConfirmationStatus(null);
                }
                if (null != request.getSupplyOrderConfirmationStatus() && request.getSupplyOrderConfirmationStatus().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }
                if (null != request.getOrderSettlementStatus() && request.getOrderSettlementStatus().equals(-1)) {
                    request.setOrderSettlementStatus(null);
                }
                if (null != request.getSupplyOrderSettlementType() && request.getSupplyOrderSettlementType().equals(-1)) {
                    request.setSupplyOrderSettlementType(null);
                }
                if (null != request.getSupplyOrderSettlementStatus() && request.getSupplyOrderSettlementStatus().equals(-1)) {
                    request.setSupplyOrderSettlementStatus(null);
                }
            }
            request.setOperator(getLoginName());
            request.setCompanyCode(getCompanyCode());
            response=orderQueryRemote.queryOrderList(request);
            log.info("orderQueryRemote.queryOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryOrderStatistics",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderStatistics() {
        Response response=null;
        try {
            QueryOrderStatisticsDTO request=new QueryOrderStatisticsDTO();
            request.setOperator(getLoginName());
            request.setCompanyCode(getCompanyCode());
            response=orderQueryRemote.queryOrderStatistics(request);
            log.info("orderQueryRemote.queryOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryOrderDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderDetail(@RequestBody OrderCodeDTO request) {
        Response response=null;
        try {
            //查询订单是否存在
            QueryOrderListDTO queryOrderListDTO=new QueryOrderListDTO();
            if (StringUtil.isValidString(request.getOrderCode())) {
                queryOrderListDTO.setOrderCode(request.getOrderCode());
            }else {//当订单编码不存在时，才使用供货单编码作为条件查询订单
                queryOrderListDTO.setSupplyOrderCode(request.getSupplyOrderCode());
            }
            queryOrderListDTO.setCompanyCode(getCompanyCode());
            queryOrderListDTO.setOperator(getLoginName());
            Response orderListResponse=orderQueryRemote.queryOrderList(queryOrderListDTO);
            if (orderListResponse==null
                    || orderListResponse.getResult()==ResultCodeEnum.FAILURE.code
                    || orderListResponse.getModel()==null){
                response=new Response(ResultCodeEnum.FAILURE.code,null, "查询订单失败");
                return response;
            }
            PaginationSupportDTO orderListPagination=JSON.parseObject(JSON.toJSONString(orderListResponse.getModel()),PaginationSupportDTO.class);
            if (orderListPagination.getItemList().size()==0){
                response=new Response(ResultCodeEnum.FAILURE.code,null, "订单不存在");
                return response;
            }
            OrderSimpleDTO orderSimpleDTO=JSON.parseObject(JSON.toJSONString(orderListPagination.getItemList().get(0)),OrderSimpleDTO.class);
            //订单加锁
//            LockOrderDTO lockOrderDTO=new LockOrderDTO();
//            lockOrderDTO.setOrderId(orderSimpleDTO.getOrderId());
//            lockOrderDTO.setLockType(1);
//            lockOrderDTO.setOperator(getLoginName());
//            lockOrderDTO.setOperatorUser(getLoginName());
//            response=orderRemote.lockOrder(lockOrderDTO);
//            if (response==null){
//                response=new Response(ResultCodeEnum.FAILURE.code,null, "加锁失败");
//                return response;
//            }else if (response.getResult()==ResultCodeEnum.FAILURE.code){
//                return response;
//            }
            request.setOrderCode(orderSimpleDTO.getOrderCode());
            request.setOrderOwnerName(getLoginName());
            response=orderQueryRemote.queryOrderDetail(request);
            log.info("orderQueryRemote.queryOrderDetail result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderDetail 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryOrderRemark",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderRemark(@RequestBody QueryOrderRemarkDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.queryOrderRemark(request);
            log.info("orderQueryRemote.queryOrderRemark result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderRemark 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryOrderLog",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderLog(@RequestBody OrderIdDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.queryOrderLog(request);
            log.info("orderQueryRemote.queryOrderLog result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderLog 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryOrderRequest",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderRequest(@RequestBody OrderIdDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.queryOrderRequest(request);
            log.info("orderQueryRemote.queryOrderRequest result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderRequest 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryConfirmOrderInfo",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryConfirmOrderInfo(@RequestBody QueryConfirmOrderInfoDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.queryConfirmOrderInfo(request);
            log.info("orderQueryRemote.queryConfirmOrderInfo result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryConfirmOrderInfo 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryOrderPriceItem",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderPriceItem(@RequestBody OrderIdDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.queryOrderPriceItem(request);
            log.info("orderQueryRemote.queryOrderPriceItem result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.queryOrderPriceItem 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/previewSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response previewSupplyOrder(@RequestBody SupplyOrderIdDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.previewSupplyOrder(request);
            log.info("orderQueryRemote.previewSupplyOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.previewSupplyOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/querySupplyOrderResult",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderResult(@RequestBody SupplyOrderIdDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.querySupplyOrderResult(request);
            log.info("orderQueryRemote.querySupplyOrderResult result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.querySupplyOrderResult 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/querySupplyProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyProduct(@RequestBody SupplyProductIdDTO request) {
        Response response=null;
        try {
            response=orderQueryRemote.querySupplyProduct(request);
            log.info("orderQueryRemote.querySupplyProduct result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.querySupplyProduct 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
