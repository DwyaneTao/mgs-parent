package com.mgs.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.mgs.common.BaseController;
import com.mgs.common.BaseRequest;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
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
 * @Description: 产品
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController {

    @Autowired
    private ProductRemote productRemote;

    @PostMapping("/queryHotelList")
    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response = null;
        try {
            queryProductRequestDTO.setCompanyCode(getCompanyCode());
            if(queryProductRequestDTO.getOffShelveProduct() != null && queryProductRequestDTO.getOffShelveProduct() == -1){
                queryProductRequestDTO.setOffShelveProduct(null);
            }
            response = productRemote.queryHotelList(queryProductRequestDTO);
        }catch (Exception e) {
            log.error("queryProductList-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryHotelProducts")
    public Response queryHotelProducts(@RequestBody QueryProductRequestDTO queryProductRequestDTO){
        Response response = null;
        try {
            //参数校验：酒店Id，开始日期，结束日期
            if (null != queryProductRequestDTO
                    && null != queryProductRequestDTO.getHotelId()
                    && StringUtil.isValidString(queryProductRequestDTO.getStartDate())
                    && StringUtil.isValidString(queryProductRequestDTO.getEndDate())) {
                queryProductRequestDTO.setCompanyCode(getCompanyCode());
                response = productRemote.queryHotelProducts(queryProductRequestDTO);
            }else {
                log.error("queryHotelProducts-controller error,params:{}", JSONObject.toJSONString(queryProductRequestDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("queryHotelProducts-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/addProduct")
    public Response addProduct(@RequestBody ProductDTO productDTO){
        Response response = null;
        try {
            //参数校验：酒店Id，房型Id，早餐，采购类型，币种，产品名称，供应商，床型
            if (null != productDTO
                    && null != productDTO.getHotelId()
                    && null != productDTO.getRoomId()
                    && null != productDTO.getBreakfastQty()
                    && null != productDTO.getPurchaseType()
                    && null != productDTO.getCurrency()
                    && StringUtil.isValidString(productDTO.getProductName())
                    && StringUtil.isValidString(productDTO.getSupplierCode())
                    && StringUtil.isValidString(productDTO.getBedTypes())) {
                if (null == productDTO.getCurrency()) {
                    productDTO.setCurrency(0);//默认人民币
                }
                productDTO.setCreatedBy(getUserName());
                productDTO.setCreatedDt(DateUtil.getCurrentDateStr(1));
                productDTO.setCompanyCode(getCompanyCode());
                response = productRemote.addProduct(productDTO);
            }else {
                log.error("addProduct-controller error,params:{}", JSONObject.toJSONString(productDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("addProduct-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryProduct")
    public Response queryProduct(@RequestBody Map<String,String> requestMap){
        Response response = null;
        try {
            //参数校验：产品Id
            if (null != requestMap
                    && StringUtil.isValidString(requestMap.get("productId"))) {
                response = productRemote.queryProduct(requestMap);
            }else {
                log.error("queryProduct-controller error,params:{}", JSONObject.toJSONString(requestMap));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("queryProduct-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/modifyProduct")
    public Response modifyProduct(@RequestBody ProductDTO productDTO){
        Response response = null;
        try {
            //参数校验：产品Id
            if (null != productDTO
                    && null != productDTO.getProductId()) {
                productDTO.setModifiedBy(getUserName());
                productDTO.setModifiedDt(DateUtil.getCurrentDateStr(1));
                response = productRemote.modifyProduct(productDTO);
            }else {
                log.error("modifyProduct-controller error,params:{}", JSONObject.toJSONString(productDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("modifyProduct-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/deleteProduct")
    public Response deleteProduct(@RequestBody Map<String,String> requestMap){
        Response response = null;
        try {
            //参数校验：产品Id
            if (null != requestMap
                    && StringUtil.isValidString(requestMap.get("productId"))) {
                requestMap.put("modifiedBy",getUserName());
                requestMap.put("modifiedDt",DateUtil.getCurrentDateStr(1));
                requestMap.put("companyCode",getCompanyCode());
                response = productRemote.deleteProduct(requestMap);
            }else {
                log.error("deleteProduct-controller error,params:{}", JSONObject.toJSONString(requestMap));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("deleteProduct-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/dailyModifyProductInfo")
    public Response dailyModifyProductInfo(@RequestBody ProductDayQuotationDTO productDayQuotationDTO){
        Response response = null;
        try {
            //参数校验：产品Id，日期
            if (null != productDayQuotationDTO
                    && null != productDayQuotationDTO.getProductId()
                    && StringUtil.isValidString(productDayQuotationDTO.getSaleDate())) {
                productDayQuotationDTO.setModifiedBy(getUserName());
                productDayQuotationDTO.setModifiedDt(DateUtil.getCurrentDateStr(1));
                response = productRemote.dailyModifyProductInfo(productDayQuotationDTO);
            }else {
                log.error("dailyModifyProductInfo-controller error,params:{}", JSONObject.toJSONString(productDayQuotationDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("dailyModifyProductInfo-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifyBasePrice")
    public Response batchModifyBasePrice(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList){
        Response response = null;
        try {
            Boolean checkParamFlag = true;
            //参数校验：每个对象的产品Id
            if (!CollectionUtils.isEmpty(batchQuotationDTOList)) {
                for (BatchQuotationDTO batchQuotationDTO : batchQuotationDTOList) {
                    if (null == batchQuotationDTO.getProductId()) {
                        checkParamFlag = false;
                    }
                    batchQuotationDTO.setModifiedBy(getUserName());
                    batchQuotationDTO.setModifiedDt(DateUtil.getCurrentDateStr(1));
                }
                if (checkParamFlag) {
                    response = productRemote.batchModifyBasePrice(batchQuotationDTOList);
                }else {
                    log.error("batchModityPrice-controller error,params:{}", JSONObject.toJSONString(batchQuotationDTOList));
                    response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                }
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("batchModityPrice-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/batchModifyRoomStatus")
    public Response batchModifyRoomStatus(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList){
        Response response = null;
        try {
            Boolean checkParamFlag = true;
            //参数校验：每个对象的产品Id
            if (!CollectionUtils.isEmpty(batchQuotationDTOList)) {
                for (BatchQuotationDTO batchQuotationDTO : batchQuotationDTOList) {
                    if (null == batchQuotationDTO.getProductId()) {
                        checkParamFlag = false;
                    }
                    batchQuotationDTO.setModifiedBy(getUserName());
                    batchQuotationDTO.setModifiedDt(DateUtil.getCurrentDateStr(1));
                }
                if (checkParamFlag) {
                    response = productRemote.batchModifyRoomStatus(batchQuotationDTOList);
                }else {
                    log.error("batchModifyRoomStatus-controller error,params:{}", JSONObject.toJSONString(batchQuotationDTOList));
                    response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                }
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("batchModifyRoomStatus-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    @RequestMapping(value = "/queryOrderProductList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryOrderProductList(@RequestBody ProductOrderQueryRequestDTO request){
        Response response = new Response();
        try{
            request.setCompanyCode(getCompanyCode());
            if (request.getHotelId()!=null
                    &&request.getRoomId()!=null){
                if(!StringUtil.isValidString(request.getEndDate()) || !StringUtil.isValidString(request.getStartDate())){
                    request.setStartDate(null);
                    request.setEndDate(null);
                }else {
                    request.setEndDate(DateUtil.dateToString(DateUtil.getDate(DateUtil.stringToDate(request.getEndDate()),-1,0)));
                }
                response = productRemote.queryOrderProductList(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("queryProductOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/queryProductLogList", produces = {"application/json;charset=UTF-8"})
    public Response queryProductLogList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("saleDate"))){
                if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                    requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
                }
                if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                    requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
                }


                response = productRemote.queryProductLogList(requestMap);
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

    @PostMapping(value = "/modifyOffShelveStatus", produces = {"application/json;charset=UTF-8"})
    public Response modifyOffShelveStatus(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("productId"))
                    && StringUtil.isValidString(requestMap.get("offShelveStatus"))){
                response =  productRemote.modifyOffShelveStatus(requestMap);
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


}

