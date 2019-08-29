package com.mgs.product.remote;

import com.mgs.common.Response;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.RoomCharterDTO;
import com.mgs.product.dto.RoomCharterQueryQuestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author py
 * @date 2019/7/18 17:09
 **/
@FeignClient(value = "mgs-product-server")
public interface RoomCharterRemote {
    @PostMapping(value = "/room/addRoomCharter")
    public Response addRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO);
    @PostMapping(value = "/room/modifyRoomCharter")
    public Response modifyRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO);
    @PostMapping(value = "/room/queryRoomCharterList")
    public Response queryRoomCharterList(@RequestBody RoomCharterQueryQuestDTO request);
    @PostMapping(value = "/room/queryRoomCharterDetail")
    public Response queryRoomCharterDetail(@RequestBody RoomCharterQueryQuestDTO request);
    @PostMapping("/room/charterRoomSales")
    public Response charterRoomSales(@RequestBody QueryProductRequestDTO queryProductRequestDTO);
}
