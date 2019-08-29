package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/19 14:30
 * @Description:
 */
@Data
public class RoomInfo {
    private long roomId;
    private long basicRoomTypeId;
    private long masterBasicRoomTypeId;
    private String roomName;
    private int extraBedFee;
    private int maxAudltOccupancy;
    private int maxChildrenOccupancy;
    private ApplicabilityInfo applicabilityInfo;
    private String broadNet;
    private String roomBedInfos;
    private int mealType;
    private String smoking;
}
