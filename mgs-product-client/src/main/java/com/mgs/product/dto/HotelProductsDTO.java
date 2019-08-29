package com.mgs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.InsufficientResourcesException;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 00:02
 * @Description: 酒店产品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelProductsDTO implements Serializable {

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 是否选中
     */
    private Integer selected;
    /**
     * 日期合计总配额数
     */
    private Integer quota;
    /**
     * 日期合计剩余配额数
     */
    private  Integer remainingQuota;
    /**
     * 日期合计已售配额数
     */
    private  Integer soldQuota;
    /**
     * 房型列表
     */
    private List<ProductRoomDTO> roomList;

}
