package com.mgs.product.server;


import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.domain.DebitedQuotaPO;
import com.mgs.product.dto.QuotaDTO;
import com.mgs.product.service.DebitedQuotaService;
import com.mgs.product.service.QuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/debitedQuota")
public class DebitedQuotaServer {

    @Autowired
    private DebitedQuotaService debitedQuotaService;



    /**
     * 查询配额记录
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryDebitedQuota")
    public Response queryDebitedQuota(@RequestBody Map<String,Integer> map) {
        Response response = null;
        try {
            response = debitedQuotaService.queryDebitedQuota(map);
        } catch (Exception e) {
            log.error("queryDebitedQuota-server error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


}
