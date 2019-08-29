package com.mgs.finance.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.domain.ExchangeRateLogPO;
import com.mgs.finance.dto.ExchangeRateLogDTO;
import com.mgs.finance.remote.request.QueryExchangeRateLogDTO;

import java.util.List;

public interface ExchangeRateLogMapper extends MyMapper <ExchangeRateLogPO>{

     List<ExchangeRateLogDTO> queryExchangeRateLog(QueryExchangeRateLogDTO exchangeRateLogDTO);
}
