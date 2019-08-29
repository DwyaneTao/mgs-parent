package com.mgs.meituan.dto.product.response;

import lombok.Data;

@Data
public class ProductDetailDTO {

    /**
     * 是否允许取消
     */
    private Integer allowCancel;

    /**
     * 底价
     */
    private int basePrice;

    /**
     * 早餐数
     */
    private int breakfastNum;

    /**
     * 最早提前预定天数
     */
    private int earliestBookingDays;

    /**
     * 最早提前预定时间
     */
    private String earliestBookingHours;

    /**
     * 最迟提前预定天数
     */
    private Integer latestBookingDays;

    /**
     * 最迟提前预定时间
     */
    private String latestBookingHours;

    /**
     * 市场价
     */
    private Integer marketPrice;

    /**
     * 提前取消的天数
     */
    private Integer moveupCancelDays;

    /**
     * 提前取消的时间。
     */
    private String moveupCancelHour;

    /**
     * 最小预定间数
     */
    private Integer roomCountMax;

    /**
     * 最大预定间数
     */
    private Integer roomCountMin;

    /**
     * 营业日期
     */
    private String roomDate;

    /**
     * 房间剩余数量
     */
    private Integer roomNum;

    /**
     * 美团售卖价格
     */
    private Integer roomPrice;

    /**
     * 房型编号
     */
    private String roomType;

    /**
     * 最大连住天数
     */
    private Integer serialCheckinMax;

    /**
     * 最小连住天数
     */
    private Integer serialCheckinMin;

    /**
     * 房态
     */
    private Integer status;
}
