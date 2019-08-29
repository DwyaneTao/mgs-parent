package com.mgs.dlt.response.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *   2018/4/8.
 */
@Data
public class PurchaseOrder {

    private String issueOrderId;
    private Integer issueStatus;
    private String hotelConfirmno;
    private String issueType;
    private String purchaseCode;
    private String purchaseName;
    private String purchaseTel;
    private String purchaseCurrency;
    private BigDecimal purchasePrice;
    private List<DltOrderRoomPrice> roomPurchasePriceList;

}
