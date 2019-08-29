package com.mgs.finance.remote.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class ExchangeRateLogDTO  {

    /**
     * 操作时间
     */
    private String  createdDt;

    /**
     * 操作人
     */
    private String  createdBy;

    /**
     * 内容
     */
    private String  content;





}
