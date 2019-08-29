package com.mgs.hotel.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * 图片管理
 */
@Data
@Table(name = "t_baseinfo_photo")
public class BasicPhotoPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer photoId;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 图片类型
     */
    private Integer photoType;

    /**
     * 图片url
     */
    private String photoUrl;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 有效性
     */
    private Integer active;

    /**
     * 图片排序
     */
    private Integer PhotoRank;


}
