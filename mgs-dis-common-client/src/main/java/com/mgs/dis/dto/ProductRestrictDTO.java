package com.mgs.dis.dto;

import lombok.Data;

/**
 * 价格计划条款
 */
@Data
public class ProductRestrictDTO {


    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 取消条款类型（0一经预订不能取消 1可以取消）
     */
    private Integer cancellationType;

    /**
     * 取消条款提前天数
     */
    private Integer cancellationAdvanceDays;

    /**
     * 取消条款提前时间点
     */
    private String cancellationDueTime;

    /**
     * 取消条款扣费说明
     */
    private String cancellationDeductionTerm;

    /**
     * 预订天数条款类型（0大于等于 1等于 2小于等于）
     */
    private Integer comparisonType;

    /**
     * 预订天数条款天数
     */
    private Integer reservationLimitNights;

    /**
     * 预订提前天数
     */
    private Integer reservationAdvanceDays;

    /**
     * 预订提前时间点
     */
    private String reservationDueTime;

    /**
     * 预订间数条款间数
     */
    private Integer reservationLimitRooms;

    /**
     * 早餐数
     */
    private Integer breakfastQty;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 币种
     */
    private Integer currency;
}
