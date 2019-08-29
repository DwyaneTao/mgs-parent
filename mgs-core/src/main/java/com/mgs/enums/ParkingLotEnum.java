package com.mgs.enums;

/**
 * 停车场
 */
public enum ParkingLotEnum {

    FREE_PARKING(0, "FREE_PARKING", "免费停车场"),
    FEE_PARKING(1,"FEE_PARKING", "收费停车场");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    ParkingLotEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
