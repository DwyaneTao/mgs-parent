package com.mgs.organization.remote.dto;

import lombok.Data;

/**
 * @author py
 * @date 2019/7/26 16:25
 **/
@Data
public class ExamineOrgNameDTO {
    /**
     * 机构名称
     */
    private  String orgName;
    /**
     * 机构类型
     */
    private  Integer type;
    /**
     * 运营商编码
     */
    private  String companyCode;
}
