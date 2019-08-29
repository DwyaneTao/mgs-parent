package com.mgs.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户登录dto
 */
@Data
public class UserDTO {

    /**
     * 登录id
     */
    private Integer loginId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 所在公司编码
     */
    private String companyCode;

    /**
     * 所在公司名称
     */
    private String companyName;

    /**
     * 所在公司域名
     */
    private String companyDomain;

    /**
     * 酒店权限
     */
    private Integer hotelPermissions;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 第一层目录
     */
    private List<FirstMenuDTO> menus;

    /**
     * token
     */
    private String token;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 角色列表
     */
    private List<RoleDTO> roleList;
    /**
     * 账号有效性
     */
    private  Integer availableStatus;

    /**
     * 结算币种
     */
    private Integer settlementCurrency;

}
