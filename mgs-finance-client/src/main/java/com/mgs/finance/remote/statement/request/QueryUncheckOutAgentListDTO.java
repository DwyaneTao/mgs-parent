package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryUncheckOutAgentListDTO extends BaseRequest{

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 日期查询类型：0下单日期，1入住日期，2离店日期
     */
    private String dateQueryType;

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
}
