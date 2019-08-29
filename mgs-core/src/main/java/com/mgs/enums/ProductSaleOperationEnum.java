package com.mgs.enums;

public enum ProductSaleOperationEnum {

    MODIFY_SALE(0, "MODIFY_SALE", "上下架"),
    SALE(1, "SALE", "售价");

    public int no;
    public String code;
    public String desc;


    ProductSaleOperationEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(ProductSaleOperationEnum productSaleOperationEnum: ProductSaleOperationEnum.values()){
            if(no == productSaleOperationEnum.no){
                return productSaleOperationEnum.desc;
            }
        }
        return null;
    }
}
