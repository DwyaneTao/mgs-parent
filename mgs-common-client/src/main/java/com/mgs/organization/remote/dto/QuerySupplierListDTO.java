package com.mgs.organization.remote.dto;

import lombok.Data;

/**
 * @author py
 * @date 2019/6/29 15:11
 **/
@Data
public class QuerySupplierListDTO {
    /**
     * 供应商Id
     */
    private  Integer supplierId;
    /**
     * 供应商类型
     */
    private  Integer supplierType;
    /**
     * 供应商名称
     */
    private  String supplierName;
    /**
     * 供应商编码
     */
   private  String supplierCode;
    /**
     * 管理员手机号
     */
   private  String  adminTel;
    /**
     * 结算币种
     */
   private  Integer  settlementCurrency;
    /**
     * 我司采购经理Id
     */
   private  Integer purchaseManagerId;
    /**
     * 我司采购经理
     */
    private String  purchaseManager;
    /**
     *启用状态
     */
    private  Integer availableStatus;
}
