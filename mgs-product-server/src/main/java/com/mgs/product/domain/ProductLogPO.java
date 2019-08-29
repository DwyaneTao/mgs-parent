package com.mgs.product.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_pro_log")
public class ProductLogPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品id
     */
    private Integer productId;


    /**
     * 调整产品日期的开始时间
     */
    private String startDate;

    /**
     * 调整产品日期的结束时间
     */
    private String endDate;

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
