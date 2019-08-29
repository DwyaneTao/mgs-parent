package com.mgs.enums;

public enum  SaleStatusEnum {

    IN_DEPOSITORY(0, "IN_DEPOSITORY", "下架"),
    ON_SALE(1, "ON_SALE", "上架");

    public int no;
    public String code;
    public String desc;


    SaleStatusEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(SaleStatusEnum saleStatusEnum: SaleStatusEnum.values()){
            if(no == saleStatusEnum.no){
                return saleStatusEnum.desc;
            }
        }
        return null;
    }
}
