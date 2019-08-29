package com.mgs.enums;

/**
 * @Auther: Owen
 * @Date: 2019/7/21 11:43
 * @Description:
 */
public enum OrderStatusEnum {

    NEW(-1,"NEW","新单"),
    CONFIRMING(0,"CONFIRMING","待确认"),
    CONFIRMED(1,"CONFIRMED","已确认"),
    CANCELED(2,"CANCELED","已取消");

    public int no;
    public String code;
    public String desc;

    OrderStatusEnum(int no,String code,String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
