package com.mgs.dlt.response.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *   2018/4/8.
 */
@Data
public class CancelRule {

    private Integer deductType;
    private Date lastCancelTime;
    private BigDecimal value;

}
