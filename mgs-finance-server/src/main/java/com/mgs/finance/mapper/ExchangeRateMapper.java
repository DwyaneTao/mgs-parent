package com.mgs.finance.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.domain.ExchangeRatePO;
import com.mgs.finance.remote.request.ExchangeRateDTO;


import java.util.List;



public interface ExchangeRateMapper extends MyMapper<ExchangeRatePO> {

    List<ExchangeRatePO> queryExchangeRate(ExchangeRateDTO exchangeRateDTO);
}
