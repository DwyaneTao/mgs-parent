package com.mgs.product.remote;

import com.mgs.common.Response;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 12:06
 * @Description: 产品
 */
@FeignClient(value = "mgs-product-server")
public interface ProductRemote {

    @PostMapping("/product/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    @PostMapping("/product/queryHotelProducts")
    public Response queryHotelProducts(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    @PostMapping("/product/addProduct")
    public Response addProduct(@RequestBody ProductDTO productDTO);

    @PostMapping("/product/queryProduct")
    public Response queryProduct(@RequestBody Map<String,String> requestMap);

    @PostMapping("/product/modifyProduct")
    public Response modifyProduct(@RequestBody ProductDTO productDTO);

    @PostMapping("/product/deleteProduct")
    public Response deleteProduct(@RequestBody Map<String,String> requestMap);

    @PostMapping("/product/dailyModifyProductInfo")
    public Response dailyModifyProductInfo(@RequestBody ProductDayQuotationDTO productDayQuotationDTO);

    @PostMapping("/product/batchModifyBasePrice")
    public Response batchModifyBasePrice(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList);

    @PostMapping("/product/batchModifyRoomStatus")
    public Response batchModifyRoomStatus(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList);

    @PostMapping("/product/queryOrderProductList")
    public Response queryOrderProductList(ProductOrderQueryRequestDTO request);

    @PostMapping(value = "/product/queryProductLogList", produces = {"application/json;charset=UTF-8"})
    Response queryProductLogList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/product/modifyOffShelveStatus", produces = {"application/json;charset=UTF-8"})
    Response modifyOffShelveStatus(@RequestBody Map<String, String> requestMap);

}
