package com.mgs.organization.remote.dto;

import lombok.Data;

/**
 * @author py
 * @date 2019/6/27 19:12
 **/
@Data
public class BankSupplierDTO {
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

}
