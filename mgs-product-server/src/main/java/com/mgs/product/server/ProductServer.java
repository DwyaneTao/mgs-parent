package com.mgs.product.server;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.product.domain.ProductPO;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.domain.QuotaAccountPO;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductLogDTO;
import com.mgs.product.dto.ProductOrderQueryDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.service.ProductService;
import com.mgs.util.BeanUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:32
 * @Description: 产品server
 */
@RestController
@Slf4j
@RequestMapping("/product")
public class ProductServer {

    @Autowired
    private ProductService productService;

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
            response = productService.queryHotelList(queryProductRequestDTO);
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
            response = productService.queryHotelProducts(queryProductRequestDTO);
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

    @PostMapping("/queryOrderProductList")
    public Response queryOrderProductList(@RequestBody ProductOrderQueryRequestDTO request) {
        Response response = new Response();
        try {
            PaginationSupportDTO<ProductOrderQueryDTO> productOrderQueryDTOPaginationSupportDTO = productService.queryOrderProductList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(productOrderQueryDTOPaginationSupportDTO);
        } catch (Exception e) {
            log.error("queryProductOrderList-server error!",e);
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
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/modifyOffShelveStatus", produces = {"application/json;charset=UTF-8"})
    public Response modifyOffShelveStatus(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("offShelveStatus"))){

                int i = productService.modifyOffShelveStatus(requestMap);
                response.setResult(1);
                response.setModel(i);
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
