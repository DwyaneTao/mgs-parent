package com.mgs.fuzzyquery.mapper;


import com.mgs.common.MyMapper;
import com.mgs.fuzzyquery.dto.*;
import com.mgs.organization.domain.OrgPO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:39
 * @Description:
 */
@Component(value = "fuzzyQueryMapper")
public interface FuzzyQueryMapper extends MyMapper<OrgPO> {

    List<FuzzyCityDTO> fuzzyQueryCity(FuzzyQueryDTO fuzzyQueryDTO);

    List<FuzzySupplierDTO> fuzzyQuerySupplier(FuzzyQueryDTO fuzzyQueryDTO);

    List<FuzzyAgentDTO> fuzzyQueryAgent(FuzzyQueryDTO fuzzyQueryDTO);

    List<FuzzyHotelDTO> fuzzyQueryHotel(FuzzyQueryDTO fuzzyQueryDTO);

    List<FuzzyRoomDTO> fuzzyQueryRoom(FuzzyQueryDTO fuzzyQueryDTO);

    List<FuzzyProductDTO> fuzzyQueryProduct(FuzzyQueryDTO fuzzyQueryDTO);
}
