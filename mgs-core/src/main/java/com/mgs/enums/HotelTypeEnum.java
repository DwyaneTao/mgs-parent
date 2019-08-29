package com.mgs.enums;

/**
 * 酒店主题
 */
public enum HotelTypeEnum {

    HOME_STAY(0, "HOME_STAY", "民宿"),
    APARTMENT(1, "APARTMENT", "公寓"),
    SCENIC(2, "SCENIC", "景区酒店"),
    TAVERN(3, "TAVERN", "客栈"),
    BUSINESS_MEETING(4, "BUSINESS_MEETING", "商务会议"),
    YOUTH(5, "YOUTH","青年旅馆"),
    RESORT(6, "RESORT", "度假村"),
    BEACHFRONT_LEISURE(7, "BEACHFRONT_LEISURE", "海滨休闲"),
    ENTERTAINMENT(8, "ENTERTAINMENT", "娱乐休闲"),
    PARENT_CHILD(9, "PARENT_CHILD", "亲子酒店"),
    FARM_HOUSE(10,"FARM_HOUSE","农家乐"),
    HOT_SPRING_VACATION(11, "HOT_SPRING_VACATION", "温泉度假"),
    VILLA(12, "VILLA", "别墅"),
    THEME(13, "THEME", "主题酒店"),
    OTHER(14, "OTHER", "其它");


    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    HotelTypeEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
