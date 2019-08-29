package com.mgs.finance.remote.statement.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateAgentStatementDTO implements Serializable{

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 账单名称
     */
    private String statementName;

    /**
     * 结算日期
     */
    private String settlementDate;

    /**
     * 日期查询类型：0下单日期，1入住日期，2离店日期
     */
    private Integer dateQueryType;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 操作人
     */
    private String operator;
}
