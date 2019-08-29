package com.mgs.finance.dto;


import lombok.Data;
import com.mgs.common.BaseDTO;
@Data
public class ExchangeRateDTO extends  BaseDTO{

    /**
     * 相互兑换的币种
     */
    private String  exchangeCurrencies;

    /**
     * 修改时间
     */
    private String  modifiedDt;

    /**
     * 人民币兑换其它币种汇率
     */
    private String  exchangeRate;

    /**
     * 其它币种兑换人民币汇率
     */
    private String  reversedExchangeRate;

    /**
     * 汇率ID
     */
    private Integer  exchangeRateId;

    /**
     * 币别
     */
    private Integer  currency;




}
