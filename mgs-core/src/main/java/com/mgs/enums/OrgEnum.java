package com.mgs.enums;

/**
 * @author py
 * @date 2019/6/22 11:38
 **/
public enum OrgEnum {
    Org_Supplier(0, "供应商"),
    Org_Agent(1, "客户"),
    Org_Company(2, "运营商");

    public Integer key;
    public String desc;

    OrgEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
