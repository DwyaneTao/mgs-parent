package com.mgs.organization.mapper;


import com.mgs.common.MyMapper;
import com.mgs.organization.domain.AreaDataPO;
import com.mgs.organization.remote.dto.AreaDataDTO;

import java.util.List;
import java.util.Map;


public interface AreaDataMapper  extends MyMapper<AreaDataPO> {


    List<AreaDataDTO>    queryAreaData(Map map);

}
