package com.mgs.organization.remote;

import com.mgs.common.Response;
import com.mgs.organization.remote.dto.CompanyAddDTO;
import com.mgs.organization.remote.dto.CompanyListRequest;
import com.mgs.organization.remote.dto.ExamineOrgNameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author py
 * @date 2019/7/2 16:01
 **/
@FeignClient(value = "mgs-common-server")
public interface CompanyRemote {
    /**
     * 新增运营商
     *
     */
    @PostMapping("/company/addCompany")
    Response addCompany(@RequestBody CompanyAddDTO request);
    /**
     * 修改运营商信息
     */
    @PostMapping("/company/modifyCompany")
    Response modifyCompany(@RequestBody CompanyAddDTO request);
    /**
     * 修改运营商启用状态
     */
    @PostMapping("/company/modifyCompanyStatus")
    Response modifyCompanyStatus(@RequestBody CompanyAddDTO request);
    /**
     * 查询运营商详情
     */
    @PostMapping("/company/queryCompanyDetail")
    Response queryCompanyDetail(@RequestBody CompanyAddDTO request);
    /**
     * 运营商列表
     * @param request
     * @return
     */
    @PostMapping("/company/queryCompanyList")
    Response queryCompanyList(@RequestBody CompanyListRequest request);
   /**
    * 查询域名是否存在
    */
   @PostMapping("/company/isCompanyExit")
   Response isCompanyExit(@RequestBody CompanyAddDTO request);
    /**
     *机构名称验重
     */
    @PostMapping("/company/examineOrgName")
    Response examineOrgName(@RequestBody ExamineOrgNameDTO request);
}
