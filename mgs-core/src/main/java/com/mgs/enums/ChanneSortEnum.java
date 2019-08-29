package com.mgs.enums;

public enum ChanneSortEnum {

    ALL(0 ,"All" ,"全部"),
    B2B(1,"B2B","同行"),
    B2C(2,"B2C","直客"),
    CTRIP(3,"Ctrip","携程"),
    QUNAR(4,"Qunar","去哪儿"),
    MEITUAN(5,"Meituan","美团"),
    FEIZHU(6,"Feizhu","飞猪");



    public int no;
    public String key;

    private ChanneSortEnum(int no,String key, String value) {
        this.no = no;
        this.key = key;
    }

    public static int getNoByKey(String key) {
        int no = 2;
        for (ChanneSortEnum channeSortEnum : ChanneSortEnum.values()) {
            if (channeSortEnum.key.equals(key)) {
                no = channeSortEnum.no;
                break;
            }
        }
        return no;
    }
}
