package com.mgs.hotel.dto;

import lombok.Data;

/**
 * 酒店操作日志封装类
 */
@Data
public class BasicHotelLogDTO {

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 操作对象
     */
    private String target;

    /**
     * 操作人
     */
    private String createdBy;

    /**
     * 操作时间
     */
    private String createdDt;

}
