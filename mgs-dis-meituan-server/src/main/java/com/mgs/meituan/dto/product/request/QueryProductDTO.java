package com.mgs.meituan.dto.product.request;

import com.mgs.meituan.dto.hotel.BaseRequestDTO;
import lombok.Data;

/**
 * 房态查询
 */
@Data
public class QueryProductDTO extends BaseRequestDTO {

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
