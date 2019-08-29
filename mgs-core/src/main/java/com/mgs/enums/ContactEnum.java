package com.mgs.enums;

/**
 * @author py
 * @date 2019/7/3 16:57
 **/
public enum  ContactEnum {
    ZERO(0, "没有分配角色"),
    ONE(1, "业务联系人"),
    TWO(2,"财务联系人");
    public Integer key;
    public String desc;

    ContactEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
