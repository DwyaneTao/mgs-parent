package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.Requestor;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/15 15:32
 * @Description:
 */
@Data
public class CreateBasicRoomRequest extends BaseRequest {
    private Requestor requestor;
    private String updator;
    private int masterHotelId;
    private int childHotelId;
    private List<Integer> masterBasicRoomIds;
    private int supplierId;
    private String opClientIP;
    private String currency;
}
