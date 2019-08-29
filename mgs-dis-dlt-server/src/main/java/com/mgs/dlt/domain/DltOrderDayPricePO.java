package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 18:12
 * @Description:
 */
@Data
@Table(name = "t_dis_dlt_order_day_price")
public class DltOrderDayPricePO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代理通订单Id
     */
    private String dltOrderId;

    private Date effectDate;

    private Integer mealType;

    private String breakfast;

    private Integer breakfastNum;

    private String currency;

    private BigDecimal price;
}
