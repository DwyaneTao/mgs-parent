package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 22:45
 * @Description: 配额账号
 */
@Data
@Table(name="t_pro_quota_account")
public class QuotaAccountPO extends BasePO {

    @Id
    @Column(name = "quota_account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quotaAccountId;

    private Integer hotelId;

    private Integer roomId;

    private String supplierCode;

    private String quotaAccountName;

    private Integer active;
}
