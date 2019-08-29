package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 22:57
 * @Description: 产品售卖状态
 */
@Data
@Table(name = "t_pro_sale_status")
public class ProductSaleStatusPO extends BasePO {

    @Id
    @Column(name = "sale_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;

    private String companyCode;

    private Integer active;

    private Integer b2bSaleStatus;

    private Integer b2cSaleStatus;

    private Integer ctripSaleStatus;

    private Integer meituanSaleStatus;

    private Integer feizhuSaleStatus;

    private Integer tcylSaleStatus;

    private Integer qunarSaleStatus;
}
