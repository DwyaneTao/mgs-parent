package com.mgs.dlt.service;

import com.mgs.dlt.domain.DltCityPO;
import com.mgs.dlt.domain.DltHotelPO;
import com.mgs.dlt.domain.DltMasterRoomPO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 23:10
 * @Description:
 */
public interface DltBaseInfoService {

    void batchInsertDltCityInfo(List<DltCityPO> dltCityPOList);

    void batchInsertDltHotelInfo(List<DltHotelPO> dltHotelPOList);

    void batchInsertDltMasterRoomInfo(List<DltMasterRoomPO> dltMasterRoomPOList);

    List<DltHotelPO> queryAllDltHotel();

    List<DltMasterRoomPO> queryAllDltMasterRoom();
}
