package com.mgs.enums;

public enum DltOperateTypeEnum {

    CTRIPACCEPT("0","CTRIPACCEPT","携程确认"),
    CTRIPREFUSE("1","CTRIPREFUSE","携程拒绝"),
    CTRIPCHANGECONFIRMNO("2","CTRIPCHANGECONFIRMNO","携程修改确认号"),
    CTRIPACCEPTCANCEL("11","CTRIPACCEPTCANCEL","携程接受取消"),
    CTRIPREFUSECANCEL("12","CTRIPREFUSECANCEL","携程拒绝取消"),
    QUNARHAVEROOMANDACCEPT("14","QUNARHAVEROOMANDACCEPT","去哪儿确认"),
    QUNARNOROOM("15","QUNARNOROOM","去哪儿拒绝"),
    QUNARAPPLYUNSUBSCRIBE("16","QUNARAPPLYUNSUBSCRIBE","去哪儿取消"),
    QUNARACCEPTUNSUBSCRIBE("17","QUNARACCEPTUNSUBSCRIBE","去哪儿取消退款"),
    QUNARREFUSEUNSUBSCRIBE("18","QUNARREFUSEUNSUBSCRIBE","去哪儿拒绝取消");

    public String no;
    public String key;
    public String value;

    private DltOperateTypeEnum(String no, String key, String value) {
        this.no = no;
        this.key = key;
        this.value = value;
    }

}
