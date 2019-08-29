package com.mgs.statistics.server;


import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.statistics.dto.AgentStatisticsDTO;
import com.mgs.statistics.dto.BusinessOverviewStatisticsDTO;
import com.mgs.statistics.dto.BusinessStatisticsDTO;
import com.mgs.statistics.dto.CashStatusStatisticsDTO;
import com.mgs.statistics.dto.CashierStatisticsDTO;
import com.mgs.statistics.dto.CurrentBusinessStatisticsDTO;
import com.mgs.statistics.dto.FinanceStatisticsDTO;
import com.mgs.statistics.dto.QuerySaleStatisticsDTO;
import com.mgs.statistics.dto.SaleStatisticsDTO;
import com.mgs.statistics.dto.SalesDetailStatisticsDTO;
import com.mgs.statistics.service.StatisticsService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class StatisticsServer {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 客户对账统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryAgentStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryAgentStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            AgentStatisticsDTO agentStatisticsDTO = statisticsService.queryAgentStatistics(requestMap);

            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(agentStatisticsDTO);
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
            CashierStatisticsDTO cashierStatisticsDTO = statisticsService.queryCashierStatistics(requestMap);

            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(cashierStatisticsDTO);
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
            FinanceStatisticsDTO financeStatisticsDTO = statisticsService.queryFinanceStatistics(requestMap);

            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(financeStatisticsDTO);
        }catch (Exception e){
            log.error("财务概况(近三个月)",e);
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
                CashStatusStatisticsDTO cashStatusStatisticsDTO = statisticsService.queryCashStatusStatistics(requestMap);

                response.setResult(ResultCodeEnum.SUCCESS.code);
                response.setModel(cashStatusStatisticsDTO);
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
     * 查询本月和上月经营统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryMonthsOperateStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryMonthsOperateStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            BusinessStatisticsDTO businessStatisticsDTO = statisticsService.queryBusinessStatistics(requestMap);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(businessStatisticsDTO);
        }catch (Exception e){
            log.error("查询本月和上月经营统计",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 查询本月经营统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCurrentMonthsOperateStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryCurrentMonthsOperateStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            CurrentBusinessStatisticsDTO currentBusinessStatisticsDTO = statisticsService.queryCurrentMonthsOperateStatistics(requestMap);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(currentBusinessStatisticsDTO);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }




    /**
     * 查询本年经营统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryYearBusinessStatistics", produces = {"application/json;charset=UTF-8"})
    public Response queryYearBusinessStatistics(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            PaginationSupportDTO<BusinessOverviewStatisticsDTO> paginationSupportDTO = statisticsService.queryYearBusinessStatistics(requestMap);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 销售明细统计
     * @param
     * @return
     */
    @PostMapping(value = "/statistics/querySalesDetailStatistics", produces = {"application/json;charset=UTF-8"})
    public Response querySalesDetailStatistics(@RequestBody QuerySaleStatisticsDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO<SalesDetailStatisticsDTO> paginationSupportDTO = statisticsService.querySalesDetailStatistics(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 销售统计
     * @param
     * @return
     */
    @PostMapping(value = "/statistics/querySalesStatistics", produces = {"application/json;charset=UTF-8"})
    public Response querySalesStatistics(@RequestBody QuerySaleStatisticsDTO request){
        Response response = new Response();
        try{
            SaleStatisticsDTO salesStatisticsDTO = statisticsService.querySalesStatistics(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(salesStatisticsDTO);
        }catch (Exception e){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }



}
