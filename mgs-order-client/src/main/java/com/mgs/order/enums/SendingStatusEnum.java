package com.mgs.order.enums;

public enum SendingStatusEnum {

    UNSEND(0,"未发单"),
    SEND_RESERVATION(1,"已发预订单"),
    SEND_RESERVATION_AGAIN(2,"已重发预订单"),
    SEND_MODIFY(3,"已发修改单"),
    SEND_CANCEL(4,"已发取消单");

    public Integer key;
    public String value;

    private SendingStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(SendingStatusEnum sendingStatusEnum : SendingStatusEnum.values()) {
            if(sendingStatusEnum.value.equals(value)) {
                key = sendingStatusEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(SendingStatusEnum sendingStatusEnum : SendingStatusEnum.values()) {
            if(sendingStatusEnum.key.equals(key)) {
                value = sendingStatusEnum.value;
                break;
            }
        }
        return value;
    }

    public static SendingStatusEnum getEnumByKey(String key){
        SendingStatusEnum sendingStatusEnum = null;
        for(SendingStatusEnum hotelFeature : SendingStatusEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                sendingStatusEnum = hotelFeature;
                break;
            }
        }
        return sendingStatusEnum;
    }

    public static SendingStatusEnum getEnumBySupplyOrderType(Integer supplyOrderType){
        SendingStatusEnum sendingStatusEnum = null;
        for(SendingStatusEnum hotelFeature : SendingStatusEnum.values()) {
            if(hotelFeature.key.equals(supplyOrderType+1)) {
                sendingStatusEnum = hotelFeature;
                break;
            }
        }
        return sendingStatusEnum;
    }
}
