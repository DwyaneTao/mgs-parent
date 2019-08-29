package com.mgs.organization.service.Impl;


import com.mgs.organization.remote.dto.AreaDataDTO;
import com.mgs.organization.mapper.AreaDataMapper;
import com.mgs.organization.service.AreaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AreaDataServiceImpl implements AreaDataService {

    @Autowired
    private AreaDataMapper areaDataMapper;



    @Override
    public  List<AreaDataDTO> queryAreaData(AreaDataDTO areaDataDTO) {
        List firstLetter = new ArrayList();
        int a = areaDataDTO.getFirstLetter().length();
        for (int i = 0; i < a; i++) {
            firstLetter.add(areaDataDTO.getFirstLetter().toUpperCase().charAt(i));
        }
        Map params = new HashMap();
        params.put("superId", areaDataDTO.getSuperId());
        if(a>0){
            params.put("firstLetter", firstLetter);
        }
        params.put("level", areaDataDTO.getLevel());
        params.put("dataName", areaDataDTO.getDataName());
        List<AreaDataDTO>  areaDataDTOList=   areaDataMapper.queryAreaData(params);
        return areaDataDTOList;
    }
}
