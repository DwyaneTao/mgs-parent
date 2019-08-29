package com.mgs.enums;

/**
 * 增量类型
 */
public enum IncrementTypeEnum {

    PRICE(0, "PRICE", "价格变化"),
    ROOM_STATUS(1, "ROOM_STATUS", "房态变化"),
    PRICE_AND_ROOM_STATUS(2, "PRICE_AND_ROOM_STATUS", "价格和房态变化"),
    SALE_PRICE(3, "SALE_PRICE", "售价"),
    ON_SALE(4, "ON_SALE", "上下架"),
    RESTRICT(5, "RESTRICT", "条款"),
    BREAKFAST(6, "BREAKFAST", "早餐"),
    RESTRICT_AND_BREAKFAST(7, "RESTRICT_AND_BREAKFAST", "早餐和条款");


    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    IncrementTypeEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
