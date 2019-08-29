package com.mgs.finance.domain;


import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_exchangerate_log")
public class ExchangeRateLogPO {

    @Id
    @Column(name = "Id")
    private  Integer id;

    /**
     * 操作人
     */
    @Column(name = "created_by")
    private  String createdBy;

    /**
     * 操作内容
     */
    private  String content;


    /**
     * 操作时间
     */
    @Column(name = "created_date")
    private String createdDt;

    /**
     * 操作用户ID
     */
    @Column(name = "user_id")
    private  String userId;

    /**
     * 币别ID
     */
    @Column(name = "currency_id")
    private  Integer currencyId;

    /**
     * 有效性
     */
    @Column(name = "active")
    private  Integer active;

}
