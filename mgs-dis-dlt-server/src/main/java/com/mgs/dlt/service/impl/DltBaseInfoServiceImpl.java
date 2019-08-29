package com.mgs.dlt.service.impl;

import com.mgs.dlt.domain.DltCityPO;
import com.mgs.dlt.domain.DltHotelPO;
import com.mgs.dlt.domain.DltMasterRoomPO;
import com.mgs.dlt.mapper.DltCityMapper;
import com.mgs.dlt.mapper.DltHotelMapper;
import com.mgs.dlt.mapper.DltMasterRoomMapper;
import com.mgs.dlt.service.DltBaseInfoService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 23:12
 * @Description:
 */
@Service
public class DltBaseInfoServiceImpl implements DltBaseInfoService  {

    @Autowired
    private DltCityMapper dltCityMapper;

    @Autowired
    private DltHotelMapper dltHotelMapper;

    @Autowired
    private DltMasterRoomMapper dltMasterRoomMapper;

    @Override
    public void batchInsertDltCityInfo(List<DltCityPO> dltCityPOList) {
        if (!CollectionUtils.isEmpty(dltCityPOList)) {
            dltCityMapper.insertList(dltCityPOList);
        }
    }

    @Override
    public void batchInsertDltHotelInfo(List<DltHotelPO> dltHotelPOList) {
        if (!CollectionUtils.isEmpty(dltHotelPOList)) {
            dltHotelMapper.insertList(dltHotelPOList);
        }
    }

    @Override
    public void batchInsertDltMasterRoomInfo(List<DltMasterRoomPO> dltMasterRoomPOList) {
        if (!CollectionUtils.isEmpty(dltMasterRoomPOList)) {
            dltMasterRoomMapper.insertList(dltMasterRoomPOList);
        }
    }

    @Override
    public List<DltHotelPO> queryAllDltHotel() {
        return dltHotelMapper.selectAll();
    }

    @Override
    public List<DltMasterRoomPO> queryAllDltMasterRoom() {
        return dltMasterRoomMapper.selectAll();
    }
}
