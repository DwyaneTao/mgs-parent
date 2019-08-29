package com.mgs.enums;

/**
 * 早餐
 */
public enum  BreakFastEnum {

    NONE(0, "NONE", "无早餐"),
    ONE(1, "ONE", "单早"),
    TWO(2, "TWO", "双早"),
    THREE(3, "THREE", "三早"),
    FOUR(4, "FOUR", "四早"),
    EVERYONE(-1, "EVERYONE", "人头早");


    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    BreakFastEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
