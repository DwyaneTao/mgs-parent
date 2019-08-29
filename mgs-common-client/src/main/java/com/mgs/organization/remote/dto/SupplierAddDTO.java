package com.mgs.organization.remote.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author py
 * @date 2019/6/19 20:47
 **/
@Data
public class SupplierAddDTO {
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
    private String purchaseManagerName;
    /**
     *供应商编码
     */
    private String supplierCode;
    /**
     *数据创建时间
     */
    private Date creatDt;
    /**
     * 数据创建人
     */
    private String createdBy;
    /**
     * 数据修改人
     */
    private String modifiedBy;
    /**
     * 数据修改时间
     */
    private Date modifiedDt;
    /**
     * 供应商启用状态
     */
    private Integer availableStatus;
    /**
     * 域名
     */
    private String orgDomain;
}
