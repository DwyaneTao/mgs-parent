package com.mgs.finance.remote.lock.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class QuerySupplyOrderFinanceLockListDTO extends BaseRequest{

    /**
     * 分销商编码
     */
    private String supplierCode;

    /**
     * 分销商名称
     */
    private String supplierName;

    /**
     * 锁定状态：0未锁定，1已锁定
     */
    private String lockStatus;

    /**
     * 供货单号
     */
    private String supplyOrderCode;

    /**
     * 商家编码
     */
    private String companyCode;
}
