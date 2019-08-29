package com.mgs.dis.remote;

import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisBaseQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:50
 * @Description: 分销对应客户
 */
@FeignClient(value = "mgs-dis-common-server")
public interface DisAgentRemote {

    @PostMapping("/agent/queryAgentList")
    public List<DisAgentDTO> queryAgentList();

    @PostMapping("/agent/queryAgentListByParam")
    public List<DisAgentDTO> queryAgentListByParam(@RequestBody DisBaseQueryDTO disBaseQueryDTO);
}
