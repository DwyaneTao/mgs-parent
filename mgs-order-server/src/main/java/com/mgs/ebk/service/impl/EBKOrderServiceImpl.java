package com.mgs.ebk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.ebk.domain.EBKSupplyOrderLogPO;
import com.mgs.ebk.domain.EBKSupplyOrderPO;
import com.mgs.ebk.domain.EBKSupplyOrderRemarkPO;
import com.mgs.ebk.mapper.EBKOrderMapper;
import com.mgs.ebk.mapper.EBKSupplyOrderLogMapper;
import com.mgs.ebk.mapper.EBKSupplyOrderMapper;
import com.mgs.ebk.order.remote.request.CancelSupplyOrderDTO;
import com.mgs.ebk.order.remote.request.ConfirmSupplyOrderDTO;
import com.mgs.ebk.order.remote.request.QuerySupplyOrderListDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderDetailDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderListDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderLogDTO;
import com.mgs.ebk.order.remote.response.SupplyOrderRemark;
import com.mgs.ebk.order.remote.response.SupplyOrderStatistics;
import com.mgs.ebk.service.EBKOrderQueryService;
import com.mgs.enums.RemarkEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SupplyRequestEnum;
import com.mgs.order.domain.OrderLogPO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.OrderRemarkPO;
import com.mgs.order.domain.SupplyOrderPO;
import com.mgs.order.domain.SupplyRequestPO;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.order.mapper.OrderRemarkMapper;
import com.mgs.order.mapper.SupplyOrderMapper;
import com.mgs.order.mapper.SupplyRequestMapper;
import com.mgs.order.remote.request.SaveSupplyResultDTO;
import com.mgs.order.service.SupplyOrderService;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mgs.util.DateUtil.hour_format;

@Service
@Slf4j
public class EBKOrderServiceImpl implements EBKOrderQueryService {

    @Autowired
    private EBKOrderMapper EBKOrderMapper;


    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private EBKSupplyOrderMapper EBKSupplyOrderMapper;

    @Autowired
    private OrderRemarkMapper orderRemarkMapper;

    @Autowired
    private EBKSupplyOrderLogMapper EBKSupplyOrderLogMapper;

    @Autowired
    private SupplyRequestMapper supplyRequestMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private SupplyOrderService supplyOrderService;

    @Override
    public Response querySupplyOrderList(QuerySupplyOrderListDTO request) {
        Response response = new Response();
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<SupplyOrderListDTO> list = EBKSupplyOrderMapper.queryOrderList(request);

        for (SupplyOrderListDTO supplyOrderListDTO : list) {

            supplyOrderListDTO.getEbkOrderConfirmationStatus();
            if(supplyOrderListDTO.getOrderConfirmationStatus()==2){

            }
            supplyOrderListDTO.setOrderAmt(new BigDecimal(supplyOrderListDTO.getOrderAmt().stripTrailingZeros().toPlainString()));
            supplyOrderListDTO.setCreatedDt(DateUtil.dateToString(DateUtil.stringToDate(supplyOrderListDTO.getCreatedDt(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
        }
        PageInfo<SupplyOrderListDTO> page = new PageInfo<SupplyOrderListDTO>(list);
        PaginationSupportDTO<SupplyOrderListDTO> paginationSupport = new PaginationSupportDTO<SupplyOrderListDTO>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupport);
        return response;
    }

    @Override
    public Response querySupplyOrderDetail(Map<String, String> request) {
        Response response = new Response();
        StringBuilder applicationContent = new StringBuilder();
        SupplyOrderDetailDTO supplyOrderDetailDTO = EBKSupplyOrderMapper.queryOrderDetail(request);

        if (null != supplyOrderDetailDTO) {

            //查询最新供货单请求
            Example supplyRequestExample = new Example(SupplyRequestPO.class);
            supplyRequestExample.setOrderByClause("created_dt desc");
            Example.Criteria supplyRequestCriteria = supplyRequestExample.createCriteria();
            supplyRequestCriteria.andEqualTo("supplyOrderId", supplyOrderDetailDTO.getSupplyOrderId());
            List<SupplyRequestPO> supplyRequestPOList = supplyRequestMapper.selectByExample(supplyRequestExample);
            if (null != supplyRequestPOList && supplyRequestPOList.size() > 0 && supplyRequestPOList.get(0).getSupplyOrderType() > 1) {
                //查询订单
                OrderPO orderPO = new OrderPO();
                orderPO.setId(supplyOrderDetailDTO.getOrderId());
                orderPO = orderMapper.selectOne(orderPO);
                if (null != orderPO) {
                    applicationContent.append("申请");
                    applicationContent.append(SupplyRequestEnum.getDesc(supplyRequestPOList.get(0).getSupplyOrderType())).append(":");
                    applicationContent.append(orderPO.getAgentName());
                    applicationContent.append("于").append(supplyRequestPOList.get(0).getCreatedDt()).append("申请").append(SupplyRequestEnum.getDesc(supplyRequestPOList.get(0).getSupplyOrderType()));
                }
            }


            //修改单查询EBK,修改内容查询供货单
            if (supplyRequestPOList.get(0).getSupplyOrderType() == 2) {
                supplyOrderDetailDTO=  EBKOrderMapper.queryOrderDetail(request);
            }

            supplyOrderDetailDTO.setApplicationContent(applicationContent.toString());
            supplyOrderDetailDTO.setApplicationType(supplyRequestPOList.get(0).getSupplyOrderType());
            supplyOrderDetailDTO.setNightQty((int) DateUtil.getDay(DateUtil.stringToDate(supplyOrderDetailDTO.getStartDate()), DateUtil.stringToDate(supplyOrderDetailDTO.getEndDate(), "yyyy-MM-dd")));
            supplyOrderDetailDTO.setUnreceivedAmt(new BigDecimal(supplyOrderDetailDTO.getUnreceivedAmt().stripTrailingZeros().toPlainString()));
            supplyOrderDetailDTO.setReceivedAmt(new BigDecimal(supplyOrderDetailDTO.getReceivedAmt().stripTrailingZeros().toPlainString()));
            if (null != supplyOrderDetailDTO.getRefundFee()) {
                supplyOrderDetailDTO.setRefundFee(new BigDecimal(supplyOrderDetailDTO.getRefundFee().stripTrailingZeros().toPlainString()));

            }
            supplyOrderDetailDTO.setSalePrice(new BigDecimal(supplyOrderDetailDTO.getSalePrice().stripTrailingZeros().toPlainString()));
            supplyOrderDetailDTO.setOrderAmt(new BigDecimal(supplyOrderDetailDTO.getOrderAmt().stripTrailingZeros().toPlainString()));
        }

        //查询备注
        SupplyOrderRemark supplyOrderRemark = new SupplyOrderRemark();
        //客户备注
        Map<String,Integer> remark= new HashMap<>();
        remark.put("orderId",supplyOrderDetailDTO.getOrderId());
        remark.put("remarkType",0);
        List<EBKSupplyOrderRemarkPO>  agentRemarkPOS= EBKSupplyOrderMapper.querySupplyOrderRemak(remark);
        StringBuilder agentRemark = new StringBuilder();
        for(EBKSupplyOrderRemarkPO EBKSupplyOrderRemarkPO:agentRemarkPOS){
            agentRemark.append(EBKSupplyOrderRemarkPO.getRemark());
        }
        supplyOrderRemark.setAgentRemark(agentRemark.toString());

        //供应商备注
        remark.put("remarkType",2);
        List<EBKSupplyOrderRemarkPO> supplierRemarkPOS=EBKSupplyOrderMapper.querySupplyOrderRemak(remark);
        StringBuilder supplierRemark = new StringBuilder();
        for(EBKSupplyOrderRemarkPO EBKSupplyOrderRemarkPO:supplierRemarkPOS){
            supplierRemark.append(EBKSupplyOrderRemarkPO.getRemark());
        }
        supplyOrderRemark.setSupplierRemark(supplierRemark.toString());
        supplyOrderDetailDTO.setRemark(supplyOrderRemark);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplyOrderDetailDTO);
        return response;
    }

    @Override
    @Transactional
    public Response confirmSupplyOrder(ConfirmSupplyOrderDTO request) {
        Response response = new Response();
        EBKSupplyOrderPO ebkSupplyOrderPO = new EBKSupplyOrderPO();
        SupplyOrderPO supplyOrderPO = new SupplyOrderPO();
        supplyOrderPO.setId(request.getSupplyOrderId());
        supplyOrderPO = supplyOrderMapper.selectOne(supplyOrderPO);

        if (null != supplyOrderPO && request.getConfirmStatus() == 0) {

            //保存EBK订单
            BeanUtils.copyProperties(supplyOrderPO, ebkSupplyOrderPO);
            ebkSupplyOrderPO.setConfirmationStatus(1);
            ebkSupplyOrderPO.setConfirmationCode(request.getConfirmationCode());
            ebkSupplyOrderPO.setCreatedBy(request.getOperator());
            ebkSupplyOrderPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
            EBKSupplyOrderMapper.insert(ebkSupplyOrderPO);

        }


        //保存结果，修改供货单结果
        SaveSupplyResultDTO saveSupplyResultDTO = new SaveSupplyResultDTO();
        saveSupplyResultDTO.setSupplyOrderId(supplyOrderPO.getId());
        saveSupplyResultDTO.setConfirmationStatus(request.getConfirmStatus() == 0 ? 1 : 2);
        saveSupplyResultDTO.setSupplierConfirmer(request.getOperator());
        saveSupplyResultDTO.setConfirmationCode(request.getConfirmationCode());
        saveSupplyResultDTO.setRemark(request.getRemark());
        supplyOrderService.saveSupplyResult(saveSupplyResultDTO);


        //插入备注
        if (StringUtil.isValidString(request.getRemark())) {
            OrderRemarkPO orderRemarkInsert = new OrderRemarkPO();
            orderRemarkInsert.setOrderId(supplyOrderPO.getOrderId());
            orderRemarkInsert.setRemark(request.getRemark());
            orderRemarkInsert.setRemarkType(3);
            orderRemarkInsert.setReceiver(RemarkEnum.getremark(3));
            orderRemarkInsert.setCreatedBy(request.getOperator());
            orderRemarkInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
            orderRemarkMapper.insert(orderRemarkInsert);
        }

        //记日志
        EBKSupplyOrderLogPO orderLogPO = new EBKSupplyOrderLogPO();
        orderLogPO.setOrderId(supplyOrderPO.getId());
        orderLogPO.setCreatedBy(request.getOperator());
        orderLogPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderLogPO.setTarget(supplyOrderPO.getSupplyOrderCode());
        orderLogPO.setContent(request.getConfirmStatus() == 0 ? "确认了订单" : "拒绝了订单");
        EBKSupplyOrderLogMapper.insert(orderLogPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response querySupplyOrderLog(Map<String, String> request) {
        Response response = new Response();
        List<SupplyOrderLogDTO> supplyOrderLogDTOList = new ArrayList<>();
        SupplyOrderLogDTO supplyOrderLogDTO = new SupplyOrderLogDTO();
        EBKSupplyOrderLogPO orderLogPO = new EBKSupplyOrderLogPO();
        orderLogPO.setOrderId(Integer.parseInt(request.get("orderId")));
        List<EBKSupplyOrderLogPO> supplyOrderLogPOList = EBKSupplyOrderLogMapper.select(orderLogPO);
        for (EBKSupplyOrderLogPO SupplyOrderLogPO : supplyOrderLogPOList) {
            supplyOrderLogDTO.setContent(SupplyOrderLogPO.getContent());
            supplyOrderLogDTO.setCreatedDt(SupplyOrderLogPO.getCreatedDt());
            supplyOrderLogDTO.setCreatedBy(SupplyOrderLogPO.getCreatedBy());
        }
        supplyOrderLogDTOList.add(supplyOrderLogDTO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplyOrderLogDTOList);
        return response;
    }


    @Override
    @Transactional
    public Response cancelSupplyOrder(CancelSupplyOrderDTO request) {
        Response response = new Response();
        EBKSupplyOrderPO ebkSupplyOrderPO = new EBKSupplyOrderPO();
        SupplyOrderPO supplyOrderPO = new SupplyOrderPO();
        supplyOrderPO.setId(request.getSupplyOrderId());
        supplyOrderPO = supplyOrderMapper.selectOne(supplyOrderPO);

        SaveSupplyResultDTO saveSupplyResultDTO = new SaveSupplyResultDTO();
        if (null != supplyOrderPO) {
            //修改供货单结果
            saveSupplyResultDTO.setSupplyOrderId(supplyOrderPO.getId());
            //确认取消
            if (0 == request.getCancelStatus()) {
                saveSupplyResultDTO.setConfirmationStatus(2);
            }
            saveSupplyResultDTO.setSupplierConfirmer(request.getOperator());
            saveSupplyResultDTO.setConfirmationCode(request.getConfirmationCode());
            saveSupplyResultDTO.setRefundFee(request.getRefundFee());
            saveSupplyResultDTO.setRemark(request.getRemark());
            saveSupplyResultDTO.setRefusedReason(request.getCancelledReason());
            saveSupplyResultDTO.setOperator(request.getOperator());
            supplyOrderService.saveSupplyResult(saveSupplyResultDTO);
        }


        //记日志
        EBKSupplyOrderLogPO orderLogPO = new EBKSupplyOrderLogPO();
        orderLogPO.setOrderId(supplyOrderPO.getId());
        orderLogPO.setCreatedBy(request.getOperator());
        orderLogPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderLogPO.setTarget(supplyOrderPO.getSupplyOrderCode());
        orderLogPO.setContent(request.getCancelStatus() == 0 ? "取消了订单" : "拒绝取消订单");
        EBKSupplyOrderLogMapper.insert(orderLogPO);

        //插入备注
        if (StringUtil.isValidString(request.getRemark())) {
            OrderRemarkPO orderRemarkInsert = new OrderRemarkPO();
            orderRemarkInsert.setOrderId(supplyOrderPO.getOrderId());
            orderRemarkInsert.setRemark(request.getRemark());
            orderRemarkInsert.setRemarkType(3);
            orderRemarkInsert.setReceiver(RemarkEnum.getremark(3));
            orderRemarkInsert.setCreatedBy(request.getOperator());
            orderRemarkInsert.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
            orderRemarkMapper.insert(orderRemarkInsert);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response queryOrderStatistics(Map<String, String> request) {
        Response response = new Response();
        SupplyOrderStatistics  supplyOrderStatistics= EBKSupplyOrderMapper.queryOrderStatistics(request);
        response.setModel(supplyOrderStatistics);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }


}
