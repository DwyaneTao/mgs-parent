package com.mgs.product.service;


import com.mgs.common.Response;
import com.mgs.product.dto.QuotaDTO;

import java.util.Map;

public interface DebitedQuotaService {


    /**
     * 查询配额
     * @param
     * @return
     */
    Response queryDebitedQuota(Map<String,Integer>  map);

}
