package com.mgs.order.domain;


import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "t_baseinfo_hotel")
public class BaseinfoHotelPO  extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotel_id;

    /**
     * 酒店名称
     */
    @Column(name = "hotel_name")
    private String hotelName;

    /**
     * 城市编码
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 城市
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 有效性
     */
    @Column(name = "active")
    private Integer active;

    /**
     * 酒店电话
     */
    @Column(name = "hotel_tel")
    private String hotelTel;

    /**
     * 酒店地址
     */
    @Column(name = "hotel_address")
    private String hotelAddress;





}
