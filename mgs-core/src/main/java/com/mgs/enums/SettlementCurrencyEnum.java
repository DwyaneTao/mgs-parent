package com.mgs.enums;

/**
 * @author py
 * @date 2019/6/20 17:31
 **/
public enum SettlementCurrencyEnum {
    ONE("0","CNY(人民币)"),
    TWO("1","HKD(港币)"),
    THREE("2","MOP(澳门元)"),
    FOUR("3","USD(美元)"),
    FIVE("4","THB(泰铢)"),
    SIX("5","SGD(新加坡元)"),
    SEVEN("6","MYR(马来西来林吉安特)"),
    EIGHT("7","JPY(日元)"),
    NINE("8","KRW(韩元)"),
    TEN("9","EUR(欧元)");
    public String key;
    public String value;

    private SettlementCurrencyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getKeyByValue(String value) {
        String key = null;
        for (SettlementCurrencyEnum settlementCurrencyEnum : SettlementCurrencyEnum.values()) {
            if (settlementCurrencyEnum.value.equals(value)) {
                key = settlementCurrencyEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(String key) {
        String value = null;
        for (SettlementCurrencyEnum settlementCurrencyEnum : SettlementCurrencyEnum.values()) {
            if (settlementCurrencyEnum.key.equals(key)) {
                value = settlementCurrencyEnum.value;
                break;
            }
        }
        return value;
    }

    public static SettlementCurrencyEnum getEnumByKey(String key) {
        SettlementCurrencyEnum settlementMethod = null;
        for (SettlementCurrencyEnum settlementCurrencyEnum : SettlementCurrencyEnum.values()) {
            if (settlementCurrencyEnum.key.equals(key)) {
                settlementMethod = settlementCurrencyEnum;
                break;
            }
        }
        return settlementMethod;
    }

}

