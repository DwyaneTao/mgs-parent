package com.mgs.dlt.response.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *   2018/2/28.
 */
@Data
public class QueryHotelProductDetailResponse implements Serializable {

    private static final long serialVersionUID = -3868591846501084306L;
    private Date saleDate;

    private String baseCurrency;

    private Integer isActive;

    private Integer payMethod;

    private Long pricePlanId;

    private String pricePlanName;

    private String saleCCurrency;

    private String supplyCode;

    private Integer saleState;

    private String quotaState;

    private Integer quotaNum;

    private Integer overdraft;

    private BigDecimal basePrice;

    /**
     * 加幅调整方式
     */
    private Integer adjustmentType;

    /**
     * 调整金额
     */
    private BigDecimal modifiedAmt;

    /**
     * 渠道币种（B2B,OTA渠道都是这个币种）
     */
    private String saleChannelCurrency;

    /**
     * 渠道售价
     */
    private BigDecimal ctripPrice;
    private BigDecimal tmPrice;
    private BigDecimal jdPrice;
    private BigDecimal xmdPrice;
    private BigDecimal tuNiuPrice;
    private BigDecimal tongChengPrice;

    /**
     * 早餐
     */
    private String breakfastNum;

    private Integer bookDays;

    private String bookTime;

    private Integer occupancyType;

    private Integer occupancyDays;

    private Integer cancelType = 1;

    private Integer cancelDays;

    private String cancelTime;

    private String cancelRemark;

    private Integer bookRooms;

}
