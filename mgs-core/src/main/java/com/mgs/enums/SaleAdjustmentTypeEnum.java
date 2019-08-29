package com.mgs.enums;

/**
 * 售价
 */
public enum SaleAdjustmentTypeEnum {

    PLUS_NUMBER(0, "PLUS_NUMBER", "在底价上\"增加#\""),
    DIVIDE_NUMBER(1, "DIVIDE_NUMBER", "在底价上\"减少#\""),
    PLUS_PERCENTAGE(2, "PLUS_PERCENTAGE", "在底价上\"加点#%\""),
    EQUALS(3, "EQUALS", "将售价设置为\"#\"");


    public int no;
    public String code;
    public String desc;


    SaleAdjustmentTypeEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(SaleAdjustmentTypeEnum saleAdjustmentTypeEnum: SaleAdjustmentTypeEnum.values()){
            if(no == saleAdjustmentTypeEnum.no){
                return saleAdjustmentTypeEnum.desc;
            }
        }
        return null;
    }
}
