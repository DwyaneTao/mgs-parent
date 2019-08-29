package com.mgs.product.remote;

import com.mgs.common.Response;
import com.mgs.product.dto.BatchSaleDTO;
import com.mgs.product.dto.BatchSaleItemDTO;
import com.mgs.product.dto.ProductPriceQueryRequestDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 12:06
 * @Description: 产品销售
 */
@FeignClient(value = "mgs-product-server")
public interface ProductSaleRemote {

    @PostMapping("/sale/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    @PostMapping("/sale/querySalePriceList")
    public Response querySalePriceList(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    @PostMapping("/sale/singleProductModifySaleStatus")
    public Response singleProductModifySaleStatus(@RequestBody Map<String,String> requestMap);

    @PostMapping("/sale/dailyModifySalePrice")
    public Response dailyModifySalePrice(@RequestBody Map<String,String> requestMap);

    @PostMapping("/sale/batchModifySalePrice")
    public Response batchModifySalePrice(@RequestBody BatchSaleDTO batchSaleDTO);

    @PostMapping("/sale/batchModifySaleStatus")
    public Response batchModifySaleStatus(@RequestBody BatchSaleDTO batchSaleDTO);

    @PostMapping("/sale/queryOrderProductPrice")
    public Response  queryOrderProductPrice(@RequestBody QueryProductRequestDTO request);

    @PostMapping(value = "/sale/queryProductSaleLogList", produces = {"application/json;charset=UTF-8"})
    Response queryProductSaleLogList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/sale/resolveDayIncrease", produces = {"application/json;charset=UTF-8"})
    public Response resolveDayIncrease(@RequestBody Map<String,String> requestMap);
}
