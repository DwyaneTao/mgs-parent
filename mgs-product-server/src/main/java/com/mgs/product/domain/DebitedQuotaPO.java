package com.mgs.product.domain;


import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Data
@Table(name = "t_pro_debited_quota")
public class DebitedQuotaPO extends BasePO {


    @Id
    @Column(name = "pro_debited_quota_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer debitedQuotaId;

    /*
     *  产品Id
     * */
    @Column(name = "product_id")
    private  Integer productId;

    /*
     *  配额账号Id
     * */
    @Column(name = "quota_account_id")
    private  Integer quotaAccountId;

    /*
     *  订单Id
     * */
    @Column(name = "order_id")
    private  Integer orderId;

    /*
     *  订单编码
     * */
    @Column(name = "order_code")
    private  String orderCode;

    /*
     *  供货单Id
     * */
    @Column(name = "supply_order_id")
    private  Integer supplyOrderId;


    /*
     *  供货单编码
     * */
    @Column(name = "supply_order_code")
    private  String supplyOrderCode;


    /*
     *  日期
     * */
    @Column(name = "sale_date")
    private  String saleDate;

    /*
     *  配额数
     * */
    @Column(name = "quota")
    private  Integer quota;

    /*
     *  方式：1：扣配额 2：退配额
     * */
    @Column(name = "type")
    private  Integer type;







}
