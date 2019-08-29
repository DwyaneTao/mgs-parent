package com.mgs.finance.statement.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.remote.statement.request.QueryStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.QueryStatementTotalAmountDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.StatementOrderDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementOrderDTO;
import com.mgs.finance.statement.domain.AgentStatementOrderPO;
import com.mgs.finance.statement.dto.InsertStatementOrderDTO;
import com.mgs.finance.statement.dto.UpdateOrderFinanceDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AgentStatementOrderMapper extends MyMapper<AgentStatementOrderPO> {

    /**
     * 批量保存账单明细
     */
    Integer saveBatchStatementOrder(InsertStatementOrderDTO insertStatementOrderDTO);

    /**
     * 更新账单明细
     */
    Integer updateStatementOrderList(StatementIdDTO request);

    /**
     * 查询账单明细
     */
    List<StatementOrderDTO> queryStatementOrderList(QueryStatementOrderListDTO request);

    /**
     * 更新账单金额
     */
    Integer updateStatementAmount(QueryStatementTotalAmountDTO request);

    /**
     * 查询订单更新后的账单金额
     */
    BigDecimal queryNewStatementAmount(StatementIdDTO request);

    /**
     * 更新订单对账状态
     */
    Integer updateOrderFinance(UpdateOrderFinanceDTO request);

    /**
     * 未出账订单查询
     */
    List<UnCheckOutOrderDTO> queryUnCheckOutOrder(QueryUnCheckOutOrderDTO request);

    /**
     * 账单明细变更查询
     */
    List<UpdatedStatementOrderDTO> queryUpdatedStatementOrderList(QueryUpdatedStatementOrderListDTO request);

    /**
     * 查询当前订单下的金额总数
     * @param request
     * @return
     */
    QueryStatementTotalAmountDTO queryStatementTotalAmount(StatementIdDTO request);
}