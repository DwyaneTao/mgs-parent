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
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.dto.OrderAmtDTO;
import com.mgs.order.mapper.OrderFinanceMapper;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.remote.request.NotifyCollectionOfOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import com.mgs.order.remote.response.OnTimeOrderDTO;
import com.mgs.order.service.OrderFinanceService;
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
public class OrderFinanceServiceImpl implements OrderFinanceService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderFinanceMapper orderFinanceMapper;

    @Autowired
    private OrderCommonService orderCommonService;

    @Autowired
    private FinanceNofityRemote financeNofityRemote;

    @Override
    public PaginationSupportDTO<OnTimeOrderDTO> queryOnTimeOrderList(QueryOnTimeOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<OnTimeOrderDTO> list =orderMapper.queryOnTimeOrderList(request);
        PageInfo<OnTimeOrderDTO> page = new PageInfo<OnTimeOrderDTO>(list);

        PaginationSupportDTO<OnTimeOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response notifyCollectionOfOrder(NotifyCollectionOfOrderDTO request) {
        log.info("notifyCollectionOfOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        OrderPO orderPO=orderMapper.selectByPrimaryKey(request.getOrderId());

        // 1.创建工单
        NotifyCollectionDTO notifyCollectionDTO=new NotifyCollectionDTO();
        BeanUtils.copyProperties(request,notifyCollectionDTO);
        notifyCollectionDTO.setBusinessType(BusinessTypeEnum.ORDER.key);
        notifyCollectionDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                orderPO.getOrderCode(),
                request.getAmt()
        )));
        notifyCollectionDTO.setCollectionAmt(request.getAmt());
        notifyCollectionDTO.setOrgCode(orderPO.getAgentCode());
        notifyCollectionDTO.setOrgName(orderPO.getAgentName());
        notifyCollectionDTO.setCompanyCode(orderPO.getCompanyCode());
        notifyCollectionDTO.setContent("订单款");
        notifyCollectionDTO.setCurrency(orderPO.getSaleCurrency());
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyCollectionDTO.setPhotoList(photoList);
        }

        notifyCollectionDTO.setCreatedBy(request.getOperator());
        notifyCollectionDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        response=financeNofityRemote.notifyCollection(notifyCollectionDTO);

        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
            // 2.更新已通知金额
            OrderFinancePO orderFinanceQuery=new OrderFinancePO();
            orderFinanceQuery.setOrderId(request.getOrderId());
            OrderFinancePO orderFinancePO=orderFinanceMapper.selectOne(orderFinanceQuery);
            OrderFinancePO orderFinanceUpdate=new OrderFinancePO();
            orderFinanceUpdate.setId(orderFinancePO.getId());
            orderFinanceUpdate.setUnconfirmedReceivedAmt(orderFinancePO.getUnconfirmedReceivedAmt().add(request.getAmt()));
            orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
        }

        // 3. 记日志
        StringBuilder content = new StringBuilder();
        content.append("订单通知财务收款，通知金额：").append(request.getAmt())
                .append("，通知结果：")
                .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
        if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
            content.append("，失败原因：" + response.getFailReason());
        }
        orderCommonService.saveOrderLog(
                request.getOrderId(),
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
    public Response notifyPaymentOfOrder(NotifyPaymentOfOrderDTO request) {
        log.info("notifyPaymentOfOrder param: {}", JSON.toJSONString(request));
        Response response=new Response();

        OrderPO orderPO=orderMapper.selectByPrimaryKey(request.getOrderId());

        // 1.创建工单
        NotifyPaymentDTO notifyPaymentDTO=new NotifyPaymentDTO();
        BeanUtils.copyProperties(request,notifyPaymentDTO);
        notifyPaymentDTO.setPaymentAmt(request.getAmt());
        notifyPaymentDTO.setBusinessType(BusinessTypeEnum.ORDER.key);
        notifyPaymentDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                orderPO.getOrderCode(),
                request.getAmt()
        )));
        notifyPaymentDTO.setOrgCode(orderPO.getAgentCode());
        notifyPaymentDTO.setOrgName(orderPO.getAgentName());
        notifyPaymentDTO.setCompanyCode(orderPO.getCompanyCode());
        notifyPaymentDTO.setContent("订单款");
        notifyPaymentDTO.setCurrency(orderPO.getSaleCurrency());
        notifyPaymentDTO.setCreatedBy(request.getOperator());
        notifyPaymentDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyPaymentDTO.setPhotoList(photoList);
        }
        response=financeNofityRemote.notifyPayment(notifyPaymentDTO);

        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
            // 2.更新已通知金额
            OrderFinancePO orderFinanceQuery=new OrderFinancePO();
            orderFinanceQuery.setOrderId(request.getOrderId());
            OrderFinancePO orderFinancePO=orderFinanceMapper.selectOne(orderFinanceQuery);
            OrderFinancePO orderFinanceUpdate=new OrderFinancePO();
            orderFinanceUpdate.setId(orderFinancePO.getId());
            orderFinanceUpdate.setUnconfirmedPaidAmt(orderFinancePO.getUnconfirmedPaidAmt().add(request.getAmt()));
            orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
        }

        // 3. 记日志
        StringBuilder content = new StringBuilder();
        content.append("订单通知财务付款，通知金额：").append(request.getAmt())
                .append("，通知结果：")
                .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
        if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
            content.append("，失败原因：" + response.getFailReason());
        }
        orderCommonService.saveOrderLog(
                request.getOrderId(),
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
    public Response notifyCollectionOfOrderList(NotifyCollectionOfOrderListDTO request) {
        log.info("notifyCollectionOfOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        List<NotifyItemDTO> notifyItemDTOList=new ArrayList<>();
        List<OrderAmtDTO> orderAmtDTOList=orderFinanceMapper.queryOrderAmt(new OrderIdListDTO(request.getOrderIdList()));
        for (OrderAmtDTO orderAmtDTO:orderAmtDTOList){
            BigDecimal collectionAmt=orderAmtDTO.getOrderAmt()
                    .subtract(orderAmtDTO.getReceivedAmt())
                    .subtract(orderAmtDTO.getUnconfirmedReceivedAmt())
                    .add(orderAmtDTO.getUnconfirmedPaidAmt());
            notifyItemDTOList.add(new NotifyItemDTO(
                    orderAmtDTO.getOrderCode(),
                    collectionAmt
            ));
        }

        // 1.创建工单
        NotifyCollectionDTO notifyCollectionDTO=new NotifyCollectionDTO();
        BeanUtils.copyProperties(request,notifyCollectionDTO);
        notifyCollectionDTO.setCollectionAmt(request.getAmt());
        notifyCollectionDTO.setBusinessType(BusinessTypeEnum.ORDER.key);
        notifyCollectionDTO.setNotifyItemDTOList(notifyItemDTOList);
        notifyCollectionDTO.setOrgCode(orderAmtDTOList.get(0).getAgentCode());
        notifyCollectionDTO.setOrgName(orderAmtDTOList.get(0).getAgentName());
        notifyCollectionDTO.setCompanyCode(orderAmtDTOList.get(0).getCompanyCode());
        notifyCollectionDTO.setContent("订单款");
        notifyCollectionDTO.setCurrency(orderAmtDTOList.get(0).getCurrency());
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyCollectionDTO.setPhotoList(photoList);
        }
        notifyCollectionDTO.setCreatedBy(request.getOperator());
        notifyCollectionDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        response=financeNofityRemote.notifyCollection(notifyCollectionDTO);

        for (OrderAmtDTO orderAmtDTO:orderAmtDTOList){
            BigDecimal collectionAmt=orderAmtDTO.getOrderAmt()
                    .subtract(orderAmtDTO.getReceivedAmt())
                    .subtract(orderAmtDTO.getUnconfirmedReceivedAmt())
                    .add(orderAmtDTO.getUnconfirmedPaidAmt());

            // 2.更新已通知金额
            if (response.getResult().equals(ResultCodeEnum.SUCCESS.code)) {
                OrderFinancePO orderFinanceUpdate=new OrderFinancePO();
                orderFinanceUpdate.setId(orderAmtDTO.getOrderFinanceId());
                orderFinanceUpdate.setUnconfirmedReceivedAmt(orderAmtDTO.getUnconfirmedReceivedAmt().add(collectionAmt));
                orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
            }

            // 3. 记日志
            StringBuilder content = new StringBuilder();
            content.append("订单通知财务收款，通知金额：").append(collectionAmt)
                    .append("，通知结果：")
                    .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
            if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                content.append("，失败原因：" + response.getFailReason());
            }
            orderCommonService.saveOrderLog(
                    orderAmtDTO.getOrderId(),
                    request.getOperator(),
                    request.getOrderOwnerName(),
                    orderAmtDTO.getOrderCode(),
                    content.toString()
            );
        }

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response notifyPaymentOfOrderList(NotifyPaymentOfOrderListDTO request) {
        log.info("notifyPaymentOfOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        List<NotifyItemDTO> notifyItemDTOList=new ArrayList<>();
        List<OrderAmtDTO> orderAmtDTOList=orderFinanceMapper.queryOrderAmt(new OrderIdListDTO(request.getOrderIdList()));
        for (OrderAmtDTO orderAmtDTO:orderAmtDTOList){
            BigDecimal paymentAmt=BigDecimal.ZERO.subtract(
                    orderAmtDTO.getOrderAmt()
                    .subtract(orderAmtDTO.getReceivedAmt())
                    .subtract(orderAmtDTO.getUnconfirmedReceivedAmt())
                    .add(orderAmtDTO.getUnconfirmedPaidAmt()));
            notifyItemDTOList.add(new NotifyItemDTO(
                    orderAmtDTO.getOrderCode(),
                    paymentAmt
            ));
        }

        // 1.创建工单
        NotifyPaymentDTO notifyPaymentDTO=new NotifyPaymentDTO();
        BeanUtils.copyProperties(request,notifyPaymentDTO);
        notifyPaymentDTO.setPaymentAmt(request.getAmt());
        notifyPaymentDTO.setBusinessType(BusinessTypeEnum.ORDER.key);
        notifyPaymentDTO.setNotifyItemDTOList(notifyItemDTOList);
        notifyPaymentDTO.setOrgCode(orderAmtDTOList.get(0).getAgentCode());
        notifyPaymentDTO.setOrgName(orderAmtDTOList.get(0).getAgentName());
        notifyPaymentDTO.setCompanyCode(orderAmtDTOList.get(0).getCompanyCode());
        notifyPaymentDTO.setContent("订单款");
        notifyPaymentDTO.setCurrency(orderAmtDTOList.get(0).getCurrency());
        notifyPaymentDTO.setCreatedBy(request.getOperator());
        notifyPaymentDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        if (!CollectionUtils.isEmpty(request.getPhotoList())) {
            List<WorkOrderAttchDTO> photoList = BeanUtil.transformList(request.getPhotoList(),WorkOrderAttchDTO.class);
            notifyPaymentDTO.setPhotoList(photoList);
        }
        response=financeNofityRemote.notifyPayment(notifyPaymentDTO);

        for (OrderAmtDTO orderAmtDTO:orderAmtDTOList){
            BigDecimal paymentAmt=BigDecimal.ZERO.subtract(
                    orderAmtDTO.getOrderAmt()
                            .subtract(orderAmtDTO.getReceivedAmt())
                            .subtract(orderAmtDTO.getUnconfirmedReceivedAmt())
                            .add(orderAmtDTO.getUnconfirmedPaidAmt()));

            // 2.更新已通知金额
            if(response.getResult().equals(ResultCodeEnum.SUCCESS.code)){
                OrderFinancePO orderFinanceUpdate=new OrderFinancePO();
                orderFinanceUpdate.setId(orderAmtDTO.getOrderFinanceId());
                orderFinanceUpdate.setUnconfirmedPaidAmt(orderAmtDTO.getUnconfirmedPaidAmt().add(paymentAmt));
                orderFinanceMapper.updateByPrimaryKeySelective(orderFinanceUpdate);
            }

            // 3. 记日志
            StringBuilder content = new StringBuilder();
            content.append("订单通知财务收款，通知金额：").append(paymentAmt)
                    .append("，通知结果：")
                    .append(response.getResult().equals(ResultCodeEnum.SUCCESS.code) ? "成功" : "失败");
            if (response.getResult().equals(ResultCodeEnum.FAILURE.code)) {
                content.append("，失败原因：" + response.getFailReason());
            }
            orderCommonService.saveOrderLog(
                    orderAmtDTO.getOrderId(),
                    request.getOperator(),
                    request.getOrderOwnerName(),
                    orderAmtDTO.getOrderCode(),
                    content.toString()
            );
        }

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response notifyCollectionPreviewOfOrderList(OrderIdListDTO request) {
        log.info("notifyPaymentPreviewOfOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        BigDecimal totalAmt=BigDecimal.ZERO;
        List<OrderAmtDTO> orderAmtDTOList=orderFinanceMapper.queryOrderAmt(new OrderIdListDTO(request.getOrderIdList()));
        for (OrderAmtDTO orderAmtDTO:orderAmtDTOList) {
            BigDecimal collectionAmt=orderAmtDTO.getOrderAmt()
                    .subtract(orderAmtDTO.getReceivedAmt())
                    .subtract(orderAmtDTO.getUnconfirmedReceivedAmt())
                    .add(orderAmtDTO.getUnconfirmedPaidAmt());
            totalAmt=totalAmt.add(collectionAmt);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(totalAmt);
        return response;
    }

    @Override
    public Response notifyPaymentPreviewOfOrderList(OrderIdListDTO request) {
        log.info("notifyPaymentPreviewOfOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        BigDecimal totalAmt=BigDecimal.ZERO;
        List<OrderAmtDTO> orderAmtDTOList=orderFinanceMapper.queryOrderAmt(new OrderIdListDTO(request.getOrderIdList()));
        for (OrderAmtDTO orderAmtDTO:orderAmtDTOList) {
            BigDecimal paymentAmt = BigDecimal.ZERO.subtract(
                    orderAmtDTO.getOrderAmt()
                            .subtract(orderAmtDTO.getReceivedAmt())
                            .subtract(orderAmtDTO.getUnconfirmedReceivedAmt())
                            .add(orderAmtDTO.getUnconfirmedPaidAmt()));
            totalAmt = totalAmt.add(paymentAmt);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(totalAmt);
        return response;
    }
}