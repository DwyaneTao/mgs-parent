package com.mgs.enums;

/**
 * 房间网络信息
 */
public enum  RoomNetWorkEnum {
    FREE_WIFI( 0, "WIFI_IN_ROOM", "免费WIFI"),
    CHARGE_WIFI( 1, "WIFI_IN_PUBLIC_AREA", "收费WIFI"),
    NO_WIFI(2, "WIRED_NETWORK_IN_ROOM","无WIFI");


    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    RoomNetWorkEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
