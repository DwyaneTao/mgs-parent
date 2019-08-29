package com.mgs.finance.enums;

public enum  StatementStatusEnum {

    UN_CHECK(0,"未对账"),
    CHECKING(1,"对账中"),
    CONFIRMED(2,"已确认"),
    CANCELED(3,"已取消");

    public Integer key;
    public String value;

    private StatementStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(StatementStatusEnum statementStatusEnum : StatementStatusEnum.values()) {
            if(statementStatusEnum.value.equals(value)) {
                key = statementStatusEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(StatementStatusEnum statementStatusEnum : StatementStatusEnum.values()) {
            if(statementStatusEnum.key.equals(key)) {
                value = statementStatusEnum.value;
                break;
            }
        }
        return value;
    }

    public static StatementStatusEnum getEnumByKey(String key){
        StatementStatusEnum statementStatusEnum = null;
        for(StatementStatusEnum hotelFeature : StatementStatusEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                statementStatusEnum = hotelFeature;
                break;
            }
        }
        return statementStatusEnum;
    }

    public static StatementStatusEnum getEnumBySupplyOrderType(Integer supplyOrderType){
        StatementStatusEnum statementStatusEnum = null;
        for(StatementStatusEnum hotelFeature : StatementStatusEnum.values()) {
            if(hotelFeature.key.equals(supplyOrderType+1)) {
                statementStatusEnum = hotelFeature;
                break;
            }
        }
        return statementStatusEnum;
    }
}
