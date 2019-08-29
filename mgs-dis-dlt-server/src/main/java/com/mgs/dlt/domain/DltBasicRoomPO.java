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
@Table(name = "t_dis_dlt_basic_room")
public class DltBasicRoomPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer masterHotelId;

    private Integer childHotelId;

    private Integer masterBasicRoomId;

    private Integer basicRoomId;

    private String basicRoomName;
}
