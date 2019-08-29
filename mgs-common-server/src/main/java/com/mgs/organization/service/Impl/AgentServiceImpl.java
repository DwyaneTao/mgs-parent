package com.mgs.organization.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.EndTypeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.AvailableEnum;
import com.mgs.enums.DeleteEnum;
import com.mgs.enums.OrgEnum;
import com.mgs.organization.domain.*;
import com.mgs.organization.mapper.AgentCreditLineMapper;
import com.mgs.organization.mapper.AgentCompanyMapper;
import com.mgs.organization.mapper.OrgMapper;
import com.mgs.organization.remote.dto.AgentAddDTO;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import com.mgs.organization.remote.dto.AgentListRequest;
import com.mgs.organization.remote.dto.AgentSelectDTO;
import com.mgs.organization.remote.dto.BankSupplierDTO;
import com.mgs.organization.remote.dto.ContactSupplierDTO;
import com.mgs.organization.remote.dto.QueryAgentListDTO;
import com.mgs.organization.service.AgentService;
import com.mgs.user.service.UserService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author py
 * @date 2019/6/26 20:32
 **/
@Service
@Slf4j
public class AgentServiceImpl implements AgentService {
    @Autowired
    private OrgMapper orgMapper;
    @Autowired
    private AgentCompanyMapper agentCompanyMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private AgentCreditLineMapper agentCreditLineMapper;
    /**
     * 新增客户
     * @param agentAddDTO
     * @return
     */
    @Override
    public Response addAgent(AgentAddDTO agentAddDTO) {
        log.info("addAgent param: {}"+ JSON.toJSONString(agentAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        UserPO userPO = new UserPO();
        AgentCompanyPO agentCompanyPO = new AgentCompanyPO();
        if (agentAddDTO.getAgentType()==0){
        orgPO.setOrgType(agentAddDTO.getAgentType());
        orgPO.setOrgName(agentAddDTO.getAgentName());
        orgPO.setOrgDomian(agentAddDTO.getOrgDomain());
        userPO.setUserName(agentAddDTO.getAdminName());
        userPO.setUserTel(agentAddDTO.getAdminTel());
        userPO.setUserAccount(agentAddDTO.getAdminAccount());
        userPO.setCreatedBy(agentAddDTO.getCreatedBy());
        agentCompanyPO.setUserName(agentAddDTO.getAdminName());
        if(agentAddDTO.getSettlementType()!=3){
            agentCompanyPO.setBalance(new BigDecimal(agentAddDTO.getCreditLine()));
            agentCompanyPO.setCreditLine(agentAddDTO.getCreditLine());
        }
        agentCompanyPO.setUserTel(agentAddDTO.getAdminTel());
        agentCompanyPO.setUserNumber(agentAddDTO.getAdminAccount());
        agentCompanyPO.setSaleManagerId(agentAddDTO.getSaleManagerId());
        agentCompanyPO.setSettlementType(agentAddDTO.getSettlementType());
        agentCompanyPO.setCompanyCode(agentAddDTO.getCompanyCode());
        agentCompanyPO.setAvailableStatus(AvailableEnum.Start.key);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        orgPO.setCreatedDt(createdate);
        orgPO.setCreatedBy(agentAddDTO.getCreatedBy());
        orgPO.setAvailableStatus(AvailableEnum.Start.key);
        agentCompanyPO.setCreatedDt(createdate);
        agentCompanyPO.setCreatedBy(agentAddDTO.getCreatedBy());
        orgPO.setType(OrgEnum.Org_Agent.key);
        orgMapper.insert(orgPO);

        OrgPO supplierAddPO1 = orgMapper.selectByPrimaryKey(orgPO.getOrgId());
        agentCompanyPO.setOrgId(orgPO.getOrgId());
        userPO.setOrgCode(supplierAddPO1.getOrgCode());
        userService.addAdminUser(userPO, EndTypeEnum._3.no);
        agentCompanyMapper.insert(agentCompanyPO);
        }
        else {
            orgPO.setOrgTel(agentAddDTO.getAgentTel());
            orgPO.setOrgType(agentAddDTO.getAgentType());
            orgPO.setOrgName(agentAddDTO.getAgentName());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取String类型的时间
            String createdate = sdf.format(date);
            orgPO.setCreatedDt(createdate);
            orgPO.setCreatedBy(agentAddDTO.getCreatedBy());
            orgPO.setAvailableStatus(AvailableEnum.Start.key);
            agentCompanyPO.setCreatedDt(createdate);
            agentCompanyPO.setCreatedBy(agentAddDTO.getCreatedBy());
            agentCompanyPO.setSettlementType(agentAddDTO.getSettlementType());
            agentCompanyPO.setSaleManagerId(agentAddDTO.getSaleManagerId());
            agentCompanyPO.setCompanyCode(agentAddDTO.getCompanyCode());
            agentCompanyPO.setAvailableStatus(AvailableEnum.Start.key);
            orgPO.setType(OrgEnum.Org_Agent.key);
            orgMapper.insert(orgPO);
            agentCompanyPO.setOrgId(orgPO.getOrgId());
            agentCompanyMapper.insert(agentCompanyPO);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
    /**
     * 修改客户启用状态
     */
    @Override
    public Response modifyAgentStatus(AgentAddDTO agentAddDTO) {
        log.info("modifyAgentStatus param: {}"+ JSON.toJSONString(agentAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        UserPO userPO = new UserPO();
        AgentCompanyPO agentCompanyPO = new AgentCompanyPO();
        orgPO.setAvailableStatus(agentAddDTO.getAvailableStatus());
        orgPO.setOrgId(agentAddDTO.getAgentId());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        orgPO.setModifiedBy(agentAddDTO.getModifiedBy());
        orgPO.setModifiedDt(createdate);
        orgPO.setOrgId(agentAddDTO.getAgentId());
        agentCompanyPO.setModifiedBy(agentAddDTO.getModifiedBy());
        agentCompanyPO.setModifiedDt(createdate);
        agentCompanyPO.setOrgId(agentAddDTO.getAgentId());
        agentCompanyPO.setAvailableStatus(agentAddDTO.getAvailableStatus());
        OrgPO orgPO1 = orgMapper.selectByPrimaryKey(orgPO);
        Integer userId = orgMapper.getUserId(orgPO1.getOrgCode());
        if(userId!=null){
            userPO.setOrgCode(orgPO1.getOrgCode());
            userPO.setAvailableStatus(agentAddDTO.getAvailableStatus());
            userPO.setModifiedBy(agentAddDTO.getModifiedBy());
            userPO.setModifiedDt(createdate);
            orgMapper.updateAdminStatus(userPO);
        }
        orgMapper.updateAgentStatus(agentCompanyPO);
        orgMapper.updateByPrimaryKeySelective(orgPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改客户信息
     * @param agentAddDTO
     * @return
     */
    @Override
    public Response modifyAgent(AgentAddDTO agentAddDTO) {
        log.info("modifyAgent param: {}"+ JSON.toJSONString(agentAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        UserPO userPO = new UserPO();
        BigDecimal sum = new BigDecimal(agentAddDTO.getCreditLine());
        AgentCompanyPO agentCompanyPO = new AgentCompanyPO();
        if(agentAddDTO.getAgentType()==0) {
            orgPO.setOrgType(agentAddDTO.getAgentType());
            orgPO.setOrgName(agentAddDTO.getAgentName());
            userPO.setUserName(agentAddDTO.getAdminName());
            userPO.setUserTel(agentAddDTO.getAdminTel());
            userPO.setUserAccount(agentAddDTO.getAdminAccount());
            userPO.setCreatedBy(agentAddDTO.getCreatedBy());
            agentCompanyPO.setUserName(agentAddDTO.getAdminName());
            agentCompanyPO.setUserTel(agentAddDTO.getAdminTel());
            agentCompanyPO.setUserNumber(agentAddDTO.getAdminAccount());
            agentCompanyPO.setSaleManagerId(agentAddDTO.getSaleManagerId());
            agentCompanyPO.setSettlementType(agentAddDTO.getSettlementType());
            agentCompanyPO.setCompanyCode(agentAddDTO.getCompanyCode());
            agentCompanyPO.setAvailableStatus(AvailableEnum.Start.key);
            agentCompanyPO.setCreditLine(agentAddDTO.getCreditLine());
            OrgPO agent = orgMapper.selectByPrimaryKey(agentAddDTO.getAgentId());
            Integer userId = orgMapper.getUserId(agent.getOrgCode());
            userPO.setUserId(userId);
            Integer agentCompany = orgMapper.getAgentCompany(agentAddDTO.getAgentId());
            AgentCompanyPO agentCompanyPO1 = agentCompanyMapper.selectByPrimaryKey(agentCompany);
            sum = sum.subtract(new BigDecimal(agentCompanyPO1.getCreditLine())).add(agentCompanyPO1.getBalance());
            agentCompanyPO.setBalance(sum);
            agentCompanyPO.setACompanyId(agentCompany);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取String类型的时间
            String createdate = sdf.format(date);
            orgPO.setOrgId(agentAddDTO.getAgentId());
            orgPO.setModifiedDt(createdate);
            orgPO.setModifiedBy(agentAddDTO.getModifiedBy());
            orgPO.setAvailableStatus(AvailableEnum.Start.key);
            agentCompanyPO.setModifiedDt(createdate);
            agentCompanyPO.setModifiedBy(agentAddDTO.getModifiedBy());
            if (userId == null) {
                userPO.setOrgCode(agent.getOrgCode());
                userPO.setCreatedBy(agentAddDTO.getCreatedBy());
                userService.addAdminUser(userPO,EndTypeEnum._3.no);
                orgPO.setModifiedDt(createdate);
                orgPO.setModifiedBy(agentAddDTO.getModifiedBy());
                orgMapper.updateByPrimaryKeySelective(orgPO);
                agentCompanyMapper.updateByPrimaryKeySelective(agentCompanyPO);
                response.setResult(ResultCodeEnum.SUCCESS.code);
            } else {
                userPO.setModifiedBy(agentAddDTO.getModifiedBy());
                userPO.setUserId(userId);
                userService.modifyAdminUser(userPO);
                orgPO.setModifiedDt(createdate);
                orgPO.setModifiedBy(agentAddDTO.getModifiedBy());
                orgMapper.updateByPrimaryKeySelective(orgPO);
                agentCompanyMapper.updateByPrimaryKeySelective(agentCompanyPO);
                response.setResult(ResultCodeEnum.SUCCESS.code);
            }
        }
        else{
            orgPO.setOrgTel(agentAddDTO.getAgentTel());
            orgPO.setOrgType(agentAddDTO.getAgentType());
            orgPO.setOrgName(agentAddDTO.getAgentName());

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取String类型的时间
            String createdate = sdf.format(date);
            Integer agentCompany = orgMapper.getAgentCompany(agentAddDTO.getAgentId());
            agentCompanyPO.setACompanyId(agentCompany);
            orgPO.setOrgId(agentAddDTO.getAgentId());
            orgPO.setCreatedDt(createdate);
            orgPO.setCreatedBy(agentAddDTO.getCreatedBy());
            orgPO.setAvailableStatus(AvailableEnum.Start.key);
            OrgPO supplierAddPO1 = orgMapper.selectByPrimaryKey(orgPO.getOrgId());
            agentCompanyPO.setOrgId(orgPO.getOrgId());
            agentCompanyPO.setCreatedDt(createdate);
            agentCompanyPO.setCreatedBy(agentAddDTO.getCreatedBy());
            agentCompanyPO.setSettlementType(agentAddDTO.getSettlementType());
            agentCompanyPO.setSaleManagerId(agentAddDTO.getSaleManagerId());
            agentCompanyPO.setCompanyCode(agentAddDTO.getCompanyCode());
            orgMapper.updateByPrimaryKeySelective(orgPO);
            agentCompanyMapper.updateByPrimaryKeySelective(agentCompanyPO);
            response.setResult(ResultCodeEnum.SUCCESS.code);
        }
        return response;
    }

    /**
     * 根据客户code,查询客户详细信息
     * @param agentAddDTO
     * @return
     */
    @Override
   public Response queryAgentDetail(AgentAddDTO agentAddDTO) {
        log.info("queryAgentDetail param: {}"+ JSON.toJSONString(agentAddDTO));
        Response response = new Response();
        AgentSelectDTO agentSelectDTO = orgMapper.AgentList(agentAddDTO.getAgentCode());
        List<BankSupplierDTO> bankAddDTOS = orgMapper.BankList(agentAddDTO.getAgentCode(), DeleteEnum.STATUS_EXIST.key);
        List<ContactSupplierDTO> contactAddDTOS = orgMapper.ConcactList(agentAddDTO.getAgentCode(), DeleteEnum.STATUS_EXIST.key);
        if(bankAddDTOS.size()!=0){
        agentSelectDTO.setBankCardList(bankAddDTOS);
        }
        if(contactAddDTOS.size()!=0){
        agentSelectDTO.setContactList(contactAddDTOS);}
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(agentSelectDTO);
        return response;
    }

    @Override
    public PaginationSupportDTO<QueryAgentListDTO> queryAgentList(AgentListRequest request) {
        log.info("queryAgentList param: {}"+ JSON.toJSONString(request));
        Response response = new Response();
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<QueryAgentListDTO> list = orgMapper.queryAgentList(request);
        PageInfo<QueryAgentListDTO> page = new PageInfo<QueryAgentListDTO>(list);
        PaginationSupportDTO<QueryAgentListDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return  paginationSupport;
    }

    @Override
    public Response deductRefundCreditLine(AgentCreditLineDTO agentCreditLineDTO) {

        Response response = new Response();
         AgentSelectDTO agentSelectDTO = orgMapper.AgentList(agentCreditLineDTO.getAgentCode());
        String sum = agentSelectDTO.getCreditLine();
        AgentCreditLinePO agentCreditLinePO = new AgentCreditLinePO();
        BeanUtils.copyProperties(agentCreditLineDTO,agentCreditLinePO);
        List<AgentCreditLineDTO> agentCreditLine = orgMapper.getAgentCreditLine(agentCreditLineDTO.getAgentCode());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        agentCreditLinePO.setCreatedDt(createdate);
        agentCreditLinePO.setCreatedBy(agentCreditLineDTO.getCreatedBy());
        agentCreditLineMapper.insert(agentCreditLinePO);
        for(int i =0;i<agentCreditLine.size();i++){
            String deductRefundCreditLine = agentCreditLine.get(i).getDeductRefundCreditLine();
            sum = sum.concat(deductRefundCreditLine);
        }
        try {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
            String result = String.valueOf(nashorn.eval(sum));
            BigDecimal d=new BigDecimal(result);
            orgMapper.updateBalance(agentSelectDTO.getAgentId(),d);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return response;
    }
}
