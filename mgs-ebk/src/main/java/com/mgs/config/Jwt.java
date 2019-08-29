package com.mgs.config;

import lombok.Data;

@Data
public class Jwt {

    private static final long serialVersionUID = 1;

    /**
     * token过期毫秒数
     * 2*60*60*1000; 2h
     */
    private long expiredMillisecond;


    /**
     * 登陆名
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 企业编码
     */
    private String orgCode;

    /**
     * 企业域名
     */
    private String domainName;

    /**
     * 酒店信息权限
     */
    private Integer hotelInfoPermission;

    /**
     * token解析异常，
     * 返回编码
     */
    private String parseResCode;

    /**
     * 1成功0失败
     */
    private int result;

}
