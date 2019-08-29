package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.Requestor;
import lombok.Data;

import java.util.List;

/**
 *   2018/4/8.
 */
@Data
public class GetDltHotelRoomStaticInfoRequest extends BaseRequest {

    private Requestor requestor;
    private int supplierId;
    private long hotelId;
    private List<String> returnDataTypeList;
}
