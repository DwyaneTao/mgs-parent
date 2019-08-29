package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 22:40
 * @Description: 产品条款
 */
@Data
@Table(name="t_pro_restrict")
public class ProductRestrictPO extends BasePO {

    @Id
    @Column(name = "restrict_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restrictId;

    private Integer productId;

    private Integer cancellationType;

    private Integer cancellationAdvanceDays;

    private String cancellationDueTime;

    private String cancellationDeductionTerm;

    private Integer comparisonType;

    private Integer reservationLimitNights;

    private String reservationDueTime;

    private Integer reservationLimitRooms;

    private Integer reservationAdvanceDays;
}
