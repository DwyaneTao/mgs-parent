package com.mgs.dis.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:18
 * @Description: 映射查询DTO
 */
@Data
public class DisMappingQueryDTO extends DisBaseQueryDTO {

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 分销端酒店Id
     */
    private String disHotelId;

    /**
     * 分销端房型Id
     */
    private String disRoomId;

    /**
     * 分销端产品Id
     */
    private String disProductId;

}
