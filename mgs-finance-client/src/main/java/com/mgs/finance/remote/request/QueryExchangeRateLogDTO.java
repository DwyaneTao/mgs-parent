package com.mgs.finance.remote.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryExchangeRateLogDTO extends BaseRequest {

    /**
     * 币别ID
     */
    private Integer  exchangeRateId;
}
