package com.mgs.hotel.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_baseinfo_room")
public class BasicRoomInfoPO extends BasePO {

    /**
     * 房型id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 窗户类型
     */
    private Integer windowAvailableType;

    /**
     * 网络类型
     */
    private Integer network;

    /**
     * 床型类型
     */
    private String bedTypes;

    /**
     * 楼层
     */
    private String floorNumber;

    /**
     * 占地面积
     */
    private String area;

    /**
     * 最大入住人数
     */
    private Integer maxGuestQty;

    /**
     * 有效性
     */
    private Integer active;

    /**
     * 房型设施
     */
    private String roomFacilities;

    /**
     * 房型英文名
     */
    private String roomEnglishName;

    /**
     * 主图url
     */
    private String mainPhotoUrl;




}
