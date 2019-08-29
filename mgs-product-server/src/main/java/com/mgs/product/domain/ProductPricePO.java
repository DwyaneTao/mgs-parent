package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 22:47
 * @Description:
 */
@Data
@Table(name = "t_pro_price")
public class ProductPricePO extends BasePO {

    @Id
    @Column(name = "price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceId;

    private Integer productId;

    private String saleDate;

    private BigDecimal basePrice;

}
