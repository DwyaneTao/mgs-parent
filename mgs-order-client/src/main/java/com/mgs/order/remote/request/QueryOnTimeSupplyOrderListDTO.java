package com.mgs.order.remote.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class QueryOnTimeSupplyOrderListDTO extends BaseRequest{

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 开始结算日期
     */
    private String startDate;

    /**
     * 结束结算日期
     */
    private String endDate;

    /**
     * 供货单号
     */
    private String supplyOrderCode;

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
