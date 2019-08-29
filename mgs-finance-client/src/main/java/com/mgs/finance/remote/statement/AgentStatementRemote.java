package com.mgs.finance.remote.statement;

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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-finance-server")
public interface AgentStatementRemote {

    /**
     * 已出账单查询
     */
    @PostMapping("/finance/agent/queryStatementList")
    Response queryStatementList(@RequestBody QueryAgentStatementListDTO request);

    /**
     * 未出账查询
     */
    @PostMapping("/finance/agent/queryUncheckOutAgentList")
    Response queryUncheckOutAgentList(@RequestBody QueryUncheckOutAgentListDTO request);

    /**
     * 创建账单
     */
    @PostMapping("/finance/agent/createStatement")
    Response createStatement(@RequestBody CreateAgentStatementDTO request);

    /**
     * 账单详情
     */
    @PostMapping("/finance/agent/queryStatementDetail")
    Response queryStatementDetail(@RequestBody StatementIdDTO request);

    /**
     * 账单明细
     */
    @PostMapping("/finance/agent/queryStatementOrderList")
    Response queryStatementOrderList(@RequestBody QueryStatementOrderListDTO request);

    /**
     * 未出账订单查询
     */
    @PostMapping("/finance/agent/queryUnCheckOutOrder")
    Response queryUnCheckOutOrder(@RequestBody QueryUnCheckOutOrderDTO request);

    /**
     * 添加账单明细
     */
    @PostMapping("/finance/agent/addStatementOrderList")
    Response addStatementOrderList(@RequestBody AddStatementOrderListDTO request);

    /**
     * 删除账单明细
     */
    @PostMapping("/finance/agent/deleteStatementOrderList")
    Response deleteStatementOrderList(@RequestBody DeleteStatementOrderListDTO request);

    /**
     * 修改账单状态
     */
    @PostMapping("/finance/agent/modifyStatementStatus")
    Response modifyStatementStatus(@RequestBody ModifyStatementStatusDTO request);

    /**
     * 账单明细变更查询
     */
    @PostMapping("/finance/agent/queryUpdatedStatementOrderList")
    Response queryUpdatedStatementOrderList(@RequestBody QueryUpdatedStatementOrderListDTO request);

    /**
     * 更新账单明细
     */
    @PostMapping("/finance/agent/updateStatementOrderList")
    Response updateStatementOrderList(@RequestBody StatementIdDTO request);

    /**
     * 通知收款
     */
    @PostMapping("/finance/agent/notifyCollectionOfStatement")
    Response notifyCollectionOfStatement(@RequestBody NotifyCollectionOfStatementDTO request);

    /**
     * 通知付款
     */
    @PostMapping("/finance/agent/notifyPaymentOfStatement")
    Response notifyPaymentOfStatement(@RequestBody NotifyPaymentOfStatementDTO request);

    /**
     * 修改账单名称
     */
    @PostMapping("/finance/agent/modifyStatementName")
    Response modifyStatementName(@RequestBody ModifyStatementNameDTO request);

    /**
     * 修改结算日期
     */
    @PostMapping("/finance/agent/modifySettlementDate")
    Response modifySettlementDate(@RequestBody ModifySettlementDateDTO request);
}
