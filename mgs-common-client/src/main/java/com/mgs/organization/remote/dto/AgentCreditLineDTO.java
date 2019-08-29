package com.mgs.organization.remote.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author py
 * @date 2019/7/15 17:01
 **/

@Data
public class AgentCreditLineDTO extends BaseDTO {
    /**
     * 额度明细Id
     */
    private  Integer agentCreditLineId;
    /**
     * 分销商编码
     */
    private String agentCode;
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 扣退额度
     */
    private String deductRefundCreditLine;
}
