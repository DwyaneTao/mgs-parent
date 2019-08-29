package com.mgs.organization.service;



import com.mgs.organization.remote.dto.AreaDataDTO;

import java.util.List;

public interface AreaDataService {

    /**
     * 查询城市
     * @param
     * @return
     */
    List<AreaDataDTO> queryAreaData(AreaDataDTO areaDataDTO);


}
