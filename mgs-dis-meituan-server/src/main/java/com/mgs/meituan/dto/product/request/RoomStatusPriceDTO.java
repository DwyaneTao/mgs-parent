package com.mgs.meituan.dto.product.request;

import lombok.Data;

@Data
public class RoomStatusPriceDTO {

    /**
     * 有效开始日期
     */
    private String startDate;

    /**
     * 价格的有效截止日期
     */
    private String endDate;

    /**
     * 美团售卖价
     */
    private Integer roomPrice;

    /**
     * 底价
     */
    private Integer basePrice;

    /**
     * 市场价
     */
    private Integer marketPrice;
}
