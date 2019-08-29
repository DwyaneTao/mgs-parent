package com.mgs.hotel.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店基本信息
 */
@Data
@Table(name = "t_baseinfo_hotel")
public class BasicHotelInfoPO extends BasePO {

    /**
     * 酒店id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 国家编码
     */
    private String countryCode;

    /**
     * 国家名称
     */
    private String countryName;

    /**
     * 酒店电话
     */
    private String hotelTel;

    /**
     * 酒店地址
     */
    private String hotelAddress;

    /**
     * 酒店传真
     */
    private String hotelFax;

    /**
     * 酒店英文名字
     */
    private String hotelEnglishName;

    /**
     * 主图url
     */
    private String mainPhotoUrl;

    /**
     * 酒店星级
     */
    private Integer hotelRank;

    /**
     * 开业时间
     */
    private String openingYear;

    /**
     * 装修时间
     */
    private String redecoratedYear;

    /**
     * 房间数
     */
    private Integer roomQty;

    /**
     * 酒店主题
     */
    private String hotelTypes;

    /**
     * 酒店介绍
     */
    private String hotelSummary;

    /**
     * 百度经度
     */
    private String longitude;

    /**
     * 百度纬度
     */
    private String latitude;

    /**
     * 入住最早时间
     */
    private String checkInTime;

    /**
     * 离店最晚时间
     */
    private String checkOutTime;

    /**
     * 宠物政策
     */
    private Integer petPolicy;

    /**
     * 可支付方式
     */
    private String hotelPaymentTypes;

    /**
     * 早餐政策
     */
    private String breakfastPolicy;

    /**
     * 其它政策
     */
    private String otherPolicy;

    /**
     * 网络政策
     */
    private String networks;

    /**
     * 停车场
     */
    private String parkingLots;

    /***
     * 餐饮服务
     */
    private String diningRooms;

    /**
     * 休息娱乐
     */
    private String entertainments;

    /**
     * 商务服务
     */
    private String businessServices;

    /**
     * 儿童服务
     */
    private String childrenServices;

    /**
     * 酒店服务
     */
    private String hotelServices;

    /**
     * 有效性
     */
    private Integer active;
}
