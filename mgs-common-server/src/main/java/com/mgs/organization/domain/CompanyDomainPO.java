package com.mgs.organization.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author py
 * @date 2019/7/3 21:44
 **/
@Data
@Table(name = "t_org_organization")
public class CompanyDomainPO {
    /**
     * 企业域名
     */
    @Column(name="org_domian")
    private String orgDomian;
}
