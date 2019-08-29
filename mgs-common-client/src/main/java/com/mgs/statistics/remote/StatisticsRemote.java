package com.mgs.statistics.remote;

import com.mgs.common.Response;
import com.mgs.statistics.dto.QuerySaleStatisticsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "mgs-common-server")
public interface StatisticsRemote {

    /**
     * 统计客户对账
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryAgentStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryAgentStatistics(@RequestBody Map<String, String> requestMap);

    /**
     * 出纳记账统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCashierStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryCashierStatistics(@RequestBody Map<String, String> requestMap);

    /**
     * 财务概况(近三个月)
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryFinanceStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryFinanceStatistics(@RequestBody Map<String, String> requestMap);

    /**
     * 现金动态统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCashStatusStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryCashStatusStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 本月和上月统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryMonthsOperateStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryMonthsOperateStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 本月统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryCurrentMonthsBusinessStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryCurrentMonthsBusinessStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 本年统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/queryYearBusinessStatistics", produces = {"application/json;charset=UTF-8"})
    Response queryYearBusinessStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 销售明细统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/querySalesDetailStatistics", produces = {"application/json;charset=UTF-8"})
    Response querySalesDetailStatistics(@RequestBody QuerySaleStatisticsDTO requestMap);

    /**
     * 销售统计
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/statistics/querySalesStatistics", produces = {"application/json;charset=UTF-8"})
    Response querySalesStatistics(@RequestBody QuerySaleStatisticsDTO requestMap);
}
