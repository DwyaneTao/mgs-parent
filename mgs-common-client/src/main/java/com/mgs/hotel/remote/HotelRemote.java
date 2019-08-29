package com.mgs.hotel.remote;

import com.mgs.common.Response;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.dto.BasicPhotoDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.bind.ValidationEvent;
import java.util.Map;

@FeignClient(value = "mgs-common-server")
public interface HotelRemote {

    @PostMapping(value = "/hotel/addHotelInfo", produces = {"application/json;charset=UTF-8"})
    Response addHotelInfo(@RequestBody BasicHotelInfoDTO basicHotelInfoDTO);

    @PostMapping(value = "/hotel/modifyHotelInfo", produces = {"application/json;charset=UTF-8"})
    Response modifyHotelInfo(@RequestBody BasicHotelInfoDTO basicHotelInfoDTO);

    @PostMapping(value = "/hotel/deleteHotelInfo", produces = {"application/json;charset=UTF-8"})
    Response deleteHotelInfo(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/modifyHotelSort", produces = {"application/json;charset=UTF-8"})
    Response modifyHotelSort(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryHotelListBySort", produces = {"application/json;charset=UTF-8"})
    Response queryHotelListBySort(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryHotelList", produces = "application/json;charset=UTF-8")
    Response queryHotelList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryHotelDetail", produces = {"application/json;charset=UTF-8"})
    Response queryHotelDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/addRoomInfo", produces = {"application/json;charset=UTF-8"})
    Response addRoomInfo(@RequestBody BasicRoomInfoDTO basicRoomInfoDTO);

    @PostMapping(value = "hotel/addHotelPhoto", produces = {"application/json;charset=UTF-8"})
    Response addHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO);

    @PostMapping(value = "/hotel/deleteHotelPhoto", produces = {"application/json;charset=UTF-8"})
    Response deleteHotelPhoto(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/modifyHotelPhoto", produces = {"application/json;charset=UTF-8"})
    Response modifyHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO);

    @PostMapping(value = "/hotel/modifyRoomInfo", produces = {"application/json;charset=UTF-8"})
    Response modifyRoomInfo(@RequestBody BasicRoomInfoDTO basicRoomInfoDTO);

    @PostMapping(value = "/hotel/deleteRoomInfo", produces = {"application/json;charset=UTF-8"})
    Response deleteRoomInfo(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryHotelPhotoList", produces = {"application/json;charset=UTF-8"})
    Response queryHotelPhotoList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryRoomList", produces = {"application/json;charset=UTF-8"})
    Response queryRoomList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryRoomDetail", produces = {"application/json;charset=UTF-8"})
    Response queryRoomDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/setDefaultPhoto", produces = {"application/json;charset=UTF-8"})
    Response setDefaultPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO);

    @PostMapping(value = "/hotel/moveHotelPhoto", produces = {"application/json;charset=UTF-8"})
    Response moveHotelPhoto(@RequestBody BasicPhotoDTO basicPhotoDTO);

    @PostMapping(value = "/hotel/examineRoomName",produces = {"application/json;charset=UTF-8"})
    Response examineRoomName(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/examineHotelName",produces = {"application/json;charset=UTF-8"})
    Response examineHotelName(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/hotel/queryHotelLogList",produces = {"application/json;charset=UTF-8"})
    Response queryHotelLog(@RequestBody Map<String, String> requestMap);
}
