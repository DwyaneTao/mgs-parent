package com.mgs.dlt.response.dto;

import com.mgs.dlt.request.base.PagingType;
import com.mgs.dlt.response.base.BaseResponse;
import lombok.Data;

import java.util.List;

/**
 *   2018/4/8.
 */
@Data
public class GetDltOrderNotifyResponse extends BaseResponse {

    private List<String> dltOrderIds;
    private PagingType pagingType;

}
