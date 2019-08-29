package com.mgs.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 现金动态统计
 */
@Data
public class CashStatusStatisticsDTO {

    /**
     * 收入
     */
    private BigDecimal income;

    /**
     * 支出
     */
    private BigDecimal expenditure;

    /**
     * 净流入
     */
    private BigDecimal netInflow;
}
