package com.mgs.organization.mapper;

import com.mgs.common.MyMapper;
import com.mgs.organization.domain.AgentCompanyPO;
import com.mgs.organization.domain.OrgPO;
import com.mgs.organization.domain.SupplierCompanyPO;
import com.mgs.organization.domain.UserPO;
import com.mgs.organization.remote.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author py
 * @date 2019/6/19 21:14
 **/
@Mapper
public interface OrgMapper extends MyMapper<OrgPO> {
    //根据供应商编号查出管理员对应的管理员Id
    public  Integer getUserId(@Param("companyCode")String companyCode);
    //根据机构Id,查询主键供应商
    public  Integer getSupplierCompany(@Param("supplierId")Integer supplierId);
    //根据机构Id,查询主键客户
    public  Integer getAgentCompany(@Param("agentId")Integer agentId);
    //根据供应商编码查询供应商详情
    SupplierSelectDTO SupplierList(@Param("orgCode")String orgCode);
    //根据供应商编码查询银行卡列表详情
    List<ContactSupplierDTO> ConcactList(@Param("orgCode")String orgCode, @Param("active")Integer active);
    //根据供应商编码查询联系人信息详情
    List<BankSupplierDTO>  BankList(@Param("orgCode")String orgCode, @Param("active")Integer active);
    //根据客户编码查询客户详情
    AgentSelectDTO AgentList(@Param("orgCode")String orgCode);
    //根据企业编码查询企业详情
    CompanySelectDTO companyList(@Param("orgCode")String orgCode);
    //根据企业编码修改总管理员启用状态
    public void updateAdminStatus(@Param("userPO") UserPO userPO);
    //供应商详情列表
    List<QuerySupplierListDTO> querySupplierList(@Param("request")SupplierListRequest request);
    //客户详情列表
    List<QueryAgentListDTO> queryAgentList(@Param("request")AgentListRequest request);
    //企业详情列表
    List<QueryCompanyListDTO> queryCompanyList(@Param("request")CompanyListRequest request);
    //修改供应商编码的role都为0
    public void updateContactRole(@Param("orgCode")String orgCode,@Param("contactRole")String  contactRole,@Param("role")String role);
   //修改供应商中间表启用状态
    public  void updateSupplierStatus(@Param("supplierCompanyPO") SupplierCompanyPO supplierCompanyPO);
    //修改客户中间表
    public  void updateAgentStatus(@Param("agentCompanyPO") AgentCompanyPO agentCompanyPO);
   //判断域名是否重复
    public  CompanySelectDTO getDomian(@Param("orgDomian")String orgDomian);
   //查询该分销商是否存在该订单的扣退额度
    public  List<AgentCreditLineDTO>  getAgentCreditLine(@Param("agentCode")String agentCode);
    //根据分销商编码插入剩余额度
    public void updateBalance(@Param("agentId")Integer agentId, @Param("Balance")BigDecimal balance);
    //机构名称验重,机构为运营商
    public  String getCompanyName(@Param("examineOrgNameDTO")ExamineOrgNameDTO examineOrgNameDTO);
    //机构名称验重，机构为客户
    public  String getAgentName(@Param("examineOrgNameDTO")ExamineOrgNameDTO examineOrgNameDTO);
    //机构名称验重，机构为供应商
    public  String getSupplierName(@Param("examineOrgNameDTO")ExamineOrgNameDTO examineOrgNameDTO);

}
