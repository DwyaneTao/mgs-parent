package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 22:52
 * @Description: 配额
 */
@Data
@Table(name = "t_pro_quota")
public class ProductQuotaPO extends BasePO {

    @Id
    @Column(name = "quota_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quotaId;

    private Integer quotaAccountId;

    private String saleDate;

    private Integer roomStatus;

    private Integer overDraftStatus;

    private Integer quota;

    private Integer remainingQuota;
}
