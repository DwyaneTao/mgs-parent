package com.mgs.dis.server;

import com.mgs.common.Response;
import com.mgs.dis.dto.DisConfigDTO;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.service.DisConfigService;
import com.mgs.dis.service.DisMappingService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/config")
public class DisConfigServer {

    @Autowired
    private DisConfigService disConfigService;

    @RequestMapping("/queryConfigList")
    public List<DisConfigDTO> queryConfigList(String channelCode) {
        try {
            return disConfigService.queryConfigList(channelCode);
        } catch (Exception e) {
            log.error("queryConfigList-server error!",e);
        }
        return null;
    }
}
