package com.mgs.hotel.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

/**
 * 酒店图片dto
 */
@Data
public class BasicPhotoDTO extends BaseDTO {

    /**
     * 图片id
     */
    private Integer photoId;

    /**
     * 图片url
     */
    private String photoUrl;

    /**
     * 图片名称
     */
    private String photoName;

    /**
     * 图片类型
     */
    private Integer photoType;


    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 房型id
     */
    private Integer roomId;

    /**
     * 操作账号
     */
    private String operatorAccount;
}
