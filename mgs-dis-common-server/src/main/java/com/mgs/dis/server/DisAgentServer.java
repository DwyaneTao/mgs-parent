package com.mgs.dis.server;

import com.mgs.common.Response;
import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisBaseQueryDTO;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.service.DisAgentService;
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
@RequestMapping("/agent")
public class DisAgentServer {

    @Autowired
    private DisAgentService disAgentService;

    @RequestMapping("/queryAgentList")
    public List<DisAgentDTO> queryAgentList() {
        try {
            return disAgentService.queryAgentList();
        } catch (Exception e) {
            log.error("queryAgentList-server error!",e);
        }
        return null;
    }

    @PostMapping("/queryAgentListByParam")
    public List<DisAgentDTO> queryAgentListByParam(@RequestBody DisBaseQueryDTO disBaseQueryDTO) {
        try {
            return disAgentService.queryAgentListByParam(disBaseQueryDTO);
        } catch (Exception e) {
            log.error("queryAgentListByParam-server error!",e);
        }
        return null;
    }
}
