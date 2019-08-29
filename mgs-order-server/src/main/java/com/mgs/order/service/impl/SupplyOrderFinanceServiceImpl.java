package com.mgs.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.enums.BusinessTypeEnum;
import com.mgs.finance.remote.workorder.FinanceNofityRemote;
import com.mgs.finance.remote.workorder.request.NotifyCollectionDTO;
import com.mgs.finance.remote.workorder.request.NotifyItemDTO;
import com.mgs.finance.remote.workorder.request.NotifyPaymentDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderAttchDTO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.SupplyOrderFinancePO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.dto.SupplyOrderAmtDTO;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.mapper.SupplyOrderFinanceMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderListDTO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.request.SupplyOrderIdListDTO;
import com.mgs.order.remote.response.OnTimeSupplyOrderDTO;
import com.mgs.order.service.SupplyOrderFinanceService;
import com.mgs.order.service.common.OrderCommonService;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SupplyOrderFinanceServiceImpl implements SupplyOrderFinanceService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private SupplyOrderFinanceMapper supplyOrderFinanceMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    @Autowired
    private FinanceNofityRemote financeNofityRemote;

    @Override
    public PaginationSupportDTO<OnTimeSupplyOrderDTO> queryOnTimeSupplyOrderList(QueryOnTimeSupplyOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<OnTimeSupplyOrderDTO> list =supplyOrderMapper.queryOnTimeSupplyOrderList(request);
        PageInfo<OnTimeSupplyOrderDTO> page = new PageInfo<OnTimeSupplyOrderDTO>(list);

        PaginationSupportDTO<OnTimeSupplyOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response notifyCollectionOfSupplyOrder(NotifyCollectionOfSupplyOrderDTO request) {
        log.info("notifyCollectionOfSupplyOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
        OrderPO orderPO=orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        // 1.创建工单
        NotifyCollectionDTO notifyCollectionDTO=new NotifyCollectionDTO();
        BeanUtils.copyProperties(request,notifyCollectionDTO);
        notifyCollectionDTO.setBusinessType(BusinessTypeEnum.SUPPLYORDER.key);
        notifyCollectionDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                supplyOrderPO.getSupplyOrderCode(),
                request.getAmt()
        )));
        notifyCollectionDTO.setCollectionAmt(request.getAmt());
        notifyCollectionDTO.setOrgCode(supplyOrderPO.getSupplierCode());
        notifyCollectionDTO.setOrgName(supplyOrderPO.getSupplierName());
        notifyCollectionDTO.setCompanyCode(orderPO.getCompanyCode());
        notifyCollectionDTO.setContent("供货单款");
        notifyCollectionDTO.setCurrency(supplyOrderPO.getBaseCurrency());
        notifyCollectionDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyCollectionDTO.setPhotoList(photoList);
        }
        notifyCollectionDTO.setCreatedBy(request.getOperator());
        response=financeNofityRemote.notifyCollection(notifyCollectionDTO);

        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
            // 2.更新已通知金额
            SupplyOrderFinancePO supplyOrderFinanceQuery=new SupplyOrderFinancePO();
            supplyOrderFinanceQuery.setSupplyOrderId(request.getSupplyOrderId());
            SupplyOrderFinancePO supplyOrderFinancePO=supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);
            SupplyOrderFinancePO supplyOrderFinanceUpdate=new SupplyOrderFinancePO();
            supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
            supplyOrderFinanceUpdate.setUnconfirmedReceivedAmt(supplyOrderFinancePO.getUnconfirmedReceivedAmt().add(request.getAmt()));
            supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
        }

        // 3. 记日志
        StringBuilder content = new StringBuilder();
        content.append("供货单").append(supplyOrderPO.getSupplyOrderCode()).append("通知财务收款，通知金额：").append(request.getAmt())
                .append("，通知结果：")
                .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
        if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
            content.append("，失败原因：" + response.getFailReason());
        }
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response notifyPaymentOfSupplyOrder(NotifyPaymentOfSupplyOrderDTO request) {
        log.info("notifyPaymentOfSupplyOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        SupplyOrderPO supplyOrderPO=supplyOrderMapper.selectByPrimaryKey(request.getSupplyOrderId());
        OrderPO orderPO=orderMapper.selectByPrimaryKey(supplyOrderPO.getOrderId());

        // 1.创建工单
        NotifyPaymentDTO notifyPaymentDTO=new NotifyPaymentDTO();
        BeanUtils.copyProperties(request,notifyPaymentDTO);
        notifyPaymentDTO.setBusinessType(BusinessTypeEnum.SUPPLYORDER.key);
        notifyPaymentDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                supplyOrderPO.getSupplyOrderCode(),
                request.getAmt()
        )));
        notifyPaymentDTO.setPaymentAmt(request.getAmt());
        notifyPaymentDTO.setOrgCode(supplyOrderPO.getSupplierCode());
        notifyPaymentDTO.setOrgName(supplyOrderPO.getSupplierName());
        notifyPaymentDTO.setCompanyCode(orderPO.getCompanyCode());
        notifyPaymentDTO.setContent("供货单款");
        notifyPaymentDTO.setCurrency(supplyOrderPO.getBaseCurrency());
        notifyPaymentDTO.setCreatedBy(request.getOperator());
        notifyPaymentDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyPaymentDTO.setPhotoList(photoList);
        }
        response=financeNofityRemote.notifyPayment(notifyPaymentDTO);

        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
            // 2.更新已通知金额
            SupplyOrderFinancePO supplyOrderFinanceQuery=new SupplyOrderFinancePO();
            supplyOrderFinanceQuery.setSupplyOrderId(request.getSupplyOrderId());
            SupplyOrderFinancePO supplyOrderFinancePO=supplyOrderFinanceMapper.selectOne(supplyOrderFinanceQuery);
            SupplyOrderFinancePO supplyOrderFinanceUpdate=new SupplyOrderFinancePO();
            supplyOrderFinanceUpdate.setId(supplyOrderFinancePO.getId());
            supplyOrderFinanceUpdate.setUnconfirmedPaidAmt(supplyOrderFinancePO.getUnconfirmedPaidAmt().add(request.getAmt()));
            supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
        }

        // 3. 记日志
        StringBuilder content = new StringBuilder();
        content.append("供货单").append(supplyOrderPO.getSupplyOrderCode()).append("通知财务付款，通知金额：").append(request.getAmt())
                .append("，通知结果：")
                .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
        if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
            content.append("，失败原因：" + response.getFailReason());
        }
        orderCommonService.saveOrderLog(
                orderPO.getId(),
                request.getOperator(),
                request.getOrderOwnerName(),
                orderPO.getOrderCode(),
                content.toString()
        );

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response notifyCollectionOfSupplyOrderList(NotifyCollectionOfSupplyOrderListDTO request) {
        log.info("notifyCollectionOfSupplyOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        List<NotifyItemDTO> notifyItemDTOList=new ArrayList<>();
        List<SupplyOrderAmtDTO> supplyOrderAmtDTOList=supplyOrderFinanceMapper.querySupplyOrderAmt(new SupplyOrderIdListDTO(request.getSupplyOrderIdList()));
        for (SupplyOrderAmtDTO supplyOrderAmtDTO:supplyOrderAmtDTOList){
            BigDecimal collectionAmt=BigDecimal.ZERO.subtract(supplyOrderAmtDTO.getSupplyOrderAmt()
                    .subtract(supplyOrderAmtDTO.getPaidAmt())
                    .subtract(supplyOrderAmtDTO.getUnconfirmedPaidAmt())
                    .add(supplyOrderAmtDTO.getUnconfirmedReceivedAmt()));
            notifyItemDTOList.add(new NotifyItemDTO(
                    supplyOrderAmtDTO.getSupplyOrderCode(),
                    collectionAmt
            ));
        }

        // 2.创建工单
        NotifyCollectionDTO notifyCollectionDTO=new NotifyCollectionDTO();
        BeanUtils.copyProperties(request,notifyCollectionDTO);
        notifyCollectionDTO.setCollectionAmt(request.getAmt());
        notifyCollectionDTO.setBusinessType(BusinessTypeEnum.SUPPLYORDER.key);
        notifyCollectionDTO.setNotifyItemDTOList(notifyItemDTOList);
        notifyCollectionDTO.setOrgCode(supplyOrderAmtDTOList.get(0).getSupplierCode());
        notifyCollectionDTO.setOrgName(supplyOrderAmtDTOList.get(0).getSupplierName());
        notifyCollectionDTO.setCompanyCode(supplyOrderAmtDTOList.get(0).getCompanyCode());
        notifyCollectionDTO.setContent("供货单款");
        notifyCollectionDTO.setCurrency(supplyOrderAmtDTOList.get(0).getCurrency());
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyCollectionDTO.setPhotoList(photoList);
        }
        notifyCollectionDTO.setCreatedBy(request.getOperator());
        notifyCollectionDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        response=financeNofityRemote.notifyCollection(notifyCollectionDTO);

        for (SupplyOrderAmtDTO supplyOrderAmtDTO:supplyOrderAmtDTOList){
            BigDecimal collectionAmt=BigDecimal.ZERO.subtract(supplyOrderAmtDTO.getSupplyOrderAmt()
                    .subtract(supplyOrderAmtDTO.getPaidAmt())
                    .subtract(supplyOrderAmtDTO.getUnconfirmedPaidAmt())
                    .add(supplyOrderAmtDTO.getUnconfirmedReceivedAmt()));

            if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
                // 2.更新已通知金额
                SupplyOrderFinancePO supplyOrderFinanceUpdate=new SupplyOrderFinancePO();
                supplyOrderFinanceUpdate.setId(supplyOrderAmtDTO.getSupplyOrderFinanceId());
                supplyOrderFinanceUpdate.setUnconfirmedReceivedAmt(supplyOrderAmtDTO.getUnconfirmedReceivedAmt().add(collectionAmt));
                supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
            }

            // 3. 记日志
            StringBuilder content = new StringBuilder();
            content.append("供货单").append(supplyOrderAmtDTO.getSupplyOrderCode()).append("通知财务收款，通知金额：").append(collectionAmt)
                    .append("，通知结果：")
                    .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
            if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                content.append("，失败原因：" + response.getFailReason());
            }
            orderCommonService.saveOrderLog(
                    supplyOrderAmtDTO.getOrderId(),
                    request.getOperator(),
                    request.getOrderOwnerName(),
                    supplyOrderAmtDTO.getOrderCode(),
                    content.toString()
            );
        }

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response notifyPaymentOfSupplyOrderList(NotifyPaymentOfSupplyOrderListDTO request) {
        log.info("notifyPaymentOfSupplyOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        List<NotifyItemDTO> notifyItemDTOList=new ArrayList<>();
        List<SupplyOrderAmtDTO> supplyOrderAmtDTOList=supplyOrderFinanceMapper.querySupplyOrderAmt(new SupplyOrderIdListDTO(request.getSupplyOrderIdList()));
        for (SupplyOrderAmtDTO supplyOrderAmtDTO:supplyOrderAmtDTOList){
            BigDecimal paymentAmt=supplyOrderAmtDTO.getSupplyOrderAmt()
                    .subtract(supplyOrderAmtDTO.getPaidAmt())
                    .subtract(supplyOrderAmtDTO.getUnconfirmedPaidAmt())
                    .add(supplyOrderAmtDTO.getUnconfirmedReceivedAmt());
            notifyItemDTOList.add(new NotifyItemDTO(
                    supplyOrderAmtDTO.getSupplyOrderCode(),
                    paymentAmt
            ));
        }

        // 2.创建工单
        NotifyPaymentDTO notifyPaymentDTO=new NotifyPaymentDTO();
        BeanUtils.copyProperties(request,notifyPaymentDTO);
        notifyPaymentDTO.setBusinessType(BusinessTypeEnum.ORDER.key);
        notifyPaymentDTO.setPaymentAmt(request.getAmt());
        notifyPaymentDTO.setNotifyItemDTOList(notifyItemDTOList);
        notifyPaymentDTO.setOrgCode(supplyOrderAmtDTOList.get(0).getSupplierCode());
        notifyPaymentDTO.setOrgName(supplyOrderAmtDTOList.get(0).getSupplierName());
        notifyPaymentDTO.setCompanyCode(supplyOrderAmtDTOList.get(0).getCompanyCode());
        notifyPaymentDTO.setContent("供货单款");
        notifyPaymentDTO.setCurrency(supplyOrderAmtDTOList.get(0).getCurrency());
        notifyPaymentDTO.setCreatedBy(request.getOperator());
        notifyPaymentDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyPaymentDTO.setPhotoList(photoList);
        }
        response=financeNofityRemote.notifyPayment(notifyPaymentDTO);

        for (SupplyOrderAmtDTO supplyOrderAmtDTO:supplyOrderAmtDTOList){
            BigDecimal paymentAmt=supplyOrderAmtDTO.getSupplyOrderAmt()
                    .subtract(supplyOrderAmtDTO.getPaidAmt())
                    .subtract(supplyOrderAmtDTO.getUnconfirmedPaidAmt())
                    .add(supplyOrderAmtDTO.getUnconfirmedReceivedAmt());

            if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
                // 2.更新已通知金额
                SupplyOrderFinancePO supplyOrderFinanceUpdate=new SupplyOrderFinancePO();
                supplyOrderFinanceUpdate.setId(supplyOrderAmtDTO.getSupplyOrderFinanceId());
                supplyOrderFinanceUpdate.setUnconfirmedPaidAmt(supplyOrderAmtDTO.getUnconfirmedPaidAmt().add(paymentAmt));
                supplyOrderFinanceMapper.updateByPrimaryKeySelective(supplyOrderFinanceUpdate);
            }

            // 3. 记日志
            StringBuilder content = new StringBuilder();
            content.append("供货单").append(supplyOrderAmtDTO.getSupplyOrderCode()).append("通知财务收款，通知金额：").append(paymentAmt)
                    .append("，通知结果：")
                    .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
            if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                content.append("，失败原因：" + response.getFailReason());
            }
            orderCommonService.saveOrderLog(
                    supplyOrderAmtDTO.getOrderId(),
                    request.getOperator(),
                    request.getOrderOwnerName(),
                    supplyOrderAmtDTO.getOrderCode(),
                    content.toString()
            );
        }

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response notifyCollectionPreviewOfSupplyOrderList(SupplyOrderIdListDTO request) {
        log.info("notifyPaymentPreviewOfSupplyOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        BigDecimal totalAmt=BigDecimal.ZERO;
        List<SupplyOrderAmtDTO> supplyOrderAmtDTOList=supplyOrderFinanceMapper.querySupplyOrderAmt(new SupplyOrderIdListDTO(request.getSupplyOrderIdList()));
        for (SupplyOrderAmtDTO supplyOrderAmtDTO:supplyOrderAmtDTOList){
            BigDecimal collectionAmt=BigDecimal.ZERO.subtract(supplyOrderAmtDTO.getSupplyOrderAmt()
                    .subtract(supplyOrderAmtDTO.getPaidAmt())
                    .subtract(supplyOrderAmtDTO.getUnconfirmedPaidAmt())
                    .add(supplyOrderAmtDTO.getUnconfirmedReceivedAmt()));
            totalAmt=totalAmt.add(collectionAmt);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(totalAmt);
        return response;
    }

    @Override
    public Response notifyPaymentPreviewOfSupplyOrderList(SupplyOrderIdListDTO request) {
        log.info("notifyPaymentPreviewOfSupplyOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        BigDecimal totalAmt=BigDecimal.ZERO;
        List<SupplyOrderAmtDTO> supplyOrderAmtDTOList=supplyOrderFinanceMapper.querySupplyOrderAmt(new SupplyOrderIdListDTO(request.getSupplyOrderIdList()));
        for (SupplyOrderAmtDTO supplyOrderAmtDTO:supplyOrderAmtDTOList){
            BigDecimal paymentAmt=supplyOrderAmtDTO.getSupplyOrderAmt()
                    .subtract(supplyOrderAmtDTO.getPaidAmt())
                    .subtract(supplyOrderAmtDTO.getUnconfirmedPaidAmt())
                    .add(supplyOrderAmtDTO.getUnconfirmedReceivedAmt());
            totalAmt = totalAmt.add(paymentAmt);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(totalAmt);
        return response;
    }
}