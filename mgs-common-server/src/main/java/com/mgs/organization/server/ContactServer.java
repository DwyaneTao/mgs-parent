package com.mgs.organization.server;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.ContactAddDTO;
import com.mgs.organization.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/6/22 12:08
 **/
@RestController
@Slf4j
@RequestMapping("/contact")
public class ContactServer  {
    @Autowired
    private ContactService contactService;
    /**
     * 新增联系人信息
     */
    @PostMapping("/addContact")
    Response addContact(@RequestBody ContactAddDTO request){
        Response response = new Response();
        try{
            response = contactService.addContact(request);
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
    Response updateContact(@RequestBody ContactAddDTO request){
        Response response = new Response();
        try{
            response = contactService.modifyContact(request);
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
            response = contactService.deleteContact(request);
        } catch (Exception e) {
            log.error("deleteContact server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
