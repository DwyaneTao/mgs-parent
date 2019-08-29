package com.mgs.product.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.RoomCharterDTO;
import com.mgs.product.dto.RoomCharterQueryDTO;
import com.mgs.product.dto.RoomCharterQueryQuestDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author py
 * @date 2019/7/18 16:34
 * 包房
 **/
public interface RoomCharterService {
    /**
     * 新增包房
     * @param roomCharterDTO
     * @return roomCharterId
     */
    Response addRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO);
    /**
     * 修改包房信息
     */
    Response modifyRoomCharter(@RequestBody RoomCharterDTO roomCharterDTO);
    /**
     * 按条件查询包房信息
     */
    PaginationSupportDTO<RoomCharterQueryDTO> queryRoomCharterList(@RequestBody RoomCharterQueryQuestDTO request);
    /**
     * 包房款详情
     */
    Response  queryRoomCharterDetail(@RequestBody RoomCharterQueryQuestDTO request);

    /**
     * 包房销量表
     */
    Response charterRoomSales(QueryProductRequestDTO request);
}
