package com.mgs.organization.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.organization.remote.dto.*;

/**
 * @author py
 * @date 2019/6/28 11:26
 **/
public interface CompanyService {
    /**
     * 新增企业信息
     * @param
     * @return
     */
    Response addCompany(CompanyAddDTO companyAddDTO);
    /**
     * 修改企业信息
     */
    Response modifyCompany(CompanyAddDTO companyAddDTO);
    /**
     * 修改企业启动状态
     * @param
     * @return
     */
    Response modifyCompanyStatus(CompanyAddDTO companyAddDTO);
    /**
     * 根据企业编码查询企业详细信息
     */
    Response queryCompanyDetail(CompanyAddDTO companyAddDTO);
    /**
     * 运营商列表
     */
    PaginationSupportDTO<QueryCompanyListDTO> queryCompanyList(CompanyListRequest request);
    /**
     * 查询域名是否存在
     */
    Response isCompanyExit(CompanyAddDTO companyAddDTO);
    /**
     * 查询机构名称是否已存在
     */
    Response examineOrgName(ExamineOrgNameDTO examineOrgNameDTO);
}
