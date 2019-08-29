package com.mgs.hotel.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 记录该酒店的操作日志
 */
@Data
@Table(name = "t_baseinfo_hotel_log")
public class BasicHotelLogPO {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 操作人
     */
    private String createdBy;

    /**
     * 操作时间
     */
    private String createdDt;

    /**
     * 操作对象
     */
    private String target;

    /**
     * 操作内容
     */
    private String content;
}
