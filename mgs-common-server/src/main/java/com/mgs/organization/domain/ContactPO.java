package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @author py
 * @date 2019/6/18 22:25
 * 联系人
 **/
@Data
@Table(name = "t_org_contact")
public class ContactPO  extends BasePO {
    /**
     * 联系人Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactId;

    /**
     * 联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系人手机号
     */
    @Column(name = "contact_tel")
    private String contactTel;
    /**
     * 联系人角色
     */
    @Column(name = "contact_role")
    private String contactRole;

    /**
     * 联系人Email
     */
    @Column(name = "contact_email")
    private String contactEmail;

    /**
     * 联系人备注
     */
    @Column(name = "contact_remark")
    private String contactRemark;

    /**
     * 机构Id
     */
    @Column(name = "org_code")
    private String orgCode;
    /**
     * 联系人存在状态
     */
    @Column(name = "active")
    private Integer active;
    /**
     * 机构类型
     */
    @Column(name = "org_type")
    private Integer orgType;
}
