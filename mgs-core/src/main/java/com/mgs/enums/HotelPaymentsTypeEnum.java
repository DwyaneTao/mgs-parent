package com.mgs.enums;

/**
 * 可用支付方式
 */
public enum HotelPaymentsTypeEnum {

    DOMESTIC_UNION_PAY_CARD(0, "DOMESTIC_UNION_PAY_CARD", "国内发行银联卡"),
    WECHAT(1, "WECHAT", "微信"),
    ALIPAY(2, "ALIPAY", "支付宝"),
    MASTER(3, "MASTER", "万事达"),
    VISA(4, "VISA", "威士"),
    DINERS_CLUB(5, "DINERS_CLUB", "大来"),
    JCB(6, "JCB", "JCB"),
    AMEX(7, "AMEX", "运通");

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    public int no;
    public String code;
    public String desc;

    HotelPaymentsTypeEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
