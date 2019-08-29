package com.mgs.dlt.request.dto;

import lombok.Data;

/**
 *  m_ 2018/6/18.
 */
@Data
public class IncrementPushDataRequest {

    /**
     * 商家编码(必传)
     */
    private String merchantCode;

    /**
     * 商家酒店Id(必传)
     */
    private Long mHotelId;

    /**
     * 商家房型Id
     */
    private Long mRoomTypeId;

    /**
     * 商家产品Id
     */
    private Long mRatePlanId;

    /**
     * 开始日期，为空则默认推送180天
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
