package com.mgs.controller.finance.ontime;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.SupplyOrderFinanceRemote;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderListDTO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.request.SupplyOrderIdListDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/finance/supplier")
public class SupplyOrderOnTimeController extends BaseController {

    @Autowired
    private SupplyOrderFinanceRemote supplyOrderFinanceRemote;

    /**
     * 单结订单查询
     */
    @RequestMapping(value = "/querySupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderList(@RequestBody QueryOnTimeSupplyOrderListDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            if (StringUtil.isValidString(request.getSettlementStatus()) && request.getSettlementStatus().equals("-1")) {
               request.setSettlementStatus(null);
            }

            if (StringUtil.isValidString(request.getOverdueStatus()) && request.getOverdueStatus().equals("-1")) {
                request.setOverdueStatus(null);
            }

            response=supplyOrderFinanceRemote.queryOnTimeSupplyOrderList(request);
            log.info("supplyOrderFinanceRemote.queryOnTimeSupplyOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.queryOnTimeSupplyOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知收款
     */
    @RequestMapping(value = "/notifyCollectionOfSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfSupplyOrder(@RequestBody NotifyCollectionOfSupplyOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=supplyOrderFinanceRemote.notifyCollectionOfSupplyOrder(request);
            log.info("supplyOrderFinanceRemote.notifyCollectionOfSupplyOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.notifyCollectionOfSupplyOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @RequestMapping(value = "/notifyPaymentOfSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfSupplyOrder(@RequestBody NotifyPaymentOfSupplyOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=supplyOrderFinanceRemote.notifyPaymentOfSupplyOrder(request);
            log.info("supplyOrderFinanceRemote.notifyPaymentOfSupplyOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.notifyPaymentOfSupplyOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款
     */
    @RequestMapping(value = "/notifyCollectionOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfSupplyOrderList(@RequestBody NotifyCollectionOfSupplyOrderListDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=supplyOrderFinanceRemote.notifyCollectionOfSupplyOrderList(request);
            log.info("supplyOrderFinanceRemote.notifyCollectionOfSupplyOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.notifyCollectionOfSupplyOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款
     */
    @RequestMapping(value = "/notifyPaymentOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfSupplyOrderList(@RequestBody NotifyPaymentOfSupplyOrderListDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=supplyOrderFinanceRemote.notifyPaymentOfSupplyOrderList(request);
            log.info("supplyOrderFinanceRemote.notifyPaymentOfSupplyOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.notifyPaymentOfSupplyOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务收款预览
     */
    @RequestMapping(value = "/notifyCollectionPreviewOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionPreviewOfSupplyOrderList(@RequestBody SupplyOrderIdListDTO request) {
        Response response=null;
        try {
            response=supplyOrderFinanceRemote.notifyCollectionPreviewOfSupplyOrderList(request);
            log.info("supplyOrderFinanceRemote.notifyCollectionPreviewOfSupplyOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.notifyCollectionPreviewOfSupplyOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 合并通知财务付款预览
     */
    @RequestMapping(value = "/notifyPaymentPreviewOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentPreviewOfSupplyOrderList(@RequestBody SupplyOrderIdListDTO request) {
        Response response=null;
        try {
            response=supplyOrderFinanceRemote.notifyPaymentPreviewOfSupplyOrderList(request);
            log.info("supplyOrderFinanceRemote.notifyPaymentPreviewOfSupplyOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderFinanceRemote.notifyPaymentPreviewOfSupplyOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
