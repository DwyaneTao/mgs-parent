package com.mgs.dlt.service;

import com.mgs.dis.dto.DisProductMappingDTO;

import java.util.Date;
import java.util.List;

/**
 *   2018/4/9.
 */
public interface BatchPushRoomDataService {

    void pushRoomDataToDltByPricePlan(Long pricePlanId, Date checkInDate, Date checkOutDate);

    void pushRoomDataToDltByMapRoomList(List<DisProductMappingDTO> dltMapRoomPOList, Date checkInDate, Date checkOutDate, String merchantCode) ;

}
