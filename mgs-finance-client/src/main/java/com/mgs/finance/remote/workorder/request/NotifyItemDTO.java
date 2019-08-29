package com.mgs.finance.remote.workorder.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyItemDTO implements Serializable{

    /**
     * 业务单号
     */
    private String businessCode;

    /**
     * 金额
     */
    private BigDecimal amount;
}
