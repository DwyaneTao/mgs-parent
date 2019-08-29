package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:32
 * @Description:
 */
@Data
@Table(name = "t_pro_dayincrease")
public class ProductDayIncreasePO extends BasePO {

    @Id
    @Column(name = "day_increase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    private String companyCode;

    private String saleDate;

    private Integer b2bAdjustmentType;

    private BigDecimal b2bModifiedAmt;

    private Integer b2cAdjustmentType;

    private BigDecimal b2cModifiedAmt;

    private Integer ctripAdjustmentType;

    private BigDecimal ctripModifiedAmt;

    private Integer qunarAdjustmentType;

    private BigDecimal qunarModifiedAmt;

    private Integer meituanAdjustmentType;

    private BigDecimal meituanModifiedAmt;

    private Integer feizhuAdjustmentType;

    private BigDecimal feizhuModifiedAmt;

    private Integer tcylAdjustmentType;

    private BigDecimal tcylModifiedAmt;
}
