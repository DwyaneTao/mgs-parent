package com.mgs.dlt.response.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *   2018/4/8.
 */
@Data
public class DltOrderInfo {

    private String dltOrderId;
    private String orderId;
    private RelationOrder releationOrder;
    private String channel;
    private String childChannel;
    private String orderCurrency;
    private BigDecimal orderPrice;
    private String formType;
    private String orderStatus;
    private List<CancelRule> cancelRules;
    private String paymentType;
    private String updateTime;
    private Date orderDate;
    private String confirmno;
    private Date checkinDate;
    private Date checkoutDate;
    private Integer cityId;
    private String cityName;
    private String cityEName;
    private String hotelId;
    private String hotelName;
    private String hotelEName;
    private String roomId;
    private String roomName;
    private String roomEName;
    private Integer roomnum;
    private String bedType;
    private String customerName;
    private String customerDid;
    private List<AdditionalService> additionalList;
    private String specialMemo;
    private String orderMemo;
    private PurchaseOrder dltIssueOrder;
    private List<DltOrderRoomPrice> roomPriceList;
}
