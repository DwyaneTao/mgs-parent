package com.mgs.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.mgs.common.BaseController;
import com.mgs.common.BaseRequest;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.dto.*;
import com.mgs.product.remote.ProductSaleRemote;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 20:40
 * @Description:
 */
@RestController
@RequestMapping("/sale")
@Slf4j
public class ProductSaleController extends BaseController{

    @Autowired
    private ProductSaleRemote productSaleRemote;

    @PostMapping("/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response = null;
        try {
            if (null != queryProductRequestDTO && StringUtil.isValidString(queryProductRequestDTO.getChannelCode())) {
                queryProductRequestDTO.setCompanyCode(getCompanyCode());
                if (null != queryProductRequestDTO && null != queryProductRequestDTO.getPurchaseType()
                        && queryProductRequestDTO.getPurchaseType().equals(-1)) {
                    queryProductRequestDTO.setPurchaseType(null);
                }
                response = productSaleRemote.queryHotelList(queryProductRequestDTO);
            }else {
                log.error("querySalePriceList-controller error,params:{}", JSONObject.toJSONString(queryProductRequestDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("queryHotelList-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/querySalePriceList")
    public Response querySalePriceList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response = null;
        try {
            //参数校验：产品Id，渠道编码，开始日期，结束日期，结束日期>开始日期
            if (null != queryProductRequestDTO
                    && null != queryProductRequestDTO.getProductId()
                    && StringUtil.isValidString(queryProductRequestDTO.getChannelCode())
                    && StringUtil.isValidString(queryProductRequestDTO.getStartDate())
                    && StringUtil.isValidString(queryProductRequestDTO.getEndDate())
                    && DateUtil.compare(DateUtil.stringToDate(queryProductRequestDTO.getStartDate()),DateUtil.stringToDate(queryProductRequestDTO.getEndDate())) < 0 ) {
                queryProductRequestDTO.setCompanyCode(getCompanyCode());
                response = productSaleRemote.querySalePriceList(queryProductRequestDTO);
            }else {
                log.error("querySalePriceList-controller error,params:{}", JSONObject.toJSONString(queryProductRequestDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("querySalePriceList-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/singleProductModifySaleStatus")
    public Response singleProductModifySaleStatus(@RequestBody Map<String,String> requestMap) {
        Response response = null;
        try {
            //参数校验：产品Id，渠道编码，售卖状态
            if (null != requestMap
                    && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("saleStatus"))
                    && StringUtil.isValidString(requestMap.get("channelCode"))) {
                requestMap.put("companyCode",getCompanyCode());
                requestMap.put("modifiedBy",getUserName());
                requestMap.put("modifiedDt", DateUtil.getCurrentDateStr(1));
                response = productSaleRemote.singleProductModifySaleStatus(requestMap);
            }else {
                log.error("singleProductModifySaleStatus-controller error,params:{}", JSONObject.toJSONString(requestMap));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("singleProductModifySaleStatus-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/dailyModifySalePrice")
    public Response dailyModifySalePrice(@RequestBody Map<String,String> requestMap) {
        Response response = null;
        try {
            //参数校验：产品Id，日期，渠道编码，调整方式，调整金额
            if (null != requestMap
                    && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("saleDate"))
                    && StringUtil.isValidString(requestMap.get("adjustmentType"))
                    && StringUtil.isValidString(requestMap.get("modifiedAmt"))
                    && StringUtil.isValidString(requestMap.get("channelCode"))) {
                requestMap.put("companyCode",getCompanyCode());
                requestMap.put("modifiedBy",getUserName());
                requestMap.put("modifiedDt", DateUtil.getCurrentDateStr(1));
                response = productSaleRemote.dailyModifySalePrice(requestMap);
            }else {
                log.error("dailyModifySalePrice-controller error,params:{}", JSONObject.toJSONString(requestMap));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("dailyModifySalePrice-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifySalePrice")
    public Response batchModifySalePrice(@RequestBody BatchSaleDTO batchSaleDTO) {
        Response response = null;
        try {
            Boolean checkParamFlag = true;
            //参数校验：渠道编码，每个对象的产品Id，开始时间，结束时间，调整方式，调整金额
            if (null != batchSaleDTO
                    && StringUtil.isValidString(batchSaleDTO.getChannelCode())
                    && !CollectionUtils.isEmpty(batchSaleDTO.getItemList())) {
                for (BatchSaleItemDTO batchSaleItemDTO : batchSaleDTO.getItemList()) {
                    if (null == batchSaleItemDTO.getProductId()
                            || !StringUtil.isValidString(batchSaleItemDTO.getStartDate())
                            || !StringUtil.isValidString(batchSaleItemDTO.getEndDate())
                            || DateUtil.compare(DateUtil.stringToDate(batchSaleItemDTO.getStartDate()),DateUtil.stringToDate(batchSaleItemDTO.getEndDate())) > 1
                            || null == batchSaleItemDTO.getAdjustmentType()
                            || null == batchSaleItemDTO.getModifiedAmt()) {
                        checkParamFlag = false;
                    }
                }
                batchSaleDTO.setModifiedBy(getUserName());
                batchSaleDTO.setModifiedDt(DateUtil.getCurrentDateStr(1));
                batchSaleDTO.setCompanyCode(getCompanyCode());
                if (checkParamFlag) {
                    response = productSaleRemote.batchModifySalePrice(batchSaleDTO);
                }else {
                    log.error("batchModifySalePrice-controller error,params:{}", JSONObject.toJSONString(batchSaleDTO));
                    response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                }
            }else {
                log.error("batchModifySalePrice-controller error,params:{}", JSONObject.toJSONString(batchSaleDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("batchModifySalePrice-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifySaleStatus")
    public Response batchModifySaleStatus(@RequestBody BatchSaleDTO batchSaleDTO) {
        Response response = null;
        try {
            Boolean checkParamFlag = true;
            //参数校验：渠道编码，售卖状态，每个对象的产品Id
            if (null != batchSaleDTO
                    && StringUtil.isValidString(batchSaleDTO.getChannelCode())
                    && null != batchSaleDTO.getSaleStatus()
                    && !CollectionUtils.isEmpty(batchSaleDTO.getItemList())) {
                for (BatchSaleItemDTO batchSaleItemDTO : batchSaleDTO.getItemList()) {
                    if (null == batchSaleItemDTO.getProductId()) {
                        checkParamFlag = false;
                    }
                }
                batchSaleDTO.setModifiedBy(getUserName());
                batchSaleDTO.setModifiedDt(DateUtil.getCurrentDateStr(1));
                batchSaleDTO.setCompanyCode(getCompanyCode());
                if (checkParamFlag) {
                    response = productSaleRemote.batchModifySaleStatus(batchSaleDTO);
                }else {
                    log.error("batchModifySalePrice-controller error,params:{}", JSONObject.toJSONString(batchSaleDTO));
                    response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                }
            }else {
                log.error("batchModifySaleStatus-controller error,params:{}", JSONObject.toJSONString(batchSaleDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("batchModifySaleStatus-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    @RequestMapping(value = "/queryOrderProductPrice",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryOrderProductPrice(@RequestBody QueryProductRequestDTO request){
        Response response = new Response();
        try{
            request.setCompanyCode(getCompanyCode());
            if (StringUtil.isValidString(request.getChannelCode())
                    &&request.getProductId()!=null
                    &&request.getStartDate()!=null
                    &&request.getEndDate()!=null
            ){
                if(request.getEndDate()!=null){
                    request.setEndDate(DateUtil.dateToString(DateUtil.getDate(DateUtil.stringToDate(request.getEndDate()),-1,0)));
                }
                    response = productSaleRemote.queryOrderProductPrice(request);
                }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("producePriceQuery server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/queryProductSaleLogList", produces = {"application/json;charset=UTF-8"})
    public Response queryProductLogList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("productId"))
                   && StringUtil.isValidString(requestMap.get("operationType"))){
                requestMap.put("companyCode", getCompanyCode());
                if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                    requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
                }
                if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                    requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
                }


                response = productSaleRemote.queryProductSaleLogList(requestMap);
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

