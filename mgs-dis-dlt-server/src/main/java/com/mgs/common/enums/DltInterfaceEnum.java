package com.mgs.common.enums;

public enum DltInterfaceEnum {

    GET_DLT_COUNTRY_LIST("getdltcountrylist", "获取国家列表"),
    GET_DLT_CITY_LIST("getdltcitylist", "获取城市列表"),
    GET_DLT_HOTEL_LIST("getdlthotellist", "获取子酒店列表"),
    GET_DLT_BASIC_ROOM_LIST("getdltbasicroomlist", "获取物理房型列表"),
    GET_HOTEL_ROOM_STATIC_INFO("gethotelroomstaticinfo","获取子房型信息"),
    CREATE_BASIC_ROOM("createBasicRoom", "创建子物理房型"),
    CREATE_ROOM("createRoom", "创建售卖房型"),
    BATCH_PUSH_ROOM_DATA("BatchPushRoomData", "直连推送报价等数据接口"),
    GET_DLT_ORDER_NOTIFY("getdltordernotify", "订单变化通知接口"),
    GET_DLT_ORDER_INFO("getdltorderinfo", "订单详情接口"),
    OPERATER_DLT_ORDER("operaterDltOrder", "订单操作接口");

    public String code;
    public String name;

    DltInterfaceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        if (null != code) {
            for (DltInterfaceEnum e : DltInterfaceEnum.values()) {
                if (e.code.equals(code)) {
                    return e.name;
                }
            }
        }
        return null;
    }
}
