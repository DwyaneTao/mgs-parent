package com.mgs.controller.fuzzy;

import com.alibaba.fastjson.JSONObject;
import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.fuzzyquery.dto.FuzzyQueryDTO;
import com.mgs.fuzzyquery.remote.FuzzyQueryRemote;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.remote.ProductRemote;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 20:40
 * @Description: 产品
 */
@RestController
@RequestMapping("/fuzzy")
@Slf4j
public class FuzzyQueryController extends BaseController {

    @Autowired
    private FuzzyQueryRemote fuzzyQueryRemote;

    @PostMapping("/queryCity")
    public Response queryCity(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryRemote.queryCity(fuzzyQueryDTO);
        }catch (Exception e) {
            log.error("queryCity-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/querySupplier")
    public Response querySupplier(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            fuzzyQueryDTO.setCompanyCode(getCompanyCode());
            response = fuzzyQueryRemote.querySupplier(fuzzyQueryDTO);
        }catch (Exception e) {
            log.error("querySupplier-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryAgent")
    public Response queryAgent(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            fuzzyQueryDTO.setCompanyCode(getCompanyCode());
            response = fuzzyQueryRemote.queryAgent(fuzzyQueryDTO);
        }catch (Exception e) {
            log.error("queryAgent-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryHotel")
    public Response queryHotel(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryRemote.queryHotel(fuzzyQueryDTO);
        }catch (Exception e) {
            log.error("queryHotel-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryRoom")
    public Response queryRoom(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            if (null != fuzzyQueryDTO
                    && null != fuzzyQueryDTO.getHotelId()) {
                response = fuzzyQueryRemote.queryRoom(fuzzyQueryDTO);
            }else {
                log.error("queryRoom-controller error,params:{}", JSONObject.toJSONString(fuzzyQueryDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("queryRoom-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryProduct")
    public Response queryProduct(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            if (null != fuzzyQueryDTO
                    && null != fuzzyQueryDTO.getHotelId()
                    && null != fuzzyQueryDTO.getRoomId()) {
                fuzzyQueryDTO.setCompanyCode(getCompanyCode());
                response = fuzzyQueryRemote.queryProduct(fuzzyQueryDTO);
            }else {
                log.error("queryProduct-controller error,params:{}", JSONObject.toJSONString(fuzzyQueryDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("queryProduct-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取采购经理列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryPurchaseManager", produces = {"application/json;charset=UTF-8"})
    public Response queryPurchaseManager(@RequestBody Map<String,String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("orgCode", super.getCompanyCode());
            response =  fuzzyQueryRemote.queryPurchaseManager(requestMap);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取销售经理列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/querySaleManager", produces = {"application/json;charset=UTF-8"})
    public Response querySaleManager(@RequestBody Map<String,String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("orgCode", super.getCompanyCode());
            response = fuzzyQueryRemote.querySaleManager(requestMap);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}

