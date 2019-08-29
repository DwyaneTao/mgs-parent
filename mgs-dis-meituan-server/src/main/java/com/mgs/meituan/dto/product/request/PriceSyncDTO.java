package com.mgs.meituan.dto.product.request;

import com.mgs.meituan.dto.hotel.BaseRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class PriceSyncDTO extends BaseRequestDTO {

    /**
     * 房态价格参数变更列表
     */
    private List<RoomStatusPriceDTO> roomStatusPriceList;

}
