package com.mgs.ebk.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "o_order_remark")
public class EBKSupplyOrderRemarkPO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 0: 分销商备注，1：供应商备注 ，2：内部备注
     */
    @Column(name = "remark_type")
    private Integer remarkType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 接收对象
     */
    private String receiver;
    private String createdBy;

    private String createdDt;



}
