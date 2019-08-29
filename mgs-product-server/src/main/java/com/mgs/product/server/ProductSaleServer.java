package com.mgs.product.server;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.dto.BatchSaleDTO;
import com.mgs.product.dto.ProductLogDTO;
import com.mgs.product.dto.ProductPriceQueryRequestDTO;
import com.mgs.product.dto.ProductSaleLogDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.service.ProductSaleService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:32
 * @Description: 产品售卖server
 */
@RestController
@Slf4j
@RequestMapping("/sale")
public class ProductSaleServer {

    @Autowired
    private ProductSaleService productSaleService;

    @PostMapping("/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response;
        try {
            response = productSaleService.queryHotelList(queryProductRequestDTO);
        } catch (Exception e) {
            log.error("queryHotelList-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/querySalePriceList")
    public Response querySalePriceList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response;
        try {
            response = productSaleService.querySalePriceList(queryProductRequestDTO);
        } catch (Exception e) {
            log.error("queryHotelList-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/singleProductModifySaleStatus")
    public Response singleProductModifySaleStatus(@RequestBody Map<String,String> requestMap) {
        Response response;
        try {
            response = productSaleService.singleProductModifySaleStatus(requestMap);
        } catch (Exception e) {
            log.error("singleProductModifySaleStatus-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/dailyModifySalePrice")
    public Response dailyModifySalePrice(@RequestBody Map<String,String> requestMap) {
        Response response;
        try {
            response = productSaleService.dailyModifySalePrice(requestMap);
        } catch (Exception e) {
            log.error("dailyModifySalePrice-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifySalePrice")
    public Response batchModifySalePrice(@RequestBody BatchSaleDTO batchSaleDTO) {
        Response response;
        try {
            response = productSaleService.batchModifySalePrice(batchSaleDTO);
        } catch (Exception e) {
            log.error("batchModifySalePrice-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifySaleStatus")
    public Response batchModifySaleStatus(@RequestBody BatchSaleDTO batchSaleDTO) {
        Response response;
        try {
            response = productSaleService.batchModifySaleStatus(batchSaleDTO);
        } catch (Exception e) {
            log.error("batchModifySaleStatus-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryOrderProductPrice")
    public Response  queryOrderProductPrice(@RequestBody QueryProductRequestDTO request) {
        Response response;
        try {
            response = productSaleService.queryOrderProductPrice(request);
        } catch (Exception e) {
            log.error("producePriceQuery-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/queryProductSaleLogList", produces = {"application/json;charset=UTF-8"})
    public Response queryProductSaleLogList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("operationType"))){

                PageInfo<ProductSaleLogDTO> item = productSaleService.queryProductSaleLogList(requestMap);
                response.setResult(1);
                if(item != null){
                    PaginationSupportDTO<ProductSaleLogDTO> supportDTO = new PaginationSupportDTO<ProductSaleLogDTO>();
                    supportDTO.copyProperties(item, ProductSaleLogDTO.class);
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


    @PostMapping(value = "/resolveDayIncrease", produces = {"application/json;charset=UTF-8"})
    public Response resolveDayIncrease(@RequestBody Map<String,String> requestMap) {
        Response response;
        try {
            if (null != requestMap && StringUtil.isValidString(requestMap.get("startDate"))
                    && StringUtil.isValidString(requestMap.get("endDate"))) {
                response = productSaleService.resolveDayIncrease(requestMap.get("startDate"),requestMap.get("endDate"));
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
