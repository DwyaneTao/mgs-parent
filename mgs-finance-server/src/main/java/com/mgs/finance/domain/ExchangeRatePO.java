package com.mgs.finance.domain;


import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Table(name = "t_exchangerate")
public class ExchangeRatePO extends BasePO {

    @Id
    @Column(name = "exchangerate_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchangeRateId;

    /**
     * 相互兑换的币种
     */
    @Column(name = "exchange_currency")
    private String exchangeCurrency;

    /**
     * 币种名称
     */
    @Column(name = "currency_name")
    private String currencyName;

    /**
     * 人民币兑换其它币种汇率
     */
    @Column(name = "exchangerate")
    private BigDecimal exchangeRate;

    /**
     * 其它币种兑换人民币汇率
     */
    @Column(name = "reversed_exchangerate")
    private BigDecimal reversedExchangeRate;


    /**
     *
     */
    @Column(name = "active")
    private Integer active;




}
