package com.mgs.controller.organization;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.ContactRemote;
import com.mgs.organization.remote.dto.ContactAddDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/7/2 17:36
 **/
@RestController
@Slf4j
@RequestMapping(value = "/contact")
public class ContactController  extends BaseController {
    @Autowired
    private ContactRemote contactRemote;
    /**
     * 新增联系人信息
     */
    @PostMapping("/addContact")
    Response addContact(@RequestBody ContactAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getOrgCode())
                    &&request.getOrgType()!=null
                    &&StringUtil.isValidString(request.getContactName())
                    &&StringUtil.isValidString(request.getContactTel())
            ){
                request.setCreatedBy(getUserName());
                response = contactRemote.addContact(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("addContact server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改联系人信息
     */
    @PostMapping("/modifyContact")
    Response modifyContact(@RequestBody ContactAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getOrgCode())
                    &&request.getContactId()!=null
                    &&StringUtil.isValidString(request.getContactName())
                    &&StringUtil.isValidString(request.getContactTel())
            ){
                request.setModifiedBy(getUserName());
                response = contactRemote.modifyContact(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("modifyContact server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除联系人信息
     */
    @PostMapping("/deleteContact")
    Response deleteContact(@RequestBody ContactAddDTO request){
        Response response = new Response();
        try{
            if (request.getContactId()!=null
            ){
                request.setModifiedBy(getUserName());
                response = contactRemote.deleteContact(request);
            }
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("deleteContact server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
