package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "o_guest")
public class GuestPO  extends BasePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 入住人名称
     */
    private String name;

}