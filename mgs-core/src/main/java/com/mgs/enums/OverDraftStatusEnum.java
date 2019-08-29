package com.mgs.enums;

/**
 * 售罄设置
 */
public enum OverDraftStatusEnum {

    CANNOT_OVER_DRAFT(0, "CANNOT_OVER_DRAFT", "不可超"),
    OVER_DRAFT(1, "OVER_DRAFT", "可超");

    public int no;
    public String code;
    public String desc;


    OverDraftStatusEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(OverDraftStatusEnum overDraftStatusEnum: OverDraftStatusEnum.values()){
            if(no == overDraftStatusEnum.no){
                return overDraftStatusEnum.desc;
            }
        }
        return null;
    }
}
