package com.mgs.enums;

/**
 * @author py
 * @date 2019/6/22 11:55
 **/
public enum  AvailableEnum {
    Start(1, "启用"),
    End(0, "不启用");
    public Integer key;
    public String desc;

    AvailableEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
