package com.mgs.finance.statement.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.remote.statement.request.QueryStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.QueryStatementTotalAmountDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutSupplyOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.StatementSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementSupplyOrderDTO;
import com.mgs.finance.statement.domain.SupplierStatementOrderPO;
import com.mgs.finance.statement.dto.InsertStatementSupplyOrderDTO;
import com.mgs.finance.statement.dto.UpdateSupplyOrderFinanceDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierStatementOrderMapper extends MyMapper<SupplierStatementOrderPO> {

    /**
     * 批量保存账单明细
     */
    Integer saveBatchStatementOrder(InsertStatementSupplyOrderDTO insertStatementSupplyOrderDTO);

    /**
     * 更新账单明细
     */
    Integer updateStatementOrderList(StatementIdDTO request);

    /**
     * 查询账单明细
     */
    List<StatementSupplyOrderDTO> queryStatementOrderList(QueryStatementSupplyOrderListDTO request);

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
    Integer updateSupplyOrderFinance(UpdateSupplyOrderFinanceDTO request);

    /**
     * 未出账订单查询
     */
    List<UnCheckOutSupplyOrderDTO> queryUnCheckOutSupplyOrder(QueryUnCheckOutSupplyOrderDTO request);

    /**
     * 账单明细变更查询
     */
    List<UpdatedStatementSupplyOrderDTO> queryUpdatedStatementOrderList(QueryUpdatedStatementOrderListDTO request);

    /**
     * 查询明细金额
     * @param request
     * @return
     */
    QueryStatementTotalAmountDTO queryStatementAmount(StatementIdDTO request);
}