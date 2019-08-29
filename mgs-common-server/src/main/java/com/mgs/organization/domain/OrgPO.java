package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @author py
 * @date 2019/6/18 21:02
 **/
@Data
@Table(name = "t_org_organization")
public class OrgPO  extends BasePO {
    /**
     * 机构Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orgId;
    /**
     * 机构名称
     */
    @Column(name = "org_name")
    private String orgName;
    /**
     * 机构编码
     */
    @Column(name = "org_code")
    private String orgCode;
    /**
     * 机构类型
     * 1-包房商，2-个人
     */
    @Column(name = "org_type")
    private Integer orgType;
    /**
     * 企业域名
     */
    @Column(name="org_domian")
    private String orgDomian;
    /**
     * 企业电话
     */
    @Column(name="org_tel")
    private  String orgTel;
    /**
     * 企业地址
     */
    @Column(name="org_address")
    private  String orgAddress;
    /**
     * 企业简介
     */
    @Column(name="org_summary")
    private  String orgSummary;
    /**
    /**
     * 数据类型
     * 1-供应商，2-客户，3-运营商
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 启用状态
     */
    @Column(name = "available_status")
    private Integer availableStatus;
    /**
     * 酒店信息权限
     */
    @Column(name = "hotel_info_permissions")
    private  Integer hotelInfoPermissions;
}
