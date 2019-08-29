package com.mgs.meituan.dto.product.request;

import com.mgs.meituan.dto.hotel.BaseRequestDTO;
import lombok.Data;

import java.util.List;

/**
 * 单个新产品创建
 */
@Data
public class SingleProductSyncDTO extends BaseRequestDTO {

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品信息数组
     */
    private List<ProductInfoDTO> details;
}
