package com.mgs.organization.remote.dto;

import lombok.Data;

/**
 * @author py
 * @date 2019/6/27 19:11
 **/
@Data
public class ContactSupplierDTO {
    /**
     * 联系人Id
     */
    private Integer contactId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactTel;
    /**
     * 联系人角色
     */
    private String contactRole;

    /**
     * 联系人Email
     */
    private String contactEmail;

    /**
     * 联系人备注
     */
    private String contactRemark;


}
