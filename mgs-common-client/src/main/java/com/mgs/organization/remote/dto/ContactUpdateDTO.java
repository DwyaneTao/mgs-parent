package com.mgs.organization.remote.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author py
 * @date 2019/6/22 15:10
 **/
@Data
public class ContactUpdateDTO {
    /**
     * 联系人Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    /**
     * 数据修改人
     */
    private String modifiedBy;
    /**
     * 数据修改时间
     */
    private Date modifiedDt;
}
