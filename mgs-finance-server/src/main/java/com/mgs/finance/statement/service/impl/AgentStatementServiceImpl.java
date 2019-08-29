package com.mgs.finance.statement.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.enums.BusinessTypeEnum;
import com.mgs.finance.enums.CheckStatusEnum;
import com.mgs.finance.enums.StatementStatusEnum;
import com.mgs.finance.remote.statement.request.QueryStatementTotalAmountDTO;
import com.mgs.finance.workorder.service.FinanceNofityService;
import com.mgs.finance.remote.workorder.request.NotifyCollectionDTO;
import com.mgs.finance.remote.workorder.request.NotifyItemDTO;
import com.mgs.finance.remote.workorder.request.NotifyPaymentDTO;
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
import com.mgs.finance.remote.statement.response.AgentStatementDetailDTO;
import com.mgs.finance.remote.statement.response.AgentStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.StatementOrderDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutOrderDTO;
import com.mgs.finance.remote.statement.response.UncheckOutAgentDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementOrderDTO;
import com.mgs.finance.statement.domain.AgentStatementOrderPO;
import com.mgs.finance.statement.domain.AgentStatementPO;
import com.mgs.finance.statement.dto.InsertStatementOrderDTO;
import com.mgs.finance.statement.dto.UpdateOrderFinanceDTO;
import com.mgs.finance.statement.mapper.AgentStatementMapper;
import com.mgs.finance.statement.mapper.AgentStatementOrderMapper;
import com.mgs.finance.statement.service.AgentStatementService;
import com.mgs.organization.remote.AgentRemote;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AgentStatementServiceImpl implements AgentStatementService{

    @Autowired
    private AgentStatementMapper agentStatementMapper;

    @Autowired
    private AgentStatementOrderMapper agentStatementOrderMapper;

    @Autowired
    private FinanceNofityService financeNofityService;

    @Autowired
    private AgentRemote agentRemote;

    @Override
    public PaginationSupportDTO<AgentStatementListResponseDTO> queryStatementList(QueryAgentStatementListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<AgentStatementListResponseDTO> list =agentStatementMapper.queryStatementList(request);
        PageInfo<AgentStatementListResponseDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<AgentStatementListResponseDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public PaginationSupportDTO<UncheckOutAgentDTO> queryUncheckOutAgentList(QueryUncheckOutAgentListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<UncheckOutAgentDTO> list =agentStatementMapper.queryUncheckOutAgentList(request);
        PageInfo<UncheckOutAgentDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<UncheckOutAgentDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response createStatement(CreateAgentStatementDTO request) throws ParseException {
        log.info("createStatement param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.创建账单
        AgentStatementPO agentStatementPO=new AgentStatementPO();
        BeanUtils.copyProperties(request,agentStatementPO);
        agentStatementPO.setStatementStatus(StatementStatusEnum.UN_CHECK.key);
        agentStatementPO.setStatementAmt(BigDecimal.ZERO);
        agentStatementPO.setReceivedAmt(BigDecimal.ZERO);
        agentStatementPO.setUnreceivedAmt(BigDecimal.ZERO);
        agentStatementPO.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        agentStatementPO.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        agentStatementPO.setStartDate(DateUtil.stringToDate(request.getStartDate()));
        agentStatementPO.setEndDate(DateUtil.stringToDate(request.getEndDate()));
        agentStatementPO.setSettlementStatus(0);
        agentStatementPO.setSettlementDate(DateUtil.stringToDate(request.getSettlementDate()));
        agentStatementPO.setCreatedBy(request.getOperator());
        agentStatementPO.setCreatedDt(new Date());
        agentStatementMapper.insert(agentStatementPO);

        // 2.保存账单明细
        InsertStatementOrderDTO insertStatementOrderDTO=new InsertStatementOrderDTO();
        BeanUtils.copyProperties(request,insertStatementOrderDTO);
        insertStatementOrderDTO.setStatementId(agentStatementPO.getId());
        agentStatementOrderMapper.saveBatchStatementOrder(insertStatementOrderDTO);

        // 3.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(agentStatementPO.getId());
        //agentStatementOrderMapper.updateStatementAmount(statementIdDTO);
        updateStatementAmount(statementIdDTO);

        // 4.更新订单对账状态为已纳入账单，并将订单加锁
        UpdateOrderFinanceDTO updateOrderFinanceDTO=new UpdateOrderFinanceDTO();
        updateOrderFinanceDTO.setStatementId(agentStatementPO.getId());
        updateOrderFinanceDTO.setCheckStatus(CheckStatusEnum.HOLD.key);
        updateOrderFinanceDTO.setFinanceLockStatus(1);
        agentStatementOrderMapper.updateOrderFinance(updateOrderFinanceDTO);

        agentStatementPO = agentStatementMapper.selectByPrimaryKey(agentStatementPO.getId());
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(agentStatementPO.getStatementCode());
        return response;
    }

    @Override
    public AgentStatementDetailDTO queryStatementDetail(StatementIdDTO request) {
        AgentStatementDetailDTO agentStatementDetailDTO=new AgentStatementDetailDTO();
        AgentStatementPO agentStatementPO = new AgentStatementPO();
        if(request.getStatementId() != null){
            agentStatementPO.setId(request.getStatementId());
        }
        if(StringUtil.isValidString(request.getStatementCode())){
            agentStatementPO.setStatementCode(request.getStatementCode());
        }
        agentStatementPO=agentStatementMapper.selectOne(agentStatementPO);
        BeanUtils.copyProperties(agentStatementPO,agentStatementDetailDTO);
        agentStatementDetailDTO.setStatementId(agentStatementPO.getId());
        // TODO: 2019/7/2 查询分销商联系人信息
        //agentStatementDetailDTO.setContactName();
        //agentStatementDetailDTO.setContactTel();
        Long days=DateUtil.getDay(agentStatementPO.getSettlementDate(), (agentStatementPO.getRealSettlementDate() == null?DateUtil.stringToDate(DateUtil.dateToString(new Date())):agentStatementPO.getRealSettlementDate()));
        agentStatementDetailDTO.setOverdueDays(days>0?days.intValue():0);

        //新增的订单明细数
        QueryUnCheckOutOrderDTO queryUnCheckOutOrderDTO = new QueryUnCheckOutOrderDTO();
        queryUnCheckOutOrderDTO.setAgentCode(agentStatementDetailDTO.getAgentCode());
        queryUnCheckOutOrderDTO.setCompanyCode(request.getCompanyCode());
        queryUnCheckOutOrderDTO.setDateQueryType(1);
        queryUnCheckOutOrderDTO.setPageSize(10000);
        agentStatementDetailDTO.setNewOrderQty(queryUnCheckOutOrder(queryUnCheckOutOrderDTO).getItemList().size());

        //更新的订单明细数
        QueryUpdatedStatementOrderListDTO queryUpdatedStatementOrderListDTO = new QueryUpdatedStatementOrderListDTO();
        queryUpdatedStatementOrderListDTO.setPageSize(10000);
        queryUpdatedStatementOrderListDTO.setStatementId(request.getStatementId());
        agentStatementDetailDTO.setUpdatedOrderQty(queryUpdatedStatementOrderList(queryUpdatedStatementOrderListDTO).getItemList().size());
        return agentStatementDetailDTO;
    }

    @Override
    public PaginationSupportDTO<StatementOrderDTO> queryStatementOrderList(QueryStatementOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<StatementOrderDTO> list =agentStatementOrderMapper.queryStatementOrderList(request);
        PageInfo<StatementOrderDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<StatementOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public PaginationSupportDTO<UnCheckOutOrderDTO> queryUnCheckOutOrder(QueryUnCheckOutOrderDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<UnCheckOutOrderDTO> list =agentStatementOrderMapper.queryUnCheckOutOrder(request);
        PageInfo<UnCheckOutOrderDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<UnCheckOutOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response addStatementOrderList(AddStatementOrderListDTO request) {
        log.info("addStatementOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        AgentStatementPO agentStatementPO=agentStatementMapper.selectByPrimaryKey(request.getStatementId());

        // 1.保存账单明细
        InsertStatementOrderDTO insertStatementOrderDTO=new InsertStatementOrderDTO();
        insertStatementOrderDTO.setStatementId(request.getStatementId());
        insertStatementOrderDTO.setOrderIdList(request.getOrderIdList());
        insertStatementOrderDTO.setCompanyCode(agentStatementPO.getCompanyCode());
        agentStatementOrderMapper.saveBatchStatementOrder(insertStatementOrderDTO);

        // 2.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        //agentStatementOrderMapper.updateStatementAmount(statementIdDTO);
        updateStatementAmount(statementIdDTO);

        // 3.更新订单对账状态为已纳入账单，并将订单加锁
        UpdateOrderFinanceDTO updateOrderFinanceDTO=new UpdateOrderFinanceDTO();
        updateOrderFinanceDTO.setStatementId(request.getStatementId());
        updateOrderFinanceDTO.setCheckStatus(CheckStatusEnum.HOLD.key);
        updateOrderFinanceDTO.setFinanceLockStatus(1);
        updateOrderFinanceDTO.setOrderIdList(request.getOrderIdList());
        agentStatementOrderMapper.updateOrderFinance(updateOrderFinanceDTO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response deleteStatementOrderList(DeleteStatementOrderListDTO request) {
        log.info("deleteStatementOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新结算状态为可出账
        UpdateOrderFinanceDTO updateOrderFinanceDTO=new UpdateOrderFinanceDTO();
        updateOrderFinanceDTO.setStatementId(request.getStatementId());
        updateOrderFinanceDTO.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
        updateOrderFinanceDTO.setStatementOrderIdList(request.getStatementOrderIdList());
        agentStatementOrderMapper.updateOrderFinance(updateOrderFinanceDTO);

        // 2.删除账单明细
        Example example=new Example(AgentStatementOrderPO.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",request.getStatementOrderIdList());
        agentStatementOrderMapper.deleteByExample(example);

        // 3.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        //agentStatementOrderMapper.updateStatementAmount(statementIdDTO);
        updateStatementAmount(statementIdDTO);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response modifyStatementStatus(ModifyStatementStatusDTO request) {
        log.info("modifyStatementStatus param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.确认账单前检查账单金额是否发生变化
        if (request.getStatementStatus()==StatementStatusEnum.CONFIRMED.key || request.getStatementStatus()== StatementStatusEnum.CANCELED.key){
            StatementIdDTO statementIdDTO=new StatementIdDTO();
            statementIdDTO.setStatementId(request.getStatementId());
            BigDecimal statementAmount=agentStatementOrderMapper.queryNewStatementAmount(statementIdDTO);
            AgentStatementPO agentStatementPO=agentStatementMapper.selectByPrimaryKey(request.getStatementId());
            if (agentStatementPO.getStatementAmt().compareTo(statementAmount)!=0 && request.getStatementStatus()==StatementStatusEnum.CONFIRMED.key){
                return new Response(ResultCodeEnum.SUCCESS.code,null,"账单应收金额发生变化，请先更新账单");
            }
            if(request.getStatementStatus()== StatementStatusEnum.CANCELED.key
                    && (agentStatementPO.getReceivedAmt()== null || agentStatementPO.getReceivedAmt().compareTo(BigDecimal.ZERO)!= 0
                    || agentStatementPO.getUnconfirmedPaidAmt()== null || agentStatementPO.getUnconfirmedPaidAmt().compareTo(BigDecimal.ZERO)!=0
                    || agentStatementPO.getUnconfirmedReceivedAmt()== null || agentStatementPO.getUnconfirmedReceivedAmt().compareTo(BigDecimal.ZERO) != 0)){
                return new Response(ResultCodeEnum.SUCCESS.code,null,"该账单已产生实收或实付，或待确认的收款或付款,不能取消");
            }
        }

        // 2.更新账单状态
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(request.getStatementId());

        // 3.更新订单对账状态为已对账
        if (request.getStatementStatus()==StatementStatusEnum.CONFIRMED.key){
            AgentStatementPO agentStatementPO = agentStatementMapper.selectOne(agentStatementUpdate);

            UpdateOrderFinanceDTO updateOrderFinanceDTO=new UpdateOrderFinanceDTO();
            updateOrderFinanceDTO.setStatementId(request.getStatementId());
            updateOrderFinanceDTO.setCheckStatus(CheckStatusEnum.CHECKED.key);
            if(agentStatementPO.getReceivedAmt() != null && agentStatementPO.getStatementAmt().compareTo(agentStatementPO.getReceivedAmt())==0){
                agentStatementUpdate.setSettlementStatus(1);
                agentStatementUpdate.setRealSettlementDate(new Date());
                updateOrderFinanceDTO.setIsUpdateSettlementStatus(1);
                updateOrderFinanceDTO.setIsUpdateSettlementAmount(1);
            }
            agentStatementOrderMapper.updateOrderFinance(updateOrderFinanceDTO);

            //返还额度
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

        }else if(request.getStatementStatus() == StatementStatusEnum.CANCELED.key){
            //查询该订单下面的所有账单
            QueryStatementOrderListDTO  queryStatementOrderListDTO  = new QueryStatementOrderListDTO();
            queryStatementOrderListDTO.setStatementId(request.getStatementId());
            List<StatementOrderDTO> list =agentStatementOrderMapper.queryStatementOrderList(queryStatementOrderListDTO);

            //相当于将所有的订单明细删除
            if(CollectionUtils.isNotEmpty(list)){
                DeleteStatementOrderListDTO deleteStatementOrderListDTO = new DeleteStatementOrderListDTO();
                deleteStatementOrderListDTO.setStatementId(request.getStatementId());
                List<Integer> orderList = new ArrayList<Integer>();
                for(StatementOrderDTO statementOrderDTO: list){
                    orderList.add(statementOrderDTO.getStatementOrderId());
                }
                deleteStatementOrderListDTO.setStatementOrderIdList(orderList);
                deleteStatementOrderList(deleteStatementOrderListDTO);
            }
        }
        agentStatementUpdate.setStatementStatus(request.getStatementStatus());
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public PaginationSupportDTO<UpdatedStatementOrderDTO> queryUpdatedStatementOrderList(QueryUpdatedStatementOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<UpdatedStatementOrderDTO> list =agentStatementOrderMapper.queryUpdatedStatementOrderList(request);
        PageInfo<UpdatedStatementOrderDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<UpdatedStatementOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response updateStatementOrderList(StatementIdDTO request) {
        log.info("updateStatementOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新账单明细
        agentStatementOrderMapper.updateStatementOrderList(request);

        // 2.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        //agentStatementOrderMapper.updateStatementAmount(statementIdDTO);
        updateStatementAmount(statementIdDTO);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response notifyCollectionOfStatement(NotifyCollectionOfStatementDTO request) {
        log.info("notifyCollectionOfStatement param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.判断账单应收金额是否发生变化
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        BigDecimal statementAmount=agentStatementOrderMapper.queryNewStatementAmount(statementIdDTO);
        AgentStatementPO agentStatementPO=agentStatementMapper.selectByPrimaryKey(request.getStatementId());
        /*if (agentStatementPO.getStatementAmt().compareTo(statementAmount)!=0){
            return new Response(ResultCodeEnum.FAILURE.code,null,"账单应收金额发生变化，请先更新账单");
        }*/

        // 2.更新已通知金额
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(request.getStatementId());
        agentStatementUpdate.setUnconfirmedReceivedAmt(agentStatementPO.getUnconfirmedReceivedAmt().add(request.getAmt()));
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);

        // 3.创建工单
        NotifyCollectionDTO notifyCollectionDTO=new NotifyCollectionDTO();
        BeanUtils.copyProperties(request,notifyCollectionDTO);
        notifyCollectionDTO.setBusinessType(BusinessTypeEnum.AGENTSTATEMENT.key);
        notifyCollectionDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                agentStatementPO.getStatementCode(),
                request.getAmt()
        )));
        notifyCollectionDTO.setCollectionAmt(request.getAmt());
        notifyCollectionDTO.setOrgCode(agentStatementPO.getAgentCode());
        notifyCollectionDTO.setOrgName(agentStatementPO.getAgentName());
        notifyCollectionDTO.setCompanyCode(agentStatementPO.getCompanyCode());
        notifyCollectionDTO.setContent("账单款");
        notifyCollectionDTO.setCurrency(agentStatementPO.getCurrency());
        notifyCollectionDTO.setPhotoList(request.getPhotoList());
        notifyCollectionDTO.setCreatedBy(request.getOperator());
        notifyCollectionDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        return financeNofityService.notifyCollection(notifyCollectionDTO);
    }

    @Transactional
    @Override
    public Response notifyPaymentOfStatement(NotifyPaymentOfStatementDTO request) {
        log.info("notifyCollectionOfStatement param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.判断账单应收金额是否发生变化
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        BigDecimal statementAmount=agentStatementOrderMapper.queryNewStatementAmount(statementIdDTO);
        AgentStatementPO agentStatementPO=agentStatementMapper.selectByPrimaryKey(request.getStatementId());
       /* if (agentStatementPO.getStatementAmt().compareTo(statementAmount)!=0){
            return new Response(ResultCodeEnum.FAILURE.code,null,"账单应收金额发生变化，请先更新账单");
        }*/

        // 2.更新已通知金额
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(request.getStatementId());
        agentStatementUpdate.setUnconfirmedPaidAmt(agentStatementPO.getUnconfirmedPaidAmt().add(request.getAmt()));
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);

        // 3.创建工单
        NotifyPaymentDTO notifyPaymentDTO=new NotifyPaymentDTO();
        BeanUtils.copyProperties(request,notifyPaymentDTO);
        notifyPaymentDTO.setPhotoList(request.getPhotoList());
        notifyPaymentDTO.setBusinessType(BusinessTypeEnum.AGENTSTATEMENT.key);
        notifyPaymentDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                agentStatementPO.getStatementCode(),
                request.getAmt()
        )));
        notifyPaymentDTO.setPaymentAmt(request.getAmt());
        notifyPaymentDTO.setOrgCode(agentStatementPO.getAgentCode());
        notifyPaymentDTO.setOrgName(agentStatementPO.getAgentName());
        notifyPaymentDTO.setCompanyCode(agentStatementPO.getCompanyCode());
        notifyPaymentDTO.setContent("账单款");
        notifyPaymentDTO.setCurrency(agentStatementPO.getCurrency());
        notifyPaymentDTO.setCreatedBy(request.getOperator());
        notifyPaymentDTO.setCreatedDt(DateUtil.dateToString(new Date(), DateUtil.hour_format));
        return financeNofityService.notifyPayment(notifyPaymentDTO);
    }

    @Transactional
    @Override
    public Response modifyStatementName(ModifyStatementNameDTO request) {
        log.info("modifyStatementName param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新账单名称
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(request.getStatementId());
        agentStatementUpdate.setStatementName(request.getStatementName());
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response modifySettlementDate(ModifySettlementDateDTO request) {
        log.info("modifySettlementDate param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新账单结算日期
        AgentStatementPO agentStatementUpdate=new AgentStatementPO();
        agentStatementUpdate.setId(request.getStatementId());
        agentStatementUpdate.setSettlementDate(DateUtil.stringToDate(request.getSettlementDate()));
        agentStatementMapper.updateByPrimaryKeySelective(agentStatementUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    private void updateStatementAmount(StatementIdDTO request){
        QueryStatementTotalAmountDTO queryStatementTotalAmountDTO = agentStatementOrderMapper.queryStatementTotalAmount(request);
        if(queryStatementTotalAmountDTO == null){
            queryStatementTotalAmountDTO = new QueryStatementTotalAmountDTO();
            queryStatementTotalAmountDTO.setStatementId(request.getStatementId());
            queryStatementTotalAmountDTO.setCurrency("0");
            queryStatementTotalAmountDTO.setAmount("0");
        }
        agentStatementOrderMapper.updateStatementAmount(queryStatementTotalAmountDTO);
    }
}
