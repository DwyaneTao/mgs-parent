package com.mgs.enums;

import java.io.Serializable;

/**
 * 结算方式
 */
public enum SettlementTypeEnum implements Serializable {

    MONTH(0, "月结"), HALFMONTH(1, "半月结"), WEEK(2, "周结"), SINGLE(3, "单结"), DAY(4, "日结");

    public int key;
    public String value;

    private SettlementTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static int getKeyByValue(String value) {
        int key = 0;
        for (SettlementTypeEnum settlementTypeEnum : SettlementTypeEnum.values()) {
            if (settlementTypeEnum.value == value) {
                key = settlementTypeEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(int key) {
        String value = null;
        for (SettlementTypeEnum settlementTypeEnum : SettlementTypeEnum.values()) {
            if (settlementTypeEnum.key == key) {
                value = settlementTypeEnum.value;
                break;
            }
        }
        return value;
    }

    public static SettlementTypeEnum getEnumByKey(int key) {
        SettlementTypeEnum settlementMethod = null;
        for (SettlementTypeEnum settlementTypeEnum : SettlementTypeEnum.values()) {
            if (settlementTypeEnum.key == key) {
                settlementMethod = settlementTypeEnum;
                break;
            }
        }
        return settlementMethod;
    }

}
