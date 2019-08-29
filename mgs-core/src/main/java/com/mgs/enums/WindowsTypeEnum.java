package com.mgs.enums;

public enum WindowsTypeEnum {

    WINDOWS(0, "WINDOWS", "有窗"),
    PART_WINDOWS(1,"PART_WINDOWS","部分有窗"),
    NO_WINDOWS(2, "NO_WINDOWS", "无窗");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    WindowsTypeEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
