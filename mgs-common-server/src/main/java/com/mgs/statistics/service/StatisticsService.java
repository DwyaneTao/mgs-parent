package com.mgs.statistics.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface StatisticsService {

    /**
     * 统计客户对账
     * @param requestMap
     * @return
     */
    AgentStatisticsDTO queryAgentStatistics(Map<String, String> requestMap);

    /**
     * 统计出纳记账
     * @param requestMap
     * @return
     */
    CashierStatisticsDTO queryCashierStatistics(Map<String, String> requestMap);

    /**
     * 财务概况(近三个月)
     * @param requestMap
     * @return
     */
    FinanceStatisticsDTO queryFinanceStatistics(Map<String, String> requestMap);

    /**
     * 现金动态统计
     * @param requestMap
     * @return
     */
    CashStatusStatisticsDTO queryCashStatusStatistics(Map<String, String> requestMap);


    /**
     * 本月和上月统计
     * @param requestMap
     * @return
     */
    BusinessStatisticsDTO queryBusinessStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 本月统计
     * @param requestMap
     * @return
     */
    CurrentBusinessStatisticsDTO queryCurrentMonthsOperateStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 本年统计
     * @param requestMap
     * @return
     */
    PaginationSupportDTO<BusinessOverviewStatisticsDTO> queryYearBusinessStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 销售明细统计
     * @param
     * @return
     */
    PaginationSupportDTO<SalesDetailStatisticsDTO> querySalesDetailStatistics(@RequestBody QuerySaleStatisticsDTO request);

    /**
     * 销售统计
     * @param
     * @return
     */
    SaleStatisticsDTO querySalesStatistics(@RequestBody QuerySaleStatisticsDTO request);
}
