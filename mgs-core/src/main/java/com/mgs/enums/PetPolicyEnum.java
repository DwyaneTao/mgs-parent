package com.mgs.enums;

/**
 * 宠物
 */
public enum PetPolicyEnum {

    PETS_LIMIT(0, "PETS_LIMIT", "不可携带宠物"),
    PETS_ALLOWED(1, "PETS_ALLOWED", "可携带宠物");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    PetPolicyEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

}
