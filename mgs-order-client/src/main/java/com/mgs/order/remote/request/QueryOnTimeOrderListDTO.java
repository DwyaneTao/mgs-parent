package com.mgs.order.remote.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class QueryOnTimeOrderListDTO extends BaseRequest{

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 开始结算日期
     */
    private String startDate;

    /**
     * 结束结算日期
     */
    private String endDate;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 结算状态：0未结算，1已结算
     */
    private String settlementStatus;

    /**
     * 是否逾期：0未逾期，1已逾期
     */
    private String overdueStatus;

    /**
     * 商家编码
     */
    private String companyCode;
}
