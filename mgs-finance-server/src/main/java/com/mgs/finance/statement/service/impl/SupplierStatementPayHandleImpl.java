package com.mgs.finance.statement.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.enums.StatementStatusEnum;
import com.mgs.finance.statement.domain.SupplierStatementPO;
import com.mgs.finance.statement.dto.CancelStatementWorkOrderDTO;
import com.mgs.finance.statement.dto.ConfirmStatementWorkOrderDTO;
import com.mgs.finance.statement.dto.UpdateSupplyOrderFinanceDTO;
import com.mgs.finance.statement.mapper.SupplierStatementMapper;
import com.mgs.finance.statement.mapper.SupplierStatementOrderMapper;
import com.mgs.finance.statement.service.SupplierStatementPayHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class SupplierStatementPayHandleImpl implements SupplierStatementPayHandle {

    @Autowired
    private SupplierStatementMapper supplierStatementMapper;

    @Autowired
    private SupplierStatementOrderMapper supplierStatementOrderMapper;

    @Transactional
    @Override
    public Response confirmStatementWorkOrder(ConfirmStatementWorkOrderDTO request) {
        log.info("confirmStatementWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新账单结算金额
        boolean isFinishSettlement=false;
        SupplierStatementPO supplierStatementQuery=new SupplierStatementPO();
        supplierStatementQuery.setStatementCode(request.getStatementCode());
        SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectOne(supplierStatementQuery);
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(supplierStatementPO.getId());
        supplierStatementUpdate.setPaidAmt(supplierStatementPO.getPaidAmt().add(request.getConfirmAmt()));
        supplierStatementUpdate.setUnpaidAmt(supplierStatementPO.getUnpaidAmt().subtract(request.getConfirmAmt()));
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            supplierStatementUpdate.setUnconfirmedPaidAmt(supplierStatementPO.getUnconfirmedPaidAmt().subtract((request.getNotifyAmt())));
        }else{
            supplierStatementUpdate.setUnconfirmedReceivedAmt(supplierStatementPO.getUnconfirmedReceivedAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }
        if (supplierStatementPO.getStatementAmt().compareTo(supplierStatementUpdate.getPaidAmt())==0){
            isFinishSettlement=true;
        }

        if (isFinishSettlement && supplierStatementPO.getStatementStatus().equals(StatementStatusEnum.CONFIRMED.key)){
            // 2.更新订单的结算状态和结算金额
            supplierStatementUpdate.setRealSettlementDate(new Date());
            supplierStatementUpdate.setSettlementStatus(1);

            UpdateSupplyOrderFinanceDTO updateSupplyOrderFinanceDTO=new UpdateSupplyOrderFinanceDTO();
            updateSupplyOrderFinanceDTO.setStatementId(supplierStatementPO.getId());
            updateSupplyOrderFinanceDTO.setIsUpdateSettlementStatus(1);
            updateSupplyOrderFinanceDTO.setIsUpdateSettlementAmount(1);
            supplierStatementOrderMapper.updateSupplyOrderFinance(updateSupplyOrderFinanceDTO);

            // 3.返还额度
            // TODO: 2019/7/5 调分销商接口返还额度
        }
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response cancelStatementWorkOrder(CancelStatementWorkOrderDTO request) {
        log.info("cancelStatementWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 更新通知金额
        SupplierStatementPO supplierStatementQuery=new SupplierStatementPO();
        supplierStatementQuery.setStatementCode(request.getStatementCode());
        SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectOne(supplierStatementQuery);
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(supplierStatementPO.getId());
        if (request.getNotifyAmt().compareTo(BigDecimal.ZERO)>0){
            supplierStatementUpdate.setUnconfirmedReceivedAmt(supplierStatementPO.getUnconfirmedReceivedAmt().subtract(BigDecimal.ZERO.subtract(request.getNotifyAmt())));
        }else{
            supplierStatementUpdate.setUnconfirmedPaidAmt(supplierStatementPO.getUnconfirmedPaidAmt().subtract(request.getNotifyAmt()));
        }
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
