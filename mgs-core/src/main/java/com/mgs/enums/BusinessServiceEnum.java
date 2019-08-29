package com.mgs.enums;

/**
 * 商务服务
 */
public enum BusinessServiceEnum {

    MEETING_ROOM(0, "MEETING_ROOM", "会议室"),
    BALL_ROOM(1, "BALL_ROOM", "宴会厅"),
    MULTIFUNCTION_ROOM(2, "MULTIFUNCTION_ROOM", "多功能室"),
    MULTIMEDIA_SYSTEM(3, "MULTIMEDIA_SYSTEM", "多媒体系统"),
    CENTER_OF_BUSINESS(4, "CENTER_OF_BUSINESS", "商务中心");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    BusinessServiceEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
