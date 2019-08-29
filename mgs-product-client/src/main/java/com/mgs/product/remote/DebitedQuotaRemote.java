package com.mgs.product.remote;


import com.mgs.common.Response;
import com.mgs.product.dto.QuotaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "mgs-product-server")
public interface DebitedQuotaRemote {

    @PostMapping("/debitedQuota/queryDebitedQuota")
    public Response queryDebitedQuota(@RequestBody Map<String,Integer> map);

}
