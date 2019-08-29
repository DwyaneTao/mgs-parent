package com.mgs.hotel.dto;

import com.mgs.annotations.Compared;
import com.mgs.common.BaseDTO;
import lombok.Data;

/**
 * 基本房型信息
 */
@Data
public class BasicRoomInfoDTO {

    /**
     * 房型id
     */
    private Integer roomId;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 房型名称
     */
    @Compared(variableName = "房型中文名")
    private String roomName;

    /**
     * 床型
     */
    @Compared(variableName = "床型信息", split = ",")
    private String bedTypes;

    /**
     * 网络
     */
    @Compared(variableName = "网络", enumsName = "com.mgs.enums.RoomNetWorkEnum")
    private Integer network;

    /**
     * 窗户
     */
    @Compared(variableName = "窗户", enumsName = "com.mgs.enums.WindowsTypeEnum")
    private Integer windowAvailableType;

    /**
     * 面积
     */
    @Compared(variableName = "房间面积")
    private String area;

    /**
     * 主图url
     */
    private String mainPhotoUrl;

    /**
     * 最大入住人数
     */
    @Compared(variableName = "最大入住数")
    private Integer maxGuestQty;

    /**
     * 楼层
     */
    @Compared(variableName = "所在楼层")
    private String floorNumber;

    /**
     * 房型设施
     */
    @Compared(variableName = "房型设施", enumsName = "com.mgs.enums.RoomFacilitiesEnum", split = ",")
    private String roomFacilities;

    /**
     * 房型英文名
     */
    @Compared(variableName = "房型英文名")
    private String roomEnglishName;

    private String createdBy;

    private String createdDt;

    private String modifiedBy;

    private String modifiedDt;

    /**
     * 操作人账号
     */
    private String operatorAccount;
}
