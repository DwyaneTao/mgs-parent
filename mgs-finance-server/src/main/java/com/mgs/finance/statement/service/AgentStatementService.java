package com.mgs.finance.statement.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.finance.remote.statement.request.AddStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.CreateAgentStatementDTO;
import com.mgs.finance.remote.statement.request.DeleteStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.ModifySettlementDateDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementNameDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementStatusDTO;
import com.mgs.finance.remote.statement.request.NotifyCollectionOfStatementDTO;
import com.mgs.finance.remote.statement.request.NotifyPaymentOfStatementDTO;
import com.mgs.finance.remote.statement.request.QueryAgentStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutAgentListDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.AgentStatementDetailDTO;
import com.mgs.finance.remote.statement.response.AgentStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.StatementOrderDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.response.UncheckOutAgentDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementOrderDTO;

import java.text.ParseException;

public interface AgentStatementService {

    /**
     * 已出账单查询
     */
    PaginationSupportDTO<AgentStatementListResponseDTO> queryStatementList(QueryAgentStatementListDTO request);

    /**
     * 未出账查询
     */
    PaginationSupportDTO<UncheckOutAgentDTO> queryUncheckOutAgentList(QueryUncheckOutAgentListDTO request);

    /**
     * 创建账单
     */
    Response createStatement(CreateAgentStatementDTO request) throws ParseException;

    /**
     * 账单详情
     */
    AgentStatementDetailDTO queryStatementDetail(StatementIdDTO request);

    /**
     * 账单明细
     */
    PaginationSupportDTO<StatementOrderDTO> queryStatementOrderList(QueryStatementOrderListDTO request);

    /**
     * 未出账订单查询
     */
    PaginationSupportDTO<UnCheckOutOrderDTO> queryUnCheckOutOrder(QueryUnCheckOutOrderDTO request);

    /**
     * 添加账单明细
     */
    Response addStatementOrderList(AddStatementOrderListDTO request);

    /**
     * 删除账单明细
     */
    Response deleteStatementOrderList(DeleteStatementOrderListDTO request);

    /**
     * 修改账单状态
     */
    Response modifyStatementStatus(ModifyStatementStatusDTO request);

    /**
     * 账单明细变更查询
     */
    PaginationSupportDTO<UpdatedStatementOrderDTO> queryUpdatedStatementOrderList(QueryUpdatedStatementOrderListDTO request);

    /**
     * 更新账单明细
     */
    Response updateStatementOrderList(StatementIdDTO request);

    /**
     * 通知收款
     */
    Response notifyCollectionOfStatement(NotifyCollectionOfStatementDTO request);

    /**
     * 通知付款
     */
    Response notifyPaymentOfStatement(NotifyPaymentOfStatementDTO request);

    /**
     * 修改账单名称
     */
    Response modifyStatementName(ModifyStatementNameDTO request);

    /**
     * 修改结算日期
     */
    Response modifySettlementDate(ModifySettlementDateDTO request);
}
