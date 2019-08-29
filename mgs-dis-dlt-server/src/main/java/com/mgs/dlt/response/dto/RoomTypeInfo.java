package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/19 12:17
 * @Description:
 */
@Data
public class RoomTypeInfo {

    private long roomTypeId;
    private long masterBasicRoomTypeId;
    private String roomTypeName;
    private String floorRange;
    private String areaRange;
    private int hasWindow;
    private int roomQuantity;
    private String bathRoomType;
    private String facilities;
    private String smoking;
    private String pictures;
    private String roomBedInfos;
    private int person;
}
