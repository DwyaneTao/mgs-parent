package com.mgs.dlt.server;

import com.mgs.common.Response;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dlt.service.DltOrderService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 12:01
 * @Description: 映射server
 */
@RestController
@Slf4j
@RequestMapping("/dlt")
public class DltOrderServer {

    @Autowired
    private DltOrderService dltOrderService;

    @RequestMapping("/queryProductMapping")
    public Response queryProductMapping(@RequestBody DisMappingQueryDTO disMappingQueryDTO) {
        Response response = null;
        try {
//            response = disMappingService.queryProductMapping(disMappingQueryDTO);
        } catch (Exception e) {
            log.error("queryProductMapping-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
