package com.mgs.dis.service;

import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:57
 * @Description: 映射
 */
public interface DisMappingService {

    /**
     * 查询产品映射
     * @param disMappingQueryDTO
     * @return
     */
    List<DisProductMappingDTO> queryProductMapping(DisMappingQueryDTO disMappingQueryDTO);

    /**
     * 新增产品映射
     * @param disProductMappingDTOList
     * @return
     */
    Integer batchAddProductMapping(@RequestBody List<DisProductMappingDTO> disProductMappingDTOList);
}
