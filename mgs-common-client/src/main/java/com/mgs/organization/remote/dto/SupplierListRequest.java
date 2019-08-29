package com.mgs.organization.remote.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

/**
 * @author py
 * @date 2019/6/29 16:29
 **/
@Data
public class SupplierListRequest extends BaseRequest {
    /**
     * 供应商Id
     */
    private Integer supplierId;
    /**
     * 供应商类型
     */
    private Integer supplierType;
    /**
     * 供应商企业名称
     */
    private String supplierName;
    /**
     * 总管理员姓名
     */
    private String adminName;
    /**
     * 总管理员账号
     */
    private String adminAccount;
    /**
     * 总管理员手机号
     */
    private String adminTel;
    /**
     * 结算方式
     */
    private Integer settlementType;
    /**
     *结算币种类型
     */
    private Integer settlementCurrency;

    /**
     *我司采购经理Id
     */
    private Integer purchaseManagerId;
    /**
     *我司采购经理姓名
     */
    private String purchaseManager;
    /**
     *供应商编码
     */
    private String supplierCode;
    /**
     * 运营商编码
     */
    private String  companyCode;
    /**
     * 供应商启用状态
     */
    private Integer availableStatus;

}
