package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "t_org_company_agent")
public class AgentCompanyPO extends BasePO {
    /**
     * 客户-运营商Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_company_id")
    private Integer aCompanyId;
    /**
     * 机构Id
     */
    @Column(name = "org_id")
    private  Integer orgId;
    /**
     * 总管理员名称
     */
    @Column(name = "user_name")
    private  String userName;
    /**
     * 总管理员账号
     */
    @Column(name = "user_number")
    private  String userNumber;
    /**
     * 总管理员手机号
     */
    @Column(name = "user_tel")
    private  String userPhone;
    /**
     * 结算方式
     */
    @Column(name = "settlement_type")
    private  Integer settlementType;
    /**
     * 信用额度
     */
    @Column(name = "credit_line")
    private BigDecimal creditLine;
    /**
     * 销售经理Id
     */
    @Column(name = "sale_manager_id")
    private  Integer saleManagerId;
    /**
     * 销售经理名称
     */
    @Column(name = "sale_manager_name")
    private  String saleManagerName;
    /**
     * 运营商编码
     */
    @Column(name = "company_code")
    private String companyCode;
    /**
     * 启用状态
     */
    @Column(name = "available_status")
    private  Integer  availableStatus;
}
