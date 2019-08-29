package com.mgs.finance.statement.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.finance.remote.statement.request.AddStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.CreateSupplierStatementDTO;
import com.mgs.finance.remote.statement.request.DeleteStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.ModifySettlementDateDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementNameDTO;
import com.mgs.finance.remote.statement.request.ModifyStatementStatusDTO;
import com.mgs.finance.remote.statement.request.NotifyCollectionOfStatementDTO;
import com.mgs.finance.remote.statement.request.NotifyPaymentOfStatementDTO;
import com.mgs.finance.remote.statement.request.QueryStatementSupplyOrderListDTO;
import com.mgs.finance.remote.statement.request.QuerySupplierStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryUnCheckOutSupplyOrderDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutSupplierListDTO;
import com.mgs.finance.remote.statement.request.QueryUpdatedStatementOrderListDTO;
import com.mgs.finance.remote.statement.request.StatementIdDTO;
import com.mgs.finance.remote.statement.response.StatementSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementDetailDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.UncheckOutSupplierDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementSupplyOrderDTO;

public interface SupplierStatementService {

    /**
     * 已出账单查询
     */
    PaginationSupportDTO<SupplierStatementListResponseDTO> queryStatementList(QuerySupplierStatementListDTO request);

    /**
     * 未出账查询
     */
    PaginationSupportDTO<UncheckOutSupplierDTO> queryUncheckOutSupplierList(QueryUncheckOutSupplierListDTO request);

    /**
     * 创建账单
     */
    Response createStatement(CreateSupplierStatementDTO request);

    /**
     * 账单详情
     */
    SupplierStatementDetailDTO queryStatementDetail(StatementIdDTO request);

    /**
     * 账单明细
     */
    PaginationSupportDTO<StatementSupplyOrderDTO> queryStatementOrderList(QueryStatementSupplyOrderListDTO request);

    /**
     * 未出账订单查询
     */
    PaginationSupportDTO<UnCheckOutSupplyOrderDTO> queryUnCheckOutSupplyOrder(QueryUnCheckOutSupplyOrderDTO request);

    /**
     * 添加账单明细
     */
    Response addStatementOrderList(AddStatementSupplyOrderListDTO request);

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
    PaginationSupportDTO<UpdatedStatementSupplyOrderDTO> queryUpdatedStatementOrderList(QueryUpdatedStatementOrderListDTO request);

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
