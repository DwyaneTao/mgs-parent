package com.mgs.product.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Data
public class DebitedQuotaDTO implements Serializable {


    private Integer debitedQuotaId;

    /*
     *  产品Id
     * */

    private  Integer productId;

    /*
     *  配额账号Id
     * */
    private  Integer quotaAccountId;

    /*
     *  订单Id
     * */
    private  Integer orderId;

    /*
     *  订单编码
     * */
    private  String orderCode;

    /*
     *  供货单Id
     * */
    private  Integer supplyOrderId;


    /*
     *  供货单编码
     * */
    private  String supplyOrderCode;


    /*
     *  日期
     * */
    private  String saleDate;

    /*
     *  配额数
     * */
    private  Integer quota;

    /*
     *  方式：1：扣配额 2：退配额
     * */
    private  Integer type;



}
