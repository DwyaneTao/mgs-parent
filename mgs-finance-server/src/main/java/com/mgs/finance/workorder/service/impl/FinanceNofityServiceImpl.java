package com.mgs.finance.workorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.exception.ParameterException;
import com.mgs.finance.enums.WorkOrderStatusEnum;
import com.mgs.finance.remote.workorder.request.*;
import com.mgs.finance.workorder.service.FinanceNofityService;
import com.mgs.finance.remote.workorder.response.NotificationLogDTO;
import com.mgs.finance.workorder.domain.WorkOrderAttchPO;
import com.mgs.finance.workorder.domain.WorkOrderItemPO;
import com.mgs.finance.workorder.domain.WorkOrderPO;
import com.mgs.finance.workorder.mapper.WorkOrderAttchMapper;
import com.mgs.finance.workorder.mapper.WorkOrderItemMapper;
import com.mgs.finance.workorder.mapper.WorkOrderMapper;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FinanceNofityServiceImpl implements FinanceNofityService {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private WorkOrderItemMapper workOrderItemMapper;

    @Autowired
    private WorkOrderAttchMapper workOrderAttchMapper;

    @Transactional
    @Override
    public Response notifyCollection(NotifyCollectionDTO request) {
        log.info("notifyCollection param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.创建工单
        WorkOrderPO workOrderInsert=new WorkOrderPO();
        BeanUtils.copyProperties(request,workOrderInsert);
        workOrderInsert.setCreatedDt(new Date());
        workOrderInsert.setCreatedBy(request.getCreatedBy());
        workOrderInsert.setAmount(request.getCollectionAmt());
        workOrderInsert.setWorkOrderStatus(WorkOrderStatusEnum.UN_SETTLE.key);
        if (request.getNotifyItemDTOList()==null || request.getNotifyItemDTOList().size()==0){
            throw new ParameterException("notifyItemDTOList不能为空");
        }else if(request.getNotifyItemDTOList().size()==1){
            workOrderInsert.setBusinessCode(request.getNotifyItemDTOList().get(0).getBusinessCode());
        }else{
            StringBuilder sb=new StringBuilder();
            for (NotifyItemDTO notifyItemDTO:request.getNotifyItemDTOList()){
                sb.append(notifyItemDTO.getBusinessCode()).append(",");
            }
            String businessCode=sb.deleteCharAt(sb.length()-1).toString();
            workOrderInsert.setBusinessCode(businessCode);
        }
        workOrderMapper.insert(workOrderInsert);
        // 2.保存工单明细
        if (request.getNotifyItemDTOList().size()>1){
            List<WorkOrderItemPO> workOrderItemInsertList=new ArrayList<>();
            for (NotifyItemDTO notifyItemDTO:request.getNotifyItemDTOList()){
                WorkOrderItemPO workOrderItemPO=new WorkOrderItemPO();
                workOrderItemPO.setWorkOrderId(workOrderInsert.getId());
                workOrderItemPO.setBusinessCode(notifyItemDTO.getBusinessCode());
                workOrderItemPO.setAmount(notifyItemDTO.getAmount());
                workOrderItemInsertList.add(workOrderItemPO);
            }
            workOrderItemMapper.insertList(workOrderItemInsertList);
        }
        // 3.保存工单附件
        if (request.getPhotoList()!=null && request.getPhotoList().size()>0){
            List<WorkOrderAttchPO> workOrderAttchPOList=new ArrayList<>();
            for (WorkOrderAttchDTO photo:request.getPhotoList()){
                if(photo == null){
                    workOrderAttchPOList = null;
                    break;
                }
                WorkOrderAttchPO workOrderAttchPO=new WorkOrderAttchPO();
                workOrderAttchPO.setWorkOrderId(workOrderInsert.getId());
                workOrderAttchPO.setUrl(photo.getUrl());
                workOrderAttchPO.setName(photo.getName());
                workOrderAttchPO.setRealpath(photo.getRealpath());
                workOrderAttchPO.setCreatedBy(request.getCreatedBy());
                workOrderAttchPO.setCreatedDt(new Date());
                workOrderAttchPOList.add(workOrderAttchPO);
            }
            if(CollectionUtils.isNotEmpty(workOrderAttchPOList)) {
                workOrderAttchMapper.insertList(workOrderAttchPOList);
            }
        }

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response notifyPayment(NotifyPaymentDTO request) {
        log.info("notifyPayment param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.创建工单
        WorkOrderPO workOrderInsert=new WorkOrderPO();
        BeanUtils.copyProperties(request,workOrderInsert);
        workOrderInsert.setCreatedDt(new Date());
        workOrderInsert.setCreatedBy(request.getCreatedBy());
        workOrderInsert.setAmount(BigDecimal.ZERO.subtract(request.getPaymentAmt()));
        workOrderInsert.setWorkOrderStatus(WorkOrderStatusEnum.UN_SETTLE.key);
        if (request.getNotifyItemDTOList()==null || request.getNotifyItemDTOList().size()==0){
            throw new ParameterException("notifyItemDTOList不能为空");
        }else if(request.getNotifyItemDTOList().size()==1){
            workOrderInsert.setBusinessCode(request.getNotifyItemDTOList().get(0).getBusinessCode());
        }else{
            StringBuilder sb=new StringBuilder();
            for (NotifyItemDTO notifyItemDTO:request.getNotifyItemDTOList()){
                sb.append(notifyItemDTO.getBusinessCode()).append(",");
            }
            String businessCode=sb.deleteCharAt(sb.length()-1).toString();
            workOrderInsert.setBusinessCode(businessCode);
        }
        workOrderMapper.insert(workOrderInsert);
        // 2.保存工单明细
        if (request.getNotifyItemDTOList().size()>1){
            List<WorkOrderItemPO> workOrderItemInsertList=new ArrayList<>();
            for (NotifyItemDTO notifyItemDTO:request.getNotifyItemDTOList()){
                WorkOrderItemPO workOrderItemPO=new WorkOrderItemPO();
                workOrderItemPO.setWorkOrderId(workOrderInsert.getId());
                workOrderItemPO.setBusinessCode(notifyItemDTO.getBusinessCode());
                workOrderItemPO.setAmount(notifyItemDTO.getAmount());
                workOrderItemInsertList.add(workOrderItemPO);
            }
            workOrderItemMapper.insertList(workOrderItemInsertList);
        }
        // 3.保存工单附件
        if (request.getPhotoList()!=null && request.getPhotoList().size()>0){
            List<WorkOrderAttchPO> workOrderAttchPOList=new ArrayList<>();
            for (WorkOrderAttchDTO photo:request.getPhotoList()){
                if(photo == null){
                    workOrderAttchPOList = null;
                    break;
                }
                WorkOrderAttchPO workOrderAttchPO=new WorkOrderAttchPO();
                workOrderAttchPO.setWorkOrderId(workOrderInsert.getId());
                workOrderAttchPO.setUrl(photo.getUrl());
                workOrderAttchPO.setName(photo.getName());
                workOrderAttchPO.setRealpath(photo.getRealpath());
                workOrderAttchPO.setCreatedBy(request.getCreatedBy());
                workOrderAttchPO.setCreatedDt(new Date());
                workOrderAttchPOList.add(workOrderAttchPO);
            }

            if(CollectionUtils.isNotEmpty(workOrderAttchPOList)) {
                workOrderAttchMapper.insertList(workOrderAttchPOList);
            }
        }

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public List<NotificationLogDTO> financeNotificationLogList(BusinessCodeDTO request) {
        if (StringUtil.isValidString(request.getBusinessCode())){
            return workOrderMapper.financeNotificationLogList(request);
        }else{
            return Collections.EMPTY_LIST;
        }
    }
}
