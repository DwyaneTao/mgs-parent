package com.mgs.order.enums;

public enum ConfirmationStatusEnum {

    UNCONFIRM(0,"未确认"),
    CONFIRMED(1,"确认"),
    CANCELED(2,"已取消");

    public Integer key;
    public String value;

    private ConfirmationStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(ConfirmationStatusEnum confirmationStatusEnum : ConfirmationStatusEnum.values()) {
            if(confirmationStatusEnum.value.equals(value)) {
                key = confirmationStatusEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(ConfirmationStatusEnum confirmationStatusEnum : ConfirmationStatusEnum.values()) {
            if(confirmationStatusEnum.key.equals(key)) {
                value = confirmationStatusEnum.value;
                break;
            }
        }
        return value;
    }

    public static ConfirmationStatusEnum getEnumByKey(String key){
        ConfirmationStatusEnum confirmationStatusEnum = null;
        for(ConfirmationStatusEnum hotelFeature : ConfirmationStatusEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                confirmationStatusEnum = hotelFeature;
                break;
            }
        }
        return confirmationStatusEnum;
    }
}
