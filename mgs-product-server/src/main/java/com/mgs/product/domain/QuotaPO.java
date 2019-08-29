package com.mgs.product.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="t_pro_quota")
public class QuotaPO {

    @Id
    @Column(name = "quota_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer quotaId;


    /*
    *  配额账号Id
    * */
    @Column(name = "quota_account_id")
    private  Integer quotaAccountId;

    /*
     *  日期
     * */
    @Column(name = "sale_date")
    private  String saleDate;

    /*
     *  房态(1开房 0关房)
     * */
    @Column(name = "room_status")
    private  String roomStatus;


    /*
     *  售罄是否可超(1可超 0不可超)
     * */
    @Column(name = "over_draft_status")
    private  String overDraftStatus;

    /*
     *  总配额数
     * */
    @Column(name = "quota")
    private Integer  quota;

    /*
     *  剩余配额数
     * */
    @Column(name = "remaining_quota")
    private  Integer remainingQuota;



}
