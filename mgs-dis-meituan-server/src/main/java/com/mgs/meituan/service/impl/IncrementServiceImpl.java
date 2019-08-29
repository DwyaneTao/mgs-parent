package com.mgs.meituan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.DataUtil;
import com.mgs.common.Response;
import com.mgs.dis.dto.IncrementDTO;
import com.mgs.dis.dto.ProductBasePriceAndRoomStatusDTO;
import com.mgs.dis.dto.ProductRestrictDTO;
import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.ExchangeRateRemote;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.keys.RedisKey;
import com.mgs.meituan.dto.product.request.PriceSyncDTO;
import com.mgs.meituan.dto.product.request.RoomStatusPriceDTO;
import com.mgs.meituan.service.IncrementService;
import com.mgs.util.DateUtil;
import com.mgs.util.RedisUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.mgs.meituan.key.InitData.channelConfigMap;

@Slf4j
@Service
public class IncrementServiceImpl implements IncrementService {


    @Resource(name = "handlerIncrementExecutor")
    private ThreadPoolTaskExecutor executor;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private ExchangeRateRemote exchangeRateRemote;

    /**
     * 推送增量
     * @param incrementList
     */
    @Override
    public void pullIncrement(List<Object> incrementList) {
        List<IncrementDTO> list = incrementList.stream().map(i -> StringUtil.parseObject(i, IncrementDTO.class)).collect(Collectors.toList());

        if(channelConfigMap == null || channelConfigMap.size() <= 0){
            log.error("没有配置的商家信息，请检查配置");
            return ;
        }
        List<Future> futures = new ArrayList<Future>();

        for(String key: channelConfigMap.keySet()){
            Future<?> future = executor.submit(()->{
                for(IncrementDTO incrementDTO: list){

                }
            });
            futures.add(future);
        }


    }

    /**
     * 推送价格同步
     * 当售价和底价有变化，则用这个方法
     * @param incrementDTO
     */
    private void pullPriceSync(IncrementDTO incrementDTO, String companyCode) {

        /**
         * 查redis
         */
        ProductSaleStatusDTO productSaleStatusDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productSaleStatusKey, StringUtil.concat(incrementDTO.getCompanyCode(), String.valueOf(incrementDTO.getProductId()))), ProductSaleStatusDTO.class);
        //该产品不在渠道上面卖
        if (productSaleStatusDTO.getMeituanSaleStatus() != 1) {
            return;
        }


        List<RoomStatusPriceDTO> priceList = new ArrayList<RoomStatusPriceDTO>();
        PriceSyncDTO priceSyncDTO = new PriceSyncDTO();
        priceSyncDTO.setRoomType(String.valueOf(incrementDTO.getProductId()));

        ProductRestrictDTO productRestrictDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productRestrictKey, String.valueOf(incrementDTO.getProductId())), ProductRestrictDTO.class);
        priceSyncDTO.setRoomType(String.valueOf(productRestrictDTO.getProductId()));
        priceSyncDTO.setPoiId(String.valueOf(productRestrictDTO.getHotelId()));
        Integer currency = productRestrictDTO.getCurrency();

        /**
         * 每日价格
         */
        List<RoomStatusPriceDTO> roomStatusPriceList = new ArrayList<RoomStatusPriceDTO>();
        for (String saleDate : incrementDTO.getSaleDate()) {
            RoomStatusPriceDTO roomStatusPriceDTO = new RoomStatusPriceDTO();
            ProductBasePriceAndRoomStatusDTO productBasePriceAndRoomStatusDTO = StringUtil.parseObject(redisUtil.hmget(StringUtil.concat(String.valueOf(incrementDTO.getProductId()), saleDate)), ProductBasePriceAndRoomStatusDTO.class);
            roomStatusPriceDTO.setStartDate(saleDate);
            roomStatusPriceDTO.setEndDate(DateUtil.dateToString(DateUtil.addDate(DateUtil.stringToDate(saleDate), 60)));

            BigDecimal basePrice = productBasePriceAndRoomStatusDTO.getBasePrice();
            ProductSaleIncreaseDTO productSaleIncreaseDTO = StringUtil.parseObject(redisUtil.hmget(StringUtil.concat(companyCode, String.valueOf(incrementDTO.getProductId()), saleDate)), ProductSaleIncreaseDTO.class);


            //查询币种对应的汇率
            BigDecimal rate = null;
            if (currency == 0) {//0为人民币默认汇率为1，不用查询汇率接口
                rate = BigDecimal.ONE;
            } else {
                ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
                exchangeRateDTO.setCurrency(currency);
                Response ExchangeRate = exchangeRateRemote.queryExchangeRate(exchangeRateDTO);
                if (ExchangeRate.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != ExchangeRate.getModel()) {
                    JSONArray jsonArray = (JSONArray) JSON.parseArray(JSONObject.toJSONString(ExchangeRate.getModel()));
                    Iterator<Object> it = jsonArray.iterator();
                    while (it.hasNext()) {
                        JSONObject arrayObj = (JSONObject) it.next();
                        rate = new BigDecimal(arrayObj.get("reversedExchangeRate").toString());
                    }
                }
            }

            Integer adjustmentType = productSaleIncreaseDTO.getMeituanAdjustmentType();
            BigDecimal modifiedAmt = productSaleIncreaseDTO.getMeituanModifiedAmt();
            BigDecimal salePrice = new BigDecimal("0");
            //没有加幅方式或者没有加幅金额，则没有售价和利润
            if (null != adjustmentType && null != modifiedAmt) {
                BigDecimal zero = new BigDecimal("0");
                switch (adjustmentType) {
                    case 0:
                        salePrice = basePrice.multiply(rate).add(modifiedAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        break;
                    case 1:
                        salePrice = basePrice.multiply(rate).subtract(modifiedAmt).compareTo(zero) > 0 ? basePrice.multiply(rate).subtract(modifiedAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN) : zero;
                        break;
                    case 2:
                        salePrice = basePrice.multiply(rate).add(basePrice.multiply(modifiedAmt)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        break;
                    case 3:
                        salePrice = modifiedAmt;
                        break;
                    default:
                }
            }
            roomStatusPriceDTO.setBasePrice(salePrice.multiply(new BigDecimal("100")).intValue());
            roomStatusPriceList.add(roomStatusPriceDTO);
        }

        //TODO 调推送接口
    }

    /**
     * 当房态有变化，则调用这个接口推送房态
     * @param incrementDTO
     */
    private void pullRoomStatusSync(IncrementDTO incrementDTO){

    }

    /**
     * 当条款有变化，就調用这个接口推送条款
     * @param incrementDTO
     */
    private void pullRestrictSync(IncrementDTO incrementDTO){

    }

    /**
     * 若销售状态有变化，则调用该接口
     * @param incrementDTO
     */
    private void handlerSaleStatus(IncrementDTO incrementDTO){}

    /**
     * 当映射关系中没有，则创建产品
     * @param incrementDTO
     */
    private void createProduct(IncrementDTO incrementDTO){}

    /**
     * 当映射关系中有，则让产品上下架
     * @param incrementDTO
     */
    private void pullSaleStatusSync(IncrementDTO incrementDTO){}
}
