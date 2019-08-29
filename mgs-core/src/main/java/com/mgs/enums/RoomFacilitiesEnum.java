package com.mgs.enums;

/**
 * 房型设施
 */
public enum  RoomFacilitiesEnum {

    TOILETRIES(0, "TOILETRIES", "洗漱用品"),
    DESK(1,"DESK","书桌"),
    IRONING_KIT(2, "IRONING_KIT", "熨衣套件"),
    BATHROOM(3, "BATHROOM", "浴室"),
    BATHTUB(4, "BATHTUB", "浴缸"),
    SLIPPERS(5, "SLIPPERS", "拖鞋"),
    HAIR_DRYER(6, "HAIR_DRYER", "吹风机"),
    KITCHEN(7, "KITCHEN", "厨房"),
    MICROWAVE_OVEN(8, "MICROWAVE_OVEN", "微波炉"),
    TEA_COFFEE_MAKER(9, "TEA_COFFEE_MAKER", "泡茶机/咖啡机"),
    WASHING_MACHINE(10, "WASHING_MACHINE", "洗衣机"),
    OTHER(11, "OTHER", "其它");

    public int no;
    public String code;
    public String desc;

    public String getDesc(){
        return desc;
    }

    public Integer getNo(){
        return no;
    }

    RoomFacilitiesEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }
}
