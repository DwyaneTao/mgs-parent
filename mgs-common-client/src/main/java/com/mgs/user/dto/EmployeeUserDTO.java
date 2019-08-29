package com.mgs.user.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 企业员工
 */
@Data
public class EmployeeUserDTO extends BaseDTO {

    /**
     *  企业成员id
     */

    private Integer employeeId;

    /**
     * 企业成员姓名
     */

    private String employeeName;

    /**
     * 企业成员手机号
     */
    private String employeeTel;

    /**
     * 企业成员账号
     */
    private String employeeAccount;

    /**
     * 企业成员角色
     */
    private List<RoleDTO> employeeRoleList;

    /**
     * 是否启用
     */
    private Integer availableStatus;

    /**
     * 是否能删除
     */
    private Integer deletableStatus;

    /**
     * 企业编码
     */
    private String orgCode;

    /**
     * 能否重置密码
     */
    private Integer resetable;

    /**
     * 能否修改
     */
    private Integer modifiable;

    /**
     * 用于判断是否是本人账号
     */
    private String modifiedAccount;

}
