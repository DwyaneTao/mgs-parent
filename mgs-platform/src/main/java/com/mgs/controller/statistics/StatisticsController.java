package com.mgs.controller.statistics;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.statistics.dto.AgentStatisticsDTO;
import com.mgs.statistics.dto.CashStatusStatisticsDTO;
import com.mgs.statistics.dto.CashierStatisticsDTO;
import com.mgs.statistics.dto.FinanceStatisticsDTO;
import com.mgs.statistics.dto.QuerySaleStatisticsDTO;
import com.mgs.statistics.remote.StatisticsRemote;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class StatisticsController extends BaseController {

    @Autowired
    private StatisticsRemote statisticsRemote;


    /**
     * 客户对账统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryAgentStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryAgentStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("companyCode", super.getCompanyCode());
            response = statisticsRemote.queryAgentStatistics(requestMap);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 出纳记账统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCashierStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryCashierStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("companyCode", super.getCompanyCode());
            response = statisticsRemote.queryCashierStatistics(requestMap);

        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务概况(近三个月)
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryFinanceStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryFinanceStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("companyCode", super.getCompanyCode());
            response = statisticsRemote.queryFinanceStatistics(requestMap);

        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 现金动态统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCashStatusStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryCashStatusStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("startDate"))
                    && StringUtil.isValidString(requestMap.get("endDate"))) {
                requestMap.put("companyCode", super.getCompanyCode());
                response = statisticsRemote.queryCashStatusStatistics(requestMap);

            }else {
                response.setResult(ResultCodeEnum.FAILURE.code);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 上月本月经营概况
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryMonthsOperateStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryMonthsOperateStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("companyCode", super.getCompanyCode());
            response = statisticsRemote.queryMonthsOperateStatistics(requestMap);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 本月经营概况
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCurrentMonthsBusinessStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryCurrentMonthsBusinessStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("companyCode", super.getCompanyCode());
            response = statisticsRemote.queryCurrentMonthsBusinessStatistics(requestMap);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 本年经营概况
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryYearBusinessStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryYearBusinessStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("companyCode", super.getCompanyCode());
            response = statisticsRemote.queryYearBusinessStatistics(requestMap);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 销售明细统计
     * @param request
     * @return
     */
    @PostMapping(value = "/statistics/querySalesDetailStatistics", produces = {"application/json;charset=UTF-8"})
    public Response querySalesDetailStatistics(@RequestBody QuerySaleStatisticsDTO request){
        Response response = new Response();
        try{
            if (null != request) {

                if (null !=  request.getChannelCode() &&  request.getChannelCode().equals("All")) {
                    request.setChannelCode(null);
                }

                if (null !=  request.getOrderConfirmationStatus() &&  request.getOrderConfirmationStatus().equals(-1)) {
                    request.setOrderConfirmationStatus(null);
                }

                if (null != request.getPurchaseManagerId() && request.getPurchaseManagerId().equals(-1)) {
                    request.setPurchaseManagerId(null);
                }

                if (null != request.getSaleManagerId() && request.getSaleManagerId().equals(-1)) {
                    request.setPurchaseManagerId(null);
                }

                if (null != request.getSupplyOrderConfirmationStatus() && request.getSupplyOrderConfirmationStatus().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }

                if (null != request.getSupplyOrderPayableType() && request.getSupplyOrderPayableType().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }

                if (null != request.getOrderReceivableType() && request.getOrderReceivableType().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }


            }


            request.setCompanyCode(super.getCompanyCode());
            response = statisticsRemote.querySalesDetailStatistics(request);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 销售统计
     * @param request
     * @return
     */
    @PostMapping(value = "/statistics/querySalesStatistics", produces = {"application/json;charset=UTF-8"})
    public Response querySalesStatistics(@RequestBody QuerySaleStatisticsDTO request){
        Response response = new Response();
        try{

            if (null != request) {

                if (null !=  request.getChannelCode() &&  request.getChannelCode().equals("All")) {
                    request.setChannelCode(null);
                }

                if (null !=  request.getOrderConfirmationStatus() &&  request.getOrderConfirmationStatus().equals(-1)) {
                    request.setOrderConfirmationStatus(null);
                }

                if (null != request.getPurchaseManagerId() && request.getPurchaseManagerId().equals(-1)) {
                    request.setPurchaseManagerId(null);
                }

                if (null != request.getSaleManagerId() && request.getSaleManagerId().equals(-1)) {
                    request.setPurchaseManagerId(null);
                }

                if (null != request.getSupplyOrderConfirmationStatus() && request.getSupplyOrderConfirmationStatus().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }

                if (null != request.getSupplyOrderPayableType() && request.getSupplyOrderPayableType().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }

                if (null != request.getOrderReceivableType() && request.getOrderReceivableType().equals(-1)) {
                    request.setSupplyOrderConfirmationStatus(null);
                }


            }
            request.setCompanyCode(super.getCompanyCode());
            response = statisticsRemote.querySalesStatistics(request);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
