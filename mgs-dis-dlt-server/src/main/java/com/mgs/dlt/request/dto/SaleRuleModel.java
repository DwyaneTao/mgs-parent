package com.mgs.dlt.request.dto;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class SaleRuleModel {

    private String channel;
    private RoomGiftRule roomGiftRule;
    private SellingRule sellingRule;
    private CtripSellRule ctripSellRule;
}
