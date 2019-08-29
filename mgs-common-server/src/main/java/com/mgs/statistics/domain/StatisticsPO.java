package com.mgs.statistics.domain;


import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class StatisticsPO {



    /**
     * 本月销售间夜数
     */
    private BigDecimal  saleNightQty;

    /**
     * 天数
     */
    private BigDecimal  day;

    /**
     * 本月营收额
     */
    private BigDecimal sales;

    /**
     * 本月利润
     */
    private BigDecimal profit;

    /**
     * 利润率
     */
    private Integer profitRate;



}
