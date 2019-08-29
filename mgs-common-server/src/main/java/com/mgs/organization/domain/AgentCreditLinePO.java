package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author py
 * @date 2019/7/15 16:20
 **/
@Data
@Table(name = "t_org_agent_credit_line")
public class AgentCreditLinePO  extends BasePO {
    /**
     * 额度明细Id
     */
    @Column(name = "agent_credit_line_id")
    private  Integer agentCreditLineId;
    /**
     * 分销商编码
     */
    @Column(name = "agent_code")
    private String agentCode;
    /**
     * 订单编码
     */
    @Column(name = "order_code")
    private String orderCode;
    /**
     * 扣退额度
     */
    @Column(name = "d_r_credit_line")
    private String deductRefundCreditLine;
}
