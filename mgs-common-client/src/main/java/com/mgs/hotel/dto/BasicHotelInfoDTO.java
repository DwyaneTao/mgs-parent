package com.mgs.hotel.dto;

import com.mgs.annotations.Compared;
import lombok.Data;

/**
 * 酒店基本信息
 */
@Data
public class BasicHotelInfoDTO{

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    @Compared(variableName = "酒店中文名")
    private String hotelName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    @Compared(variableName = "城市")
    private String cityName;

    /**
     * 国家编码
     */
    private String countryCode;

    /**
     * 国家名称
     */
    @Compared(variableName = "国家")
    private String countryName;

    /**
     * 酒店电话
     */
    @Compared(variableName = "酒店电话")
    private String hotelTel;

    /**
     * 酒店地址
     */
    @Compared(variableName = "酒店地址")
    private String hotelAddress;

    /**
     * 酒店英文名字
     */
    @Compared(variableName = "酒店英文名")
    private String hotelEnglishName;

    /**
     * 主图url
     */
    private String mainPhotoUrl;

    /**
     * 酒店星级
     */
    @Compared(variableName = "酒店星级", enumsName = "com.mgs.enums.HotelRankEnum")
    private Integer hotelRank;

    /**
     * 开业时间
     */
    @Compared(variableName = "开业时间")
    private String openingYear;

    /**
     * 装修时间
     */
    @Compared(variableName = "装修时间")
    private String redecoratedYear;

    /**
     * 房间数
     */
    @Compared(variableName = "酒店房间数")
    private Integer roomQty;

    /**
     * 酒店主题
     */
    @Compared(variableName = "酒店主题",enumsName = "com.mgs.enums.HotelTypeEnum")
    private String hotelTypes;

    /**
     * 酒店介绍
     */
    @Compared(variableName = "酒店介绍")
    private String hotelSummary;

    /**
     * 酒店传真
     */
    @Compared(variableName = "酒店传真")
    private String hotelFax;

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
    @Compared(variableName = "入住最早时间")
    private String checkInTime;

    /**
     * 离店最晚时间
     */
    @Compared(variableName = "离店最晚时间")
    private String checkOutTime;

    /**
     * 宠物政策
     */
    @Compared(variableName = "宠物", enumsName = "com.mgs.enums.PetPolicyEnum")
    private Integer petPolicy;

    /**
     * 可支付方式
     */
    @Compared(variableName = "可支付方式", enumsName = "com.mgs.enums.HotelPaymentsTypeEnum")
    private String hotelPaymentTypes;

    /**
     * 早餐政策
     */
    @Compared(variableName = "早餐", split = "\\|", enumsName = "com.mgs.enums.BreakFastEnum")
    private String breakfastPolicy;

    /**
     * 其它政策
     */
    @Compared(variableName = "其它政策")
    private String otherPolicy;

    /**
     * 网络政策
     */
    @Compared(variableName = "网络", split = ",", enumsName = "com.mgs.enums.NetWorkEnum")
    private String networks;

    /**
     * 停车场
     */
    @Compared(variableName = "停车场", split = ",", enumsName = "com.mgs.enums.ParkingLotEnum")
    private String parkingLots;

    /***
     * 餐饮设施
     */
    @Compared(variableName = "餐饮设施", split = ",", enumsName = "com.mgs.enums.DietRoomEnum")
    private String diningRooms;

    /**
     * 休息娱乐
     */
    @Compared(variableName = "休息娱乐", split = ",", enumsName = "com.mgs.enums.EntertainmentEnum")
    private String entertainments;

    /**
     * 商务服务
     */
    @Compared(variableName = "商务服务", split = ",", enumsName = "com.mgs.enums.BusinessServiceEnum")
    private String businessServices;

    /**
     * 儿童服务
     */
    @Compared(variableName = "儿童服务", split = ",", enumsName = "com.mgs.enums.ChildrenServiceEnum")
    private String childrenServices;

    /**
     * 酒店设施
     */
    @Compared(variableName = "酒店设施", split = ",", enumsName = "com.mgs.enums.HotelServiceEnum")
    private String hotelServices;

    /**
     * 酒店排序
     */
    private Integer sortRank;

    private String createdBy;

    private String createdDt;

    private String modifiedBy;

    private String modifiedDt;

    /**
     * 操作人的账号
     */
    private String operatorAccount;

}
