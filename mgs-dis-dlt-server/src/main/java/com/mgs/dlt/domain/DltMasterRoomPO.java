package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 21:38
 * @Description:
 */
@Data
@Table(name = "t_dis_dlt_master_room")
public class DltMasterRoomPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer masterHotelID;

    private Integer hotelID;

    private Integer masterBasicRoomId;

    private Integer basicRoomTypeID;

    private String basicRoomName;

    private String basicRoomNameEn;

    private String isAdd;

    private Integer companyHotelId;

    private Integer companyRoomId;
}
