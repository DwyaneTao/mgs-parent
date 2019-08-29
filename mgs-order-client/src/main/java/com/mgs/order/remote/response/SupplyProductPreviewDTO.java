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
public class SupplyProductPreviewDTO implements Serializable{

    /**
     * 房型名称
     */
    private String roomName;

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
     * 晚数
     */
    private Integer nightQty;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 底价
     */
    private BigDecimal basePrice;

    /**
     * 总价
     */
    private BigDecimal totalAmt;
}
