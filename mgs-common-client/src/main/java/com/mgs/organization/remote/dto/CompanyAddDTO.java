package com.mgs.organization.remote.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * @author py
 * @date 2019/6/27 21:40
 **/
@Data
public class CompanyAddDTO extends BaseDTO {
    /**
     * 企业Id
     */
    private  Integer companyId;
    /**
     * 企业名称
     */
    private  String companyName;
    /**
     * 启用编码
     */
    private  String companyCode;
    /**
     * 企业域名
     */
    private String companyDomain;
    /**
     * 总管理员名称
     */
    private  String adminName;
    /**
     * 总管理员账号
     */
    private  String adminAccount;
    /**
     * 总管理员手机号
     */
    private  String adminTel;
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
     * 企业启用状态
     */
    private Integer availableStatus;
    /**
     * 图片List
     */
    private List<PictureAddDTO> pictureList;
    /**
     * 酒店信息权限
     */
    private  Integer hotelInfoPermissions;
}
