package com.mgs.controller.hotel;

import com.mgs.common.BaseController;
import com.mgs.common.BaseRequest;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.dto.BasicHotelLogDTO;
import com.mgs.hotel.dto.BasicPhotoDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController extends BaseController {

    @Autowired
    private HotelRemote hotelRemote;

    /**
     * 新增酒店基本信息
     * @param basicHotelInfoDTO
     * @return
     */
    @PostMapping(value = "/addHotelInfo", produces = {"application/json;charset=UTF-8"})
    public Response addHotelBasicInfo(@RequestBody BasicHotelInfoDTO basicHotelInfoDTO){
        Response response = new Response();
        try{
            if(basicHotelInfoDTO != null && StringUtil.isValidString(basicHotelInfoDTO.getHotelName())
                    && StringUtil.isValidString(basicHotelInfoDTO.getHotelAddress()) && StringUtil.isValidString(basicHotelInfoDTO.getCityCode())
                    && StringUtil.isValidString(basicHotelInfoDTO.getCountryCode()) && StringUtil.isValidString(basicHotelInfoDTO.getMainPhotoUrl())
                    && StringUtil.isValidString(basicHotelInfoDTO.getHotelTel()) && basicHotelInfoDTO.getHotelRank() != null
                    && StringUtil.isValidString(basicHotelInfoDTO.getOpeningYear()) && basicHotelInfoDTO.getRoomQty() != null
                    && StringUtil.isValidString(basicHotelInfoDTO.getHotelTypes()) && StringUtil.isValidString(basicHotelInfoDTO.getHotelSummary())
                    && StringUtil.isValidString(basicHotelInfoDTO.getCheckInTime()) && StringUtil.isValidString(basicHotelInfoDTO.getCheckOutTime())
                    && basicHotelInfoDTO.getPetPolicy() != null && StringUtil.isValidString(basicHotelInfoDTO.getBreakfastPolicy())){
                        basicHotelInfoDTO.setCreatedBy(super.getUserName());
                        basicHotelInfoDTO.setOperatorAccount(super.getLoginName());
                       response = hotelRemote.addHotelInfo(basicHotelInfoDTO);

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/modifyHotelInfo", produces = {"application/json;charset=UTF-8"})
    public Response modifyHotelInfo(@RequestBody BasicHotelInfoDTO basicHotelInfoDTO){
        Response response = new Response();
        try{
            if(basicHotelInfoDTO != null && StringUtil.isValidString(basicHotelInfoDTO.getHotelName())
                    && StringUtil.isValidString(basicHotelInfoDTO.getHotelAddress()) && StringUtil.isValidString(basicHotelInfoDTO.getCityCode())
                    && StringUtil.isValidString(basicHotelInfoDTO.getCountryCode()) && StringUtil.isValidString(basicHotelInfoDTO.getMainPhotoUrl())
                    && StringUtil.isValidString(basicHotelInfoDTO.getHotelTel()) && basicHotelInfoDTO.getHotelRank() != null
                    && StringUtil.isValidString(basicHotelInfoDTO.getOpeningYear()) && basicHotelInfoDTO.getRoomQty() != null
                    && StringUtil.isValidString(basicHotelInfoDTO.getHotelTypes()) && StringUtil.isValidString(basicHotelInfoDTO.getHotelSummary())
                    && StringUtil.isValidString(basicHotelInfoDTO.getCheckInTime()) && StringUtil.isValidString(basicHotelInfoDTO.getCheckOutTime())
                    && basicHotelInfoDTO.getPetPolicy() != null && StringUtil.isValidString(basicHotelInfoDTO.getBreakfastPolicy())
                    && basicHotelInfoDTO.getHotelId() != null){
                basicHotelInfoDTO.setModifiedBy(super.getUserName());
                basicHotelInfoDTO.setOperatorAccount(super.getLoginName());
                response = hotelRemote.modifyHotelInfo(basicHotelInfoDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/deleteHotelInfo", produces = {"application/json;charset=UTF-8"})
    public Response deleteHotelInfo(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))){
                requestMap.put("modifiedBy", super.getUserName());
                requestMap.put("operatorAccount", super.getLoginName());
                response = hotelRemote.deleteHotelInfo(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改酒店排序
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/modifyHotelSort", produces = {"application/json;charset=UTF-8"})
    public Response modifyHotelSort(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))
                    && StringUtil.isValidString(requestMap.get("sortRank"))){
                requestMap.put("orgCode", super.getCompanyCode());
                requestMap.put("modifiedBy", super.getUserName());
                response = hotelRemote.modifyHotelSort(requestMap);
            }else {
                response.setResult(1);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询酒店详情列表(有排序)
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryHotelListBySort", produces = {"application/json;charset=UTF-8"})
    public Response queryHotelListBySort(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("orgCode", super.getCompanyCode());

            if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
            }
            if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
            }

            response = hotelRemote.queryHotelListBySort(requestMap);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }



    /**
     * 查询酒店列表（无排序）
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryHotelList", produces = "application/json;charset=UTF-8")
    public Response queryHotelList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null){
                if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                    requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
                }
                if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                    requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
                }

            }else {
                requestMap.put("pageSize", "10");
                requestMap.put("currentPage", "1");
            }

            response = hotelRemote.queryHotelList(requestMap);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/queryHotelDetail", produces = {"application/json;charset=UTF-8"})
    public Response queryHotelDetail(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))){
                response = hotelRemote.queryHotelDetail(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 增加房型
     * @param basicRoomInfoDTO
     * @return
     */
    @PostMapping(value = "/addRoomInfo", produces = {"application/json;charset=UTF-8"})
    public Response addBasicRoomInfo(@RequestBody BasicRoomInfoDTO basicRoomInfoDTO){
        Response response = new Response();
        try{
            if(basicRoomInfoDTO != null && basicRoomInfoDTO.getHotelId() != null
              && StringUtil.isValidString(basicRoomInfoDTO.getRoomName())
              && StringUtil.isValidString(basicRoomInfoDTO.getMainPhotoUrl())
              && StringUtil.isValidString(basicRoomInfoDTO.getBedTypes())
              && StringUtil.isValidString(basicRoomInfoDTO.getArea())
              && basicRoomInfoDTO.getMaxGuestQty() != null
              && basicRoomInfoDTO.getWindowAvailableType() != null
              && basicRoomInfoDTO.getNetwork() != null){
                basicRoomInfoDTO.setCreatedBy(super.getUserName());
                basicRoomInfoDTO.setOperatorAccount(super.getLoginName());
                response = hotelRemote.addRoomInfo(basicRoomInfoDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改房型
     * @param basicRoomInfoDTO
     * @return
     */
    @PostMapping(value = "/modifyRoomInfo", produces = {"application/json;charset=UTF-8"})
    public Response modifyBasicRoomInfo(@RequestBody BasicRoomInfoDTO basicRoomInfoDTO){
        Response response = new Response();
        try{
            if(basicRoomInfoDTO != null && basicRoomInfoDTO.getRoomId() != null){
                basicRoomInfoDTO.setModifiedBy(super.getUserName());
                basicRoomInfoDTO.setOperatorAccount(super.getLoginName());
                response = hotelRemote.modifyRoomInfo(basicRoomInfoDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除房型
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/deleteRoomInfo", produces = {"application/json;charset=UTF-8"})
    public Response deleteBasicRoomInfo(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("roomId"))){
                requestMap.put("modifiedBy", super.getUserName());
                requestMap.put("operatorAccount", super.getLoginName());
                response = hotelRemote.deleteRoomInfo(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询房型列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryRoomList", produces = {"application/json;charset=UTF-8"})
    public Response queryBasicRoomList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))){

                if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                    requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
                }
                if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                    requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
                }

                response = hotelRemote.queryRoomList(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询房型详情
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryRoomDetail", produces = {"application/json;charset=UTF-8"})
    public Response queryBasicRoomDetail(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("roomId"))){
                response = hotelRemote.queryRoomDetail(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 新增图片
     * @param basicPhotoDTO
     * @return
     */
    @PostMapping(value = "/addHotelPhoto", produces = {"application/json;charset=UTF-8"})
    public Response addHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO){
        Response response = new Response();
        try{
            if(basicPhotoDTO != null && basicPhotoDTO.getHotelId() != null && basicPhotoDTO.getPhotoType() != null){
                basicPhotoDTO.setCreatedBy(super.getUserName());
                basicPhotoDTO.setOperatorAccount(super.getLoginName());
                response = hotelRemote.addHotelPhoto(basicPhotoDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改图片
     * @param basicPhotoDTO
     * @return
     */
    @PostMapping(value = "/modifyHotelPhoto", produces = {"application/json;charset=UTF-8"})
    public Response modifyHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO){
        Response response = new Response();
        try{
            if(basicPhotoDTO != null && basicPhotoDTO.getPhotoId() != null
              && basicPhotoDTO.getHotelId() != null && basicPhotoDTO.getPhotoType() != null){
                basicPhotoDTO.setModifiedBy(super.getUserName());
                basicPhotoDTO.setOperatorAccount(super.getLoginName());
                response = hotelRemote.modifyHotelPhoto(basicPhotoDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除图片
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/deleteHotelPhoto", produces = {"application/json;charset=UTF-8"})
    public Response deleteHotelPhoto(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("photoId"))){
                requestMap.put("modifiedBy", super.getUserName());
                requestMap.put("operatorAccount", super.getLoginName());
                response = hotelRemote.deleteHotelPhoto(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询酒店图片列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryHotelPhotoList",produces = {"application/json;charset=UTF-8"})
    public Response queryHotelPhotoList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))
              && StringUtil.isValidString(requestMap.get("photoType"))){

                if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                    requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
                }
                if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                    requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
                }


                response = hotelRemote.queryHotelPhotoList(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/setDefaultPhoto", produces = {"application/json;charset=UTF-8"})
    public Response setDefaultPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO){
        Response response = new Response();
        try{
            if(basicPhotoDTO != null && basicPhotoDTO.getPhotoId() != null
                    && basicPhotoDTO.getPhotoType() != null && basicPhotoDTO.getHotelId() != null
                    && StringUtil.isValidString(basicPhotoDTO.getPhotoUrl())){
                basicPhotoDTO.setModifiedBy(super.getUserName());
                basicPhotoDTO.setOperatorAccount(super.getLoginName());
                response =  hotelRemote.setDefaultPhoto(basicPhotoDTO);

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    @PostMapping(value = "/moveHotelPhoto", produces = {"application/json;charset=UTF-8"})
    public Response moveHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO){
        Response response = new Response();
        try{
            if(basicPhotoDTO != null && basicPhotoDTO.getPhotoId() != null
                    && basicPhotoDTO.getPhotoType() != null ){
                basicPhotoDTO.setModifiedBy(super.getUserName());
                basicPhotoDTO.setOperatorAccount(super.getLoginName());
                response =  hotelRemote.moveHotelPhoto(basicPhotoDTO);

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/examineRoomName",produces = {"application/json;charset=UTF-8"})
    public Response examineRoomName(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("roomName"))
                    && StringUtil.isValidString(requestMap.get("hotelId"))){
                response = hotelRemote.examineRoomName(requestMap);

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/examineHotelName",produces = {"application/json;charset=UTF-8"})
    public Response examineHotelName(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelName"))){
                response = hotelRemote.examineHotelName(requestMap);

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/queryHotelLogList",produces = {"application/json;charset=UTF-8"})
    public Response queryHotelLogList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))){
                if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                    requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
                }
                if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                    requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
                }

                response = hotelRemote.queryHotelLog(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


}
