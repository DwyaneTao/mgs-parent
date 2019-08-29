package com.mgs.organization.remote;


import com.mgs.common.Response;
import com.mgs.organization.remote.dto.AreaDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "mgs-common-server")
public interface AreaDataRemote {

    /**
     * 添加客户信息
     */
    @PostMapping("/areaData/queryAreaData")
    Response queryAreaData(AreaDataDTO areaDataDTO);
}
