package com.mgs.dlt.response.dto;

import com.mgs.dlt.response.base.BaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 21:55
 * @Description:
 */
@Data
public class CreateBasicRoomResponse extends BaseResponse {
    private List<Integer> basicRoomIds;
}
