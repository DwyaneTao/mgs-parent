package com.mgs.enums;

/**
 * 配额调整方式
 */
public enum AdjustmentTypeEnum {

    ADD(0, "ADD", "加"),
    DIVIDE(1, "DIVIDE", "减"),
    EQUALS(2, "EQUALS", "改为");

    public int no;
    public String code;
    public String desc;


    AdjustmentTypeEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(AdjustmentTypeEnum adjustmentTypeEnum: AdjustmentTypeEnum.values()){
            if(no == adjustmentTypeEnum.no){
                return adjustmentTypeEnum.desc;
            }
        }
        return null;
    }
}
