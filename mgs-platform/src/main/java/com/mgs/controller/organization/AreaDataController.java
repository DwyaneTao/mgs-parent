package com.mgs.controller.organization;


import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.AreaDataRemote;
import com.mgs.organization.remote.dto.AreaDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/areaData")
public class AreaDataController {

    @Autowired
    private AreaDataRemote areaDataRemote;

    /**
     * 查询城市
     */
    @RequestMapping(value = "/queryAreaData",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryAreaData(@RequestBody AreaDataDTO request){
        Response response = new Response();
        try{
            response= areaDataRemote.queryAreaData(request);
        } catch (Exception e) {
            log.error("queryAreaData server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


}
