package com.mgs.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 财务概况统计
 */
@Data
public class FinanceStatisticsDTO {

    /**
     * 客户未收
     */
    private BigDecimal agentUnreceivedAmt;

    /**
     * 供应商未付
     */
    private BigDecimal supplierUnpaidAmt;
}
