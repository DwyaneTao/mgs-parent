package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/19 14:32
 * @Description:
 */
@Data
public class RoomStaticInfo {
    private String supplierInfo;
    private RoomTypeInfos roomTypeInfos;
    private RoomInfos roomInfos;
}
