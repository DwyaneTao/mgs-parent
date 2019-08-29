package com.mgs.ebk.remote;

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
public interface EbkProductRemote {

    @PostMapping("/ebk/product/getCompanyCode")
    public String  getCompanyCode(@RequestBody String supplierCode);
    @PostMapping("/ebk/product/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    @PostMapping("/ebk/product/queryHotelProducts")
    public Response queryHotelProducts(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    @PostMapping("/ebk/product/addProduct")
    public Response addProduct(@RequestBody ProductDTO productDTO);

    @PostMapping("/ebk/product/queryProduct")
    public Response queryProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping("/ebk/product/modifyProduct")
    public Response modifyProduct(@RequestBody ProductDTO productDTO);

    @PostMapping("/ebk/product/deleteProduct")
    public Response deleteProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping("/ebk/product/dailyModifyProductInfo")
    public Response dailyModifyProductInfo(@RequestBody ProductDayQuotationDTO productDayQuotationDTO);

    @PostMapping("/ebk/product/batchModifyBasePrice")
    public Response batchModifyBasePrice(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList);

    @PostMapping("/ebk/product/batchModifyRoomStatus")
    public Response batchModifyRoomStatus(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList);

    @PostMapping("/ebk/product/queryOrderProductList")
    public Response queryOrderProductList(ProductOrderQueryRequestDTO request);

    @PostMapping(value = "/ebk/product/queryProductLogList", produces = {"application/json;charset=UTF-8"})
    Response queryProductLogList(@RequestBody Map<String, String> requestMap);

}
