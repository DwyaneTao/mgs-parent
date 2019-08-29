package com.mgs.finance.server;


import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;

import com.mgs.finance.dto.ExchangeRateLogDTO;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.finance.remote.request.QueryExchangeRateLogDTO;
import com.mgs.finance.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import com.mgs.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ExchangeRateServer  {

     @Autowired
     private ExchangeRateService exchangeRateService;

    /**
     * 查询汇率
     * @param request
     * @return
     */
    @PostMapping("/finance/queryExchangeRate")
    Response queryExchangeRate(@RequestBody ExchangeRateDTO request){
        Response response = new Response();
        try{
            List<ExchangeRateDTO> ExchangeRateDTOList=exchangeRateService.queryExchangeRate(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(ExchangeRateDTOList);
        } catch (Exception e) {
            log.error("queryExchangeRate server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 新增汇率
     * @param request
     * @return
     */
    @PostMapping("/finance/addExchangeRate")
    Response addExchangeRate(@RequestBody ExchangeRateDTO request){
        Response response = new Response();
        try{
            response= exchangeRateService.addExchangeRate(request);
        } catch (Exception e) {
            log.error("addExchangeRate server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 编辑汇率
     * @param request
     * @return
     */
    @PostMapping("/finance/modifyExchangeRate")
    Response updateExchangeRate(@RequestBody ExchangeRateDTO request){
        Response response = new Response();
        try{
            response= exchangeRateService.updateExchangeRate(request);
        } catch (Exception e) {
            log.error("addExchangeRate server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询操作日志
     * @param
     * @return
     */
    @PostMapping("/finance/queryExchangeRateLog")
    Response updateExchangeRate(@RequestBody QueryExchangeRateLogDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO<ExchangeRateLogDTO> paginationSupportDTO=exchangeRateService.queryExchangeRateLog(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("updateExchangeRate server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }






}
