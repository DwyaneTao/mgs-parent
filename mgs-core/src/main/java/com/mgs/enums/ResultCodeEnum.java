package com.mgs.enums;

public enum ResultCodeEnum {

    SUCCESS(1,"成功"),

    FAILURE(0,"失败");

    public int code;
    public String desc;

    private ResultCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
