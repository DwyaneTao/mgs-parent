package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryUncheckOutSupplierListDTO extends BaseRequest{

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

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
