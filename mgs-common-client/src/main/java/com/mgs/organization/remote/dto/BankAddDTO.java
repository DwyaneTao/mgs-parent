package com.mgs.organization.remote.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author py
 * @date 2019/6/21 21:27
 **/
@Data
public class BankAddDTO implements Serializable {
    /**
     * 银行卡Id
     */
    private Integer bankId;
    /**
     * 银行卡名称
     */
    private String bankName;
    /**
     * 开户名
     */
    private String accountName;
    /**
     * 账号
     */
    private String accountNumber;
    /**
     * 行号
     */
    private String bankCode;

    /**
     * 银行卡状态
     */
    private Integer bankStatus;

    /**
     * 机构编码
     */
    private String orgCode;
    /**
     * 机构类型
     */
    private Integer orgType;

    /**
     * 数据创建时间
     */
    private Date createdDt;

    /**
     * 数据创建人
     */
    private String createdBy;
    /**
     * 数据修改人
     */
    private String modifiedBy;
    /**
     * 数据修改时间
     */
    private Date modifiedDt;
}
