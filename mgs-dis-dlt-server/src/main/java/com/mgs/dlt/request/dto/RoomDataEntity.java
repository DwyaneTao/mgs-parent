package com.mgs.dlt.request.dto;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class RoomDataEntity {

    private Long roomId;
    private String startDate;
    private String endDate;
    private String weekDayIndex;
    private RoomPriceModel roomPriceModel;
    private PurchasePriceModel purchasePriceModel;
    private RoomStatusModel roomStatusModel;
    private RoomInventoryModel roomInventoryModel;
    private SaleRuleModel saleRuleModel;

}
