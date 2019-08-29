package com.mgs.product.dto;

import lombok.Data;

@Data
public class ProductSaleLogDTO {

    /**
     * 操作人
     */
    private String createdBy;

    /**
     * 操作时间
     */
    private String createdDt;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 操作的星期
     */
    private String operationWeek;
}
