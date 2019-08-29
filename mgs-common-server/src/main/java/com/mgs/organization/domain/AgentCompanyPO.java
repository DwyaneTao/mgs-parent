package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author py
 * @date 2019/6/26 21:02
 **/
@Data
@Table(name = "t_org_company_agent")
public class AgentCompanyPO extends BasePO {
    /**
     * 客户-运营商Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aCompanyId;
    /**
     * 机构Id
     */
    private  Integer orgId;
    /**
     * 总管理员名称
     */
    private  String userName;
    /**
     * 总管理员账号
     */
    private  String userNumber;
    /**
     * 总管理员手机号
     */
    private  String userTel;
    /**
     * 结算方式
     */
    private  Integer settlementType;
    /**
     * 信用额度
     */
    private  String creditLine;
    /**
     * 剩余额度
     */
    private BigDecimal balance;
    /**
     * 销售经理Id
     */
    private  Integer saleManagerId;

    /**
     * 运营商编码
     */
    private String companyCode;
    /**
     * 启用状态
     */
    private  Integer  availableStatus;
}
