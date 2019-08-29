package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.RoomCharterPO;
import com.mgs.product.dto.ProductTempDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.QueryRoomCharterDetailDTO;
import com.mgs.product.dto.RoomCharterQueryDTO;
import com.mgs.product.dto.RoomCharterQueryQuestDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author py
 * @date 2019/7/18 16:32
 * 包房
 **/
public interface RoomCharterMapper extends MyMapper<RoomCharterPO> {
    /**
     * 按条件查询包房列表
     */
    List<RoomCharterQueryDTO> queryRoomCharterList(@Param("request") RoomCharterQueryQuestDTO request);
    /**
     * 查询包房款详情
     */
    QueryRoomCharterDetailDTO queryRoomCharterDetail(@Param("roomCharterCode") String roomCharterCode);
    /**
     * 已售间夜数
     */
    public Integer getSoldNightQty(@Param("roomCharterCode")String roomCharterCode);
    List<ProductTempDTO> queryHotelProducts(QueryProductRequestDTO queryProductRequestDTO);
}
