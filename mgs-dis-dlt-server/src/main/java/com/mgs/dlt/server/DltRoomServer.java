package com.mgs.dlt.server;

import com.mgs.common.Response;
import com.mgs.common.constant.InitData;
import com.mgs.dlt.domain.DltCityPO;
import com.mgs.dlt.domain.DltHotelPO;
import com.mgs.dlt.domain.DltMasterRoomPO;
import com.mgs.dlt.request.base.Pager;
import com.mgs.dlt.request.base.PagingSettings;
import com.mgs.dlt.request.base.SearchCandidate;
import com.mgs.dlt.request.dto.GetDltCityInfoRequest;
import com.mgs.dlt.request.dto.GetDltHotelRequest;
import com.mgs.dlt.request.dto.GetDltMasterRoomRequest;
import com.mgs.dlt.response.base.PagingInfo;
import com.mgs.dlt.response.dto.*;
import com.mgs.dlt.service.DltBaseInfoService;
import com.mgs.dlt.service.DltRoomService;
import com.mgs.dlt.utils.DltInterfaceInvoker;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.util.BeanUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:38
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/dlt/room")
public class DltRoomServer {

    @Autowired
    private DltRoomService dltRoomService;

    @RequestMapping("/queryNeedPushSaleRoomCount")
    public Response queryNeedPushSaleRoomCount(String companyCode) {
        Response response = new Response(1);
        try {
            if (StringUtil.isValidString(companyCode)) {
                Integer saleRoomCount = dltRoomService.queryNeedPushSaleRoomCount(companyCode);
                response.setModel(saleRoomCount);
            }else {
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("queryNeedPushSaleRoomCount-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping("/pushSaleRoom")
    public Response pushSaleRoom(String companyCode) {
        Response response = new Response(1);
        try {
            if (StringUtil.isValidString(companyCode)) {
                //返回预计推送的产品数
                Integer pushSaleRoomCount = dltRoomService.queryNeedPushSaleRoomCount(companyCode);
                response.setModel("预计推送"+pushSaleRoomCount+"个售卖房型（产品）");
                //推送售卖房型（产品）
                dltRoomService.pushSaleRoom(companyCode);
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("pushSaleRoom-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
