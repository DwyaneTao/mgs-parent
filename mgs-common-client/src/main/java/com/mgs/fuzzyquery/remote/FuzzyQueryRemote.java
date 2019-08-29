package com.mgs.fuzzyquery.remote;

import com.mgs.common.Response;
import com.mgs.fuzzyquery.dto.FuzzyQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:39
 * @Description: 模糊查询
 */
@FeignClient(value = "mgs-common-server")
public interface FuzzyQueryRemote {

    @PostMapping("/fuzzy/queryCity")
    public Response queryCity(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    @PostMapping("/fuzzy/querySupplier")
    public Response querySupplier(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    @PostMapping("/fuzzy/queryAgent")
    public Response queryAgent(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    @PostMapping("/fuzzy/queryHotel")
    public Response queryHotel(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    @PostMapping("/fuzzy/queryRoom")
    public Response queryRoom(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    @PostMapping("/fuzzy/queryProduct")
    public Response queryProduct(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    @PostMapping("/fuzzy/queryPurchaseManager")
    Response queryPurchaseManager(@RequestBody Map<String, String> requestMap);

    @PostMapping("/fuzzy/querySaleManager")
    Response querySaleManager(@RequestBody Map<String, String> requestMap);
}
