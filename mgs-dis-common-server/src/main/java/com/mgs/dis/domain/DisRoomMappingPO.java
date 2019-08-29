package com.mgs.dis.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/7/26 20:19
 * @Description: 房型映射
 */
@Data
@Table(name = "t_dis_mapping_room")
public class DisRoomMappingPO extends BasePO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 分销端酒店Id
     */
    private String disHotelId;

    /**
     * 分销端房型Id
     */
    private String disRoomId;

    /**
     * 分销商（OTA，分销端）
     */
    private String distributor;

    /**
     * 运营商编码
     */
    private String companyCode;
}
