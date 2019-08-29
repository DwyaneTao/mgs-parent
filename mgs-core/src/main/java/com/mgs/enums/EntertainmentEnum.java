package com.mgs.enums;

/**
 * 休息娱乐
 */
public enum  EntertainmentEnum {

    OUTSIDE_SWIMMING_POOL(0, "OUTSIDE_SWIMMING_POOL", "室外游泳池"),
    INSIDE_SWIMING_POOL(1, "INSIDE_SWIMMING_POOL", "室内游泳池"),
    GYM(2, "GYM", "健身房"),
    BILLIARD_ROOM(3, "BILLIARD_ROOM", "桌球室"),
    SPA(4, "SPA", "SPA"),
    OTHER(5, "OTHER", "其它");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    EntertainmentEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
