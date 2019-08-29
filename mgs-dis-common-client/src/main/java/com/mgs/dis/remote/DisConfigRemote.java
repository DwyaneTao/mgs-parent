package com.mgs.dis.remote;

import com.mgs.common.Response;
import com.mgs.dis.dto.DisConfigDTO;
import com.mgs.dis.dto.DisMappingQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:50
 * @Description: 分销配置
 */
@FeignClient(value = "mgs-dis-common-server")
public interface DisConfigRemote {

    @PostMapping("/config/queryConfigList")
    public List<DisConfigDTO> queryConfigList(@RequestParam(name = "channelCode") String channelCode);

}
