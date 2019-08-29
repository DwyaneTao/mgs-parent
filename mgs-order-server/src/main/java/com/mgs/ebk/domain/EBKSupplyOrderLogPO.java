package com.mgs.ebk.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "o_ebk_supply_order_log")
public class EBKSupplyOrderLogPO {

    /**
     * 供货单日志
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;


    /**
     * 操作对象
     */
    @Column(name = "target")
    private String target;


    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String CreatedBy;


    /**
     * 操作时间
     */
    @Column(name = "created_dt")
    private String createdDt;




}
