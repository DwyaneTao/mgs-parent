package com.mgs.enums;

/**
 * 关房设置
 */
public enum RoomStatusEnum {

    CLOSE(0,"CLOSE","关房"),
    OPEN(1, "OPEN", "开房");

    public int no;
    public String code;
    public String desc;

    RoomStatusEnum(Integer no, String code, String desc) {
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int no){
        for(RoomStatusEnum roomStatusEnum: RoomStatusEnum.values()){
            if(no == roomStatusEnum.no){
                return roomStatusEnum.desc;
            }
        }
        return null;
    }
}
