package com.mgs.organization.remote.dto;

import lombok.Data;

import java.util.List;

/**
 * @author py
 * @date 2019/6/29 9:46
 **/
@Data
public class CompanySelectDTO {
    /**
     * 企业Id
     */
    private  Integer companyId;
    /**
     * 企业名称
     */
    private  String companyName;
    /**
     * 企业编码
     */
    private  String companyCode;
    /**
     * 企业域名
     */
    private  String companyDomain;
    /**
     * 总管理员姓名
     */
    private String adminName;
    /**
     * 总管理员账号
     */
    private  String adminAccount;
    /**
     * 总管理员手机号
     */
    private  String adminTel;
    /**
     * 企业营业执照List
     */
    private List<CompanyBusinessLicenseUrlDTO> PictureList;
    /**
     * 企业LOGO
     */
    private  String   CompanyLogoUrl;
    /**
     * 企业电话
     */
    private  String companyTel;
    /**
     * 企业地址
     */
    private  String companyAddress;
    /**
     * 企业简介
     */
    private  String companySummary;
    /**
     * 企业创建时间
     */
    private  String createdDt;
}
