package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:03
 * @Description: 产品加幅
 */
@Data
@Table(name = "t_pro_increase")
public class ProductIncreasePO extends BasePO {

    @Id
    @Column(name = "increase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    private String companyCode;

    private Integer channelId;

    private String channelCode;

    private String startDate;

    private String endDate;

    private String weekStr;

    /**
     * 调整方式（0加数值 1减数值 2加百分比 3等于）
     */
    private Integer adjustmentType;

    /**
     * 调整金额
     */
    private BigDecimal modifiedAmt;
}
