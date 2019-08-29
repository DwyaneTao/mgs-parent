package com.mgs.dlt.response.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 *   2018/4/8.
 */
@Data
public class AdditionalService {

    private String extraFareName;
    private BigDecimal extraFareAmount;
    private Integer extraFareQuantity;
    private String extraFareUnit;
    private String extraFareCurrency;

}
