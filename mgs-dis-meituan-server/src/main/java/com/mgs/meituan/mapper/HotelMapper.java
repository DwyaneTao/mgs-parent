package com.mgs.meituan.mapper;

import com.mgs.meituan.dto.hotel.request.PoiParamDTO;

import java.util.List;

public interface HotelMapper {

    /**
     * 查询酒店信息
     * @return
     */
    List<PoiParamDTO> queryHotelMapper();
}
