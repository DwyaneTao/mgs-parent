package com.mgs.dlt.request.dto;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class CtripSellRule {

    private Short latestconfirmTimeOfDays;
    private Short latestconfirmTimeOfHours;
    private Short latestBookingTimeOfDays;
    private Short latestBookingTimeOfHours;
    private Short cancelType;
    private Short latestCancelTimeOfDays;
    private Short latestCancelTimeOfHours;
    private String overduePaymentType;

}
