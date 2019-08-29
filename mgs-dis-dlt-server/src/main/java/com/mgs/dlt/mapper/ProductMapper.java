package com.mgs.dlt.mapper;


import com.mgs.dlt.request.dto.QueryHotelProductDetailRequest;
import com.mgs.dlt.response.dto.DltNeedPushSaleRoomDTO;
import com.mgs.dlt.response.dto.QueryHotelProductDetailResponse;

import java.util.List;

public interface ProductMapper {

    List<QueryHotelProductDetailResponse> selectProductDetail(QueryHotelProductDetailRequest queryHotelProductDetailRequest);

    Integer queryNeedPushSaleRoomCount(String companyCode);

    List<DltNeedPushSaleRoomDTO> queryNeedPushSaleRoom(String companyCode);
}