package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:25
 * @Description: 产品
 */
@Data
@Table(name="t_pro_product")
public class ProductPO extends BasePO {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;

    private Integer hotelId;

    private Integer roomId;

    private String supplierCode;

    private String bedTypes;

    private Integer breakfastQty;

    private Integer purchaseType;

    private String remark;

    private Integer active;

    private Integer quotaAccountId;

    private Integer currency;

    private Integer advancePaymentId;

}
