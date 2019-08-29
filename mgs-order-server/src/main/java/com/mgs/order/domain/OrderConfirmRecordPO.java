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
@Table(name = "o_order_confirm_record")
public class OrderConfirmRecordPO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 确认号
     */
    @Column(name = "confirmation_code")
    private String confirmationCode;

    /**
     * 内容
     */
    @Column(name = "confirmation_content")
    private String confirmationContent;
    private String createdBy;

    private String createdDt;
}