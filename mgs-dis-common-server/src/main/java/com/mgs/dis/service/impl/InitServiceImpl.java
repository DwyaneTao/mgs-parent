package com.mgs.dis.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.dis.dto.ProductBasePriceAndRoomStatusDTO;
import com.mgs.dis.dto.ProductCompanyRelationDTO;
import com.mgs.dis.dto.ProductRestrictDTO;
import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;
import com.mgs.dis.mapper.InitMapper;
import com.mgs.keys.RedisKey;
import com.mgs.dis.service.InitService;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.util.RedisUtil;
import com.mgs.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InitServiceImpl implements InitService {

    @Autowired
    private InitMapper initMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 初始化条款
     * @return
     */
    @Override
    public int initRestrict(ProductDTO productDTO) {

        //没有productId,默认初始化全局
        if (productDTO != null) {
           List<ProductRestrictDTO> productRestrictList = initMapper.queryRestrictList();
           Map<String, String> restrictMap = productRestrictList.stream().collect(Collectors.toMap(t -> String.valueOf(t.getProductId()), t -> JSON.toJSONString(t), (s1, s2) -> s2));
           handlerModifyData(restrictMap, RedisKey.productRestrictKey);
           return restrictMap.size();
        }

        ProductRestrictDTO productRestrictDTO = new ProductRestrictDTO();
        if(StringUtil.parseObject(redisUtil.hmget(RedisKey.productRestrictKey, String.valueOf(productDTO.getProductId())), ProductRestrictDTO.class) != null) {
            productRestrictDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productRestrictKey, String.valueOf(productDTO.getProductId())), ProductRestrictDTO.class);
        }

        productRestrictDTO.setProductId(productDTO.getProductId());

        if(productDTO.getBreakfastQty() != null){
            productRestrictDTO.setBreakfastQty(productDTO.getBreakfastQty());
        }

        if(productDTO.getCancellationAdvanceDays() != null){
            productRestrictDTO.setCancellationAdvanceDays(productDTO.getCancellationAdvanceDays());
        }

        if(productDTO.getCancellationDeductionTerm() != null){
            productRestrictDTO.setCancellationDeductionTerm(productDTO.getCancellationDeductionTerm());
        }

        if(productDTO.getCancellationDueTime() != null){
            productRestrictDTO.setCancellationDueTime(productDTO.getCancellationDueTime());
        }

        if(productDTO.getCancellationType() != null){
            productRestrictDTO.setCancellationType(productDTO.getCancellationType());
        }

        if(productDTO.getReservationAdvanceDays() != null){
            productRestrictDTO.setReservationAdvanceDays(productDTO.getReservationAdvanceDays());
        }

        if(productDTO.getReservationDueTime() != null){
            productRestrictDTO.setReservationDueTime(productDTO.getReservationDueTime());
        }

        if(productDTO.getReservationLimitNights() != null){
            productRestrictDTO.setReservationLimitNights(productDTO.getReservationLimitNights());
        }

        if(productDTO.getReservationLimitRooms() != null){
            productRestrictDTO.setReservationLimitRooms(productDTO.getReservationLimitRooms());
        }

        if(productDTO.getComparisonType() != null){
            productRestrictDTO.setComparisonType(productDTO.getComparisonType());
        }


        redisUtil.hmset(RedisKey.productRestrictKey, String.valueOf(productDTO.getProductId()), JSON.toJSONString(productRestrictDTO));
        return 1;
    }

    /**
     * 初始化底价和房态
     * @return
     */
    @Override
    public int initBasePriceAndRoomStatus(List<ProductDayQuotationDTO> productDayQuotationDTOList) {
        if(CollectionUtils.isEmpty(productDayQuotationDTOList)) {
            List<ProductBasePriceAndRoomStatusDTO> productBasePriceAndRoomStatusList = initMapper.queryBasePriceAndRoomStatusList();
            Map<String, String> basePriceMap = productBasePriceAndRoomStatusList.stream().collect(Collectors.toMap(ProductBasePriceAndRoomStatusDTO::getRedisKey, t-> JSON.toJSONString(t), (s1 , s2) -> s2));
            handlerModifyData(basePriceMap, RedisKey.productBasePriceAndRoomStatusKey);
            return basePriceMap.size();
        }

        Map<String, String> redisMap = new HashMap<String, String>();
        for(ProductDayQuotationDTO productDayQuotationDTO: productDayQuotationDTOList){
            String redisKey = StringUtil.concat(String.valueOf(productDayQuotationDTO.getProductId()), "_", productDayQuotationDTO.getSaleDate());
            ProductBasePriceAndRoomStatusDTO productBasePriceAndRoomStatusDTO = new ProductBasePriceAndRoomStatusDTO();
            if(StringUtil.parseObject(redisUtil.hmget(RedisKey.productBasePriceAndRoomStatusKey, redisKey), ProductBasePriceAndRoomStatusDTO.class) != null){
                productBasePriceAndRoomStatusDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productBasePriceAndRoomStatusKey, redisKey), ProductBasePriceAndRoomStatusDTO.class);
            }

            productBasePriceAndRoomStatusDTO.setProductId(productDayQuotationDTO.getProductId());
            productBasePriceAndRoomStatusDTO.setSaleDate(productDayQuotationDTO.getSaleDate());


            //底价
            if(productDayQuotationDTO.getBasePriceAdjustmentType() != null && productDayQuotationDTO.getModifiedBasePrice() != null){
                if(productDayQuotationDTO.getBasePriceAdjustmentType() == 2){
                    productBasePriceAndRoomStatusDTO.setBasePrice(productDayQuotationDTO.getModifiedBasePrice());
                }else if(productDayQuotationDTO.getQuotaAdjustmentType() == 1){
                    productBasePriceAndRoomStatusDTO.setBasePrice(productBasePriceAndRoomStatusDTO.getBasePrice() == null?BigDecimal.ZERO: (productBasePriceAndRoomStatusDTO.getBasePrice().subtract(productDayQuotationDTO.getModifiedBasePrice()).compareTo(BigDecimal.ZERO) < 0? BigDecimal.ZERO : productBasePriceAndRoomStatusDTO.getBasePrice().subtract(productDayQuotationDTO.getModifiedBasePrice())));
                }else if(productDayQuotationDTO.getQuotaAdjustmentType() == 0){
                    productBasePriceAndRoomStatusDTO.setBasePrice(productBasePriceAndRoomStatusDTO.getBasePrice() == null?productDayQuotationDTO.getModifiedBasePrice(): (productBasePriceAndRoomStatusDTO.getBasePrice().add(productDayQuotationDTO.getModifiedBasePrice())));
                }
            }

            //售罄
            if(productDayQuotationDTO.getOverDraftStatus() != null){
                productBasePriceAndRoomStatusDTO.setOverDraftStatus(productDayQuotationDTO.getOverDraftStatus());
            }

            //房态
            if(productDayQuotationDTO.getRoomStatus() != null){
                productBasePriceAndRoomStatusDTO.setRoomStatus(productDayQuotationDTO.getRoomStatus());
            }

            if(productDayQuotationDTO.getQuotaAdjustmentType() != null && productDayQuotationDTO.getModifiedQuota() != null){
                if(productDayQuotationDTO.getQuotaAdjustmentType() == 2){
                    productBasePriceAndRoomStatusDTO.setQuota(productDayQuotationDTO.getModifiedQuota());
                    productBasePriceAndRoomStatusDTO.setRemainingQuota(productDayQuotationDTO.getModifiedQuota());
                }else if(productDayQuotationDTO.getQuotaAdjustmentType() == 1){
                    productBasePriceAndRoomStatusDTO.setQuota(productBasePriceAndRoomStatusDTO.getQuota() == null?0: (productBasePriceAndRoomStatusDTO.getQuota() - productDayQuotationDTO.getModifiedQuota() <= 0? 0: productBasePriceAndRoomStatusDTO.getQuota() - productDayQuotationDTO.getModifiedQuota()));
                    productBasePriceAndRoomStatusDTO.setRemainingQuota(productBasePriceAndRoomStatusDTO.getRemainingQuota() == null?0: (productBasePriceAndRoomStatusDTO.getRemainingQuota() - productDayQuotationDTO.getModifiedQuota() <= 0? 0: productBasePriceAndRoomStatusDTO.getRemainingQuota() - productDayQuotationDTO.getModifiedQuota()));
                }else if(productDayQuotationDTO.getQuotaAdjustmentType() == 0){
                    productBasePriceAndRoomStatusDTO.setQuota(productBasePriceAndRoomStatusDTO.getQuota() == null?productDayQuotationDTO.getModifiedQuota(): (productBasePriceAndRoomStatusDTO.getQuota() + productDayQuotationDTO.getModifiedQuota()));
                    productBasePriceAndRoomStatusDTO.setRemainingQuota(productBasePriceAndRoomStatusDTO.getRemainingQuota() == null?productDayQuotationDTO.getModifiedQuota(): (productBasePriceAndRoomStatusDTO.getRemainingQuota() + productDayQuotationDTO.getModifiedQuota()));
                }
            }
            redisMap.put(redisKey, JSON.toJSONString(productBasePriceAndRoomStatusDTO));
        }
        redisUtil.hmset(RedisKey.productBasePriceAndRoomStatusKey, redisMap);
        return redisMap.size();
    }


    /**
     * 初始化销售价格
     * @return
     */
    @Override
    public int initSalePrice(List<ProductSaleIncreaseDTO> productSaleIncreaseList) {

        if(CollectionUtils.isEmpty(productSaleIncreaseList)){
            List<ProductSaleIncreaseDTO> productSaleIncreaseDTOS = initMapper.querySalePriceList();
            Map<String, String> productSaleMap = productSaleIncreaseDTOS.stream().collect(Collectors.toMap(ProductSaleIncreaseDTO::getRedisKey, t-> JSON.toJSONString(t), (s1 , s2) -> s2));
            handlerModifyData(productSaleMap, RedisKey.productSalePriceKey);
            return productSaleMap.size();
        }

        Map<String, String> redisMap = new HashMap<String, String>();
        for(ProductSaleIncreaseDTO productSaleIncreaseDTO: productSaleIncreaseList){
            String redisKey = StringUtil.concat(productSaleIncreaseDTO.getCompanyCode(), "_", String.valueOf(productSaleIncreaseDTO.getProductId()), "_", productSaleIncreaseDTO.getSaleDate());
            ProductSaleIncreaseDTO productSaleIncrease = new ProductSaleIncreaseDTO();
            if(StringUtil.parseObject(redisUtil.hmget(RedisKey.productSalePriceKey, redisKey), ProductBasePriceAndRoomStatusDTO.class) != null){
                productSaleIncrease = StringUtil.parseObject(redisUtil.hmget(RedisKey.productBasePriceAndRoomStatusKey, redisKey), ProductSaleIncreaseDTO.class);
            }

            productSaleIncrease.setProductId(productSaleIncreaseDTO.getProductId());
            productSaleIncrease.setSaleDate(productSaleIncreaseDTO.getSaleDate());
            productSaleIncrease.setCompanyCode(productSaleIncreaseDTO.getCompanyCode());


            if(productSaleIncreaseDTO.getB2bAdjustmentType() != null){
                productSaleIncrease.setB2bAdjustmentType(productSaleIncreaseDTO.getB2bAdjustmentType());
            }

            if(productSaleIncreaseDTO.getB2bModifiedAmt() != null){
                productSaleIncrease.setB2bModifiedAmt(productSaleIncreaseDTO.getB2bModifiedAmt());
            }

            if(productSaleIncreaseDTO.getB2cAdjustmentType() != null){
                productSaleIncrease.setB2cAdjustmentType(productSaleIncreaseDTO.getB2cAdjustmentType());
            }

            if(productSaleIncreaseDTO.getB2cModifiedAmt() != null){
                productSaleIncrease.setB2bModifiedAmt(productSaleIncreaseDTO.getB2cModifiedAmt());
            }

            if(productSaleIncreaseDTO.getCtripModifiedAmt() != null){
                productSaleIncrease.setCtripModifiedAmt(productSaleIncreaseDTO.getCtripModifiedAmt());
            }

            if(productSaleIncreaseDTO.getCtripAdjustmentType() != null){
                productSaleIncrease.setCtripAdjustmentType(productSaleIncreaseDTO.getCtripAdjustmentType());
            }

            if(productSaleIncreaseDTO.getMeituanModifiedAmt() != null){
                productSaleIncrease.setMeituanModifiedAmt(productSaleIncreaseDTO.getMeituanModifiedAmt());
            }

            if(productSaleIncreaseDTO.getMeituanAdjustmentType() != null){
                productSaleIncrease.setMeituanAdjustmentType(productSaleIncreaseDTO.getMeituanAdjustmentType());
            }

            if(productSaleIncreaseDTO.getFeizhuModifiedAmt() != null){
                productSaleIncrease.setFeizhuModifiedAmt(productSaleIncreaseDTO.getFeizhuModifiedAmt());
            }

            if(productSaleIncreaseDTO.getFeizhuAdjustmentType() != null){
                productSaleIncrease.setFeizhuAdjustmentType(productSaleIncreaseDTO.getFeizhuAdjustmentType());
            }

            if(productSaleIncreaseDTO.getQunarModifiedAmt() != null){
                productSaleIncrease.setQunarModifiedAmt(productSaleIncreaseDTO.getQunarModifiedAmt());
            }

            if(productSaleIncreaseDTO.getQunarAdjustmentType() != null){
                productSaleIncrease.setQunarAdjustmentType(productSaleIncreaseDTO.getQunarAdjustmentType());
            }

            if(productSaleIncreaseDTO.getTcylModifiedAmt() != null){
                productSaleIncrease.setTcylModifiedAmt(productSaleIncreaseDTO.getTcylModifiedAmt());
            }

            if(productSaleIncreaseDTO.getTcylAdjustmentType() != null){
                productSaleIncrease.setTcylAdjustmentType(productSaleIncreaseDTO.getTcylAdjustmentType());
            }

            redisMap.put(redisKey, JSON.toJSONString(productSaleIncrease));
        }
        redisUtil.hmset(RedisKey.productBasePriceAndRoomStatusKey, redisMap);
        return redisMap.size();

    }

    /**
     * 初始化上下架信息
     * @return
     */
    @Override
    public int initSaleStatus(List<ProductSaleStatusDTO> productSaleStatusPOList) {

        if(CollectionUtils.isEmpty(productSaleStatusPOList)){
            List<ProductSaleStatusDTO> saleStatusList = initMapper.querySaleStatusList();
            Map<String, String> saleStatusMap = saleStatusList.stream().collect(Collectors.toMap(ProductSaleStatusDTO::getRedisKey , t-> JSON.toJSONString(t), (s1 , s2) -> s2));
            handlerModifyData(saleStatusMap, RedisKey.productSaleStatusKey);
            return saleStatusMap.size();
        }

        Map<String, String> redisMap = new HashMap<String, String>();
        for(ProductSaleStatusDTO productSaleStatusDTO: productSaleStatusPOList){
            String redisKey = StringUtil.concat(productSaleStatusDTO.getCompanyCode(), "_", String.valueOf(productSaleStatusDTO.getProductId()));
            ProductSaleStatusDTO productSaleStatus = new ProductSaleStatusDTO();
            if(StringUtil.parseObject(redisUtil.hmget(RedisKey.productSaleStatusKey, redisKey), ProductSaleStatusDTO.class) != null){
                productSaleStatus = StringUtil.parseObject(redisUtil.hmget(RedisKey.productSaleStatusKey, redisKey), ProductSaleStatusDTO.class);
            }

            productSaleStatus.setActive(1);
            productSaleStatus.setProductId(productSaleStatusDTO.getProductId());
            productSaleStatus.setCompanyCode(productSaleStatusDTO.getCompanyCode());

            if(productSaleStatusDTO.getB2bSaleStatus() != null){
                productSaleStatus.setB2bSaleStatus(productSaleStatusDTO.getB2bSaleStatus());
            }

            if(productSaleStatusDTO.getB2cSaleStatus() != null){
                productSaleStatus.setB2bSaleStatus(productSaleStatusDTO.getB2cSaleStatus());
            }

            if(productSaleStatusDTO.getCtripSaleStatus() != null){
                productSaleStatus.setCtripSaleStatus(productSaleStatusDTO.getCtripSaleStatus());
            }

            if(productSaleStatusDTO.getFeizhuSaleStatus() != null){
                productSaleStatus.setFeizhuSaleStatus(productSaleStatusDTO.getFeizhuSaleStatus());
            }

            if(productSaleStatusDTO.getMeituanSaleStatus() != null){
                productSaleStatus.setMeituanSaleStatus(productSaleStatusDTO.getMeituanSaleStatus());
            }

            if(productSaleStatusDTO.getQunarSaleStatus() != null){
                productSaleStatus.setQunarSaleStatus(productSaleStatusDTO.getQunarSaleStatus());
            }

            if(productSaleStatusDTO.getTcylSaleStatus() != null){
                productSaleStatus.setTcylSaleStatus(productSaleStatusDTO.getTcylSaleStatus());
            }
            redisMap.put(redisKey, JSON.toJSONString(productSaleStatus));
        }
        redisUtil.hmset(RedisKey.productSaleStatusKey, redisMap);
        return redisMap.size();
    }

    /**
     * 初始化产品商家关系表
     * @return
     */
    @Override
    public int initProductCompanyRelation() {
        List<ProductCompanyRelationDTO> productCompanyRelationList = initMapper.queryProductCompanyRelationList();
        Map<String, String> productCompanyMap = productCompanyRelationList.stream().collect(Collectors.toMap(ProductCompanyRelationDTO::getProductId , t-> JSON.toJSONString(t), (s1 , s2) -> s2));
        handlerModifyData(productCompanyMap, RedisKey.productCompanyRelationKey);
        return productCompanyMap.size();
    }

    private void handlerModifyData(Map<String, String> databaseMap, String redisKey){
        //redis里面的数据
        Map<Object, Object> redisMap  = redisUtil.hmget(redisKey);
        List<String> redisDel = new ArrayList<String>();
        if(databaseMap != null && databaseMap.size() > 0 && redisMap != null && redisMap.size() > 0){
            for(Object key: redisMap.keySet()){
                String mapKey = (String) key;
                if(databaseMap.containsKey(mapKey)){
                    if(String.valueOf(redisMap.get(mapKey)).equals(databaseMap.get(mapKey))){ //比较相同的就不进行本次redis操作
                        databaseMap.remove(mapKey);
                    }
                }else {
                    redisDel.add(mapKey);
                }
            }
        }

        //需要删除的数据
        if(CollectionUtils.isNotEmpty(redisDel)){
           redisUtil.hdel(redisKey, redisDel.toArray());
        }

        //新增和修改的数据
        if (databaseMap != null && databaseMap.size() > 0) {
            redisUtil.hmset(redisKey, databaseMap);
        }
    }
}
