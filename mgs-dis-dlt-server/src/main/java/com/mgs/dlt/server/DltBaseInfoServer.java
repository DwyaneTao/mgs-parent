package com.mgs.dlt.server;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.common.constant.InitData;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dlt.domain.DltCityPO;
import com.mgs.dlt.domain.DltHotelPO;
import com.mgs.dlt.domain.DltMasterRoomPO;
import com.mgs.dlt.request.base.Pager;
import com.mgs.dlt.request.base.PagingSettings;
import com.mgs.dlt.request.base.PagingType;
import com.mgs.dlt.request.base.SearchCandidate;
import com.mgs.dlt.request.dto.*;
import com.mgs.dlt.response.base.PagingInfo;
import com.mgs.dlt.response.dto.*;
import com.mgs.dlt.service.DltBaseInfoService;
import com.mgs.dlt.utils.DltInterfaceInvoker;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:38
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/dlt/baseinfo")
public class DltBaseInfoServer {

    @Autowired
    private DltBaseInfoService dltBaseInfoService;

    @RequestMapping("/queryCityInfo")
    public Response queryCityInfo() {
        log.info("start queryCityInfo");
        Response response = new Response(1);
        try {
            GetDltCityInfoRequest getDltCityInfoRequest = new GetDltCityInfoRequest();
            PagingSettings pagingSettings = new PagingSettings();
            pagingSettings.setCurrentPageIndex(1);
            pagingSettings.setPageSize(100);
            getDltCityInfoRequest.setPagingSettings(pagingSettings);
            SearchCandidate searchCandidate = new SearchCandidate();
            searchCandidate.setCountryID(1);
            getDltCityInfoRequest.setSearchCandidate(searchCandidate);

            List<DltCityInfo> dltCityInfoList = new ArrayList<>();
            this.queryCityInfoFromDlt(getDltCityInfoRequest,dltCityInfoList,InitData.merchantCode);
            if (!CollectionUtils.isEmpty(dltCityInfoList)) {
                List<DltCityPO> dltCityPOList = BeanUtil.transformList(dltCityInfoList,DltCityPO.class);
                dltBaseInfoService.batchInsertDltCityInfo(dltCityPOList);
                log.info("queryCityInfo count: " + dltCityInfoList.size());
            }
        } catch (Exception e) {
            log.error("queryCityInfo-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping("/queryHotelInfo")
    public Response queryHotelInfo() {
        log.info("start queryHotelInfo");
        Response response = new Response(1);
        try {
            GetDltHotelRequest getDltHotelRequest = new GetDltHotelRequest();
            Pager pager = new Pager();
            pager.setPageIndex(1);
            pager.setPageSize(200);
            getDltHotelRequest.setPager(pager);

            List<DltHotel> dltHotelList = new ArrayList<>();
            this.queryHotelInfoFromDlt(getDltHotelRequest,dltHotelList,InitData.merchantCode);
            if (!CollectionUtils.isEmpty(dltHotelList)) {
                List<DltHotelPO> dltHotelPOList = BeanUtil.transformList(dltHotelList,DltHotelPO.class);

                //酒店去重
                dltHotelPOList = this.hotelDuplicateRemoval(dltHotelPOList);
                if (!CollectionUtils.isEmpty(dltHotelList)) {
                    dltBaseInfoService.batchInsertDltHotelInfo(dltHotelPOList);
                    log.info("queryHotelInfo" + dltHotelPOList.size());
                }
            }
        } catch (Exception e) {
            log.error("queryHotelInfo-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping("/queryMasterRoomInfo")
    public Response queryMasterRoomInfo() {
        Response response = new Response(1);
        try {
            GetDltMasterRoomRequest getDltMasterRoomRequest = null;
            GetDltHotelRoomStaticInfoRequest getDltHotelRoomStaticInfoRequest = null;
            GetDltMasterRoomResponse getDltMasterRoomResponse = null;
            GetDltHotelRoomStaticInfoResponse getDltHotelRoomStaticInfoResponse = null;
            Map<Long,Long> roomMap = null;//物理房型和子房型map
            List<String> returnDataTypeList = new ArrayList<>();
            returnDataTypeList.add("BasicRoomTypeSimpleEntity");
            returnDataTypeList.add("RoomTypeSimpleEntity");

            List<DltHotelPO> dltHotelList = dltBaseInfoService.queryAllDltHotel();

            if (!CollectionUtils.isEmpty(dltHotelList)) {
                List<DltMasterRoomPO> dltMasterRoomPOList = new ArrayList<>();
                for (DltHotelPO dltHotelPO : dltHotelList) {
                    getDltMasterRoomRequest = new GetDltMasterRoomRequest();
                    getDltMasterRoomRequest.setHotelID(dltHotelPO.getHotelID());

                    getDltHotelRoomStaticInfoRequest = new GetDltHotelRoomStaticInfoRequest();
                    getDltHotelRoomStaticInfoRequest.setHotelId(dltHotelPO.getHotelID());
                    getDltHotelRoomStaticInfoRequest.setReturnDataTypeList(returnDataTypeList);
                    //查询母物理房型
                    getDltMasterRoomResponse = DltInterfaceInvoker.invoke(getDltMasterRoomRequest,InitData.merchantCode);
                    getDltHotelRoomStaticInfoResponse = DltInterfaceInvoker.invoke(getDltHotelRoomStaticInfoRequest,InitData.merchantCode);
                    roomMap = new HashMap<>();
                    if (null != getDltHotelRoomStaticInfoResponse && null != getDltHotelRoomStaticInfoResponse.getRoomStaticInfos()
                            && null != getDltHotelRoomStaticInfoResponse.getRoomStaticInfos().getRoomStaticInfo()) {
                        log.info("携程查询子房型返回信息：" + JSON.toJSONString(getDltHotelRoomStaticInfoResponse.getRoomStaticInfos().getRoomStaticInfo()));
                        for (RoomStaticInfo roomStaticInfo : getDltHotelRoomStaticInfoResponse.getRoomStaticInfos().getRoomStaticInfo()) {
                            if (null != roomStaticInfo.getRoomTypeInfos() && !CollectionUtils.isEmpty(roomStaticInfo.getRoomTypeInfos().getRoomTypeInfo())) {
                                for (RoomTypeInfo roomTypeInfo : roomStaticInfo.getRoomTypeInfos().getRoomTypeInfo()) {
                                    roomMap.put(roomTypeInfo.getMasterBasicRoomTypeId(),roomTypeInfo.getRoomTypeId());
                                    log.info("子房型信息：" + JSON.toJSONString(roomMap));
                                }
                            }
                        }
                    }
                    if (null != getDltMasterRoomResponse && null != getDltMasterRoomResponse.getResultStatus() && 0 == getDltMasterRoomResponse.getResultStatus().getResultCode()
                            && null != getDltMasterRoomResponse.getDltBasicRoomTypeEntityList()) {
                        List<DltMasterRoomPO> dltMasterRoomPOListTemp = BeanUtil.transformList(getDltMasterRoomResponse.getDltBasicRoomTypeEntityList(),DltMasterRoomPO.class);
                        for (DltMasterRoomPO dltMasterRoomPO : dltMasterRoomPOListTemp) {
                            dltMasterRoomPO.setMasterHotelID(dltHotelPO.getMasterHotelID());
                            dltMasterRoomPO.setHotelID(dltHotelPO.getHotelID());
                            if (null != roomMap && null != roomMap.get(dltMasterRoomPO.getMasterBasicRoomId().longValue())) {
                                dltMasterRoomPO.setBasicRoomTypeID(roomMap.get(dltMasterRoomPO.getMasterBasicRoomId().longValue()).intValue());
                            }
                        }
                        dltMasterRoomPOList.addAll(dltMasterRoomPOListTemp);
                    }
                }
                //房型去重
                dltMasterRoomPOList = this.roomDuplicateRemoval(dltMasterRoomPOList);
                if (!CollectionUtils.isEmpty(dltMasterRoomPOList)) {
                    dltBaseInfoService.batchInsertDltMasterRoomInfo(dltMasterRoomPOList);
                }
            }
        } catch (Exception e) {
            log.error("queryMasterRoomInfo-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    private void queryCityInfoFromDlt(GetDltCityInfoRequest getDltCityInfoRequest,List<DltCityInfo> dltCityInfoList, String merchantCode) {

        GetDltCityInfoResponse response = DltInterfaceInvoker.invoke(getDltCityInfoRequest,merchantCode);
        if (null != response && null != response.getResultStatus() && 0 == response.getResultStatus().getResultCode()
                && null != response.getCityInfoList() && null != response.getCityInfoList().getCityInfo()) {
            dltCityInfoList.addAll(response.getCityInfoList().getCityInfo());

            // 递归分页获取数据
            PagingInfo pt = response.getPagingInfo();
            if (null != pt && pt.getTotalPages() > pt.getCurrentPageIndex()) {
                getDltCityInfoRequest.getPagingSettings().setCurrentPageIndex(pt.getCurrentPageIndex() + 1);
                this.queryCityInfoFromDlt(getDltCityInfoRequest,dltCityInfoList,merchantCode);
            }
        }
    }

    private void queryHotelInfoFromDlt(GetDltHotelRequest getDltHotelRequest,List<DltHotel> dltHotelList, String merchantCode) {

        GetDltHotelResponse response = DltInterfaceInvoker.invoke(getDltHotelRequest,merchantCode);
        if (null != response && null != response.getResultStatus() && 0 == response.getResultStatus().getResultCode()
                && null != response.getDltHotelEntityList()) {
            dltHotelList.addAll(response.getDltHotelEntityList());

            // 递归分页获取数据
            Pager pt = response.getPager();
            if (null != pt && pt.getTotalPages() > pt.getPageIndex()) {
                getDltHotelRequest.getPager().setPageIndex(pt.getPageIndex() + 1);
                this.queryHotelInfoFromDlt(getDltHotelRequest,dltHotelList,merchantCode);
            }
        }
    }

    //酒店去重
    private List<DltHotelPO> hotelDuplicateRemoval(List<DltHotelPO> dltHotelPOList) {
        if (!CollectionUtils.isEmpty(dltHotelPOList)) {
            //查询库存中的酒店
            List<DltHotelPO> existsHotelList = dltBaseInfoService.queryAllDltHotel();
            if (!CollectionUtils.isEmpty(existsHotelList)) {
                Map<Integer,Integer> existsHotelMap = existsHotelList.stream().collect(Collectors.toMap(DltHotelPO::getHotelID,DltHotelPO::getHotelID));
                for (int i = 0 ; i < dltHotelPOList.size() ; i ++) {
                    if (null != existsHotelMap.get(dltHotelPOList.get(i).getHotelID())) {
                        dltHotelPOList.remove(i--);
                    }
                }
            }
        }
        return dltHotelPOList;
    }

    //房型去重
    private List<DltMasterRoomPO> roomDuplicateRemoval(List<DltMasterRoomPO> dltMasterRoomPOList) {
        if (!CollectionUtils.isEmpty(dltMasterRoomPOList)) {
            //查询库存中的房型
            List<DltMasterRoomPO> existsRoomList = dltBaseInfoService.queryAllDltMasterRoom();
            if (!CollectionUtils.isEmpty(existsRoomList)) {
                Map<Integer,Integer> existsRoomMap = existsRoomList.stream().collect(Collectors.toMap(DltMasterRoomPO::getMasterBasicRoomId,DltMasterRoomPO::getMasterBasicRoomId));
                for (int i = 0 ; i < dltMasterRoomPOList.size() ; i ++) {
                    if (null != existsRoomMap.get(dltMasterRoomPOList.get(i).getMasterBasicRoomId())) {
                        dltMasterRoomPOList.remove(i--);
                    }
                }
            }
        }
        return dltMasterRoomPOList;
    }
}
