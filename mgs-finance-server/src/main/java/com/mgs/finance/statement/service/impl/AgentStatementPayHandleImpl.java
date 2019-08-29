package com.mgs.finance.statement.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.enums.StatementStatusEnum;
import com.mgs.finance.remote.statement.request.QueryStatementOrderListDTO;
import com.mgs.finance.remote.statement.response.StatementOrderDTO;
import com.mgs.finance.statement.domain.AgentStatementPO;
import com.mgs.finance.statement.dto.CancelStatementWorkOrderDTO;
import com.mgs.finance.statement.dto.ConfirmStatementWorkOrderDTO;
import com.mgs.finance.statement.dto.UpdateOrderFinanceDTO;
import com.mgs.finance.statement.mapper.AgentStatementMapper;
import com.mgs.finance.statement.mapper.AgentStatementOrderMapper;
import com.mgs.finance.statement.service.AgentStatementPayHandle;
import com.mgs.organization.remote.AgentRemote;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AgentStatementPayHandleImpl implements AgentStatementPayHandle {

    @Autowired
    private AgentStatementMapper agentStatementMapper;

    @Autowired
    private AgentStatementOrderMapper agentStatementOrderMapper;

    @Autowired
    private AgentRemote agentRemote;

    @Transactional
    @Override
    public Response confirmStatementWorkOrder(ConfirmStatementWorkOrderDTO request) {
        log.info("confirmStatementWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新账单结算金额
        boolean isFinishSettlement=false;
        AgentStatementPO agentStatementQuery=new AgentStatementPO();
        agentStatementQuery.setStatementCode(request.getStatementCode());
        AgentStatementPO agentStatementPO=agentStatementMapper.selectOne(agentStatementQuery);
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(agentStatementPO.getId());
        agentStatementUpdate.setReceivedAmt(agentStatementPO.getReceivedAmt().add(request.getConfirmAmt()));
        agentStatementUpdate.setUnreceivedAmt(agentStatementPO.getUnreceivedAmt().subtract(request.getConfirmAmt()));
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            agentStatementUpdate.setUnconfirmedReceivedAmt(agentStatementPO.getUnconfirmedReceivedAmt().subtract(request.getNotifyAmt()));
        }else{
            agentStatementUpdate.setUnconfirmedPaidAmt(agentStatementPO.getUnconfirmedPaidAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        if (agentStatementPO.getStatementAmt().compareTo(agentStatementUpdate.getReceivedAmt())==0){
            isFinishSettlement=true;
        }


        if (isFinishSettlement && agentStatementPO.getStatementStatus().equals(StatementStatusEnum.CONFIRMED.key)){
            agentStatementUpdate.setRealSettlementDate(new Date());
            agentStatementUpdate.setSettlementStatus(1);
            // 2.更新订单的结算状态和结算金额
            UpdateOrderFinanceDTO updateOrderFinanceDTO=new UpdateOrderFinanceDTO();
            updateOrderFinanceDTO.setStatementId(agentStatementPO.getId());
            updateOrderFinanceDTO.setIsUpdateSettlementStatus(1);
            updateOrderFinanceDTO.setIsUpdateSettlementAmount(1);
            agentStatementOrderMapper.updateOrderFinance(updateOrderFinanceDTO);

            // 3.返还额度
            // TODO: 2019/7/5 调分销商接口返还额度（已完成）
            QueryStatementOrderListDTO queryStatementOrderListDTO = new QueryStatementOrderListDTO();
            queryStatementOrderListDTO.setStatementId(agentStatementPO.getId());
            List<StatementOrderDTO> statementOrderList =agentStatementOrderMapper.queryStatementOrderList(queryStatementOrderListDTO);

            //返回额度封装
            List<AgentCreditLineDTO> list = new ArrayList<AgentCreditLineDTO>();
            for(StatementOrderDTO statementOrderDTO : statementOrderList){
                AgentCreditLineDTO agentCreditLineDTO = new AgentCreditLineDTO();
                agentCreditLineDTO.setAgentCode(agentStatementPO.getAgentCode());
                agentCreditLineDTO.setDeductRefundCreditLine(BigDecimal.ZERO.subtract(statementOrderDTO.getReceivableAmt()).compareTo(BigDecimal.ZERO) < 0?"-"+statementOrderDTO.getReceivableAmt().toString(): "+"+statementOrderDTO.getReceivableAmt().toString());
                agentCreditLineDTO.setOrderCode(statementOrderDTO.getOrderCode());

                list.add(agentCreditLineDTO);
            }
            agentRemote.modifyDeductRefundCreditLine(list);
        }
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response cancelStatementWorkOrder(CancelStatementWorkOrderDTO request) {
        log.info("cancelStatementWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 更新通知金额
        AgentStatementPO agentStatementQuery=new AgentStatementPO();
        agentStatementQuery.setStatementCode(request.getStatementCode());
        AgentStatementPO agentStatementPO=agentStatementMapper.selectOne(agentStatementQuery);
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(agentStatementPO.getId());
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            agentStatementUpdate.setUnconfirmedReceivedAmt(agentStatementPO.getUnconfirmedReceivedAmt().subtract(request.getNotifyAmt()));
        }else{
            agentStatementUpdate.setUnconfirmedPaidAmt(agentStatementPO.getUnconfirmedPaidAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
