package com.mgs.organization.remote.dto;

import lombok.Data;

import java.util.List;

/**
 * @author py
 * @date 2019/6/27 15:54
 **/
@Data
public class SupplierSelectDTO {
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
     *
     * 供应商编码
     */
    private  String supplierCode;
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
     * 我司采购经理Id
     */
    private  Integer  purchaseManagerId;
    /**
     *我司采购经理姓名
     */
    private String purchaseManager;
    /**
     * 联系人信息
     */
    private List<ContactSupplierDTO> contactList;
    /**
     * 银行卡信息
     */
    private List<BankSupplierDTO> bankCardList;
}
