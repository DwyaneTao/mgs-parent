package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @author py
 * @date 2019/6/19 16:32
 **/
@Data
@Table(name = "t_auth_user")
public class UserPO extends BasePO {
    /**
     * 账号信息Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 登录账号
     */
    @Column(name = "user_account")
    private String userAccount;

    /**
     * 手机号
     */
    @Column(name = "user_tel")
    private String userTel;
    /**
     * 有效性
     */
    @Column(name = "active")
    private Integer active;


    /**
     *是否是超级管理员
     */
    @Column(name = "is_super_admin")
    private Integer isSuperAdmin;
    /**
     *邮箱
     */
    @Column(name = "email")
    private Integer email;

    /**
     *是否启用1是，0否
     */
    @Column(name = "available_status")
    private Integer availableStatus;

    /**
     *登录密码
     */
    @Column(name = "user_pwd")
    private String userPwd;

    /**
     *盐值
     */
    @Column(name = "salt")
    private String salt;
    /**
     *微信openId,公众号内唯一
     */
    @Column(name = "wexin_openid")
    private String wexinOpenid;
    /**
     *微信unionid,企业内唯一
     */
    @Column(name = "wexin_unionid")
    private String wexinUnionid;

    /**
     *合作商类型
     */
    @Column(name = "partner")
    private String partner;

    /**
     *企业编码
     */
    @Column(name = "org_code")
    private String orgCode;

    public UserPO(){}

    public UserPO(Integer userId) {
        this.userId = userId;
    }
}
