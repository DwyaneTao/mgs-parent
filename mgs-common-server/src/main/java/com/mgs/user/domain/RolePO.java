package com.mgs.user.domain;


import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 角色PO
 */
@Data
@Table(name = "t_auth_role")
public class RolePO extends BasePO {

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescription;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     *  哪个系统的角色(0.运营端，1.供应端， 2.平台端，3.营销端)
     */
    private Integer type;

    /**
     * 有效性
     */
    private Integer active;


}
