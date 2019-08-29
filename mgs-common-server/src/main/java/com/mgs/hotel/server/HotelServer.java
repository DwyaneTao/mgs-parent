package com.mgs.hotel.server;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.dto.BasicHotelLogDTO;
import com.mgs.hotel.dto.BasicPhotoDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.service.HotelService;
import com.mgs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/hotel")
public class HotelServer {

    @Autowired
    private HotelService hotelService;

    @PostMapping(value = "/addHotelInfo", produces = {"application/json;charset=UTF-8"})
    public Response addHotelInfo(@RequestBody BasicHotelInfoDTO basicHotelInfoDTO) {
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
                int i = hotelService.addHotelInfo(basicHotelInfoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailCode(ErrorCodeEnum.EXIST_HOTEL.errorCode);
                    response.setFailReason(ErrorCodeEnum.EXIST_HOTEL.errorDesc);
                }
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
                int i = hotelService.modifyHotelInfo(basicHotelInfoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.NOT_EXIST_HOTEL.errorDesc);
                    response.setFailCode(ErrorCodeEnum.NOT_EXIST_HOTEL.errorCode);
                }
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

                int i = hotelService.deleteHotelInfo(requestMap);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailCode(ErrorCodeEnum.EXIST_PRODUCT.errorCode);
                    response.setFailReason(ErrorCodeEnum.EXIST_PRODUCT.errorDesc);
                }
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

    @PostMapping(value = "/modifyHotelSort", produces = {"application/json;charset=UTF-8"})
    public Response modifyHotelSort(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))
                    && StringUtil.isValidString(requestMap.get("sortRank"))){
                int i = hotelService.modifyHotelSort(requestMap);

                if(i > 0) {
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.NOT_EXIST_HOTEL.errorDesc);
                    response.setFailCode(ErrorCodeEnum.NOT_EXIST_HOTEL.errorCode);
                }
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

    @PostMapping(value = "/queryHotelListBySort", produces = {"application/json;charset=UTF-8"})
    public Response queryHotelListBySort(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            PageInfo<BasicHotelInfoDTO> hotelList = hotelService.queryHotelListBySort(requestMap);

            response.setResult(1);
            if(hotelList != null){
                PaginationSupportDTO<BasicHotelInfoDTO> supportDTO = new PaginationSupportDTO<BasicHotelInfoDTO>();
                supportDTO.copyProperties(hotelList, BasicHotelInfoDTO.class);
                response.setModel(supportDTO);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/queryHotelList", produces = "application/json;charset=UTF-8")
    public Response queryHotelList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            PageInfo<BasicHotelInfoDTO> pageInfo = hotelService.queryHotelList(requestMap);

            response.setResult(1);
            if(pageInfo != null){
                PaginationSupportDTO<BasicHotelInfoDTO> supportDTO = new PaginationSupportDTO<BasicHotelInfoDTO>();
                supportDTO.copyProperties(pageInfo, BasicHotelInfoDTO.class);
                response.setModel(supportDTO);
            }
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
                BasicHotelInfoDTO basicHotelInfoDTO = hotelService.queryHotelDetail(requestMap);

                response.setResult(1);
                response.setModel(basicHotelInfoDTO);
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


    @PostMapping(value = "/addRoomInfo", produces = {"application/json;charset=UTF-8"})
    public Response addBasicRoomInfo(@RequestBody BasicRoomInfoDTO basicRoomInfoDTO){
        Response response = new Response();
        try{
            if(basicRoomInfoDTO != null && basicRoomInfoDTO.getHotelId() != null
              && StringUtil.isValidString(basicRoomInfoDTO.getRoomName()) && StringUtil.isValidString(basicRoomInfoDTO.getMainPhotoUrl())
              && StringUtil.isValidString(basicRoomInfoDTO.getBedTypes())
              && StringUtil.isValidString(basicRoomInfoDTO.getArea())
              && basicRoomInfoDTO.getMaxGuestQty() != null && basicRoomInfoDTO.getWindowAvailableType() != null
              && basicRoomInfoDTO.getNetwork() != null){
                int i = hotelService.addRoomInfo(basicRoomInfoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailCode(ErrorCodeEnum.EXIST_ROOM.errorCode);
                    response.setFailReason(ErrorCodeEnum.EXIST_ROOM.errorDesc);
                }
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


    @PostMapping(value = "/modifyRoomInfo", produces = {"application/json;charset=UTF-8"})
    public Response modifyBasicRoomInfo(@RequestBody BasicRoomInfoDTO basicRoomInfoDTO){
        Response response = new Response();
        try{
            if(basicRoomInfoDTO != null && basicRoomInfoDTO.getRoomId() != null
              && basicRoomInfoDTO.getHotelId() != null){
               int i =  hotelService.modifyRoomInfo(basicRoomInfoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.NOT_EXIST_ROOM.errorDesc);
                    response.setFailCode(ErrorCodeEnum.NOT_EXIST_ROOM.errorCode);
                }
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

    @PostMapping(value = "/deleteRoomInfo", produces = {"application/json;charset=UTF-8"})
    public Response deleteBasicRoomInfo(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("roomId"))){

                int i  = hotelService.deleteRoomInfo(requestMap);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.EXIST_PRODUCT.errorDesc);
                    response.setFailCode(ErrorCodeEnum.EXIST_PRODUCT.errorCode);
                }
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

    @PostMapping(value = "/queryRoomList", produces = {"application/json;charset=UTF-8"})
    public Response queryBasicRoomList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))){
                PageInfo<BasicRoomInfoDTO> pageInfo = hotelService.queryRoomList(requestMap);

                response.setResult(1);
                if(pageInfo != null){
                    PaginationSupportDTO paginationSupportDTO = new PaginationSupportDTO();
                    paginationSupportDTO.copyProperties(pageInfo, BasicRoomInfoDTO.class);
                    response.setModel(paginationSupportDTO);
                }
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

    @PostMapping(value = "/queryRoomDetail", produces = {"application/json;charset=UTF-8"})
    public Response queryBasicRoomDetail(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("roomId"))){
                BasicRoomInfoDTO basicRoomInfoDTO = hotelService.queryRoomDetail(requestMap);

                response.setResult(1);
                response.setModel(basicRoomInfoDTO);
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

    @PostMapping(value = "/addHotelPhoto", produces = {"application/json;charset=UTF-8"})
    public Response addHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO){
        Response response = new Response();
        try{
            if(basicPhotoDTO != null && basicPhotoDTO.getHotelId() != null && basicPhotoDTO.getPhotoType() != null){
                int i = hotelService.addHotelPhoto(basicPhotoDTO);

                response.setResult(1);
                response.setModel(i);

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
              && basicPhotoDTO.getPhotoType() != null){
               int i =  hotelService.moveHotelPhoto(basicPhotoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.NOT_EXIST_PHOTO.errorDesc);
                    response.setFailCode(ErrorCodeEnum.NOT_EXIST_PHOTO.errorCode);
                }
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

                int i =  hotelService.setDefaultPhoto(basicPhotoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.NOT_EXIST_PHOTO.errorDesc);
                    response.setFailCode(ErrorCodeEnum.NOT_EXIST_PHOTO.errorCode);
                }
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

    @PostMapping(value = "/deleteHotelPhoto", produces = {"application/json;charset=UTF-8"})
    public Response deleteHotelPhoto(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("photoId"))){
                BasicPhotoDTO basicPhotoDTO = new BasicPhotoDTO();
                basicPhotoDTO.setPhotoId(Integer.valueOf(requestMap.get("photoId")));
                basicPhotoDTO.setModifiedBy(requestMap.get("modifiedBy"));
                basicPhotoDTO.setOperatorAccount(requestMap.get("operatorAccount"));

                int i = hotelService.deleteHotelPhoto(basicPhotoDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else {
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.NOT_EXIST_PHOTO.errorDesc);
                    response.setFailCode(ErrorCodeEnum.NOT_EXIST_PHOTO.errorCode);
                }
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

    @PostMapping(value = "/queryHotelPhotoList",produces = {"application/json;charset=UTF-8"})
    public Response queryHotelPhotoList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))
                    && StringUtil.isValidString(requestMap.get("photoType"))){

                PageInfo<BasicPhotoDTO> basicPhotoDTOPageInfo = hotelService.queryHotelPhotoList(requestMap);

                response.setResult(1);
                if(basicPhotoDTOPageInfo != null){
                    PaginationSupportDTO<BasicPhotoDTO> supportDTO = new PaginationSupportDTO<BasicPhotoDTO>();
                    supportDTO.copyProperties(basicPhotoDTOPageInfo, BasicPhotoDTO.class);
                    response.setModel(supportDTO);
                }

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
                int i = hotelService.examineRoomName(requestMap);

                response.setResult(1);
                response.setModel(i);

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

                int i = hotelService.examineHotelName(requestMap);

                response.setResult(1);
                response.setModel(i);

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
    public Response queryHotelLogList(@RequestBody Map<String, String> requestMap) {
        Response response = new Response();
        try {
            if (requestMap != null && StringUtil.isValidString(requestMap.get("hotelId"))) {
                PageInfo<BasicHotelLogDTO> basicHotelLogDTO = hotelService.queryHotelLogList(requestMap);

                response.setResult(1);
                if(basicHotelLogDTO != null){
                    PaginationSupportDTO<BasicHotelLogDTO> supportDTO = new PaginationSupportDTO<BasicHotelLogDTO>();
                    supportDTO.copyProperties(basicHotelLogDTO, BasicHotelLogDTO.class);
                    response.setModel(supportDTO);
                }

            } else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

}
