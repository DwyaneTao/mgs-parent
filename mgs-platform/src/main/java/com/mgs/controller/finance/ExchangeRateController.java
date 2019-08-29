package com.mgs.controller.finance;


import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.ExchangeRateRemote;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.finance.remote.request.QueryExchangeRateLogDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/finance")
public class ExchangeRateController extends BaseController {

    @Autowired
    private ExchangeRateRemote exchangeRateRemote;


    @RequestMapping(value = "/queryExchangeRate",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryExchangeRate(@RequestBody ExchangeRateDTO exchangeRateDTO){

        Response response=null;
        try {
            response=exchangeRateRemote.queryExchangeRate(exchangeRateDTO);
            log.info("exchangeRateRemote.queryExchangeRate result:"+response.getResult());
        } catch (Exception e) {
            log.error("exchangeRateRemote.queryExchangeRate 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/addExchangeRate",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addExchangeRate(@RequestBody ExchangeRateDTO request){
        Response response=null;
        try {
                request.setCreatedBy(super.getUserName());
                response=exchangeRateRemote.addExchangeRate(request);

            log.info("exchangeRateRemote.addExchangeRate result:"+response.getResult());
        } catch (Exception e) {
            log.error("exchangeRateRemote.addExchangeRate 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    @RequestMapping(value = "/modifyExchangeRate",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response updateExchangeRate(@RequestBody ExchangeRateDTO request){
        Response response=null;
        try {
                request.setModifiedBy(super.getUserName());
                response=exchangeRateRemote.updateExchangeRate(request);
            log.info("exchangeRateRemote.updateExchangeRate result:"+response.getResult());
        } catch (Exception e) {
            log.error("exchangeRateRemote.updateExchangeRate 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/queryExchangeRateLog",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryExchangeRateLog(@RequestBody QueryExchangeRateLogDTO request){
        Response response=new Response();
        try {
            response=exchangeRateRemote.queryExchangeRateLog(request);
            log.info("exchangeRateRemote.queryExchangeRateLog result:"+response.getResult());
        } catch (Exception e) {
            log.error("exchangeRateRemote.queryExchangeRateLog 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }



}
