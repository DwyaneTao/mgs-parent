package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryUnCheckOutOrderDTO extends BaseRequest{

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
     * 订单号
     */
    private String orderCode;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 分销商编码
     */
    private String agentCode;
}
