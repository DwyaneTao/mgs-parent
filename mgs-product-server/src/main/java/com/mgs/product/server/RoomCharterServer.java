package com.mgs.product.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.RoomCharterDTO;
import com.mgs.product.dto.RoomCharterQueryDTO;

import com.mgs.product.dto.RoomCharterQueryQuestDTO;
import com.mgs.product.service.RoomCharterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/7/18 17:01
 **/
@RestController
@Slf4j
@RequestMapping("/room")
public class RoomCharterServer {
    @Autowired
    private RoomCharterService  roomCharterService;

    /**
     * 新增包房
     * @param roomCharterDTO
     * @return
     */
    @PostMapping(value = "/addRoomCharter")
    public Response addRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO){
        Response response = new Response();
        try {
            response = roomCharterService.addRoomCharter(roomCharterDTO);
        } catch (Exception e) {
            log.error("addRoomCharter-server error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return  response;
    }
    /**
     * 修改包房信息
     */
    @PostMapping(value = "/modifyRoomCharter")
    public Response modifyRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO){
        Response response = new Response();
        try {
            response = roomCharterService.modifyRoomCharter(roomCharterDTO);
        } catch (Exception e) {
            log.error("modifyRoomCharter-server error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return  response;
    }
    /**
     * 按条件查询包房管理信息
     */
    @PostMapping(value = "/queryRoomCharterList")
    public Response queryRoomCharterList(@RequestBody RoomCharterQueryQuestDTO request){
        Response response = new Response();
        try {
            PaginationSupportDTO<RoomCharterQueryDTO> roomCharterQueryDTOPaginationSupportDTO = roomCharterService.queryRoomCharterList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(roomCharterQueryDTOPaginationSupportDTO);
        } catch (Exception e) {
            log.error("queryRoomCharterList-server error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return  response;
    }
    /**
     * 查询包房款详情
     */
    @PostMapping(value = "/queryRoomCharterDetail")
    public Response queryRoomCharterDetail(@RequestBody RoomCharterQueryQuestDTO request){
        Response response = new Response();
        try {
            response = roomCharterService.queryRoomCharterDetail(request);
        } catch (Exception e) {
            log.error("queryRoomCharterDetail-server error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return  response;
    }

    /**
     * 包房销量表
     * @param queryProductRequestDTO
     * @return
     */
    @PostMapping("/charterRoomSales")
    public Response charterRoomSales(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
        Response response = null;
        try {
            response = roomCharterService.charterRoomSales(queryProductRequestDTO);
        } catch (Exception e) {
            log.error("charterRoomSales-server error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
