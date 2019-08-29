package com.mgs.enums;

/**
 * 酒店图片类型
 */
public enum HotelPhotoEnum {

    OUTSIDE(0, "OUTSIDE", "外景图"),
    INSIDE(1, "INSIDE", "内景图"),
    ROOM(2, "ROOM", "房间图"),
    OTHER(3, "OTHER", "其它图");

    public int no;
    public String code;
    public String desc;

    HotelPhotoEnum(int no, String code, String desc){
        this.no = no;
        this.code = code;
        this.desc = desc;
    }

    public static String getHotelPhotoDesc(int no){
        for(HotelPhotoEnum hotelPhotoEnum: HotelPhotoEnum.values()){
            if(no == hotelPhotoEnum.no){
                return hotelPhotoEnum.desc;
            }
        }
        return HotelPhotoEnum.OTHER.desc;
    }
}
