package com.mgs.organization.remote.dto;

import lombok.Data;

import java.util.List;

/**
 * @author py
 * @date 2019/6/28 20:54
 **/
@Data
public class AgentSelectDTO {
    /**
     * 客户Id
     */
    private  Integer  agentId;
    /**
     * 客户类型
     */
    private  Integer  agentType;
    /**
     * 客户名称
     */
    private  String agentName;
    /**
     * 客户编码
     */
    private  String  agentCode;
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
     * 个人手机号
     */
    private  String  agentTel;
    /**
     * 结算方式
     */
    private Integer settlementType;
    /**
     * 信用额度
     */
    private  String creditLine;
    /**
     * 剩余额度
     */
    private  String Balance;
    /**
     * 我司销售经理ID
     */
    private  Integer saleManagerId;
    /**
     * 我司销售经理
     */
    private  String  saleManager;
    /**
     * 联系人信息
     */
    private List<ContactSupplierDTO> contactList;
    /**
     * 银行卡信息
     */
    private List<BankSupplierDTO> bankCardList;
}
