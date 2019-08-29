package com.mgs.dlt.response.dto;

import com.mgs.dlt.request.base.Pager;
import com.mgs.dlt.response.base.BaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 21:59
 * @Description:
 */
@Data
public class GetDltHotelResponse extends BaseResponse {

    private List<DltHotel> dltHotelEntityList;

    private Pager pager;
}
