package com.mgs.user.dto;

import lombok.Data;

/**
 * 菜单(对应数据库的菜单表)
 */
@Data
public class MenuDTO {

    /**
     * 菜单id
     */
     private Integer menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
     private String menuCode;

    /**
     * 菜单级别
     */
    private Integer menuLevel;

    /**
     * 菜单排序
     */
     private Integer menuRank;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 后台url
     */
     private String backEndUrl;

    /**
     * 前端url
     */
    private String frontEndUrl;

    /**
     * 打开方式： 1当前页签 2新页签
     */
    private Integer openType;

}
