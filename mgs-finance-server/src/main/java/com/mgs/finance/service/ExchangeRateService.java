package com.mgs.finance.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;

import com.mgs.finance.dto.ExchangeRateLogDTO;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.finance.remote.request.QueryExchangeRateLogDTO;

import java.util.List;

public interface ExchangeRateService {


    /**
     * 查询汇率
     * @param
     * @return
     */
    List<ExchangeRateDTO> queryExchangeRate(ExchangeRateDTO exchangeRateDTO);


    /**
     * 新增汇率
     * @param
     * @return
     */
    Response   addExchangeRate(ExchangeRateDTO exchangeRateDTO);


    /**
     * 修改汇率
     * @param
     * @return
     */
    Response   updateExchangeRate(ExchangeRateDTO exchangeRateDTO);




    /**
     * 查询操作日志
     * @param
     * @return
     */
    PaginationSupportDTO<ExchangeRateLogDTO> queryExchangeRateLog(QueryExchangeRateLogDTO request);

}
