package com.mgs.product.service.impl;


import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.domain.DebitedQuotaPO;
import com.mgs.product.domain.ProductDayIncreasePO;
import com.mgs.product.domain.QuotaPO;
import com.mgs.product.dto.QuotaDTO;
import com.mgs.product.mapper.DebitedQuotaMapper;
import com.mgs.product.mapper.QuotaMapper;
import com.mgs.product.service.QuotaService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("quotaService")
public class QuotaServicelmpl implements QuotaService {

    @Autowired
    private QuotaMapper quotaMapper;

    @Autowired
    private DebitedQuotaMapper debitedQuotaMapper;

    @Override
    public Response modifyQuota(QuotaDTO request) {
        Response response=new Response();
        String  quotaAccountId= quotaMapper.queryQuotaAccountId(request.getProductId());//通过供应商编码查询配额账号ID
        if(StringUtil.isValidString(quotaAccountId)){
            Map params = new HashMap();
            List saleDate = new ArrayList();
            String saleDates[]=  request.getSaleDate().split(",");
            for(int i=0; i<saleDates.length-1;i++){//减掉最后一天，07-23 - 07-24 为一天
                saleDate.add(saleDates[i]);
            }

            params.put("quotaAccountId",quotaAccountId);
            params.put("saleDate",saleDate);
            List<QuotaPO> quotas = quotaMapper.queryQuota(params);
            List<DebitedQuotaPO>  debitedQuotaPOS = new ArrayList<>();
            Date date=new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            int quotai=0;
            for(QuotaPO quota :quotas){
                if(request.getQuota()>0){//判断是增加或者扣除
                    quotai= request.getQuota();
                }else{//为负数就变为整数去做比较，取剩余的负数
                    quotai=  (-request.getQuota()>quota.getRemainingQuota())?-quota.getRemainingQuota():request.getQuota();//对比如果当前数不够扣除，就扣除剩余数
                }
                QuotaPO quotaPO=new QuotaPO();
                BeanUtils.copyProperties(quota,quotaPO);
                quotaPO.setQuotaId(quota.getQuotaId());
                quotaPO.setRemainingQuota(quota.getRemainingQuota()+quotai);//正负得负，正正得正
                quotaMapper.updateByPrimaryKey(quotaPO);


                DebitedQuotaPO  debitedQuotaPO = new DebitedQuotaPO();
                debitedQuotaPO.setOrderCode(request. getOrderCode());
                debitedQuotaPO.setOrderId(request.getOrderId());
                debitedQuotaPO.setProductId(request.getProductId());
                debitedQuotaPO.setQuota(quotai);
                debitedQuotaPO.setSupplyOrderId(request.getSupplyOrderId());
                debitedQuotaPO.setSupplyOrderCode(request.getSupplyOrderCode());
                debitedQuotaPO.setSaleDate(quota.getSaleDate());
                debitedQuotaPO.setQuotaAccountId(quota.getQuotaAccountId());
                debitedQuotaPO.setCreatedBy("");
                debitedQuotaPO.setCreatedDt(format.format(date));
                debitedQuotaPO.setType(request.getQuota()>0?2:1);//1：扣配额 2：退配额
                debitedQuotaPOS.add(debitedQuotaPO);
            }
            debitedQuotaMapper.insertDebitedQuota(debitedQuotaPOS);
            response.setResult(ResultCodeEnum.SUCCESS.code);
        }
        return response;
    }
}
