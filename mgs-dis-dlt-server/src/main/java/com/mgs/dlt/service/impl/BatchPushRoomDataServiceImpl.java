package com.mgs.dlt.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.common.enums.DltChannelEnum;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.remote.DisMappingRemote;
import com.mgs.dlt.mapper.ProductMapper;
import com.mgs.dlt.request.dto.*;
import com.mgs.dlt.response.dto.QueryHotelProductDetailResponse;
import com.mgs.dlt.service.BatchPushRoomDataService;
import com.mgs.dlt.utils.DltInterfaceInvoker;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 *   2018/3/1.
 */
@Service("batchPushRoomDataService")
public class BatchPushRoomDataServiceImpl implements BatchPushRoomDataService {

    private static final Logger LOG = LoggerFactory.getLogger(BatchPushRoomDataServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DisMappingRemote disMappingRemote;

    @Override
    @Async
    public void pushRoomDataToDltByPricePlan(Long pricePlanId, Date checkInDate, Date checkOutDate) {
        if (null == pricePlanId || null == checkInDate || null == checkOutDate) {
            LOG.error("推送房型售卖详情传入的参数不合法，pricePlanId=" + pricePlanId + ", checkInDate="
                    + checkInDate + ", checkOutDate=" + checkOutDate);
            return;
        }

        DisMappingQueryDTO disMappingQueryDTO = new DisMappingQueryDTO();
        disMappingQueryDTO.setProductId(pricePlanId.intValue());
        List<DisProductMappingDTO> disProductMappingDTOList = disMappingRemote.queryProductMapping(disMappingQueryDTO);
        if (!CollectionUtils.isEmpty(disProductMappingDTOList)) {
            List<DisProductMappingDTO> dltMapRoomPOList = null;
            //一个产品对应多个运营商情况，一般情况下，一个产品只属于一个运营商
            for (DisProductMappingDTO disProductMappingDTO : disProductMappingDTOList) {
                dltMapRoomPOList = new ArrayList<DisProductMappingDTO>();
                dltMapRoomPOList.add(disProductMappingDTO);
                this.pushRoomDataToDltByMapRoomList(dltMapRoomPOList, checkInDate, checkOutDate,disProductMappingDTO.getCompanyCode());
            }
        }
    }

    @Override
    @Async
    public void pushRoomDataToDltByMapRoomList(List<DisProductMappingDTO> dltMapRoomPOList, Date checkInDate, Date checkOutDate, String merchantCode) {

        LOG.info("代理通推送请求参数：dltMapRoomPOList" + JSON.toJSONString(dltMapRoomPOList) + ";checkInDate:" + checkInDate +
                ";checkOutDate:" + checkOutDate);
        if (CollectionUtils.isEmpty(dltMapRoomPOList) || null == checkInDate || null == checkOutDate
                || !StringUtil.isValidString(merchantCode)) {
            LOG.error("推送房型售卖详情传入的参数不合法，pricePlanIdList=" + dltMapRoomPOList + ", checkInDate="
                    + checkInDate + ", checkOutDate=" + checkOutDate + ",merchantCode=" + merchantCode);
            return;
        }

        Date checkInDateOld = checkInDate;
        Date checkOutDateOld = checkOutDate;

        // 将大的List，按照酒店ID划分到不同的小List中，按照每个酒店做一次推送请求，减少整体推送次数
        Map<Integer, List<DisProductMappingDTO>> perHotelMapRoomList = new HashMap<>();
        dltMapRoomPOList.forEach(dltMapRoomPO -> {

            if (null != perHotelMapRoomList.get(dltMapRoomPO.getHotelId())) {
                perHotelMapRoomList.get(dltMapRoomPO.getHotelId()).add(dltMapRoomPO);
            } else {
                List<DisProductMappingDTO> subList = new ArrayList<>();
                subList.add(dltMapRoomPO);
                perHotelMapRoomList.put(dltMapRoomPO.getHotelId(), subList);
            }
        });

        for (Map.Entry<Integer, List<DisProductMappingDTO>> entry : perHotelMapRoomList.entrySet()) {

            Integer hotelId = Integer.valueOf(entry.getValue().get(0).getDisHotelId());
            List<DisProductMappingDTO> subList = entry.getValue();

            checkInDate = checkInDateOld;
            checkOutDate = checkOutDateOld;

            //代理通限制每次推送的数据量的房型*日期不能大于300.
            //对房型*日期大于等于300的酒店数据，按单个房型推送
            //对房型*日期小于300的酒店数据，按酒店推送
            long days = DateUtil.getDay(checkInDate,checkOutDate);
            if(days * subList.size() * 4 >= 300) {
                LOG.info("代理通推送产品模式：按单房型推送");
                int i = 0;
                //每60天循环一次
                while (i <= days/60) {
                    checkOutDate = (DateUtil.compare(DateUtil.getDate(checkInDate,60,0),DateUtil.getDate(DateUtil.getCurrentDate(),181,0))<0)?DateUtil.getDate(checkInDate,60,0):DateUtil.getDate(DateUtil.getCurrentDate(),180,0);
                    if(DateUtil.compare(checkInDate,checkOutDate)<0) {
                        for (DisProductMappingDTO po : subList) {
                            List<RoomDataEntity> roomDataEntityList = new ArrayList<>();
                            // 查询指定产品指定日期的产品售卖详情9
                            QueryHotelProductDetailRequest request = new QueryHotelProductDetailRequest();
                            request.setPricePlanId(po.getProductId());
                            request.setCheckInDate(checkInDate);
                            request.setCheckOutDate(checkOutDate);
                            request.setCompanyCode(merchantCode);
                            request.setChannelCode(DltChannelEnum.CTRIP.key);
                            LOG.info("单房型查询价格信息入参："+JSON.toJSONString(request));
                            List<QueryHotelProductDetailResponse> ctripResponseList = productMapper.selectProductDetail(request);
                            this.assemblyProductDetail(ctripResponseList);

                            if (CollectionUtils.isEmpty(ctripResponseList)) {
                                LOG.error("该产品在指定的日期段内未查询到任何售卖详情信息，request = " + request);
                                continue;
                            }

                            request.setChannelCode(DltChannelEnum.QUNAR.key);
                            List<QueryHotelProductDetailResponse> qunarResponseList = productMapper.selectProductDetail(request);
                            this.assemblyProductDetail(qunarResponseList);

                            if (CollectionUtils.isEmpty(qunarResponseList)) {
                                LOG.error("该产品在指定的日期段内未查询到任何售卖详情信息，request = " + request);
                                continue;
                            }

                            roomDataEntityList.addAll(this.buildRoomDataEntityList(ctripResponseList,qunarResponseList, Long.valueOf(po.getDisRoomId())));
                            // 构建请求对象，调用代理通接口输出数据
                            BatchPushRoomDataRequest batchPushRoomDataRequest = new BatchPushRoomDataRequest();
                            batchPushRoomDataRequest.setHotelId(hotelId);
                            batchPushRoomDataRequest.setRoomDataEntitys(roomDataEntityList);
                            LOG.info("推送产品价格到代理通param：" + JSON.toJSONString(batchPushRoomDataRequest));
                            DltInterfaceInvoker.invoke(batchPushRoomDataRequest,merchantCode);
                        }
                    }

                    i ++;
                    checkInDate = checkOutDate;
                }
            }else {
                LOG.info("代理通推送产品模式：按酒店推送");
                List<RoomDataEntity> roomDataEntityList = new ArrayList<>();
                for (DisProductMappingDTO po : subList) {
                    // 查询指定产品指定日期的产品售卖详情9
                    QueryHotelProductDetailRequest request = new QueryHotelProductDetailRequest();
                    request.setPricePlanId(po.getProductId());
                    request.setCheckInDate(checkInDate);
                    request.setCheckOutDate(checkOutDate);
                    request.setChannelCode(DltChannelEnum.CTRIP.key);
                    request.setCompanyCode(merchantCode);
                    LOG.info("单酒店查询价格信息入参："+JSON.toJSONString(request));
                    List<QueryHotelProductDetailResponse> ctripResponseList = productMapper.selectProductDetail(request);
                    this.assemblyProductDetail(ctripResponseList);

                    if (CollectionUtils.isEmpty(ctripResponseList)) {
                        LOG.error("该产品在指定的日期段内未查询到任何售卖详情信息，request = " + request);
                        continue;
                    }

                    request.setChannelCode(DltChannelEnum.QUNAR.key);
                    List<QueryHotelProductDetailResponse> qunarResponseList = productMapper.selectProductDetail(request);
                    this.assemblyProductDetail(qunarResponseList);

                    if (CollectionUtils.isEmpty(qunarResponseList)) {
                        LOG.error("该产品在指定的日期段内未查询到任何售卖详情信息，request = " + request);
                        continue;
                    }

                    roomDataEntityList.addAll(this.buildRoomDataEntityList(ctripResponseList,qunarResponseList, Long.valueOf(po.getDisRoomId())));
                }

                // 构建请求对象，调用代理通接口输出数据
                BatchPushRoomDataRequest request = new BatchPushRoomDataRequest();
                request.setHotelId(hotelId);
                request.setRoomDataEntitys(roomDataEntityList);
                LOG.info("推送产品价格到代理通param：" + JSON.toJSONString(request));
                DltInterfaceInvoker.invoke(request,merchantCode);
            }

        }

    }

    /**
     * 计算返回数据的渠道售价
     * @param ctripResponseList
     */
    private void assemblyProductDetail(List<QueryHotelProductDetailResponse> ctripResponseList) {
        if (!org.apache.commons.collections.CollectionUtils.isEmpty(ctripResponseList)) {
            for (QueryHotelProductDetailResponse queryHotelProductDetailResponse : ctripResponseList) {
                if (null != queryHotelProductDetailResponse.getAdjustmentType() && null != queryHotelProductDetailResponse.getModifiedAmt()
                        && null != queryHotelProductDetailResponse.getBasePrice()) {
                    switch (queryHotelProductDetailResponse.getAdjustmentType()) {
                        case 0:
                            queryHotelProductDetailResponse.setCtripPrice(queryHotelProductDetailResponse.getBasePrice().add(queryHotelProductDetailResponse.getModifiedAmt()));
                            break;
                        case 1:
                            queryHotelProductDetailResponse.setCtripPrice(queryHotelProductDetailResponse.getBasePrice().subtract(queryHotelProductDetailResponse.getModifiedAmt()).compareTo(BigDecimal.ZERO) > 0 ? queryHotelProductDetailResponse.getBasePrice().subtract(queryHotelProductDetailResponse.getModifiedAmt()) : BigDecimal.ZERO);
                            break;
                        case 2:
                            queryHotelProductDetailResponse.setCtripPrice(queryHotelProductDetailResponse.getBasePrice().add(queryHotelProductDetailResponse.getBasePrice().multiply(queryHotelProductDetailResponse.getModifiedAmt())).setScale(2,BigDecimal.ROUND_HALF_DOWN));
                            break;
                        case 3:
                            queryHotelProductDetailResponse.setCtripPrice(queryHotelProductDetailResponse.getModifiedAmt());
                            break;
                        default:
                    }
                }
            }
        }
    }

    private List<RoomDataEntity> buildRoomDataEntityList(List<QueryHotelProductDetailResponse> ctripResponseList,
                                                         List<QueryHotelProductDetailResponse> qunarResponseList,Long dltRoomId) {
        List<RoomDataEntity> roomDataEntityList = new ArrayList<>();

        // 每个渠道单独一个RoomDataEntityList
//        List<String> channelList = Arrays.asList("Ctrip", "Qunar");
        List<String> channelList = Arrays.asList("Ctrip", "B2B", "ChannelA","Qunar");

        //Ctrip,B2B,ChannelA渠道组装数据
        for (String channel : channelList) {
            RoomDataEntity previousRoomDataEntity = null;
            if(!channel.equals("Qunar")) {
                for (QueryHotelProductDetailResponse response : ctripResponseList) {

                    RoomDataEntity entity = new RoomDataEntity();
                    entity.setRoomId(dltRoomId);
                    entity.setStartDate(DateUtil.dateToString(response.getSaleDate()));
                    entity.setEndDate(DateUtil.dateToString(response.getSaleDate()));
                    entity.setWeekDayIndex("1111111");

                    // 不可卖的场景，构建默认满房的房型数据实体
                    if (1 != response.getIsActive() || null == response.getSaleState()
                            || 0 == response.getSaleState() || null == response.getBaseCurrency() || null == response.getCtripPrice()
                            || null == response.getSaleChannelCurrency() || null == response.getQuotaState()
                            || null == response.getQuotaNum()) {

                        this.buildFullRoomRoomDataEntity(response, entity, channel);
                    } else {

                        RoomPriceModel roomPriceModel = new RoomPriceModel();
                        RoomStatusModel roomStatusModel = new RoomStatusModel();
                        RoomInventoryModel roomInventoryModel = new RoomInventoryModel();

                        // 设置房价
                        this.setRoomPriceModel(response, roomPriceModel, channel);
                        // 设置房态房量
                        this.setRoomStatusAndInventory(response, roomStatusModel, roomInventoryModel, channel);

                        entity.setRoomPriceModel(roomPriceModel);
                        entity.setRoomStatusModel(roomStatusModel);
                        entity.setRoomInventoryModel(roomInventoryModel);
                    }

                    // 设置售卖规则
                    SaleRuleModel saleRuleModel = new SaleRuleModel();
                    this.setSaleRuleModel(response, saleRuleModel, channel);
                    entity.setSaleRuleModel(saleRuleModel);

                    // 将不同日期，但是房价、房态、房量、售卖规则一样的，聚合到一个roomDateEntity对象，只是更新一下endDate即可
                    if (null == previousRoomDataEntity || !this.compareRoomDateEntity(previousRoomDataEntity, entity)) {
                        roomDataEntityList.add(entity);
                        previousRoomDataEntity = entity;
                    } else {
                        roomDataEntityList.get(roomDataEntityList.size() - 1).setEndDate(DateUtil.dateToString(response.getSaleDate()));
                        previousRoomDataEntity.setEndDate(DateUtil.dateToString(response.getSaleDate()));
                    }
                }
            }else {
                for (QueryHotelProductDetailResponse response : qunarResponseList) {

                    RoomDataEntity entity = new RoomDataEntity();
                    entity.setRoomId(dltRoomId);
                    entity.setStartDate(DateUtil.dateToString(response.getSaleDate()));
                    entity.setEndDate(DateUtil.dateToString(response.getSaleDate()));
                    entity.setWeekDayIndex("1111111");

                    // 不可卖的场景，构建默认满房的房型数据实体
                    if (1 != response.getIsActive() || null == response.getSaleState()
                            || 0 == response.getSaleState()
                            || null == response.getBaseCurrency() || null == response.getCtripPrice()
                            || null == response.getSaleChannelCurrency() || null == response.getQuotaState()
                            || null == response.getQuotaNum()) {

                        this.buildFullRoomRoomDataEntity(response, entity, channel);
                    } else {

                        RoomPriceModel roomPriceModel = new RoomPriceModel();
                        RoomStatusModel roomStatusModel = new RoomStatusModel();
                        RoomInventoryModel roomInventoryModel = new RoomInventoryModel();

                        // 设置房价
                        this.setRoomPriceModel(response, roomPriceModel, channel);
                        // 设置房态房量
                        this.setRoomStatusAndInventory(response, roomStatusModel, roomInventoryModel, channel);

                        entity.setRoomPriceModel(roomPriceModel);
                        entity.setRoomStatusModel(roomStatusModel);
                        entity.setRoomInventoryModel(roomInventoryModel);
                    }

                    // 设置售卖规则
                    SaleRuleModel saleRuleModel = new SaleRuleModel();
                    this.setSaleRuleModel(response, saleRuleModel, channel);
                    entity.setSaleRuleModel(saleRuleModel);

                    // 将不同日期，但是房价、房态、房量、售卖规则一样的，聚合到一个roomDateEntity对象，只是更新一下endDate即可
                    if (null == previousRoomDataEntity || !this.compareRoomDateEntity(previousRoomDataEntity, entity)) {
                        roomDataEntityList.add(entity);
                        previousRoomDataEntity = entity;
                    } else {
                        roomDataEntityList.get(roomDataEntityList.size() - 1).setEndDate(DateUtil.dateToString(response.getSaleDate()));
                        previousRoomDataEntity.setEndDate(DateUtil.dateToString(response.getSaleDate()));
                    }
                }
            }

        }
        return roomDataEntityList;
    }

    private Boolean compareRoomDateEntity(RoomDataEntity previous, RoomDataEntity current) {

        if (null == previous) {
            return false;
        }

        if (null == previous.getRoomId() || null == current.getRoomId()
                || !previous.getRoomId().equals(current.getRoomId())) {
            return false;
        }

        RoomPriceModel previousRoomPriceModel = previous.getRoomPriceModel();
        RoomPriceModel currentRoomPriceModel = current.getRoomPriceModel();
        if (null == previousRoomPriceModel || null == currentRoomPriceModel
                || !previousRoomPriceModel.toString().equals(currentRoomPriceModel.toString())) {
            return false;
        }

        RoomStatusModel previousRoomStatusModel = previous.getRoomStatusModel();
        RoomStatusModel currentRoomStatusModel = current.getRoomStatusModel();
        if (null == previousRoomStatusModel || null == currentRoomStatusModel
                || !previousRoomStatusModel.toString().equals(currentRoomStatusModel.toString())) {
            return false;
        }

        RoomInventoryModel previousRoomInventoryModel = previous.getRoomInventoryModel();
        RoomInventoryModel currentRoomInventoryModel = current.getRoomInventoryModel();
        if (null == previousRoomInventoryModel || null == currentRoomInventoryModel
                || !previousRoomInventoryModel.toString().equals(currentRoomInventoryModel.toString())) {
            return false;
        }

        SaleRuleModel previousSaleRuleModel = previous.getSaleRuleModel();
        SaleRuleModel currentSaleRuleModel = current.getSaleRuleModel();
        if (null == previousSaleRuleModel || null == currentSaleRuleModel
                || !previousSaleRuleModel.toString().equals(currentSaleRuleModel.toString())) {
            return false;
        }

        return true;
    }

    private RoomDataEntity buildFullRoomRoomDataEntity(QueryHotelProductDetailResponse response, RoomDataEntity entity, String channel) {
        // 设置房价
        RoomPriceModel roomPriceModel = new RoomPriceModel();
        roomPriceModel.setRoomPrice(BigDecimal.ZERO);
        roomPriceModel.setTax(BigDecimal.ZERO);
        //价格默认人民币
        //roomPriceModel.setCurrency(null == response.getSaleChannelCurrency() ? "CNY" : response.getSaleChannelCurrency());
        roomPriceModel.setCurrency("CNY");
        roomPriceModel.setChannel(channel);
        //早餐转换
        int breakfast = 0;
        if(StringUtil.isValidString(response.getBreakfastNum())) {
            if(Integer.valueOf(response.getBreakfastNum()) > 0){
                breakfast = Integer.valueOf(response.getBreakfastNum()) - 1;
            }
        }

        roomPriceModel.setBreakfast(breakfast);

        // 设置房态房量
        RoomStatusModel roomStatusModel = new RoomStatusModel();
        roomStatusModel.setSaleStatus(0);
        roomStatusModel.setChannel(channel);

        RoomInventoryModel roomInventoryModel = new RoomInventoryModel();
        roomInventoryModel.setPreservedQuantity(0);
        roomInventoryModel.setUnPreservedQuantity(0);
        roomInventoryModel.setAutoCloseRoom(1);
        roomInventoryModel.setChannel(channel);

        entity.setRoomPriceModel(roomPriceModel);
        entity.setRoomStatusModel(roomStatusModel);
        entity.setRoomInventoryModel(roomInventoryModel);
        return entity;
    }

    private void setRoomPriceModel(QueryHotelProductDetailResponse response, RoomPriceModel roomPriceModel, String channel) {
        roomPriceModel.setRoomPrice(response.getCtripPrice());
        roomPriceModel.setTax(BigDecimal.ZERO);
        roomPriceModel.setCurrency(StringUtil.isValidString(response.getSaleChannelCurrency()) ? "CNY" : response.getSaleChannelCurrency());
        //早餐转换
        int breakfast = 0;
        if(StringUtil.isValidString(response.getBreakfastNum())) {
//            if(Integer.valueOf(response.getBreakfastNum()) > 0){
                breakfast = Integer.valueOf(response.getBreakfastNum());
//            }
        }
        roomPriceModel.setBreakfast(breakfast);
        roomPriceModel.setChannel(channel);
    }

    private void setRoomStatusAndInventory(QueryHotelProductDetailResponse response,
                                           RoomStatusModel roomStatusModel, RoomInventoryModel roomInventoryModel, String channel) {

        roomStatusModel.setChannel(channel);
        roomInventoryModel.setAutoCloseRoom(1);
        roomInventoryModel.setChannel(channel);

        //当天是无房态情况
        if (null == response.getQuotaState() || 0 == Integer.valueOf(response.getQuotaState())) {//房态为空或者关房
            roomStatusModel.setSaleStatus(0);
            roomInventoryModel.setPreservedQuantity(0);
            roomInventoryModel.setUnPreservedQuantity(0);
        } else if (1 == Integer.valueOf(response.getQuotaState())) {//开房
            if (null == response.getQuotaNum() || (response.getQuotaNum() <= 0 && (null == response.getOverdraft() || response.getOverdraft() <= 0))) {
                roomStatusModel.setSaleStatus(0);
                roomInventoryModel.setPreservedQuantity(0);
                roomInventoryModel.setUnPreservedQuantity(0);
            } else {
                roomStatusModel.setSaleStatus(1);
                roomInventoryModel.setPreservedQuantity(response.getQuotaNum());
                roomInventoryModel.setUnPreservedQuantity(0);
            }
        }
    }

    /**
     * 组装售卖规则
     * 代理通售卖规则设置总结如下：
     * ctrip：    可通过接口推送，且可默认到天
     * qunar:     可以在静态售卖规则中设置房型维度的取消规则默认值
     * channela:  可以在静态售卖规则中设置房型维度的取消规则默认值
     * B2B:       取消规则都是默认的不可取消
     * @param response
     * @param saleRuleModel
     * @param channel
     */
    private void setSaleRuleModel(QueryHotelProductDetailResponse response, SaleRuleModel saleRuleModel, String channel) {

        saleRuleModel.setChannel(channel);
        if (channel.toLowerCase().equals("ctrip")) {
            CtripSellRule ctripSellRule = new CtripSellRule();
            ctripSellRule.setLatestBookingTimeOfDays(null != response.getBookDays() ? response.getBookDays().shortValue() : 0);
            ctripSellRule.setLatestBookingTimeOfHours(null != response.getBookTime() ? Short.valueOf(response.getBookTime()) : 23);
            ctripSellRule.setCancelType((short)1);
            ctripSellRule.setLatestconfirmTimeOfDays((short)0);
            ctripSellRule.setLatestconfirmTimeOfHours((short)18);
            saleRuleModel.setCtripSellRule(ctripSellRule);
        } else {
            SellingRule sellingRule = new SellingRule();
            sellingRule.setLatestconfirmTimeOfDays((short)0);
            sellingRule.setLatestconfirmTimeOfHours((short)18);
            saleRuleModel.setSellingRule(sellingRule);
        }

    }
}
