package com.mgs.order.enums;

/**
 * 供货单发送类型
 */
public enum SendingTypeEnum {

    EBK(1, "EBK"),EMAIL(2,"EMAIL"),FAX(3,"FAX"),PHONE(4,"PHONE"),WECHAT(5,"WECHAT"),QQ(6,"QQ")
    ,DIRECT_LINK(7,"直连");

    public int key;
    public String value;

    SendingTypeEnum(int key, String name) {
        this.key = key;
        this.value = name;
    }

    public static String getValueByKey(int key) {
        String value = null;
        for (SendingTypeEnum statusEnum : SendingTypeEnum.values()) {
            if (key == statusEnum.key) {
                value = statusEnum.value;
                break;
            }
        }
        return value;
    }

    public static int getKeyByValue(String value) {
        int key = 0;
        for (SendingTypeEnum statusEnum : SendingTypeEnum.values()) {
            if (statusEnum.value.equals(value)) {
                key = statusEnum.key;
                break;
            }
        }
        return key;
    }

    public static SendingTypeEnum getEnumByKey(int key) {
        SendingTypeEnum returnEnum = null;
        for (SendingTypeEnum statusEnum : SendingTypeEnum.values()) {
            if (key == statusEnum.key) {
                returnEnum = statusEnum;
                break;
            }
        }
        return returnEnum;
    }
}
