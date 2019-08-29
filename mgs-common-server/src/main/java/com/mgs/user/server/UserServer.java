package com.mgs.user.server;


import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.organization.domain.UserPO;
import com.mgs.user.dto.EmployeeUserDTO;
import com.mgs.user.dto.MenuDTO;
import com.mgs.user.dto.RoleAndDomainDTO;
import com.mgs.user.dto.UserDTO;
import com.mgs.user.service.UserService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserServer{

    @Autowired
    private UserService userService;

    @PostMapping(value = "/resetAdminPwd",produces = { "application/json;charset=UTF-8" })
    public Response resetAdminPwd(@RequestBody Map<String,String> requestMap){
      Response response = new Response();
        try {
            if (requestMap != null && StringUtil.isValidString(requestMap.get("adminAccount"))){
                UserPO userPO = new UserPO();
                userPO.setUserAccount(requestMap.get("adminAccount"));
                userPO.setModifiedBy(requestMap.get("modifiedBy"));
                int i = userService.modifyAdminUserPwd(userPO);

                 if (i > 0) {
                   response.setResult(1);
                 } else {
                   response.setResult(1);
                   response.setFailCode(ErrorCodeEnum.NOT_ADMIN.errorCode);
                   response.setFailReason(ErrorCodeEnum.NOT_ADMIN.errorDesc);
                 }
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    @PostMapping(value = "/queryRoleAndDomain", produces = {"application/json;charset=UTF-8"})
    public Response getRoleAndDomain(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("endType"))){
                RoleAndDomainDTO roleAndDomain = userService.getRoleAndDomain(requestMap);
                response.setResult(1);
                response.setModel(roleAndDomain);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    @PostMapping(value = "/queryEmployeeList", produces = {"application/json;charset=UTF-8"})
    public Response getEmployeeList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            PageInfo<EmployeeUserDTO> employeeUserDTOS = userService.getEmployeeList(requestMap);

            response.setResult(1);
            if(employeeUserDTOS != null){
                PaginationSupportDTO<EmployeeUserDTO> userDTO = new PaginationSupportDTO<EmployeeUserDTO>();
                userDTO.copyProperties(employeeUserDTOS, EmployeeUserDTO.class);
                response.setModel(userDTO);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/addEmployee", produces = {"application/json;charset=UTF-8"})
    public Response addEmployeeUser(@RequestBody EmployeeUserDTO employeeUserDTO){
        Response response = new Response();
        try{
            if(employeeUserDTO != null && StringUtil.isValidString(employeeUserDTO.getEmployeeAccount())
              && StringUtil.isValidString(employeeUserDTO.getEmployeeName())
              && StringUtil.isValidString(employeeUserDTO.getEmployeeTel())
              && CollectionUtils.isNotEmpty(employeeUserDTO.getEmployeeRoleList())){
                int i = userService.addEmployeeUser(employeeUserDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else if(i == -2){
                    response.setResult(1);
                    response.setFailCode(ErrorCodeEnum.NO_ADMIN_AUTH.errorCode);
                    response.setFailReason(ErrorCodeEnum.NO_ADMIN_AUTH.errorDesc);
                }else {
                    response.setResult(1);
                    response.setFailCode(ErrorCodeEnum.REPEAT_ACCOUNT.errorCode);
                    response.setFailReason(ErrorCodeEnum.REPEAT_ACCOUNT.errorDesc);
                }
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping(value = "/modifyEmployee", produces = {"application/json;charset=UTF-8"})
    public Response modifyEmployeeUser(@RequestBody EmployeeUserDTO employeeUserDTO){
        Response response = new Response();
        try{
            if(employeeUserDTO != null && employeeUserDTO.getEmployeeId() != null){

                int i = userService.modifyEmployeeUser(employeeUserDTO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else if(i == -2){
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.THIS_USER_CANNOT_MODIFY.errorCode);
                    response.setFailCode(ErrorCodeEnum.THIS_USER_CANNOT_MODIFY.errorDesc);
                }else  {
                    response.setResult(1);
                    response.setFailCode(ErrorCodeEnum.ADMIN_OR_NOT_EXIST_USER.errorCode);
                    response.setFailReason(ErrorCodeEnum.ADMIN_OR_NOT_EXIST_USER.errorDesc);
                }
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 重置密码或修改密码
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/modifyEmployeePwd",produces = {"application/json;charset=UTF-8"})
    public Response modifyUserPwd(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("employeeId"))){
                UserPO userPO = new UserPO();
                userPO.setUserId(Integer.valueOf(requestMap.get("employeeId")));
                if(StringUtil.isValidString(requestMap.get("employeePwd"))){
                    userPO.setUserPwd(requestMap.get("employeePwd"));
                }
                userPO.setModifiedBy(requestMap.get("modifiedBy"));

                int i = userService.modifyEmployeePwd(userPO);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else if(i == -2){
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.THIS_USER_CANNOT_MODIFY.errorDesc);
                    response.setFailCode(ErrorCodeEnum.THIS_USER_CANNOT_MODIFY.errorCode);
                }else{
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.ADMIN_OR_NOT_EXIST_USER.errorDesc);
                    response.setFailCode(ErrorCodeEnum.ADMIN_OR_NOT_EXIST_USER.errorCode);
                }
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除员工
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/deleteEmployee",produces = {"application/json;charset=UTF-8"})
    public Response deleteEmployeeUser(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("employeeId"))){
                UserPO userPO = new UserPO();
                userPO.setUserId(Integer.valueOf(requestMap.get("employeeId")));
                userPO.setModifiedBy(requestMap.get("modifiedBy"));
                String modifiedAccount = requestMap.get("modifiedAccount");
                int i = userService.deleteEmployeeUser(userPO, modifiedAccount);

                if(i > 0){
                    response.setResult(1);
                    response.setModel(i);
                }else if(i == -2){
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.THIS_USER_CANNOT_MODIFY.errorDesc);
                    response.setFailCode(ErrorCodeEnum.THIS_USER_CANNOT_MODIFY.errorCode);
                }else{
                    response.setResult(1);
                    response.setFailReason(ErrorCodeEnum.ADMIN_OR_NOT_EXIST_USER.errorDesc);
                    response.setFailCode(ErrorCodeEnum.ADMIN_OR_NOT_EXIST_USER.errorCode);
                }

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取企业员工详情
     * @return
     */
    @PostMapping(value = "/queryEmployeeDetail", produces = {"application/json;charset=UTF-8"})
    public Response getEmployeeUserDetail(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("employeeId"))){
                EmployeeUserDTO employeeUserDTO =  userService.getEmployeeUserDetail(requestMap);
                response.setResult(1);
                response.setModel(employeeUserDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 是否存在该用户名
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/examineUserAccount", produces = {"application/json;charset=UTF-8"})
    public Response examineUserAccount(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            if(requestMap != null && StringUtil.isValidString(requestMap.get("adminAccount"))){
                int i = userService.examineUserAccount(requestMap.get("adminAccount"));

                response.setResult(1);
                response.setModel(i);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 用户登陆
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public Response login(@RequestBody UserDTO userDTO){
        Response response = new Response();
        try {
            UserDTO userDB = userService.login(userDTO);

            response.setResult(1);
            response.setModel(userDB);

        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(e.getMessage());
            response.setFailReason(ErrorCodeEnum.getDescByValue(e.getMessage()));
        }
        return response;
    }

    /**
     * 通过登陆名查询菜单
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryMenuListByLoginName", produces = {"application/json;charset=UTF-8"})
    public Response queryMenuListByLoginName(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            List<MenuDTO> menuDTOS = userService.queryMenuListByLoginName(requestMap);

            response.setResult(1);
            response.setModel(menuDTOS);
        }catch (Exception e){
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
