package com.mgs.product.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_pro_sale_log")
public class ProductSaleLogPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 操作类型
     */
    private Integer operationType;

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
     * 渠道编码
     */
    private String channelCode;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 操作的星期
     */
    private String operationWeek;
}
