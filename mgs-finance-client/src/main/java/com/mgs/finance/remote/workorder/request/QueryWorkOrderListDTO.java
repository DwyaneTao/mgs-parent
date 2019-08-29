package com.mgs.finance.remote.workorder.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class QueryWorkOrderListDTO extends BaseRequest{

    /**
     * 结算状态：0未结算，1已结算
     */
    private String workOrderStatus;

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 财务类型：0收款，1付款
     */
    private String financeType;

    /**
     * 开始创建日期
     */
    private String startDate;

    /**
     * 结束创建日期
     */
    private String endDate;

    /**
     * 商家编码
     */
    private String companyCode;
}
