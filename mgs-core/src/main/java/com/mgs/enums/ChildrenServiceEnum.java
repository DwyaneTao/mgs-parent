package com.mgs.enums;

public enum ChildrenServiceEnum {

    CHILDREN_CARE(0, "CHILDREN_CARE", "儿童看护"),
    CHILDREN_PLAYGROUND(1, "CHILDREN_PLAYGROUND", "儿童乐园"),
    CHILDREN_TOY(2, "CHILDREN_TOY", "儿童玩具");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    ChildrenServiceEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
