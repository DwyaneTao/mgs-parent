package com.mgs.controller.finance.ontime;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.OrderFinanceRemote;
import com.mgs.order.remote.request.NotifyCollectionOfOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/finance/agent")
public class OrderOnTimeController extends BaseController {

    @Autowired
    private OrderFinanceRemote orderFinanceRemote;

    /**
     * 单结订单查询
     */
    @RequestMapping(value = "/queryOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderList(@RequestBody QueryOnTimeOrderListDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            if ( StringUtil.isValidString(request.getSettlementStatus()) && request.getSettlementStatus().equals("-1")) {
                request.setSettlementStatus(null);
            }
            if (StringUtil.isValidString(request.getOverdueStatus()) && request.getOverdueStatus().equals("-1")) {
                request.setOverdueStatus(null);
            }

            response=orderFinanceRemote.queryOnTimeOrderList(request);
            log.info("orderFinanceRemote.queryOnTimeOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.queryOnTimeOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知收款
     */
    @RequestMapping(value = "/notifyCollectionOfOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfOrder(@RequestBody NotifyCollectionOfOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=orderFinanceRemote.notifyCollectionOfOrder(request);
            log.info("orderFinanceRemote.notifyCollectionOfOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.notifyCollectionOfOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @RequestMapping(value = "/notifyPaymentOfOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfOrder(@RequestBody NotifyPaymentOfOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=orderFinanceRemote.notifyPaymentOfOrder(request);
            log.info("orderFinanceRemote.notifyPaymentOfOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.notifyPaymentOfOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款
     */
    @RequestMapping(value = "/notifyCollectionOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfOrderList(@RequestBody NotifyCollectionOfOrderListDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=orderFinanceRemote.notifyCollectionOfOrderList(request);
            log.info("orderFinanceRemote.notifyCollectionOfOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.notifyCollectionOfOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款
     */
    @RequestMapping(value = "/notifyPaymentOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfOrderList(@RequestBody NotifyPaymentOfOrderListDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=orderFinanceRemote.notifyPaymentOfOrderList(request);
            log.info("orderFinanceRemote.notifyPaymentOfOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.notifyPaymentOfOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款预览
     */
    @RequestMapping(value = "/notifyCollectionPreviewOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionPreviewOfOrderList(@RequestBody OrderIdListDTO request) {
        Response response=null;
        try {
            response=orderFinanceRemote.notifyCollectionPreviewOfOrderList(request);
            log.info("orderFinanceRemote.notifyCollectionPreviewOfOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.notifyCollectionPreviewOfOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款预览
     */
    @RequestMapping(value = "/notifyPaymentPreviewOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentPreviewOfOrderList(@RequestBody OrderIdListDTO request) {
        Response response=null;
        try {
            response=orderFinanceRemote.notifyPaymentPreviewOfOrderList(request);
            log.info("orderFinanceRemote.notifyPaymentPreviewOfOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderFinanceRemote.notifyPaymentPreviewOfOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
