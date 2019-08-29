package com.mgs.dlt.response.dto;

import com.mgs.dlt.response.base.BaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:04
 * @Description:
 */
@Data
public class GetDltHotelRoomStaticInfoResponse extends BaseResponse {

    private RoomStaticInfos roomStaticInfos;
}
