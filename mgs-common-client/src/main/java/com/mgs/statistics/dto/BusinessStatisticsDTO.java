package com.mgs.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BusinessStatisticsDTO {

    /**
     * 本月销售间夜数
     */
    private Integer  currentMonthSaleNightQty;

    /**
     * 上月销售间夜数
     */
    private Integer  prevMonthSaleNightQty;

    /**
     * 本月营收额
     */
    private BigDecimal currentMonthSales;

    /**
     * 上月营收额
     */
    private BigDecimal prevMonthSales;


    /**
     * 本月利润
     */
    private BigDecimal currentMonthProfit;


    /**
     * 上月利润
     */
    private BigDecimal prevMonthProfit;

    /**
     * 利润率
     */
    private String currentMonthProfitRate;

    /**
     * 上月利润率
     */
    private String prevMonthProfitRate;

}
