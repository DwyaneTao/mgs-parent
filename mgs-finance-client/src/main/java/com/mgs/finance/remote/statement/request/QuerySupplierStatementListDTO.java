package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class QuerySupplierStatementListDTO extends BaseRequest {

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
     * 账单编码
     */
    private String statementCode;

    /**
     * 账单状态：0未对账，1对账中，2已确定，3已取消
     */
    private String statementStatus;

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
