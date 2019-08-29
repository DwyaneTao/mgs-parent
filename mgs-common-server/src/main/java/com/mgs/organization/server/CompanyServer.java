package com.mgs.organization.server;

import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.*;
import com.mgs.organization.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/6/28 15:25
 **/
@RestController
@Slf4j
@RequestMapping("/company")
public class CompanyServer{
    @Autowired
    private CompanyService companyService;
    /**
     * 新增运营商
     *
     */
    @PostMapping("/addCompany")
    Response addCompany(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            response= companyService.addCompany(request);
        } catch (Exception e) {
            log.error("addCompany server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    /**
     * 修改运营商信息
     */
    @PostMapping("/modifyCompany")
    Response modifyCompany(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            response= companyService.modifyCompany(request);

        } catch (Exception e) {
            log.error("updateCompany server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    /**
     * 修改运营商启用状态
     */
    @PostMapping("/modifyCompanyStatus")
    Response modifyCompanyStatus(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            response= companyService.modifyCompanyStatus(request);
        } catch (Exception e) {
            log.error("modifyCompanyStatus server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    /**
     * 查询运营商详情
     */
    @PostMapping("/queryCompanyDetail")
    Response queryCompanyDetail(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            response= companyService.queryCompanyDetail(request);
        } catch (Exception e) {
            log.error("queryCompanyDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 运营商列表
     * @param request
     * @return
     */
    @PostMapping("/queryCompanyList")
    Response queryCompanyList(@RequestBody CompanyListRequest request){
        Response response = new Response();
        try{
            PaginationSupportDTO<QueryCompanyListDTO> queryCompanyListDTOPaginationSupportDTO = companyService.queryCompanyList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(queryCompanyListDTOPaginationSupportDTO);
        } catch (Exception e) {
            log.error("queryCompanyList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 判断域名是否为空
     * @param request
     * @return
     */
    @PostMapping("/isCompanyExit")
    Response isExit(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            response = companyService.isCompanyExit(request);
        } catch (Exception e) {
            log.error("queryCompanyList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    /**
     * 判断机构名称验重
     */
    @PostMapping("/examineOrgName")
     Response examineOrgName(@RequestBody ExamineOrgNameDTO request) {
        Response response = new Response();
        try{
            response = companyService.examineOrgName(request);
        } catch (Exception e) {
            log.error("examineOrgName server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
