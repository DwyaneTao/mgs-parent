package com.mgs.enums;

/**
 * 那个端的用户
 */
public enum EndTypeEnum {

     _0(0,"运营端"),
    _1(1,"供应端"),
    _2(2,"平台端"),
    _3(3,"营销端");

    public Integer no;
    public String desc;


    EndTypeEnum(Integer no, String desc){
        this.no = no;
        this.desc = desc;
    }

}
