package com.mgs.statistics.dto;

import lombok.Data;

/**
 * 出纳记账统计
 */
@Data
public class CashierStatisticsDTO {

    /**
     * 待收款记账
     */
    private Integer receivableAmtAccounting;

    /**
     * 待付款记账
     */
    private Integer payableAmtAccounting;
}
