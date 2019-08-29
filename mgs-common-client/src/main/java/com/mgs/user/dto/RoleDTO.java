package com.mgs.user.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

@Data
public class RoleDTO extends BaseDTO {

    /**
     * 企业成员角色id
     */
    private Integer employeeRoleId;

    /**
     * 企业成员角色名称
     */
    private String employeeRoleName;

    /**
     * 企业成员角色描述
     */
    private String employeeRoleDescription;


    private Integer userRoleId;

    private Integer employeeId;

    public RoleDTO(){}

    public RoleDTO(Integer employeeRoleId, String employeeRoleName) {
        this.employeeRoleId = employeeRoleId;
        this.employeeRoleName = employeeRoleName;
    }
}
