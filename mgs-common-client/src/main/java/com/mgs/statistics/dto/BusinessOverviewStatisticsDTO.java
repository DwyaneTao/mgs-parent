package com.mgs.statistics.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessOverviewStatisticsDTO {


    /**
     * 订单数量
     */
    private  Integer orderQty;

    /**
     * 成交间夜
     */
    private  Integer saleNightQty;

    /**
     * 时间
     */
    private  String period;

    /**
     * 销售额
     */
    private BigDecimal sales;

    /**
     * 采购成本
     */
    private  BigDecimal costs;

    /**
     * 利润
     */
    private  BigDecimal profit;

    /**
     * 利润率
     */
    private  String profitRate;



}
