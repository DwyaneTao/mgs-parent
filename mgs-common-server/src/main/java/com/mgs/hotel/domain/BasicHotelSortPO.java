package com.mgs.hotel.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店排序
 */
@Data
@Table(name = "t_baseinfo_hotel_sort")
public class BasicHotelSortPO extends BasePO {

    /**
     *  id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 酒店排序
     */
    private Integer sortRank;

    /**
     * 有效性
     */
    private Integer active;

    /**
     * 企业编码
     */
    private String orgCode;

}
