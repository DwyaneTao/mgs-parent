package com.mgs.meituan.dto.hotel.response;

import lombok.Data;

/**
 * 查询酒店详情返回的数据
 */
@Data
public class ResponseDataDTO {

    /**
     * POI id
     */
    private String poiId;

    /**
     * POI名称
     */
    private String pointName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     *  地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 星级
     */
    private Integer startLevel;

    /**
     * 酒店主题
     */
    private Integer themeTag;

    /**
     * 营业时间，格式：yyyy-MM-dd
     */
    private String openDate;

    /**
     * 装修时间 格式：yyyy-MM-dd
     */
    private String decorationDate;

    /**
     * 官方品牌
     */
    private String officialBrand;

    /**
     * 楼高层数
     */
    private Integer floorNum;

    /**
     * 房间总数
     */
    private Integer roomNum;

    /**
     * 押金类型
     */
    private Integer foregiftType;

    /**
     * 酒店类型
     */
    private String poiType;

    /**
     * wifi
     */
    private Integer wifi;

    /**
     * 中式餐厅
     */
    private Integer chineseRestaurant;

    /**
     * 西式餐厅
     */
    private Integer westernRestaurant;

    /**
     * 前台保险柜
     */
    private Integer deskSafe;

    /**
     * 茶室
     */
    private Integer teaRoom;

    /**
     * 咖啡厅
     */
    private Integer cafe;

    /**
     * 酒吧
     */
    private Integer bar;

    /**
     * 商务中心
     */
    private Integer businessCenter;

    /**
     * 宴会厅
     */
    private Integer banqueHall;

    /**
     * 会议室
     */
    private Integer meetingRoom;

    /**
     * 健身中心
     */
    private Integer fitnessRoom;

    /**
     * 停车场
     */
    private Integer parking;

    /**
     * 行李寄存
     */
    private Integer luggageStorage;

    /**
     * 叫醒服务
     */
    private Integer wakeUpCall;

    /**
     * 送餐服务
     */
    private Integer deliverMeal;

    /**
     * 信用卡/银联卡收费
     */
    private Integer cardCharge;

    /**
     * 洗衣服务
     */
    private Integer laundry;

    /**
     * 24小时前台接待服务
     */
    private Integer twentyFourHour;

    /**
     * 接送机服务
     */
    private Integer pickUp;

    /**
     * 宠物携带
     */
    private Integer petAllow;

    /**
     * 区域
     */
    private Integer regionType;

}
