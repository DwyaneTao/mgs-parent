package com.mgs.product.service.impl;


import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.domain.DebitedQuotaPO;
import com.mgs.product.mapper.DebitedQuotaMapper;
import com.mgs.product.service.DebitedQuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service("DebitedQuotaServer")
public class DebitedQuotaServicelmpl implements DebitedQuotaService {

    @Autowired
    private DebitedQuotaMapper debitedQuotaMapper;


    @Override
    public Response queryDebitedQuota(Map<String, Integer> map) {
        Response response = new Response();
        List<DebitedQuotaPO> debitedQuotaPO = debitedQuotaMapper.queryDebitedQuota(map);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(debitedQuotaPO);
        return response;
    }
}
