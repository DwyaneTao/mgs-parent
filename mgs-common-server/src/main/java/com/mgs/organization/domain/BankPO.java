package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @author py
 * @date 2019/6/19 11:05
 **/
@Data
@Table(name = "t_org_bank")
public class BankPO  extends BasePO {
    /**
     * 银行信息Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;
    /**
     * 银行卡名称
     */
    @Column(name = "bank_name")
    private String bankName;
    /**
     * 开户名
     */
    @Column(name = "account_name")
    private String accountName;
    /**
     * 账号
     */
    @Column(name = "account_number")
    private String accountNumber;
    /**
     * 行号
     */
    @Column(name = "bank_code")
    private String bankCode;

    /**
     * 银行卡状态
     */
    @Column(name = "active")
    private Integer active;


    /**
     * 机构Id
     */
    @Column(name = "org_code")
    private String orgCode;
    /**
     * 机构类型
     */
    @Column(name = "org_type")
    private Integer orgType;



}
