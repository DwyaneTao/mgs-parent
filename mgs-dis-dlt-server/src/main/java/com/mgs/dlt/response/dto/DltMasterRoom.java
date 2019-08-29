package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:06
 * @Description:
 */
@Data
public class DltMasterRoom {

    private long hotelID;
    private long masterBasicRoomId;
    private String basicRoomName;
    private String basicRoomNameEn;
    private String isAdd;
}
