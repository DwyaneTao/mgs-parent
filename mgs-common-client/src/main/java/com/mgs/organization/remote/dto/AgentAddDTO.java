package com.mgs.organization.remote.dto;

/**
 * @author py
 * @date 2019/6/26 19:31
 **/

import com.mgs.common.BasePO;
import lombok.Data;
@Data
public class AgentAddDTO  extends BasePO {
    /**
     * 客户Id
     */
    private  Integer agentId;
    /**
     * 客户类型
     */
    private  Integer  agentType;
    /**
     * 客户名称
     */
    private  String   agentName;
    /**
     * 客户编码
     */
    private  String   agentCode;
    /**
     * 客户手机号
     */
    private  String  agentTel;
    /**
     * 信用额度
     */
    private  String  creditLine;
    /**
     * 我司销售经理ID
     */
    private Integer saleManagerId;
    /**
     * 我司销售经理名称
     */
    private  String  saleManagerName;
    /**
     * 总管理员姓名
     */
    private  String adminName;
    /**
     * 总管理员账号
     */
    private  String  adminAccount;
    /**
     * 总管理员手机号
     */
    private  String adminTel;
    /**
     * 结算方式
     */
    private  Integer settlementType;
    /**
     * 客户启用状态
     */
    private Integer availableStatus;
    /**
     * companyCode
     */
    private  String companyCode;
    /**
     * 域名
     */
    private String orgDomain;
}
