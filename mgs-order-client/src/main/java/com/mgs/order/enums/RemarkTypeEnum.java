package com.mgs.order.enums;

/**
 * 备注类型
 */
public enum RemarkTypeEnum {

    AGENT_NOTE(0, "分销商备注"),
    SUPPLY_NOTE(1, "供应商备注"),
    INNER_NOTE(2, "内部备注");

    public int key;
    public String value;

    RemarkTypeEnum(int key, String name) {
        this.key = key;
        this.value = name;
    }

    public static String getValueByKey(int key) {
        String value = null;
        for (RemarkTypeEnum statusEnum : RemarkTypeEnum.values()) {
            if (key == statusEnum.key) {
                value = statusEnum.value;
                break;
            }
        }
        return value;
    }

    public static int getKeyByValue(String value) {
        int key = 0;
        for (RemarkTypeEnum statusEnum : RemarkTypeEnum.values()) {
            if (statusEnum.value.equals(value)) {
                key = statusEnum.key;
                break;
            }
        }
        return key;
    }

    public static RemarkTypeEnum getEnumByKey(int key) {
        RemarkTypeEnum returnEnum = null;
        for (RemarkTypeEnum statusEnum : RemarkTypeEnum.values()) {
            if (key == statusEnum.key) {
                returnEnum = statusEnum;
                break;
            }
        }
        return returnEnum;
    }
}
