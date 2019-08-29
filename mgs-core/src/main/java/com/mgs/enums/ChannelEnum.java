package com.mgs.enums;

public enum ChannelEnum {

    B2B(0,"B2B","B2B"),
    B2C(1,"B2C","散客"),
    CTRIP(2,"Ctrip","携程"),
    QUNAR(3,"Qunar","去哪儿"),
    MEITUAN(4,"Meituan","美团"),
    FEIZHU(5,"Feizhu","飞猪"),
    TCYL(6,"Tcyl","同程艺龙"),
    CTRIP_B2B(11,"Ctrip_b2b","携程B2B"),
    CTRIP_CHANNEL_A(12,"Ctrip_channel_a","携程ChannelA");

    public int no;
    public String key;
    public String value;

    private ChannelEnum(int no,String key, String value) {
        this.no = no;
        this.key = key;
        this.value = value;
    }

    public static String getKeyByValue(String value) {
        String key = null;
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
            if (channelEnum.value == value) {
                key = channelEnum.key;
                break;
            }
        }
        return key;
    }

    public static int getNoByKey(String key) {
        int no = 99;
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
            if (channelEnum.key.equals(key)) {
                no = channelEnum.no;
                break;
            }
        }
        return no;
    }

    public static String getValueByKey(String key) {
        String value = null;
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
            if (channelEnum.key.equals(key)) {
                value = channelEnum.value;
                break;
            }
        }
        return value;
    }

    public static ChannelEnum getEnumByKey(String key) {
        ChannelEnum settlementMethod = null;
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
            if (channelEnum.key == key) {
                settlementMethod = channelEnum;
                break;
            }
        }
        return settlementMethod;
    }

}
