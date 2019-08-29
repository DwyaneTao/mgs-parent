package com.mgs.enums;

/**
 * 网络
 */
public enum  NetWorkEnum {

    WIFI_IN_ROOM( 0, "WIFI_IN_ROOM", "房间WIFI"),
    WIFI_IN_PUBLIC_AREA( 1, "WIFI_IN_PUBLIC_AREA", "公共区WIFI"),
    WIRED_NETWORK_IN_ROOM(2, "WIRED_NETWORK_IN_ROOM","房间有线网络");


    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    NetWorkEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
