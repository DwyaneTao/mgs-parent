package com.mgs.product.remote;


import com.mgs.common.Response;
import com.mgs.product.dto.QuotaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-product-server")
public interface QuotaRemote {


    @PostMapping("/quota/modifyQuota")
    public Response  modifyQuota(@RequestBody QuotaDTO request);


}
