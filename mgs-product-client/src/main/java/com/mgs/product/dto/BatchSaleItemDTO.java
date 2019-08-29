package com.mgs.product.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 01:35
 * @Description: 批量调整售价
 */
@Data
public class BatchSaleItemDTO {

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 星期（1-7表示周一至周天，选择周一，周二，周四，传入数据为：1,2,4）
     */
    private String weekList;

    /**
     * 调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer adjustmentType;

    /**
     * 调整金额
     */
    private BigDecimal modifiedAmt;
}
