package com.mgs.finance.statement.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateSupplyOrderFinanceDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 对账状态：0新建，1可出账，2已纳入账单，3已对账
     */
    private Integer checkStatus;

    /**
     * 是否更新结算状态
     */
    private Integer isUpdateSettlementStatus;

    /**
     * 是否更新结算金额
     */
    private Integer isUpdateSettlementAmount;

    /**
     * 财务锁单状态，0：未锁定，1：已锁定
     */
    private Integer financeLockStatus;

    /**
     * 供货单idList
     */
    private List<Integer> supplyOrderIdList;

    /**
     * 账单明细idList
     */
    private List<Integer> statementOrderIdList;
}
