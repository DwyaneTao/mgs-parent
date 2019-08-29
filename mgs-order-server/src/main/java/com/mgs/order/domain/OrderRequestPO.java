package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "o_order_request")
public class OrderRequestPO  extends BasePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 订单申请类型，0取消单申请，1修改单申请
     */
    @Column(name = "request_type")
    private Integer requestType;

    /**
     * 处理结果：0未处理，1同意处理，2拒绝处理
     */
    @Column(name = "handle_result")
    private Integer handleResult;

    /**
     * 备注
     */
    private String remark;

}