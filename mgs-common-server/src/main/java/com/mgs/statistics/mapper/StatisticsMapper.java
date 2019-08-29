package com.mgs.statistics.mapper;

import com.mgs.common.Response;
import com.mgs.statistics.domain.OperateStatisticsPO;
import com.mgs.statistics.domain.StatisticsPO;
import com.mgs.statistics.dto.AgentStatisticsDTO;
import com.mgs.statistics.dto.BusinessOverviewStatisticsDTO;
import com.mgs.statistics.dto.BusinessStatisticsDTO;
import com.mgs.statistics.dto.CashStatusStatisticsDTO;
import com.mgs.statistics.dto.CashierStatisticsDTO;
import com.mgs.statistics.dto.FinanceStatisticsDTO;
import com.mgs.statistics.dto.QuerySaleStatisticsDTO;
import com.mgs.statistics.dto.SaleStatisticsDTO;
import com.mgs.statistics.dto.SalesDetailStatisticsDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StatisticsMapper extends MySqlMapper<StatisticsPO> {

    /**
     * 统计客户对账（客户周期结有关【待确认账单，逾期账单】）
     * @param requestMap
     * @return
     */
    AgentStatisticsDTO queryStatementStatistics(Map<String, String> requestMap);

    /**
     * 统计客户对账（客户单结有关 【逾期订单】）
     * @param requestMap
     * @return
     */
    Integer queryStatementOrderStatistics(Map<String, String> requestMap);

    /**
     * 统计出纳记账
     * @param requestMap
     * @return
     */
    CashierStatisticsDTO queryCashierStatistics(Map<String, String> requestMap);

    /**
     * 客户单结未收+ 客户周期结未出账订单未收
     * @param requestMap
     * @return
     */
     BigDecimal queryAgentOrderUnReceived(Map<String, String> requestMap);

    /**
     * 客户账单未收
     * @param requestMap
     * @return
     */
    BigDecimal queryAgentStatementUnReceived(Map<String, String> requestMap);

    /**
     * 供应商单结未付+ 供应商周期结未出账供应单未付
     * @param requestMap
     * @return
     */
    BigDecimal querySupplierOrderUnpaid(Map<String, String> requestMap);

    /**
     * 供应商账单未付
     * @param requestMap
     * @return
     */
    BigDecimal querySupplierStatementUnpaid(Map<String, String> requestMap);

    /**
     * 现金动态统计
     * @param requestMap
     * @return
     */
    CashStatusStatisticsDTO queryCashStatusStatistics(Map<String, String> requestMap);

    /**
     * 本月统计
     * @param requestMap
     * @return
     */
    StatisticsPO queryBusinessStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 本月统计
     * @param requestMap
     * @return
     */
    List<BusinessOverviewStatisticsDTO>  queryYearBusinessStatistics(@RequestBody Map<String, String> requestMap);


    /**
     * 销售明细统计
     * @param
     * @return
     */
    List<SalesDetailStatisticsDTO>  querySalesDetailStatistics(@RequestBody QuerySaleStatisticsDTO request);


    /**
     * 销售统计
     * @param
     * @return
     */
    SaleStatisticsDTO querySalesStatistics(@RequestBody QuerySaleStatisticsDTO request);

}
