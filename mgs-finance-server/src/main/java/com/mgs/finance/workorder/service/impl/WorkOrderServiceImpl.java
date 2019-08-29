package com.mgs.finance.workorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.enums.BusinessTypeEnum;
import com.mgs.finance.enums.WorkOrderStatusEnum;
import com.mgs.finance.remote.workorder.request.ConfirmWorkOrderDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderAttchDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderIdDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderListResponseDTO;
import com.mgs.finance.statement.dto.CancelStatementWorkOrderDTO;
import com.mgs.finance.statement.dto.ConfirmStatementWorkOrderDTO;
import com.mgs.finance.statement.service.AgentStatementPayHandle;
import com.mgs.finance.statement.service.SupplierStatementPayHandle;
import com.mgs.finance.workorder.domain.WorkOrderAttchPO;
import com.mgs.finance.workorder.domain.WorkOrderPO;
import com.mgs.finance.workorder.mapper.WorkOrderAttchMapper;
import com.mgs.finance.workorder.mapper.WorkOrderMapper;
import com.mgs.finance.workorder.service.WorkOrderService;
import com.mgs.order.remote.OrderFinanceHandleRemote;
import com.mgs.order.remote.SupplyOrderFinanceHandleRemote;
import com.mgs.order.remote.request.CancelOrderWorkOrderDTO;
import com.mgs.order.remote.request.CancelSupplyOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderWorkOrderDTO;
import com.mgs.order.remote.request.ConfirmSupplyOrderWorkOrderDTO;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private WorkOrderAttchMapper workOrderAttchMapper;

    @Autowired
    private AgentStatementPayHandle agentStatementPayHandle;

    @Autowired
    private SupplierStatementPayHandle supplierStatementPayHandle;

    @Autowired
    private OrderFinanceHandleRemote orderFinanceHandleRemote;

    @Autowired
    private SupplyOrderFinanceHandleRemote supplyOrderFinanceHandleRemote;

    @Override
    public PaginationSupportDTO<WorkOrderListResponseDTO> queryWorkOrderList(QueryWorkOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<WorkOrderListResponseDTO> list =workOrderMapper.queryWorkOrderList(request);
        PageInfo<WorkOrderListResponseDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<WorkOrderListResponseDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public WorkOrderDTO queryWorkOrderDetail(WorkOrderIdDTO request) {
        WorkOrderDTO workOrderDTO=new WorkOrderDTO();
        WorkOrderPO workOrderPO=workOrderMapper.selectByPrimaryKey(request.getWorkOrderId());
        BeanUtils.copyProperties(workOrderPO,workOrderDTO);
        workOrderDTO.setWorkOrderId(workOrderPO.getId());
        workOrderDTO.setAmt(workOrderPO.getAmount());
        workOrderDTO.setSettledBy(workOrderPO.getModifiedBy());
        workOrderDTO.setSettledDt(workOrderPO.getModifiedDt());

        WorkOrderAttchPO workOrderAttchQuery=new WorkOrderAttchPO();
        workOrderAttchQuery.setWorkOrderId(request.getWorkOrderId());
        List<WorkOrderAttchPO> workOrderAttchPOList=workOrderAttchMapper.select(workOrderAttchQuery);
        if (workOrderAttchPOList.size()>0){
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(workOrderAttchPOList,WorkOrderAttchDTO.class);
            workOrderDTO.setPhotoList(photoList);
        }
        return workOrderDTO;
    }

    @Transactional
    @Override
    public Response confirmWorkOrder(ConfirmWorkOrderDTO request) {
        log.info("confirmWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response(ResultCodeEnum.SUCCESS.code);

        WorkOrderPO workOrderPO=workOrderMapper.selectByPrimaryKey(request.getWorkOrderId());

        // 1.保存工单状态
        WorkOrderPO workOrderUpdate=new WorkOrderPO();
        BeanUtils.copyProperties(request,workOrderUpdate);
        workOrderUpdate.setId(request.getWorkOrderId());
        workOrderUpdate.setAmount(request.getAmt());
        workOrderUpdate.setWorkOrderStatus(WorkOrderStatusEnum.SETTLED.key);
        workOrderUpdate.setModifiedDt(new Date());
        workOrderUpdate.setModifiedBy(request.getOperator());
        workOrderMapper.updateByPrimaryKeySelective(workOrderUpdate);

        // 2.保存工单附件
        WorkOrderAttchPO workOrderAttchDelete=new WorkOrderAttchPO();
        workOrderAttchDelete.setWorkOrderId(request.getWorkOrderId());
        workOrderAttchMapper.delete(workOrderAttchDelete);
        // 3.保存工单附件
        if (request.getPhotoList()!=null && request.getPhotoList().size()>0){
            List<WorkOrderAttchPO> workOrderAttchPOList=new ArrayList<>();
            for (WorkOrderAttchDTO photo:request.getPhotoList()){
                if (null != photo && StringUtil.isValidString(photo.getUrl())) {
                    WorkOrderAttchPO workOrderAttchPO=new WorkOrderAttchPO();
                    workOrderAttchPO.setWorkOrderId(request.getWorkOrderId());
                    workOrderAttchPO.setUrl(photo.getUrl());
                    workOrderAttchPO.setName(photo.getName());
                    workOrderAttchPO.setRealpath(photo.getRealpath());
                    workOrderAttchPO.setCreatedBy(request.getOperator());
                    workOrderAttchPO.setCreatedDt(new Date());
                    workOrderAttchPOList.add(workOrderAttchPO);
                }
            }
            if (!CollectionUtils.isEmpty(workOrderAttchPOList)) {
                workOrderAttchMapper.insertList(workOrderAttchPOList);
            }
        }

        // 4.回调订单或账单更新状态
        if (workOrderPO.getBusinessType()== BusinessTypeEnum.ORDER.key){
            ConfirmOrderWorkOrderDTO confirmOrderWorkOrderDTO=new ConfirmOrderWorkOrderDTO();
            confirmOrderWorkOrderDTO.setOrderCode(workOrderPO.getBusinessCode());
            confirmOrderWorkOrderDTO.setNotifyAmt(workOrderPO.getAmount());
            confirmOrderWorkOrderDTO.setConfirmAmt(request.getAmt());
            confirmOrderWorkOrderDTO.setOperator(request.getOperator());
            response=orderFinanceHandleRemote.confirmOrderWorkOrder(confirmOrderWorkOrderDTO);
        }else if(workOrderPO.getBusinessType()== BusinessTypeEnum.SUPPLYORDER.key){
            ConfirmSupplyOrderWorkOrderDTO confirmSupplyOrderWorkOrderDTO=new ConfirmSupplyOrderWorkOrderDTO();
            confirmSupplyOrderWorkOrderDTO.setSupplyOrderCode(workOrderPO.getBusinessCode());
            confirmSupplyOrderWorkOrderDTO.setNotifyAmt(BigDecimal.ZERO.subtract(workOrderPO.getAmount()));
            confirmSupplyOrderWorkOrderDTO.setConfirmAmt(BigDecimal.ZERO.subtract(request.getAmt()));
            confirmSupplyOrderWorkOrderDTO.setOperator(request.getOperator());
            response=supplyOrderFinanceHandleRemote.confirmSupplyOrderWorkOrder(confirmSupplyOrderWorkOrderDTO);
        }else if(workOrderPO.getBusinessType()== BusinessTypeEnum.AGENTSTATEMENT.key){
            ConfirmStatementWorkOrderDTO confirmStatementWorkOrderDTO=new ConfirmStatementWorkOrderDTO();
            confirmStatementWorkOrderDTO.setStatementCode(workOrderPO.getBusinessCode());
            confirmStatementWorkOrderDTO.setNotifyAmt(workOrderPO.getAmount());
            confirmStatementWorkOrderDTO.setConfirmAmt(request.getAmt());
            confirmStatementWorkOrderDTO.setOperator(request.getOperator());
            response=agentStatementPayHandle.confirmStatementWorkOrder(confirmStatementWorkOrderDTO);
        }else if(workOrderPO.getBusinessType()== BusinessTypeEnum.SUPPLIERSTATEMENT.key){
            ConfirmStatementWorkOrderDTO confirmStatementWorkOrderDTO=new ConfirmStatementWorkOrderDTO();
            confirmStatementWorkOrderDTO.setStatementCode(workOrderPO.getBusinessCode());
            confirmStatementWorkOrderDTO.setNotifyAmt(BigDecimal.ZERO.subtract(workOrderPO.getAmount()));
            confirmStatementWorkOrderDTO.setConfirmAmt(BigDecimal.ZERO.subtract(request.getAmt()));
            confirmStatementWorkOrderDTO.setOperator(request.getOperator());
            response=supplierStatementPayHandle.confirmStatementWorkOrder(confirmStatementWorkOrderDTO);
        }
        return response;
    }

    @Transactional
    @Override
    public Response deleteWorkOrder(WorkOrderIdDTO request) {
        log.info("deleteWorkOrder param: {}", JSON.toJSONString(request));
        Response response=new Response(ResultCodeEnum.SUCCESS.code);

        WorkOrderPO workOrderPO=workOrderMapper.selectByPrimaryKey(request.getWorkOrderId());

        // 1.保存工单状态
        WorkOrderPO workOrderUpdate=new WorkOrderPO();
        workOrderUpdate.setId(request.getWorkOrderId());
        workOrderUpdate.setWorkOrderStatus(WorkOrderStatusEnum.CANCELED.key);
        workOrderMapper.updateByPrimaryKeySelective(workOrderUpdate);

        // 2.回调订单或账单更新状态
        if (workOrderPO.getBusinessType()== BusinessTypeEnum.ORDER.key){
            CancelOrderWorkOrderDTO cancelOrderWorkOrderDTO=new CancelOrderWorkOrderDTO();
            cancelOrderWorkOrderDTO.setOrderCode(workOrderPO.getBusinessCode());
            cancelOrderWorkOrderDTO.setNotifyAmt(workOrderPO.getAmount());
            cancelOrderWorkOrderDTO.setOperator(request.getOperator());
            response=orderFinanceHandleRemote.cancelOrderWorkOrder(cancelOrderWorkOrderDTO);
        }else if(workOrderPO.getBusinessType()== BusinessTypeEnum.SUPPLYORDER.key){
            CancelSupplyOrderWorkOrderDTO cancelSupplyOrderWorkOrderDTO=new CancelSupplyOrderWorkOrderDTO();
            cancelSupplyOrderWorkOrderDTO.setSupplyOrderCode(workOrderPO.getBusinessCode());
            cancelSupplyOrderWorkOrderDTO.setNotifyAmt(BigDecimal.ZERO.subtract(workOrderPO.getAmount()));
            cancelSupplyOrderWorkOrderDTO.setOperator(request.getOperator());
            response=supplyOrderFinanceHandleRemote.cancelSupplyOrderWorkOrder(cancelSupplyOrderWorkOrderDTO);
        }else if(workOrderPO.getBusinessType()== BusinessTypeEnum.AGENTSTATEMENT.key){
            CancelStatementWorkOrderDTO cancelStatementWorkOrderDTO=new CancelStatementWorkOrderDTO();
            cancelStatementWorkOrderDTO.setStatementCode(workOrderPO.getBusinessCode());
            cancelStatementWorkOrderDTO.setNotifyAmt(workOrderPO.getAmount());
            cancelStatementWorkOrderDTO.setOperator(request.getOperator());
            response=agentStatementPayHandle.cancelStatementWorkOrder(cancelStatementWorkOrderDTO);
        }else if(workOrderPO.getBusinessType()== BusinessTypeEnum.SUPPLIERSTATEMENT.key){
            CancelStatementWorkOrderDTO cancelStatementWorkOrderDTO=new CancelStatementWorkOrderDTO();
            cancelStatementWorkOrderDTO.setStatementCode(workOrderPO.getBusinessCode());
            cancelStatementWorkOrderDTO.setNotifyAmt(BigDecimal.ZERO.subtract(workOrderPO.getAmount()));
            cancelStatementWorkOrderDTO.setOperator(request.getOperator());
            response=supplierStatementPayHandle.cancelStatementWorkOrder(cancelStatementWorkOrderDTO);
        }
        return response;
    }
}
