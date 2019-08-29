package com.mgs.enums;

public enum SupplyRequestEnum {



    ORDERFORM(0, "ORDERFORM", "预订单"),
    REPEAT_ORDERFORM(1, "REPEAT_ORDERFORM", "重发预订单"),
    MODIFY_ORDER(2, "MODIFY_ORDER", "修改订单"),
    CANCEL_ORDER(3, "CANCEL_ORDER", "取消订单");

    public int no;
    public String code;
    public String desc;


    SupplyRequestEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(SupplyRequestEnum supplyRequestEnum: SupplyRequestEnum.values()){
            if(no == supplyRequestEnum.no){
                return supplyRequestEnum.desc;
            }
        }
        return null;
    }


}
