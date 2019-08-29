package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "t_org_company_supplier")
public class SupplierCompanyPO extends BasePO {
    /**
     * 供应商-运营商Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sCompanyId;

    /**
     * 机构Id,在这里特指运营商
     */
    @Column(name = "org_id")
    private Integer orgId;

    /**
     * 总管理员姓名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 总管理员账号
     */
    @Column(name = "user_number")
    private String userNumber;
    /**
     * 总管理员手机号
     */
    @Column(name = "user_tel")
    private String userTel;
    /**
     *结算方式
     */
    @Column(name = "settlement_type")
    private Integer settlementType;

    /**
     *结算币种类型
     */
    @Column(name = "settlement_currency")
    private Integer settlementCurrency;

    /**
     *我司采购经理Id
     */
    @Column(name = "purchase_manager_id")
    private Integer purchaseManagerId;
    /**
     *我司采购经理姓名
     */
    @Column(name = "purchase_manager_name")
    private String purchaseManagerName;

    /**
     *启用状态
     */
    @Column(name = "available_status")
    private Integer availableStatus;

    /**
     *运营商Id
     */
    @Column(name = "company_code")
    private String operatorCode;

    /**
     * 运营商编码
     */
    @Column(name = "company_code")
    private String companyCode;
}
