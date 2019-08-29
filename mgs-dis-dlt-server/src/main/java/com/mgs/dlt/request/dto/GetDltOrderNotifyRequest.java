package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.PagingType;
import com.mgs.dlt.request.base.Requestor;
import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class GetDltOrderNotifyRequest extends BaseRequest {

    private Requestor requestor;
    private PagingType pagingType;
    private Integer supplierID;

    private String startTime;
    private String endTime;

}
