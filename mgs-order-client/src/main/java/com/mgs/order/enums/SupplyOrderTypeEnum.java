package com.mgs.order.enums;

public enum SupplyOrderTypeEnum {

    BOOK(0, "预订单"),
    RESEND(1, "重发预订单"),
    REVISE(2, "修改单"),
    CANCEL(3, "取消单");

    public Integer key;
    public String value;

    private SupplyOrderTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(SupplyOrderTypeEnum supplyOrderTypeEnum : SupplyOrderTypeEnum.values()) {
            if(supplyOrderTypeEnum.value.equals(value)) {
                key = supplyOrderTypeEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(SupplyOrderTypeEnum supplyOrderTypeEnum : SupplyOrderTypeEnum.values()) {
            if(supplyOrderTypeEnum.key.equals(key)) {
                value = supplyOrderTypeEnum.value;
                break;
            }
        }
        return value;
    }

    public static SupplyOrderTypeEnum getEnumByKey(String key){
        SupplyOrderTypeEnum supplyOrderTypeEnum = null;
        for(SupplyOrderTypeEnum hotelFeature : SupplyOrderTypeEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                supplyOrderTypeEnum = hotelFeature;
                break;
            }
        }
        return supplyOrderTypeEnum;
    }
}
