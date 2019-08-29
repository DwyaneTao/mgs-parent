package com.mgs.product.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 01:20
 * @Description: 批量调整行情
 */
@Data
public class BatchQuotationDTO extends BaseDTO {

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
     * 底价
     */
    private BigDecimal modifiedBasePrice;

    /**
     * 房态（0关房 1开房 null表示不变）
     */
    private Integer roomStatus;

    /**
     * 配额数
     */
    private Integer modifiedQuota;

    /**
     * 配额调整方式（0加 1减 2等于）
     */
    private Integer quotaAdjustmentType;

    /**
     * 底价调整方式 （0加 1减 2等于）
     */
    private Integer basePriceAdjustmentType;

    /**
     * 售罄设置（1可超 0不可超 null表示不变）
     */
    private Integer overDraftStatus;
}
