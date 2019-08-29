package com.mgs.statistics.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrentBusinessStatisticsDTO {

    /**
     * 本月销售间夜数
     */
    private Integer  currentMonthSaleNightQty;


    /**
     * 本月营收额
     */
    private BigDecimal currentMonthSales;


    /**
     * 本月利润
     */
    private BigDecimal currentMonthProfit;


    /**
     * 本月利润率
     */
    private String currentMonthProfitRate;


}
