package com.mgs.product.server;


import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.dto.QuotaDTO;
import com.mgs.product.service.QuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/quota")
public class QuotaServer {

    @Autowired
    private QuotaService quotaService;

    /**
     * 修改配额
     *
     * @param
     * @return
     */
    @PostMapping(value = "/modifyQuota")
    public Response modifyQuota(@RequestBody QuotaDTO quotaDTO) {
        Response response = null;
        try {
            response = quotaService.modifyQuota(quotaDTO);
        } catch (Exception e) {
            log.error("modifyQuota-server error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


}
