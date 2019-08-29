package com.mgs.dlt.service;

import com.mgs.dlt.response.dto.DltNeedPushSaleRoomDTO;

/**
 * @Auther: Owen
 * @Date: 2019/8/15 21:55
 * @Description:
 */
public interface DltRoomService {

    Integer queryNeedPushSaleRoomCount(String companyCode);

    void pushSaleRoom(String companyCode);
}
