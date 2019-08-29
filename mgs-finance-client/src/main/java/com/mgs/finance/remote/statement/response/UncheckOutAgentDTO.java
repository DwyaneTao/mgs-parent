package com.mgs.finance.remote.statement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UncheckOutAgentDTO implements Serializable{

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 订单数
     */
    private Integer orderQty;

    /**
     * 应收金额
     */
    private BigDecimal receivableAmt;

    /**
     * 币种
     */
    private Integer currency;
}
