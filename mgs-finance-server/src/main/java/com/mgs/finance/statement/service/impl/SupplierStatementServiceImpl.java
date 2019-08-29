package com.mgs.finance.statement.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SettlementTypeEnum;
import com.mgs.finance.enums.BusinessTypeEnum;
import com.mgs.finance.enums.CheckStatusEnum;
import com.mgs.finance.enums.StatementStatusEnum;
import com.mgs.finance.remote.statement.request.QueryStatementTotalAmountDTO;
import com.mgs.finance.statement.domain.AgentStatementPO;
import com.mgs.finance.statement.dto.UpdateOrderFinanceDTO;
import com.mgs.finance.statement.mapper.AgentStatementMapper;
import com.mgs.finance.workorder.service.FinanceNofityService;
import com.mgs.finance.remote.workorder.request.NotifyCollectionDTO;
import com.mgs.finance.remote.workorder.request.NotifyItemDTO;
import com.mgs.finance.remote.workorder.request.NotifyPaymentDTO;
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
import com.mgs.finance.remote.statement.response.StatementSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementDetailDTO;
import com.mgs.finance.remote.statement.response.SupplierStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.UnCheckOutSupplyOrderDTO;
import com.mgs.finance.remote.statement.response.UncheckOutSupplierDTO;
import com.mgs.finance.remote.statement.response.UpdatedStatementSupplyOrderDTO;
import com.mgs.finance.statement.domain.SupplierStatementOrderPO;
import com.mgs.finance.statement.domain.SupplierStatementPO;
import com.mgs.finance.statement.dto.InsertStatementSupplyOrderDTO;
import com.mgs.finance.statement.dto.UpdateSupplyOrderFinanceDTO;
import com.mgs.finance.statement.mapper.SupplierStatementMapper;
import com.mgs.finance.statement.mapper.SupplierStatementOrderMapper;
import com.mgs.finance.statement.service.SupplierStatementService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SupplierStatementServiceImpl implements SupplierStatementService{

    @Autowired
    private SupplierStatementMapper supplierStatementMapper;

    @Autowired
    private SupplierStatementOrderMapper supplierStatementOrderMapper;

    @Autowired
    private FinanceNofityService financeNofityService;

    @Autowired
    private AgentStatementMapper agentStatementMapper;

    @Override
    public PaginationSupportDTO<SupplierStatementListResponseDTO> queryStatementList(QuerySupplierStatementListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<SupplierStatementListResponseDTO> list =supplierStatementMapper.queryStatementList(request);
        PageInfo<SupplierStatementListResponseDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<SupplierStatementListResponseDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public PaginationSupportDTO<UncheckOutSupplierDTO> queryUncheckOutSupplierList(QueryUncheckOutSupplierListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<UncheckOutSupplierDTO> list =supplierStatementMapper.queryUncheckOutSupplierList(request);
        PageInfo<UncheckOutSupplierDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<UncheckOutSupplierDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response createStatement(CreateSupplierStatementDTO request) {
        log.info("createStatement param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.创建账单
        SupplierStatementPO supplierStatementPO=new SupplierStatementPO();
        BeanUtils.copyProperties(request,supplierStatementPO);
        supplierStatementPO.setStatementStatus(StatementStatusEnum.UN_CHECK.key);
        supplierStatementPO.setStatementAmt(BigDecimal.ZERO);
        supplierStatementPO.setPaidAmt(BigDecimal.ZERO);
        supplierStatementPO.setUnpaidAmt(BigDecimal.ZERO);
        supplierStatementPO.setUnconfirmedReceivedAmt(BigDecimal.ZERO);
        supplierStatementPO.setUnconfirmedPaidAmt(BigDecimal.ZERO);
        supplierStatementPO.setStartDate(DateUtil.stringToDate(request.getStartDate()));
        supplierStatementPO.setEndDate(DateUtil.stringToDate(request.getEndDate()));
        supplierStatementPO.setSettlementStatus(0);
        supplierStatementPO.setCreatedBy(request.getOperator());
        supplierStatementPO.setCreatedDt(new Date());
        supplierStatementPO.setSettlementDate(DateUtil.stringToDate(request.getSettlementDate()));
        supplierStatementMapper.insert(supplierStatementPO);

        // 2.保存账单明细
        InsertStatementSupplyOrderDTO insertStatementSupplyOrderDTO=new InsertStatementSupplyOrderDTO();
        BeanUtils.copyProperties(request,insertStatementSupplyOrderDTO);
        insertStatementSupplyOrderDTO.setStatementId(supplierStatementPO.getId());
        supplierStatementOrderMapper.saveBatchStatementOrder(insertStatementSupplyOrderDTO);

        // 3.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(supplierStatementPO.getId());
        //supplierStatementOrderMapper.updateStatementAmount(statementIdDTO);
        updateStatementAmount(statementIdDTO);

        // 4.更新订单对账状态为已纳入账单，并将订单加锁
        UpdateSupplyOrderFinanceDTO updateSupplyOrderFinanceDTO=new UpdateSupplyOrderFinanceDTO();
        updateSupplyOrderFinanceDTO.setStatementId(supplierStatementPO.getId());
        updateSupplyOrderFinanceDTO.setCheckStatus(CheckStatusEnum.HOLD.key);
        updateSupplyOrderFinanceDTO.setFinanceLockStatus(1);
        supplierStatementOrderMapper.updateSupplyOrderFinance(updateSupplyOrderFinanceDTO);

        supplierStatementPO = supplierStatementMapper.selectByPrimaryKey(supplierStatementPO.getId());
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplierStatementPO.getStatementCode());
        return response;
    }

    @Override
    public SupplierStatementDetailDTO queryStatementDetail(StatementIdDTO request) {
        SupplierStatementDetailDTO supplierStatementDetailDTO=new SupplierStatementDetailDTO();
        SupplierStatementPO supplierStatementPO = new SupplierStatementPO();
        if(request.getStatementId() != null){
            supplierStatementPO.setId(request.getStatementId());
        }
        if(StringUtil.isValidString(request.getStatementCode())){
            supplierStatementPO.setStatementCode(request.getStatementCode());
        }
        supplierStatementPO=supplierStatementMapper.selectOne(supplierStatementPO);
        BeanUtils.copyProperties(supplierStatementPO,supplierStatementDetailDTO);
        supplierStatementDetailDTO.setStatementId(supplierStatementPO.getId());
        // TODO: 2019/7/2 查询分销商联系人信息
        //supplierStatementDetailDTO.setContactName();
        //supplierStatementDetailDTO.setContactTel();
        if(supplierStatementDetailDTO.getStatementStatus() < 2){
            supplierStatementDetailDTO.setModifyBy(null);
            supplierStatementDetailDTO.setModifyDt(null);
        }
        Long days=DateUtil.getDay((supplierStatementPO.getSettlementDate()), (supplierStatementPO.getRealSettlementDate() == null?DateUtil.stringToDate(DateUtil.dateToString(new Date())): supplierStatementPO.getRealSettlementDate()));
        supplierStatementDetailDTO.setOverdueDays(days>0?days.intValue():0);


        //查询新增明细数
        QueryUnCheckOutSupplyOrderDTO queryUnCheckOutSupplyOrderDTO = new QueryUnCheckOutSupplyOrderDTO();
        queryUnCheckOutSupplyOrderDTO.setPageSize(10000);
        queryUnCheckOutSupplyOrderDTO.setDateQueryType(1);
        queryUnCheckOutSupplyOrderDTO.setSupplierCode(supplierStatementDetailDTO.getSupplierCode());
        queryUnCheckOutSupplyOrderDTO.setCompanyCode(request.getCompanyCode());
        supplierStatementDetailDTO.setNewOrderQty(queryUnCheckOutSupplyOrder(queryUnCheckOutSupplyOrderDTO).getItemList().size());

        //查询更新明细数
        QueryUpdatedStatementOrderListDTO queryUpdatedStatementOrderListDTO = new QueryUpdatedStatementOrderListDTO();
        queryUpdatedStatementOrderListDTO.setStatementId(request.getStatementId());
        queryUpdatedStatementOrderListDTO.setPageSize(10000);
        supplierStatementDetailDTO.setUpdatedOrderQty(queryUpdatedStatementOrderList(queryUpdatedStatementOrderListDTO).getItemList().size());
        return supplierStatementDetailDTO;
    }

    @Override
    public PaginationSupportDTO<StatementSupplyOrderDTO> queryStatementOrderList(QueryStatementSupplyOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<StatementSupplyOrderDTO> list =supplierStatementOrderMapper.queryStatementOrderList(request);
        PageInfo<StatementSupplyOrderDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<StatementSupplyOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Override
    public PaginationSupportDTO<UnCheckOutSupplyOrderDTO> queryUnCheckOutSupplyOrder(QueryUnCheckOutSupplyOrderDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<UnCheckOutSupplyOrderDTO> list =supplierStatementOrderMapper.queryUnCheckOutSupplyOrder(request);
        PageInfo<UnCheckOutSupplyOrderDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<UnCheckOutSupplyOrderDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return paginationSupport;
    }

    @Transactional
    @Override
    public Response addStatementOrderList(AddStatementSupplyOrderListDTO request) {
        log.info("addStatementOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectByPrimaryKey(request.getStatementId());

        // 1.保存账单明细
        InsertStatementSupplyOrderDTO insertStatementSupplyOrderDTO=new InsertStatementSupplyOrderDTO();
        insertStatementSupplyOrderDTO.setStatementId(request.getStatementId());
        insertStatementSupplyOrderDTO.setSupplyOrderIdList(request.getSupplyOrderIdList());
        insertStatementSupplyOrderDTO.setCompanyCode(supplierStatementPO.getCompanyCode());
        supplierStatementOrderMapper.saveBatchStatementOrder(insertStatementSupplyOrderDTO);

        // 2.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        //supplierStatementOrderMapper.updateStatementAmount(statementIdDTO);
        updateStatementAmount(statementIdDTO);

        // 3.更新订单对账状态为已纳入账单，并将订单加锁
        UpdateSupplyOrderFinanceDTO updateSupplyOrderFinanceDTO=new UpdateSupplyOrderFinanceDTO();
        updateSupplyOrderFinanceDTO.setStatementId(request.getStatementId());
        updateSupplyOrderFinanceDTO.setCheckStatus(CheckStatusEnum.HOLD.key);
        updateSupplyOrderFinanceDTO.setFinanceLockStatus(1);
        updateSupplyOrderFinanceDTO.setSupplyOrderIdList(request.getSupplyOrderIdList());
        supplierStatementOrderMapper.updateSupplyOrderFinance(updateSupplyOrderFinanceDTO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response deleteStatementOrderList(DeleteStatementOrderListDTO request) {
        log.info("deleteStatementOrderList param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新结算状态为可出账
        UpdateSupplyOrderFinanceDTO updateSupplyOrderFinanceDTO=new UpdateSupplyOrderFinanceDTO();
        updateSupplyOrderFinanceDTO.setStatementId(request.getStatementId());
        updateSupplyOrderFinanceDTO.setCheckStatus(CheckStatusEnum.CAN_CHECK.key);
        updateSupplyOrderFinanceDTO.setStatementOrderIdList(request.getStatementOrderIdList());
        supplierStatementOrderMapper.updateSupplyOrderFinance(updateSupplyOrderFinanceDTO);

        // 2.删除账单明细
        Example example=new Example(SupplierStatementOrderPO.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",request.getStatementOrderIdList());
        supplierStatementOrderMapper.deleteByExample(example);

        // 3.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        //supplierStatementOrderMapper.updateStatementAmount(statementIdDTO);
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
        if (request.getStatementStatus()==StatementStatusEnum.CONFIRMED.key || request.getStatementStatus()==StatementStatusEnum.CANCELED.key){
            StatementIdDTO statementIdDTO=new StatementIdDTO();
            statementIdDTO.setStatementId(request.getStatementId());
            BigDecimal statementAmount=supplierStatementOrderMapper.queryNewStatementAmount(statementIdDTO);
            SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectByPrimaryKey(request.getStatementId());
            if (supplierStatementPO.getStatementAmt().compareTo(statementAmount)!=0 && request.getStatementStatus()==StatementStatusEnum.CONFIRMED.key){
                return new Response(ResultCodeEnum.SUCCESS.code,null,"账单应收金额发生变化，请先更新账单");
            }
            if(request.getStatementStatus()==StatementStatusEnum.CANCELED.key
              && (supplierStatementPO.getUnconfirmedPaidAmt() == null || supplierStatementPO.getUnconfirmedPaidAmt().compareTo(BigDecimal.ZERO) != 0
              || supplierStatementPO.getUnconfirmedReceivedAmt() == null || supplierStatementPO.getUnconfirmedReceivedAmt().compareTo(BigDecimal.ZERO) != 0
              || supplierStatementPO.getPaidAmt() == null || supplierStatementPO.getPaidAmt().compareTo(BigDecimal.ZERO) != 0)){
                return new Response(ResultCodeEnum.SUCCESS.code,null,"该账单已产生实收或实付，或待确认的收款或付款,不能取消");
            }
        }

        // 2.更新账单状态
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(request.getStatementId());

        // 3.更新订单对账状态为已对账
        if (request.getStatementStatus()==StatementStatusEnum.CONFIRMED.key){
            supplierStatementUpdate.setModifiedBy(request.getOperator());
            supplierStatementUpdate.setModifiedDt(new Date());

            SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectOne(supplierStatementUpdate);

            UpdateSupplyOrderFinanceDTO updateSupplyOrderFinanceDTO=new UpdateSupplyOrderFinanceDTO();
            updateSupplyOrderFinanceDTO.setStatementId(request.getStatementId());
            updateSupplyOrderFinanceDTO.setCheckStatus(CheckStatusEnum.CHECKED.key);
            if(supplierStatementPO.getPaidAmt() != null && supplierStatementPO.getStatementAmt().compareTo(supplierStatementPO.getPaidAmt())==0){
                supplierStatementUpdate.setSettlementStatus(1);
                supplierStatementUpdate.setRealSettlementDate(new Date());
                updateSupplyOrderFinanceDTO.setIsUpdateSettlementStatus(1);
                updateSupplyOrderFinanceDTO.setIsUpdateSettlementAmount(1);
            }
            supplierStatementOrderMapper.updateSupplyOrderFinance(updateSupplyOrderFinanceDTO);
        }else if(request.getStatementStatus() == StatementStatusEnum.CANCELED.key){
            supplierStatementUpdate.setModifiedBy(request.getOperator());
            supplierStatementUpdate.setModifiedDt(new Date());

            //查询该订单下面的所有账单
            QueryStatementSupplyOrderListDTO  queryStatementOrderListDTO  = new QueryStatementSupplyOrderListDTO();
            queryStatementOrderListDTO.setStatementId(request.getStatementId());
            List<StatementSupplyOrderDTO> list =supplierStatementOrderMapper.queryStatementOrderList(queryStatementOrderListDTO);

            //相当于将所有的订单明细删除
            if(CollectionUtils.isNotEmpty(list)){
                DeleteStatementOrderListDTO deleteStatementOrderListDTO = new DeleteStatementOrderListDTO();
                deleteStatementOrderListDTO.setStatementId(request.getStatementId());
                List<Integer> orderList = new ArrayList<Integer>();
                for(StatementSupplyOrderDTO statementSupplyOrderDTO: list){
                    orderList.add(statementSupplyOrderDTO.getStatementOrderId());
                }
                deleteStatementOrderListDTO.setStatementOrderIdList(orderList);
                deleteStatementOrderList(deleteStatementOrderListDTO);
            }
        }
        supplierStatementUpdate.setStatementStatus(request.getStatementStatus());
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public PaginationSupportDTO<UpdatedStatementSupplyOrderDTO> queryUpdatedStatementOrderList(QueryUpdatedStatementOrderListDTO request) {
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<UpdatedStatementSupplyOrderDTO> list =supplierStatementOrderMapper.queryUpdatedStatementOrderList(request);
        PageInfo<UpdatedStatementSupplyOrderDTO> page = new PageInfo<>(list);

        PaginationSupportDTO<UpdatedStatementSupplyOrderDTO> paginationSupport=new PaginationSupportDTO<>();
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
        supplierStatementOrderMapper.updateStatementOrderList(request);

        // 2.更新账单金额
        StatementIdDTO statementIdDTO=new StatementIdDTO();
        statementIdDTO.setStatementId(request.getStatementId());
        //supplierStatementOrderMapper.updateStatementAmount(statementIdDTO);
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
        BigDecimal statementAmount=supplierStatementOrderMapper.queryNewStatementAmount(statementIdDTO);
        SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectByPrimaryKey(request.getStatementId());
        /*if (supplierStatementPO.getStatementAmt().compareTo(statementAmount)!=0){
            return new Response(ResultCodeEnum.FAILURE.code,null,"账单应收金额发生变化，请先更新账单");
        }*/

        // 2.更新已通知金额
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(request.getStatementId());
        supplierStatementUpdate.setUnconfirmedReceivedAmt(supplierStatementPO.getUnconfirmedReceivedAmt().add(request.getAmt()));
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);

        // 3.创建工单
        NotifyCollectionDTO notifyCollectionDTO=new NotifyCollectionDTO();
        BeanUtils.copyProperties(request,notifyCollectionDTO);
        notifyCollectionDTO.setCollectionAmt(request.getAmt());
        notifyCollectionDTO.setPhotoList(request.getPhotoList());
        notifyCollectionDTO.setBusinessType(BusinessTypeEnum.SUPPLIERSTATEMENT.key);
        notifyCollectionDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                supplierStatementPO.getStatementCode(),
                request.getAmt()
        )));
        notifyCollectionDTO.setOrgCode(supplierStatementPO.getSupplierCode());
        notifyCollectionDTO.setOrgName(supplierStatementPO.getSupplierName());
        notifyCollectionDTO.setCompanyCode(supplierStatementPO.getCompanyCode());
        notifyCollectionDTO.setContent("账单款");
        notifyCollectionDTO.setCurrency(supplierStatementPO.getCurrency());
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
        BigDecimal statementAmount=supplierStatementOrderMapper.queryNewStatementAmount(statementIdDTO);
        SupplierStatementPO supplierStatementPO=supplierStatementMapper.selectByPrimaryKey(request.getStatementId());
        /*if (supplierStatementPO.getStatementAmt().compareTo(statementAmount)!=0){
            return new Response(ResultCodeEnum.FAILURE.code,null,"账单应收金额发生变化，请先更新账单");
        }*/

        // 2.更新已通知金额
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(request.getStatementId());
        supplierStatementUpdate.setUnconfirmedPaidAmt(supplierStatementPO.getUnconfirmedPaidAmt().add(request.getAmt()));
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);

        // 3.创建工单
        NotifyPaymentDTO notifyPaymentDTO=new NotifyPaymentDTO();
        BeanUtils.copyProperties(request,notifyPaymentDTO);
        notifyPaymentDTO.setBusinessType(BusinessTypeEnum.SUPPLIERSTATEMENT.key);
        notifyPaymentDTO.setNotifyItemDTOList(Arrays.asList(new NotifyItemDTO(
                supplierStatementPO.getStatementCode(),
                request.getAmt()
        )));
        notifyPaymentDTO.setPaymentAmt(request.getAmt());
        notifyPaymentDTO.setOrgCode(supplierStatementPO.getSupplierCode());
        notifyPaymentDTO.setOrgName(supplierStatementPO.getSupplierName());
        notifyPaymentDTO.setCompanyCode(supplierStatementPO.getCompanyCode());
        notifyPaymentDTO.setContent("账单款");
        notifyPaymentDTO.setCurrency(supplierStatementPO.getCurrency());
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
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(request.getStatementId());
        supplierStatementUpdate.setStatementName(request.getStatementName());
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Transactional
    @Override
    public Response modifySettlementDate(ModifySettlementDateDTO request) {
        log.info("modifySettlementDate param: {}", JSON.toJSONString(request));
        Response response=new Response();

        // 1.更新账单结算日期
        SupplierStatementPO supplierStatementUpdate=new SupplierStatementPO();
        supplierStatementUpdate.setId(request.getStatementId());
        supplierStatementUpdate.setSettlementDate(DateUtil.stringToDate(request.getSettlementDate()));
        supplierStatementMapper.updateByPrimaryKeySelective(supplierStatementUpdate);

        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 更新账单金额
     * @param statementIdDTO
     */
    private void updateStatementAmount(StatementIdDTO statementIdDTO){
        QueryStatementTotalAmountDTO queryStatementTotalAmountDTO = supplierStatementOrderMapper.queryStatementAmount(statementIdDTO);
        if(queryStatementTotalAmountDTO == null){
            queryStatementTotalAmountDTO = new QueryStatementTotalAmountDTO();
            queryStatementTotalAmountDTO.setAmount("0");
            queryStatementTotalAmountDTO.setCurrency("0");
            queryStatementTotalAmountDTO.setStatementId(statementIdDTO.getStatementId());
        }
        supplierStatementOrderMapper.updateStatementAmount(queryStatementTotalAmountDTO);
    }
}
