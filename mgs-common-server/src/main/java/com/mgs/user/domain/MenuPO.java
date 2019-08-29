package com.mgs.user.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_auth_menu")
public class MenuPO extends BasePO {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 目录等级
     */
    private Integer menuLevel;

    /**
     * 菜单在当前层级排序
     */
    private Integer menuSort;

    /**
     * 父节点code
     */
    private String parentCode;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 哪个系统的角色(0.运营端，1.供应端， 2.平台端，3.营销端)
     */
    private Integer type;

    /**
     * 有效性
     */
    private Integer active;



}
