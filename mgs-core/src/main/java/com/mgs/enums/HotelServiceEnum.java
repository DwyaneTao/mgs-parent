package com.mgs.enums;

/**
 * 酒店服务
 */
public enum HotelServiceEnum {

    LUGGAGE_STORAGE(0, "LUGGAGE_STORAGE", "行李寄存"),
    FRONT_DESK_24_HOURS(1, "FRONT_DESK_24_HOUR", "24小时前台"),
    LOBBY_MANAGER_24_HOURS(2, "LOBBY_MANAGER_24_HOURS", "24小时大堂经理"),
    WAKE_UP_SERVICE(3, "WAKE_UP_SERVICE", "叫醒服务"),
    FREE_PICK_UP(4, "FREE_PICK_UP", "免费接机/接站"),
    CHARGE_PICK_UP(5, "CHARGE_PICK_UP", "收费接机/接站"),
    CAR_RENTAL_SERVICE(6, "CAR_RENTAL_SERVICE", "租车服务"),
    VALET_PARKING(7, "VALET_PARKING", "代客泊车"),
    RECEPTION_OF_FOREIGN(8, "RECEPTION_OF_FOREIGN", "接待外宾服务"),
    INFIRMARY(9, "INFIRMARY", "医务室"),
    MULTILINGUAL_STAFF(10, "MULTILINGUAL_STAFF", "多语种工作人员"),
    OTHER(11, "OTHER", "其它");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    HotelServiceEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
