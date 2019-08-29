package com.mgs.fuzzyquery.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.fuzzyquery.dto.FuzzyQueryDTO;
import com.mgs.fuzzyquery.service.FuzzyQueryService;
import com.mgs.user.dto.PurchaseManagerDTO;
import com.mgs.user.dto.SaleManagerDTO;
import com.mgs.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 12:11
 * @Description:模糊查询server
 */
@RestController
@Slf4j
@RequestMapping("/fuzzy")
public class FuzzyQueryServer {

    @Autowired
    private FuzzyQueryService fuzzyQueryService;

    @Autowired
    private UserService userService;

    @PostMapping("/queryCity")
    public Response queryCity(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryService.queryCity(fuzzyQueryDTO);
        } catch (Exception e) {
            log.error("queryCity-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/querySupplier")
    public Response querySupplier(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryService.querySupplier(fuzzyQueryDTO);
        } catch (Exception e) {
            log.error("querySupplier-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryAgent")
    public Response queryAgent(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryService.queryAgent(fuzzyQueryDTO);
        } catch (Exception e) {
            log.error("queryAgent-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryHotel")
    public Response queryHotel(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryService.queryHotel(fuzzyQueryDTO);
        } catch (Exception e) {
            log.error("queryHotel-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryRoom")
    public Response queryRoom(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryService.queryRoom(fuzzyQueryDTO);
        } catch (Exception e) {
            log.error("queryRoom-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryProduct")
    public Response queryProduct(@RequestBody FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = null;
        try {
            response = fuzzyQueryService.queryProduct(fuzzyQueryDTO);
        } catch (Exception e) {
            log.error("queryProduct-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/queryPurchaseManager")
    public Response queryPurchaseManager(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            List<PurchaseManagerDTO> purchaseManagerList = userService.getPurchaseManagerList(requestMap);
            response.setResult(1);
            response.setModel(purchaseManagerList);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/querySaleManager")
    public Response querySaleManager(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            List<SaleManagerDTO> saleManagerList = userService.getSaleManagerList(requestMap);
            response.setResult(1);
            response.setModel(saleManagerList);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

}
