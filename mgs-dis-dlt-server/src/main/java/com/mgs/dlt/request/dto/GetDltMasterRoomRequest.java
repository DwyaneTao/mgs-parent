package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.Requestor;
import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class GetDltMasterRoomRequest extends BaseRequest {

    private Requestor requestor;
    private Integer supplierID;
    private Integer hotelID;
}
