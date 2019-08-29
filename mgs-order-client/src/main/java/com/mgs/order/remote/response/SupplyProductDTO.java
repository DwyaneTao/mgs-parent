package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyProductDTO implements Serializable{

    /**
     * 供货单产品id
     */
    private Integer supplyProductId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 房型名称
     */
    private Integer roomId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 入住日期
     */
    private String startDate;

    /**
     * 离店日期
     */
    private String endDate;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 底价
     */
    private BigDecimal basePrice;

    /**
     * 早餐数
     */
    private Integer breakfastQty;

    /**
     * 晚数
     */
    private Integer nightQty;

}
