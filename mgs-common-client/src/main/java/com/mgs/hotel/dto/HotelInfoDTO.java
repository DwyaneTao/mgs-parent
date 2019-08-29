package com.mgs.hotel.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 酒店信息
 */
@Data
public class HotelInfoDTO extends BaseDTO {

    /**
     * 酒店基本信息
     */
    private BasicHotelInfoDTO hotelBasicInfo;

    /**
     * 房型基本信息
     */
    private List<BasicRoomInfoDTO> roomInfoList;

    /**
     * 图片基本信息
     */
    private List<BasicPhotoDTO> photoList;
}
