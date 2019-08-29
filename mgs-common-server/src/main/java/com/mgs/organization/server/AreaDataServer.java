package com.mgs.organization.server;


import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.AreaDataDTO;
import com.mgs.organization.service.AreaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AreaDataServer {
    @Autowired
    private AreaDataService areaDataService;


    /**
     * 查询城市
     * @param request
     * @return
     */
    @PostMapping("/areaData/queryAreaData")
    Response queryAreaData(@RequestBody AreaDataDTO request){
        Response response = new Response();
        try{
            List<AreaDataDTO> areaDataDTOList=areaDataService.queryAreaData(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(areaDataDTOList);
        } catch (Exception e) {
            log.error("queryAreaData server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
