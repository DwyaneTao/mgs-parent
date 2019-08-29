package com.mgs.order.enums;

public enum SendingResultEnum {

    UNSEND(0,"未发单"),
    SUCCESS(1,"成功"),
    FAILURE(2,"失败");

    public Integer key;
    public String value;

    private SendingResultEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(SendingResultEnum sendingResultEnum : SendingResultEnum.values()) {
            if(sendingResultEnum.value.equals(value)) {
                key = sendingResultEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(SendingResultEnum sendingResultEnum : SendingResultEnum.values()) {
            if(sendingResultEnum.key.equals(key)) {
                value = sendingResultEnum.value;
                break;
            }
        }
        return value;
    }

    public static SendingResultEnum getEnumByKey(String key){
        SendingResultEnum sendingResultEnum = null;
        for(SendingResultEnum hotelFeature : SendingResultEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                sendingResultEnum = hotelFeature;
                break;
            }
        }
        return sendingResultEnum;
    }

    public static SendingResultEnum getEnumBySupplyOrderType(String supplyOrderType){
        SendingResultEnum sendingResultEnum = null;
        for(SendingResultEnum hotelFeature : SendingResultEnum.values()) {
            if(hotelFeature.key.equals(supplyOrderType+1)) {
                sendingResultEnum = hotelFeature;
                break;
            }
        }
        return sendingResultEnum;
    }
}
