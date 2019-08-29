package com.mgs.finance.remote;


import com.mgs.common.Response;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.finance.remote.request.QueryExchangeRateLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-finance-server")
public interface ExchangeRateRemote {


    /**
     * 查询汇率
     * @param request
     * @return
     */
    @PostMapping("/finance/queryExchangeRate")
    Response queryExchangeRate(@RequestBody ExchangeRateDTO request);


    /**
     * 新增汇率
     * @param request
     * @return
     */
    @PostMapping("/finance/addExchangeRate")
    Response addExchangeRate(@RequestBody ExchangeRateDTO request);

    /**
     * 编辑汇率
     * @param request
     * @return
     */
    @PostMapping("/finance/modifyExchangeRate")
    Response updateExchangeRate(@RequestBody ExchangeRateDTO request);

    /**
     * 查询操作日志
     * @param
     * @return
     */
    @PostMapping("/finance/queryExchangeRateLog")
    Response queryExchangeRateLog(@RequestBody QueryExchangeRateLogDTO request);

}
