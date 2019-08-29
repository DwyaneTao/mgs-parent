package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.PagingSettings;
import com.mgs.dlt.request.base.Requestor;
import com.mgs.dlt.request.base.SearchCandidate;
import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class GetDltCityInfoRequest extends BaseRequest {

    private Requestor requestor;
    private Integer supplierID;
    private SearchCandidate searchCandidate;
    private PagingSettings pagingSettings;
}
