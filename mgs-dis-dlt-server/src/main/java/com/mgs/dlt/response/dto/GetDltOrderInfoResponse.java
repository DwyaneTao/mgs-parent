package com.mgs.dlt.response.dto;

import com.mgs.dlt.response.base.BaseResponse;
import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class GetDltOrderInfoResponse extends BaseResponse {

    private DltOrderInfo dltOrderInfo;
}
