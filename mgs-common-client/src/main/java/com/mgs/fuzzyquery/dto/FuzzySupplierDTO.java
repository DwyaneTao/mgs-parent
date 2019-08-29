package com.mgs.fuzzyquery.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:43
 * @Description:
 */
@Data
public class FuzzySupplierDTO implements Serializable {

    private String supplierCode;

    private String supplierName;

    private Integer settlementCurrency;
}
