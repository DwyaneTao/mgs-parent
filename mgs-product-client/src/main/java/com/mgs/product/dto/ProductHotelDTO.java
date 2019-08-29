package com.mgs.product.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 00:00
 * @Description: 酒店列表
 */
@Data
public class ProductHotelDTO implements Serializable {

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;
}
