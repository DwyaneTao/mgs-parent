package com.mgs.meituan.dto.product.request;

import com.mgs.meituan.dto.hotel.BaseRequestDTO;
import lombok.Data;

/**
 * 销售规则
 */
@Data
public class RuleSyncDTO extends BaseRequestDTO {

    /**
     * 规则有效起始日期。
     */
    private String ruleStartDate;

    /**
     * 规则有效截止日期。
     */
    private String ruleEndDate;

    /**
     * 最早提前预定天数。
     */
    private Integer earliestBookingDays;

    /**
     * 最早提前预定小时
     */
    private Integer earliestBookingHours;

    /**
     * 最晚提前预定天数
     */
    private Integer latestBookingDays;

    /**
     * 最晚提前预定小时。
     */
    private Integer latestBookingHours;

    /**
     * 最小连住天数
     */
    private Integer serialCheckinMin;

    /**
     * 最大连住天数。
     */
    private Integer serialCheckinMax;

    /**
     * 最小预定间数。
     */
    private Integer roomCountMin;

    /**
     * 最大预定间数。
     */
    private Integer roomCountMax;

    /**
     * 是否允许取消：1：允许，0：不允许
     */
    private Integer allowCancel;

    /**
     * 提前取消的天数。
     */
    private Integer moveupCancelDays;

    /**
     * 提前取消的时间。
     */
    private Integer moveupCancelHour;
}
