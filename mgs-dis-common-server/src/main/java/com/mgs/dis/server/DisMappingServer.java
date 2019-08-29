package com.mgs.dis.server;

import com.mgs.common.Response;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.service.DisMappingService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 12:01
 * @Description: 映射server
 */
@RestController
@Slf4j
@RequestMapping("/mapping")
public class DisMappingServer {

    @Autowired
    private DisMappingService disMappingService;

    @RequestMapping("/queryProductMapping")
    public List<DisProductMappingDTO> queryProductMapping(@RequestBody DisMappingQueryDTO disMappingQueryDTO) {
        try {
            return disMappingService.queryProductMapping(disMappingQueryDTO);
        } catch (Exception e) {
            log.error("queryProductMapping-server error!",e);
        }
        return null;
    }

    @RequestMapping("/batchAddProductMapping")
    public Integer batchAddProductMapping(@RequestBody List<DisProductMappingDTO> disProductMappingDTOList) {
        try {
            return disMappingService.batchAddProductMapping(disProductMappingDTOList);
        }catch (Exception e) {
            log.error("batchAddProductMapping-server error!",e);
        }
        return 0;
    }
}
