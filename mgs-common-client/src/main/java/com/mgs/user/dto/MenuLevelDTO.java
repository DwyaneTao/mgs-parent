package com.mgs.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 用于封装到前端请求的目录dto
 */
@Data
public class MenuLevelDTO {


    /**
     * 菜单名称
     */
    private String secondMenu;

    /**
     * 后台url
     */
    private String backEndUrl;

    /**
     * 前台url
     */
    private String path;

    /**
     * 打开方式： 1当前页签 2新页签
     */
    private Integer openType;

    /**
     * 目录排序
     */
    private Integer menuRank;

    /**
     * 菜单code
     */
    private String menuCode;





}
