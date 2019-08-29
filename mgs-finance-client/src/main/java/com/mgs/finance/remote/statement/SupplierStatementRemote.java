package com.mgs.finance.remote.statement;

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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-finance-server")
public interface SupplierStatementRemote {

    /**
     * 已出账单查询
     */
    @PostMapping("/finance/supplier/queryStatementList")
    Response queryStatementList(@RequestBody QuerySupplierStatementListDTO request);

    /**
     * 未出账查询
     */
    @PostMapping("/finance/supplier/queryUncheckOutSupplierList")
    Response queryUncheckOutSupplierList(@RequestBody QueryUncheckOutSupplierListDTO request);

    /**
     * 创建账单
     */
    @PostMapping("/finance/supplier/createStatement")
    Response createStatement(@RequestBody CreateSupplierStatementDTO request);

    /**
     * 账单详情
     */
    @PostMapping("/finance/supplier/queryStatementDetail")
    Response queryStatementDetail(@RequestBody StatementIdDTO request);

    /**
     * 账单明细
     */
    @PostMapping("/finance/supplier/queryStatementOrderList")
    Response queryStatementOrderList(@RequestBody QueryStatementSupplyOrderListDTO request);

    /**
     * 未出账订单查询
     */
    @PostMapping("/finance/supplier/queryUnCheckOutSupplyOrder")
    Response queryUnCheckOutSupplyOrder(@RequestBody QueryUnCheckOutSupplyOrderDTO request);

    /**
     * 添加账单明细
     */
    @PostMapping("/finance/supplier/addStatementOrderList")
    Response addStatementOrderList(@RequestBody AddStatementSupplyOrderListDTO request);

    /**
     * 删除账单明细
     */
    @PostMapping("/finance/supplier/deleteStatementOrderList")
    Response deleteStatementOrderList(@RequestBody DeleteStatementOrderListDTO request);

    /**
     * 修改账单状态
     */
    @PostMapping("/finance/supplier/modifyStatementStatus")
    Response modifyStatementStatus(@RequestBody ModifyStatementStatusDTO request);

    /**
     * 账单明细变更查询
     */
    @PostMapping("/finance/supplier/queryUpdatedStatementOrderList")
    Response queryUpdatedStatementOrderList(@RequestBody QueryUpdatedStatementOrderListDTO request);

    /**
     * 更新账单明细
     */
    @PostMapping("/finance/supplier/updateStatementOrderList")
    Response updateStatementOrderList(@RequestBody StatementIdDTO request);

    /**
     * 通知收款
     */
    @PostMapping("/finance/supplier/notifyCollectionOfStatement")
    Response notifyCollectionOfStatement(@RequestBody NotifyCollectionOfStatementDTO request);

    /**
     * 通知付款
     */
    @PostMapping("/finance/supplier/notifyPaymentOfStatement")
    Response notifyPaymentOfStatement(@RequestBody NotifyPaymentOfStatementDTO request);

    /**
     * 修改账单名称
     */
    @PostMapping("/finance/supplier/modifyStatementName")
    Response modifyStatementName(@RequestBody ModifyStatementNameDTO request);

    /**
     * 修改结算日期
     */
    @PostMapping("/finance/supplier/modifySettlementDate")
    Response modifySettlementDate(@RequestBody ModifySettlementDateDTO request);
}
