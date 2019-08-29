package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 18:06
 * @Description:
 */
@Data
@Table(name = "t_dis_dlt_order_cancel_rules")
public class DltOrderCancelRulesPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代理通订单Id
     */
    private String dltOrderId;

    private Integer deductType;

    private Date lastCancelTime;
}
