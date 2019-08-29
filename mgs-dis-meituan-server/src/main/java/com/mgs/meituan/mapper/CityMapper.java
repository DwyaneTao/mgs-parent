package com.mgs.meituan.mapper;

import com.mgs.common.MyMapper;
import com.mgs.meituan.domain.MeituanCityPO;
import com.mgs.meituan.dto.city.response.CityDTO;

import java.util.List;

/**
 * 初始化美团City映射所用的mapper
 */
public interface CityMapper extends MyMapper<MeituanCityPO> {

    /**
     * 查询城市列表
     * @return
     */
    List<CityDTO> queryCityList();
}
