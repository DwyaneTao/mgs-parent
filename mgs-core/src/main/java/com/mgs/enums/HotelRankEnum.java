package com.mgs.enums;

/**
 * 酒店星级
 */
public enum HotelRankEnum {

    ONE(0, "ONE", "经济型"),
    TWO(1, "TWO", "二星"),
    THREE(2, "THREE", "舒服型"),
    FOUR(3, "FOUR", "三星"),
    FIVE(4, "FIVE", "高档型"),
    SIX(5, "SIX", "四星"),
    SEVERN(6, "SEVERN", "豪华型"),
    EIGHT(7, "EIGHT", "五星");

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    public int no;
    public String code;
    public String desc;

    HotelRankEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
