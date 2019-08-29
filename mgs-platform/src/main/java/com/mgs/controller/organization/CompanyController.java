package com.mgs.controller.organization;

import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.CompanyRemote;
import com.mgs.organization.remote.dto.CompanyAddDTO;
import com.mgs.organization.remote.dto.CompanyListRequest;
import com.mgs.organization.remote.dto.ExamineOrgNameDTO;
import com.mgs.organization.remote.dto.QueryCompanyListDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author py
 * @date 2019/7/2 16:12
 **/
@RestController
@Slf4j
@RequestMapping(value = "/company")
public class CompanyController  extends BaseController {
    @Autowired
    private CompanyRemote companyRemote;

    /**
     * 新增运营商
     *
     */
    @RequestMapping(value = "/addCompany",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response addCompany(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getCompanyName())
            &&StringUtil.isValidString(request.getCompanyDomain())
            &&StringUtil.isValidString(request.getAdminName())
            &&StringUtil.isValidString(request.getAdminAccount())
            &&StringUtil.isValidString(request.getAdminTel())
            ){
                request.setCreatedBy(getUserName());
                response= companyRemote.addCompany(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

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
    @RequestMapping(value = "/modifyCompany",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifyCompany(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{

            if (request.getCompanyId()!=null
            &&StringUtil.isValidString(request.getCompanyName())
            &&StringUtil.isValidString(request.getCompanyDomain())
            &&StringUtil.isValidString(request.getAdminName())
            &&StringUtil.isValidString(request.getAdminAccount())
            &&StringUtil.isValidString(request.getAdminTel())
            )
        {
                request.setModifiedBy(getUserName());
                response= companyRemote.modifyCompany(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }


        } catch (Exception e) {
            log.error("modifyCompany server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    /**
     * 修改运营商启用状态
     */
    @RequestMapping(value = "/modifyCompanyStatus",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifyCompanyStatus(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            if (request.getCompanyId()!=null
            &&request.getAvailableStatus()!=null
            ){
                request.setModifiedBy(getUserName());
                response= companyRemote.modifyCompanyStatus(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

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
    @RequestMapping(value = "/queryCompanyDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryCompanyDetail(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            if(request.getCompanyCode()==null){
                request.setCompanyCode(getCompanyCode());
            }
            response= companyRemote.queryCompanyDetail(request);
        } catch (Exception e) {
            log.error("CompanySelect server error!",e);
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
    @RequestMapping(value = "/queryCompanyList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response queryCompanyList(@RequestBody CompanyListRequest request){
        Response response = new Response();
        try{
            response = companyRemote.queryCompanyList(request);
        } catch (Exception e) {
            log.error("queryCompanyList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    @RequestMapping(value = "/isCompanyExit",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response isCompanyExit(@RequestBody CompanyAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getCompanyDomain())){
                response= companyRemote.isCompanyExit(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("queryCompanyList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
    @RequestMapping(value = "/examineOrgName",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response examineOrgName(@RequestBody ExamineOrgNameDTO request){
        Response response = new Response();
        try{
            request.setCompanyCode(getCompanyCode());
            if (StringUtil.isValidString(request.getOrgName())&&request.getType()!=null){
                response= companyRemote.examineOrgName(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("examineOrgName server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
