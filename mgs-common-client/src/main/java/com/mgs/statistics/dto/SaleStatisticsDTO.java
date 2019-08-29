package com.mgs.statistics.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleStatisticsDTO {

    /**
     * 销售间夜
     */
    private  Integer saleNightQty;

    /**
     * 销售金额
     */
    private  BigDecimal   saleAmt;

    /**
     * 未收金额
     */
    private  BigDecimal unreceivedAmt;

    /**
     * 利润
     */
    private  BigDecimal profit;

    /**
     * 利润率
     */
    private String profitRate;


}
