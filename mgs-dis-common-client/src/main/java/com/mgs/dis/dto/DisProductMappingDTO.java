package com.mgs.dis.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:06
 * @Description: 产品映射
 */
@Data
public class DisProductMappingDTO extends BaseDTO {

    private Integer id;

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

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

    /**
     * 分销商（OTA，分销端）
     */
    private String distributor;

    /**
     * 运营商编码
     */
    private String companyCode;
}
