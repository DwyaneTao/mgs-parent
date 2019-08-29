package com.mgs.common.enums;

/**
 *   2018/1/23.
 */
public enum DltChannelEnum {

    B2B("B2B","B2B"),
    CTRIP("Ctrip","携程"),
    CTRIP_B2B("Ctrip_b2b","携程B2B"),
    CTRIP_CHANNEL_A("Ctrip_channel_a","携程ChannelA"),
    QUNAR("Qunar","去哪儿"),
    FEIZHU("Feizhu","飞猪"),
    MEITUAN("Meituan","美团"),
    TCYL("Tcyl","同程艺龙");

    public String key;
    public String value;

    private DltChannelEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getKeyByValue(String value) {
        String key = null;
        DltChannelEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            DltChannelEnum roomStateEnum = arr$[i$];
            if (roomStateEnum.value.equals(value)) {
                key = roomStateEnum.key;
                break;
            }
        }

        return key;
    }

    public static String getValueByKey(String key) {
        String value = null;
        DltChannelEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            DltChannelEnum roomStateEnum = arr$[i$];
            if (roomStateEnum.key.equals(key)) {
                value = roomStateEnum.value;
                break;
            }
        }

        return value;
    }

    public static DltChannelEnum getEnumByKey(String key) {
        DltChannelEnum roomStateEnum = null;
        DltChannelEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            DltChannelEnum bedType = arr$[i$];
            if (bedType.key.equals(key)) {
                roomStateEnum = bedType;
                break;
            }
        }

        return roomStateEnum;
    }
}
