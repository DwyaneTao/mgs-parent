package com.mgs.statistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.statistics.domain.OperateStatisticsPO;
import com.mgs.statistics.domain.StatisticsPO;
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
import com.mgs.statistics.mapper.StatisticsMapper;
import com.mgs.statistics.service.StatisticsService;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    /**
     * 客户对账统计
     * @param requestMap
     * @return
     */
    @Override
    public AgentStatisticsDTO queryAgentStatistics(Map<String, String> requestMap) {
        AgentStatisticsDTO agentStatisticsDTO =  statisticsMapper.queryStatementStatistics(requestMap);
        Integer overdueOrder = statisticsMapper.queryStatementOrderStatistics(requestMap);
        agentStatisticsDTO.setOverdueOrder(overdueOrder);
        return agentStatisticsDTO;
    }

    /**
     * 出纳记账统计
     * @param requestMap
     * @return
     */
    @Override
    public CashierStatisticsDTO queryCashierStatistics(Map<String, String> requestMap) {
        return statisticsMapper.queryCashierStatistics(requestMap);
    }

    /***
     * 财务概况（近三个月）
     * //TODO 近三个月没写
     * @param requestMap
     * @return
     */
    @Override
    public FinanceStatisticsDTO queryFinanceStatistics(Map<String, String> requestMap) {
        FinanceStatisticsDTO financeStatisticsDTO = new FinanceStatisticsDTO();

        // 客户单结未收+ 客户周期结未出账订单未收
        BigDecimal agentOrderUnReceived = statisticsMapper.queryAgentOrderUnReceived(requestMap);
        if(agentOrderUnReceived == null){
            agentOrderUnReceived = new BigDecimal("0");
        }

        // 客户账单未收
        BigDecimal agentStatementUnReceived = statisticsMapper.queryAgentStatementUnReceived(requestMap);
        if(agentStatementUnReceived == null){
            agentStatementUnReceived = new BigDecimal("0");
        }

        // 供应商单结未付+ 供应商周期结未出账供应单未付
        BigDecimal supplierOrderUnpaid = statisticsMapper.querySupplierOrderUnpaid(requestMap);
        if(supplierOrderUnpaid == null){
            supplierOrderUnpaid = new BigDecimal("0");
        }

        // 供应商账单未付
        BigDecimal supplierStatementUnpaid = statisticsMapper.querySupplierStatementUnpaid(requestMap);
        if(supplierStatementUnpaid == null){
            supplierStatementUnpaid = new BigDecimal("0");
        }

        financeStatisticsDTO.setAgentUnreceivedAmt(agentOrderUnReceived.add(agentStatementUnReceived));
        financeStatisticsDTO.setSupplierUnpaidAmt(supplierOrderUnpaid.add(supplierStatementUnpaid));
        return financeStatisticsDTO;
    }

    /**
     * 现金动态统计
     * @param requestMap
     * @return
     */
    @Override
    public CashStatusStatisticsDTO queryCashStatusStatistics(Map<String, String> requestMap) {
        CashStatusStatisticsDTO cashStatusStatisticsDTO =  statisticsMapper.queryCashStatusStatistics(requestMap);
        return cashStatusStatisticsDTO;
    }


    @Override
    public BusinessStatisticsDTO queryBusinessStatistics(Map<String, String> requestMap) {
        BusinessStatisticsDTO businessStatisticsDTO = new BusinessStatisticsDTO();

        requestMap.put("data",DateUtil.dateToString(DateUtil.getDate(new Date(),0),"yyyy-MM-dd"));
        BigDecimal bg = new BigDecimal(100);
        StatisticsPO statisticsPO = statisticsMapper.queryBusinessStatistics(requestMap);//查询本月
        if (null != statisticsPO) {
            businessStatisticsDTO.setCurrentMonthSaleNightQty(statisticsPO.getDay().multiply(statisticsPO.getSaleNightQty()).intValue());
            businessStatisticsDTO.setCurrentMonthSales(statisticsPO.getSales());
            businessStatisticsDTO.setCurrentMonthProfit(statisticsPO.getProfit());
            businessStatisticsDTO.setCurrentMonthProfitRate(statisticsPO.getProfit().divide(statisticsPO.getSales(), 2, BigDecimal.ROUND_HALF_UP).multiply(bg).stripTrailingZeros().toPlainString()+"%");
        }

        requestMap.put("data",DateUtil.dateToString(DateUtil.getDate(new Date(),-1),"yyyy-MM-dd"));
        StatisticsPO statisticsPO1 = statisticsMapper.queryBusinessStatistics(requestMap);//查询上月
        if (null != statisticsPO1) {
            businessStatisticsDTO.setPrevMonthSaleNightQty(statisticsPO1.getDay().multiply(statisticsPO1.getSaleNightQty()).intValue());
            businessStatisticsDTO.setPrevMonthProfit(statisticsPO1.getProfit());
            businessStatisticsDTO.setPrevMonthSales(statisticsPO1.getSales());
            businessStatisticsDTO.setPrevMonthProfitRate(statisticsPO1.getProfit().divide(statisticsPO1.getSales(), 2, BigDecimal.ROUND_HALF_UP).multiply(bg).stripTrailingZeros().toPlainString()+"%");
        }

        return businessStatisticsDTO;
    }

    @Override
    public CurrentBusinessStatisticsDTO queryCurrentMonthsOperateStatistics(Map<String, String> requestMap) {
        CurrentBusinessStatisticsDTO  currentBusinessStatisticsDTO = new CurrentBusinessStatisticsDTO();

        requestMap.put("data",DateUtil.dateToString(DateUtil.getDate(new Date(),0),"yyyy-MM-dd"));
        StatisticsPO statisticsPO = statisticsMapper.queryBusinessStatistics(requestMap);//查询本月
        currentBusinessStatisticsDTO.setCurrentMonthSaleNightQty(statisticsPO.getDay().multiply(statisticsPO.getSaleNightQty()).intValue());
        currentBusinessStatisticsDTO.setCurrentMonthSales(statisticsPO.getSales());
        currentBusinessStatisticsDTO.setCurrentMonthProfit(statisticsPO.getProfit());
        BigDecimal bg = new BigDecimal(100);
        currentBusinessStatisticsDTO.setCurrentMonthProfitRate(statisticsPO.getProfit().divide(statisticsPO.getSales(), 2, BigDecimal.ROUND_HALF_UP).multiply(bg).stripTrailingZeros().toPlainString()+"%");

        return currentBusinessStatisticsDTO;
    }

    @Override
    public PaginationSupportDTO<BusinessOverviewStatisticsDTO> queryYearBusinessStatistics(Map<String, String> requestMap) {

        BusinessOverviewStatisticsDTO businessOverviewStatisticsDTO = new BusinessOverviewStatisticsDTO();
        List<BusinessOverviewStatisticsDTO> list = statisticsMapper.queryYearBusinessStatistics(requestMap);
        BigDecimal bg = new BigDecimal(100);

        for(BusinessOverviewStatisticsDTO businessOverviewStatisticsDTOS:list){
            BigDecimal bg1 = new BigDecimal(businessOverviewStatisticsDTOS.getProfitRate());
            BigDecimal  ProfitRate=bg1.setScale(2,BigDecimal.ROUND_UP);
            businessOverviewStatisticsDTOS.setProfitRate(ProfitRate.multiply(bg).stripTrailingZeros().toPlainString()+"%");
        }

        PageInfo<BusinessOverviewStatisticsDTO> page = new PageInfo<BusinessOverviewStatisticsDTO>(list);
        PaginationSupportDTO<BusinessOverviewStatisticsDTO> paginationSupport= new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public PaginationSupportDTO<SalesDetailStatisticsDTO> querySalesDetailStatistics(QuerySaleStatisticsDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<SalesDetailStatisticsDTO>   list=   statisticsMapper.querySalesDetailStatistics(request);
        PageInfo<SalesDetailStatisticsDTO>  page = new PageInfo<SalesDetailStatisticsDTO>(list);
        PaginationSupportDTO<SalesDetailStatisticsDTO> paginationSupport= new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public SaleStatisticsDTO querySalesStatistics(QuerySaleStatisticsDTO request) {
        BigDecimal bg = new BigDecimal(100);
        SaleStatisticsDTO  saleStatisticsDTO =   statisticsMapper.querySalesStatistics(request);
        if(null != saleStatisticsDTO){
          BigDecimal profitRate = new BigDecimal(saleStatisticsDTO.getProfitRate()).setScale(2,BigDecimal.ROUND_UP);
          saleStatisticsDTO.setProfitRate(profitRate.multiply(bg).stripTrailingZeros().toPlainString()+"%");
        }
        return saleStatisticsDTO;
    }


}
