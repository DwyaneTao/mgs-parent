package com.mgs.finance.enums;

public enum WorkOrderStatusEnum {

    UN_SETTLE(0,"未结算"),
    SETTLED(1,"已结算"),
    CANCELED(2,"已取消");

    public Integer key;
    public String value;

    private WorkOrderStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(WorkOrderStatusEnum workOrderStatusEnum : WorkOrderStatusEnum.values()) {
            if(workOrderStatusEnum.value.equals(value)) {
                key = workOrderStatusEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(WorkOrderStatusEnum workOrderStatusEnum : WorkOrderStatusEnum.values()) {
            if(workOrderStatusEnum.key.equals(key)) {
                value = workOrderStatusEnum.value;
                break;
            }
        }
        return value;
    }

    public static WorkOrderStatusEnum getEnumByKey(String key){
        WorkOrderStatusEnum workOrderStatusEnum = null;
        for(WorkOrderStatusEnum hotelFeature : WorkOrderStatusEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                workOrderStatusEnum = hotelFeature;
                break;
            }
        }
        return workOrderStatusEnum;
    }

    public static WorkOrderStatusEnum getEnumBySupplyOrderType(Integer supplyOrderType){
        WorkOrderStatusEnum workOrderStatusEnum = null;
        for(WorkOrderStatusEnum hotelFeature : WorkOrderStatusEnum.values()) {
            if(hotelFeature.key.equals(supplyOrderType+1)) {
                workOrderStatusEnum = hotelFeature;
                break;
            }
        }
        return workOrderStatusEnum;
    }
}
