package com.mgs.dlt.response.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *   2018/4/8.
 */
@Data
public class DltOrderRoomPrice {

    private Date effectDate;
    private Integer mealType;
    private String breakfast;
    private Integer breakfastNum;
    private String currency;
    private BigDecimal price;
}
