package com.mgs.ebk.server;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.ebk.service.EbkProductService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductLogDTO;
import com.mgs.product.dto.ProductOrderQueryDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.service.ProductService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author py
 * @date 2019/8/5 18:33
 **/
@RestController
@Slf4j
@RequestMapping("/ebk/product")
public class EbkProductServer {
    @Autowired
    private EbkProductService ebkProductService;
    @Autowired
    private ProductService productService;
    /**
     * 根据供应商编码查询运营商编码
     * @param supplierCode
     * @return
     */
    @PostMapping(value = "/getCompanyCode")
    public String  getCompanyCode(@RequestBody String supplierCode) {
        String companyCode ="";
        try {
            companyCode   = ebkProductService.getCompanyCode(supplierCode);
        } catch (Exception e) {
            log.error("addProduct-server error!",e);
        }
        return companyCode;
    }


    /**
     * 新增产品
     * @param productDTO
     * @return
     */
    @PostMapping(value = "/addProduct")
    public Response addProduct(@RequestBody ProductDTO productDTO) {
        Response response = null;
        try {
            response = productService.addProduct(productDTO);
        } catch (Exception e) {
            log.error("addProduct-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/modifyProduct")
    public Response modifyProduct(@RequestBody ProductDTO productDTO) {
        Response response = null;
        try {
            response = productService.modifyProduct(productDTO);
        } catch (Exception e) {
            log.error("modifyProduct-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/deleteProduct")
    public Response deleteProduct(@RequestBody Map<String,String> requestMap) {
        Response response = null;
        try {
            response = productService.deleteProduct(requestMap);
        } catch (Exception e) {
            log.error("deleteProduct-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryProduct")
    public Response queryProduct(@RequestBody Map<String,String> requestMap) {
        Response response = null;
        try {
            response = productService.queryProduct(requestMap);
        } catch (Exception e) {
            log.error("queryProduct-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response = null;
        try {
            response = ebkProductService.queryHotelList(queryProductRequestDTO);
        } catch (Exception e) {
            log.error("queryHotelList-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryHotelProducts")
    public Response queryHotelProducts(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response = null;
        try {
            response = ebkProductService.queryHotelProducts(queryProductRequestDTO);
        } catch (Exception e) {
            log.error("queryHotelProducts-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/dailyModifyProductInfo")
    public Response dailyModifyProductInfo(@RequestBody ProductDayQuotationDTO productDayQuotationDTO) {
        Response response = null;
        try {
            response = productService.dailyModifyProductInfo(productDayQuotationDTO);
        } catch (Exception e) {
            log.error("dailyModifyProductInfo-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifyBasePrice")
    public Response batchModifyBasePrice(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList) {
        Response response = null;
        try {
            response = productService.batchModifyBasePrice(batchQuotationDTOList);
        } catch (Exception e) {
            log.error("batchModityPrice-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifyRoomStatus")
    public Response batchModifyRoomStatus(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList) {
        Response response = null;
        try {
            response = productService.batchModifyRoomStatus(batchQuotationDTOList);
        } catch (Exception e) {
            log.error("batchModifyRoomStatus-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    @PostMapping(value = "/queryProductLogList", produces = {"application/json;charset=UTF-8"})
    public Response queryProductLogList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("saleDate"))){

                PageInfo<ProductLogDTO> item = productService.queryProductLogList(requestMap);
                response.setResult(1);
                if(item != null){
                    PaginationSupportDTO<ProductLogDTO> supportDTO = new PaginationSupportDTO<ProductLogDTO>();
                    supportDTO.copyProperties(item, ProductLogDTO.class);
                    response.setModel(supportDTO);
                }
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
