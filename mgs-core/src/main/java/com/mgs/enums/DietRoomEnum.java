package com.mgs.enums;

/**
 * 餐饮设备
 */
public enum  DietRoomEnum {

    CHINESE_RESTAURANT(0, "CHINESE_RESTAURANT", "中餐厅"),
    WESTERN_RESTAURANT(1, "WESTERN_RESTAURANT", "西餐厅"),
    JAPANESE_RESTAURANT(2, "JAPANESE_RESTAURANT", "日式餐厅"),
    DELIVERY_SERVICE(3, "DELIVERY_SERVICE", "送餐服务"),
    BAR(4, "BAR", "酒吧"),
    CAFE(5, "CAFE", "咖啡厅"),
    OTHER(6, "OTHER", "其它");



    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    DietRoomEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
