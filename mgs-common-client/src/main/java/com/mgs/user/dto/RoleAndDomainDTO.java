package com.mgs.user.dto;


import lombok.Data;

import java.util.List;

/**
 * 角色域名
 */
@Data
public class RoleAndDomainDTO {

    /**
     * 角色列表
     */
    private List<RoleDTO> employeeRoleList;

    /**
     * 域名
     */
    private String domainName;
}
