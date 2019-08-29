package com.mgs.finance.enums;

public enum CheckStatusEnum {

    NEW(0,"新建"),
    CAN_CHECK(1,"可出账"),
    HOLD(2,"已纳入账单"),
    CHECKED(3,"已对账");

    public Integer key;
    public String value;

    private CheckStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(CheckStatusEnum checkStatusEnum : CheckStatusEnum.values()) {
            if(checkStatusEnum.value.equals(value)) {
                key = checkStatusEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(CheckStatusEnum checkStatusEnum : CheckStatusEnum.values()) {
            if(checkStatusEnum.key.equals(key)) {
                value = checkStatusEnum.value;
                break;
            }
        }
        return value;
    }

    public static CheckStatusEnum getEnumByKey(String key){
        CheckStatusEnum checkStatusEnum = null;
        for(CheckStatusEnum hotelFeature : CheckStatusEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                checkStatusEnum = hotelFeature;
                break;
            }
        }
        return checkStatusEnum;
    }

    public static CheckStatusEnum getEnumBySupplyOrderType(Integer supplyOrderType){
        CheckStatusEnum checkStatusEnum = null;
        for(CheckStatusEnum hotelFeature : CheckStatusEnum.values()) {
            if(hotelFeature.key.equals(supplyOrderType+1)) {
                checkStatusEnum = hotelFeature;
                break;
            }
        }
        return checkStatusEnum;
    }
}
