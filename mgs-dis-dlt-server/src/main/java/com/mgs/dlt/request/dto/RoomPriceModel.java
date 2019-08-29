package com.mgs.dlt.request.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 *   2018/4/8.
 */
@Data
public class RoomPriceModel {

    private BigDecimal roomPrice;
    private BigDecimal tax;
    private String currency;
    private Integer breakfast;
    private String channel;

}
