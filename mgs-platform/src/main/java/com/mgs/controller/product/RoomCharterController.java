package com.mgs.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.AgentListRequest;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.RoomCharterDTO;
import com.mgs.product.dto.RoomCharterQueryQuestDTO;
import com.mgs.product.remote.RoomCharterRemote;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/7/18 17:11
 **/
@RestController
@RequestMapping("/room")
@Slf4j
public class RoomCharterController extends BaseController {
    @Autowired
    private RoomCharterRemote roomCharterRemote;

    /**
     * 添加包房信息
     * @param roomCharterDTO
     * @return
     */
    @PostMapping(value = "/addRoomCharter")
    public Response addRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO){
        Response response = new Response();
        try {
            roomCharterDTO.setCreatedBy(getUserName());
            roomCharterDTO.setCompanyCode(getCompanyCode());
            //参数校验：供应商编码，酒店Id，包房批次名，包房间夜数，有效开始时间，有效结束时间，合同金额
            if (null != roomCharterDTO
                    && null != roomCharterDTO.getHotelId()
                    && null != roomCharterDTO.getNightQty()
                    && null != roomCharterDTO.getStartDate()
                    && null != roomCharterDTO.getEndDate()
                    && null != roomCharterDTO.getContractAmt()
                    && StringUtil.isValidString(roomCharterDTO.getSupplierCode())
                    && StringUtil.isValidString(roomCharterDTO.getRoomCharterName())) {
                response = roomCharterRemote.addRoomCharter(roomCharterDTO);
            }else {
                log.error("addRoomCharter-controller error,params:{}", JSONObject.toJSONString(roomCharterDTO));
                response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("addRoomCharter-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改包房信息
     * @param roomCharterDTO
     * @return
     */
    @PostMapping(value = "/modifyRoomCharter")
    public Response modifyRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO){
        Response response = new Response();
        try {
            roomCharterDTO.setModifiedBy(getUserName());
            //参数校验：包房Id,供应商编码，酒店Id，包房批次名，包房间夜数，有效开始时间，有效结束时间，合同金额
            if (null != roomCharterDTO
                    && null !=roomCharterDTO.getRoomCharterId()
                    && null != roomCharterDTO.getHotelId()
                    && null != roomCharterDTO.getNightQty()
                    && null != roomCharterDTO.getStartDate()
                    && null != roomCharterDTO.getEndDate()
                    && null != roomCharterDTO.getContractAmt()
                    && StringUtil.isValidString(roomCharterDTO.getSupplierCode())
                    && StringUtil.isValidString(roomCharterDTO.getRoomCharterName())) {
                response = roomCharterRemote.modifyRoomCharter(roomCharterDTO);
            }else {
                log.error("modifyRoomCharter-controller error,params:{}", JSONObject.toJSONString(roomCharterDTO));
                response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("modifyRoomCharter-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    /**
     * 按条件查询包房信息
     */
    @RequestMapping(value = "/queryRoomCharterList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public  Response queryRoomCharterList(@RequestBody RoomCharterQueryQuestDTO request){
        Response response = new Response();
        try{
            request.setCompanyCode(getCompanyCode());
            if(request.getOverdueStatus()!=null){
                if(request.getOverdueStatus().equals(-1
                )){
                    request.setOverdueStatus(null);
                }}
            response = roomCharterRemote.queryRoomCharterList(request);
        } catch (Exception e) {
            log.error("queryAgentList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    @RequestMapping(value = "/queryRoomCharterDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public  Response queryRoomCharterDetail(@RequestBody RoomCharterQueryQuestDTO request){
        Response response = new Response();
        try{
            if(request.getRoomCharterCode()!=null){
            response = roomCharterRemote.queryRoomCharterDetail(request);
            }
        } catch (Exception e) {
            log.error("queryAgentList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/charterRoomSales")
    public Response charterRoomSales(@RequestBody QueryProductRequestDTO queryProductRequestDTO){
        Response response = null;
        try {
            //参数校验：酒店Id，开始日期，结束日期
            if (null != queryProductRequestDTO
                    && null != queryProductRequestDTO.getHotelId()
                    && StringUtil.isValidString(queryProductRequestDTO.getStartDate())
                    && StringUtil.isValidString(queryProductRequestDTO.getEndDate())) {
                queryProductRequestDTO.setCompanyCode(getCompanyCode());
                response = roomCharterRemote.charterRoomSales(queryProductRequestDTO);
            }else {
                log.error("charterRoomSales-controller error,params:{}", JSONObject.toJSONString(queryProductRequestDTO));
                response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("charterRoomSales-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
