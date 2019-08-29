package com.mgs.finance.remote.statement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UncheckOutSupplierDTO implements Serializable{

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 订单数
     */
    private Integer orderQty;

    /**
     * 应付
     */
    private BigDecimal payableAmt;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 供应商编码
     */
    private String supplierCode;
}
