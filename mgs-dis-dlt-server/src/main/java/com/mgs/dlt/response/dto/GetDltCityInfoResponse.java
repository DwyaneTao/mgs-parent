package com.mgs.dlt.response.dto;

import com.mgs.dlt.response.base.BaseResponse;
import com.mgs.dlt.response.base.PagingInfo;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 21:58
 * @Description:
 */
@Data
public class GetDltCityInfoResponse extends BaseResponse{

    private DltCityInfoList cityInfoList;

    private PagingInfo pagingInfo;
}
