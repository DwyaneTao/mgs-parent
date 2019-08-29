package com.mgs.finance.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SettlementCurrencyEnum;
import com.mgs.finance.domain.ExchangeRateLogPO;
import com.mgs.finance.domain.ExchangeRatePO;

import com.mgs.finance.dto.ExchangeRateLogDTO;
import com.mgs.finance.mapper.ExchangeRateLogMapper;
import com.mgs.finance.mapper.ExchangeRateMapper;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.finance.remote.request.QueryExchangeRateLogDTO;
import com.mgs.finance.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService    {

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @Autowired
    private ExchangeRateLogMapper exchangeRateLogMapper;

    @Override
    public List<ExchangeRateDTO> queryExchangeRate(ExchangeRateDTO request) {
          log.info("queryExchangeRate param: {}"+request.toString());
          List<ExchangeRateDTO> exchangeRateLsit = new ArrayList<>();
          ExchangeRatePO exchangeRatePO =new ExchangeRatePO();
          if(request.getCurrency()!=null&&!(request.getCurrency().equals(""))){
              String Currency=SettlementCurrencyEnum.getValueByKey(request.getCurrency().toString()).toString();
              String[] arr = Currency.split("\\(");
              request.setExchangeCurrencies(arr[0]);
          }else{
              request.setExchangeCurrencies("");
          }

          List<ExchangeRatePO> exchangeRateList  = exchangeRateMapper.queryExchangeRate(request);
        for (ExchangeRatePO exchangeRatePOList:exchangeRateList){
            ExchangeRateDTO exchangeRateDTO =new ExchangeRateDTO();
            exchangeRateDTO.setExchangeCurrencies("CNY(人民币)——"+exchangeRatePOList.getExchangeCurrency()+"("+exchangeRatePOList.getCurrencyName()+")");
            exchangeRateDTO.setExchangeRate(new BigDecimal(exchangeRatePOList.getExchangeRate().stripTrailingZeros().toPlainString()));
            exchangeRateDTO.setReversedExchangeRate(new BigDecimal(exchangeRatePOList.getReversedExchangeRate().stripTrailingZeros().toPlainString()));
            exchangeRateDTO.setExchangeRateId(exchangeRatePOList.getExchangeRateId());
            exchangeRateDTO.setModifiedDt(exchangeRatePOList.getModifiedDt());
            exchangeRateDTO.setCreatedBy(exchangeRatePOList.getCreatedBy());
            exchangeRateDTO.setCreatedDt(exchangeRatePOList.getCreatedDt());
            exchangeRateDTO.setModifiedBy(exchangeRatePOList.getModifiedBy());
            exchangeRateDTO.setCurrency(Integer.parseInt(SettlementCurrencyEnum.getKeyByValue(exchangeRatePOList.getExchangeCurrency()+"("+exchangeRatePOList.getCurrencyName()+")")));
            exchangeRateLsit.add(exchangeRateDTO);
        }
        return exchangeRateLsit;
    }

    @Override
    public Response addExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        log.info("addExchangeRate param: {}"+ JSON.toJSONString(exchangeRateDTO));
        Response response = new Response();
        ExchangeRatePO exchangeRatePO =new ExchangeRatePO();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BeanUtils.copyProperties(exchangeRateDTO,exchangeRatePO);

        String currencyCode=exchangeRateDTO.getCurrency().toString();
        String Currency=SettlementCurrencyEnum.getValueByKey(currencyCode).toString();
        exchangeRateDTO.setExchangeCurrencies(Currency);
        String[] arr = Currency.split("\\(");
        exchangeRatePO.setExchangeCurrency(arr[0]);
        exchangeRatePO.setCurrencyName(arr[1].replace(")",""));
        ExchangeRatePO exchangeRatePOQy =new ExchangeRatePO();
        exchangeRatePOQy.setCurrencyName(exchangeRatePO.getCurrencyName());
        List<ExchangeRatePO> list= exchangeRateMapper.select(exchangeRatePOQy);
        if(list.size()>0){
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailReason("此币种以添加！");
            return response;
        }
        BigDecimal bg = new BigDecimal(exchangeRateDTO.getExchangeRate().doubleValue());
        exchangeRatePO.setExchangeRate(bg.setScale(4, BigDecimal.ROUND_HALF_UP));
        BigDecimal bg1 = new BigDecimal(1/bg.doubleValue());
        exchangeRatePO.setReversedExchangeRate(bg1.setScale(4, BigDecimal.ROUND_HALF_UP));
        exchangeRatePO.setCreatedDt(simpleDate.format(new Date()));
        exchangeRatePO.setModifiedDt(simpleDate.format(new Date()));
        exchangeRatePO.setActive(1);
        exchangeRateMapper.insert(exchangeRatePO);

        ExchangeRateLogPO exchangeRateLogPO =new ExchangeRateLogPO();
        exchangeRateLogPO.setContent("创建了汇率,CNY兑换成"+exchangeRateDTO.getExchangeCurrencies()+"的汇率为"+exchangeRateDTO.getReversedExchangeRate());
        exchangeRateLogPO.setCreatedBy(exchangeRateDTO.getCreatedBy());
        exchangeRateLogPO.setCreatedDt(simpleDate.format(new Date()));
        exchangeRateLogPO.setCurrencyId(exchangeRatePO.getExchangeRateId());
        exchangeRateLogPO.setActive(1);
        exchangeRateLogMapper.insert(exchangeRateLogPO);


        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response updateExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        log.info("updateExchangeRate param: {}"+ JSON.toJSONString(exchangeRateDTO));
        Response response = new Response();
        ExchangeRatePO exchangeRatePO =new ExchangeRatePO();
        ExchangeRateLogPO exchangeRateLogPO =new ExchangeRateLogPO();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        exchangeRatePO= exchangeRateMapper.selectByPrimaryKey(exchangeRateDTO.getExchangeRateId());
        if(exchangeRatePO==null){
            response.setResult(ResultCodeEnum.FAILURE.code);
            return response;
        }
//        String currencyCode=exchangeRateDTO.getExchangeCurrency();
//        String Currency=SettlementCurrencyEnum.getValueByKey(currencyCode).toString();
//        exchangeRateDTO.setExchangeCurrency(Currency);
        BigDecimal bgs = new BigDecimal(exchangeRatePO.getExchangeRate().toString());
        if(!exchangeRateDTO.getExchangeRate().equals(exchangeRatePO.getExchangeRate())){
            exchangeRateLogPO.setContent("CNY兑换成"+exchangeRatePO.getExchangeCurrency()+"("+exchangeRatePO.getCurrencyName()+")"+"的汇率，由"+bgs.stripTrailingZeros()+"改为"+exchangeRateDTO.getExchangeRate());
            exchangeRateLogPO.setCreatedBy(exchangeRateDTO.getModifiedBy());
            exchangeRateLogPO.setCreatedDt(simpleDate.format(new Date()));
            exchangeRateLogPO.setCurrencyId(exchangeRateDTO.getExchangeRateId());
            exchangeRateLogPO.setActive(1);
            exchangeRateLogMapper.insert(exchangeRateLogPO);
        }
        exchangeRateDTO.setExchangeCurrencies(exchangeRatePO.getExchangeCurrency());

        //BeanUtils.copyProperties(exchangeRateDTO,exchangeRatePO);
        BigDecimal bg = new BigDecimal(exchangeRateDTO.getExchangeRate().toString());
        exchangeRatePO.setExchangeRate(bg.setScale(4, BigDecimal.ROUND_HALF_UP));
        BigDecimal bg1 = new BigDecimal(1/bg.doubleValue());
        exchangeRatePO.setReversedExchangeRate(bg1.setScale(4, BigDecimal.ROUND_HALF_UP));
        exchangeRatePO.setModifiedDt(simpleDate.format(new Date()));
        exchangeRateMapper.updateByPrimaryKey(exchangeRatePO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }



    @Override
    public PaginationSupportDTO<ExchangeRateLogDTO> queryExchangeRateLog(QueryExchangeRateLogDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<ExchangeRateLogDTO>  list =exchangeRateLogMapper.queryExchangeRateLog(request);
        PageInfo<ExchangeRateLogDTO> page = new PageInfo<ExchangeRateLogDTO>(list);
        PaginationSupportDTO<ExchangeRateLogDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }
}
