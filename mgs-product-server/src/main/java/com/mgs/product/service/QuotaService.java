package com.mgs.product.service;


import com.mgs.common.Response;
import com.mgs.product.dto.QuotaDTO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: GB
 * @Date: 2019/7/17
 * @Description: 配额
 */
public interface QuotaService {


    /**
     * 修改配额
     * @param
     * @return
     */
    Response modifyQuota(QuotaDTO request) ;


}
