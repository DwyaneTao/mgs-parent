package com.mgs.statistics.dto;

import lombok.Data;

/**
 * 客户对账统计
 */
@Data
public class AgentStatisticsDTO {

    /**
     * 待确认账单
     */
    private Integer unconfirmedStatement;

    /**
     * 逾期账单
     */
    private Integer overdueStatement;

    /**
     * 逾期订单
     */
    private Integer overdueOrder;
}
