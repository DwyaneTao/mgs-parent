package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "o_order_log")
public class OrderLogPO   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单编码
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 操作对象
     */
    private String target;

    private String createdBy;

    private String createdDt;
}