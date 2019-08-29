package com.mgs.meituan.dto.product.request;

import com.mgs.meituan.dto.hotel.BaseRequestDTO;
import lombok.Data;

/**
 * 库存同步
 */
@Data
public class QuotaSyncDTO extends BaseRequestDTO {

    /**
     * 新美大产品的状态
     */
    private Integer status;
}
