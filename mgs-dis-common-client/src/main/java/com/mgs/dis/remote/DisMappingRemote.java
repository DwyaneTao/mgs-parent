package com.mgs.dis.remote;

import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:50
 * @Description: 映射
 */
@FeignClient(value = "mgs-dis-common-server")
public interface DisMappingRemote {

    @PostMapping("/mapping/queryProductMapping")
    public List<DisProductMappingDTO> queryProductMapping(@RequestBody DisMappingQueryDTO disMappingQueryDTO);

    @PostMapping("/mapping/batchAddProductMapping")
    public Integer batchAddProductMapping(@RequestBody List<DisProductMappingDTO> disProductMappingDTOList);
}
