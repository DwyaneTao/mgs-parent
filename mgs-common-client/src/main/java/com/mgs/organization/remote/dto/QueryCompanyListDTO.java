package com.mgs.organization.remote.dto;

import lombok.Data;

/**
 * @author py
 * @date 2019/6/29 19:07
 **/
@Data
public class QueryCompanyListDTO {
    /**
     * 企业Id
     */
    private Integer companyId;
    /**
     * 企业名称
     */
    private  String companyName;
    /**
     * 企业编码
     */
    private  String companyCode;
    /**
     * 总管理员姓名
     */
    private  String adminName;
    /**
     * 总管理员手机号
     */
    private  String adminTel;
    /**
     * 总管理员启用状态
     */
    private  Integer availableStatus;
}
