package com.mgs.organization.remote.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author py
 * @date 2019/6/29 17:36
 **/
@Data
public class QueryAgentListDTO {
    /**
     * 客户Id
     */
    private  Integer agentId;
    /**
     * 客户名称
     */
    private  String agentName;
    /**
     * 客户类型
     */
    private  Integer agentType;
    /**
     * 客户编码
     */
    private  String agentCode;
    /**
     * 客户手机号
     */
    private  String  agentTel;
    /**
     * 总管理员手机号
     */
    private  String adminTel;
    /**
     * 我司销售经理
     */
    private  String  saleManager;
    /**
     * 剩余额度
     */
    private  BigDecimal balance;
    /**
     * 客户启用状态
     */
    private  Integer  availableStatus;
}
