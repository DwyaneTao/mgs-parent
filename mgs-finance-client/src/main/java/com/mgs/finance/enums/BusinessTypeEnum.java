package com.mgs.finance.enums;

public enum BusinessTypeEnum {

    ORDER(0,"订单"),
    SUPPLYORDER(1,"供货单"),
    AGENTSTATEMENT(2,"分销商账单结算"),
    SUPPLIERSTATEMENT(3,"供应商账单结算");

    public Integer key;
    public String value;

    private BusinessTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = null;
        for(BusinessTypeEnum businessTypeEnum : BusinessTypeEnum.values()) {
            if(businessTypeEnum.value.equals(value)) {
                key = businessTypeEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(Integer key) {
        String value = null;
        for(BusinessTypeEnum businessTypeEnum : BusinessTypeEnum.values()) {
            if(businessTypeEnum.key.equals(key)) {
                value = businessTypeEnum.value;
                break;
            }
        }
        return value;
    }

    public static BusinessTypeEnum getEnumByKey(String key){
        BusinessTypeEnum businessTypeEnum = null;
        for(BusinessTypeEnum hotelFeature : BusinessTypeEnum.values()) {
            if(hotelFeature.key.equals(key)) {
                businessTypeEnum = hotelFeature;
                break;
            }
        }
        return businessTypeEnum;
    }

    public static BusinessTypeEnum getEnumBySupplyOrderType(Integer supplyOrderType){
        BusinessTypeEnum businessTypeEnum = null;
        for(BusinessTypeEnum hotelFeature : BusinessTypeEnum.values()) {
            if(hotelFeature.key.equals(supplyOrderType+1)) {
                businessTypeEnum = hotelFeature;
                break;
            }
        }
        return businessTypeEnum;
    }
}
