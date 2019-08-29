package com.mgs.controller.user;

import com.mgs.common.BaseController;
import com.mgs.common.BaseRequest;
import com.mgs.common.Response;
import com.mgs.config.Jwt;
import com.mgs.config.JwtUtil;
import com.mgs.config.SecurityCache;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.user.dto.EmployeeUserDTO;
import com.mgs.user.dto.FirstMenuDTO;
import com.mgs.user.dto.UserDTO;
import com.mgs.ebk.user.EbkUserRemote;
import com.mgs.user.remote.UserRemote;
import com.mgs.util.BeanUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/ebk/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserRemote userRemote;

    @Autowired
    private EbkUserRemote ebkUserRemote;

    /**
     * 重置总管理员密码
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/resetAdminPwd",produces = { "application/json;charset=UTF-8" })
    public Response resetAdminPwd(@RequestBody Map<String,String> requestMap){
        Response response = new Response();
        try {
            if (requestMap != null && StringUtil.isValidString(requestMap.get("adminAccount"))){
                requestMap.put("modifiedBy", super.getUserName());
                response = userRemote.resetAdminPwd(requestMap);
            }else{
                response.setResult(0);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
            }
        }catch (Exception e){
            log.error("resetAdminPwd-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取采购经理列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryPurchaseManagerList", produces = {"application/json;charset=UTF-8"})
    public Response getPurchaseManagerList(@RequestBody Map<String,String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("orgCode", super.getCompanyCode());
            response =  userRemote.getPurchaseManagerList(requestMap);
        }catch (Exception e){
            log.error("getPurchaseManagerList-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 退出
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/logout", produces = {"application/json;charset=UTF-8"})
    public Response logout(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{

        }catch (Exception e){
            log.error("examineUserAccount-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取销售经理列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/querySaleManagerList", produces = {"application/json;charset=UTF-8"})
    public Response getSaleManagerList(@RequestBody Map<String,String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("orgCode", super.getCompanyCode());
            response = userRemote.getSaleManagerList(requestMap);
        }catch (Exception e){
            log.error("getSaleManagerList-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取域名和角色
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryRoleAndDomain", produces = {"application/json;charset=UTF-8"})
    public Response getRoleAndDomain(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try {
            if(requestMap != null && StringUtil.isValidString(requestMap.get("endType"))){
                requestMap.put("orgCode", super.getCompanyCode());
                response = userRemote.getRoleAndDomain(requestMap);
            }else {
                response.setResult(0);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
            }
        }catch (Exception e){
            log.error("getRoleAndDomain-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 获取企业用户列表
     * @param requestMap
     * @return
     */
    @PostMapping(value = "/queryEmployeeList", produces = {"application/json;charset=UTF-8"})
    public Response getEmployeeList(@RequestBody Map<String, String> requestMap){
        Response response = new Response();
        try{
            requestMap.put("orgCode", super.getCompanyCode());
            //登陆的人
            requestMap.put("user", super.getLoginName());
            if(!StringUtil.isValidString(requestMap.get("pageSize"))){
                requestMap.put("pageSize", String.valueOf(new BaseRequest().getPageSize()));
            }
            if(!StringUtil.isValidString(requestMap.get("currentPage"))){
                requestMap.put("currentPage", String.valueOf(new BaseRequest().getCurrentPage()));
            }


            response = userRemote.getEmployeeList(requestMap);

        }catch (Exception e){
            log.error("getEmployeeList-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 增加企业员工
     * @param employeeUserDTO
     * @return
     */
    @PostMapping(value = "/addEmployee", produces = {"application/json;charset=UTF-8"})
    public Response addEmployeeUser(@RequestBody EmployeeUserDTO employeeUserDTO){
        Response response = new Response();
        try{
            if(employeeUserDTO != null && StringUtil.isValidString(employeeUserDTO.getEmployeeAccount())
                    && StringUtil.isValidString(employeeUserDTO.getEmployeeName())
                    && StringUtil.isValidString(employeeUserDTO.getEmployeeTel())
                    && CollectionUtils.isNotEmpty(employeeUserDTO.getEmployeeRoleList())){
                employeeUserDTO.setOrgCode(super.getCompanyCode());
                employeeUserDTO.setCreatedBy(super.getUserName());
                response = userRemote.addEmployeeUser(employeeUserDTO);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            log.error("addEmployeeUser-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改企业员工
     * @param employeeUserDTO
     * @return
     */
    @PostMapping(value = "/modifyEmployee", produces = {"application/json;charset=UTF-8"})
    public Response modifyEmployeeUser(@RequestBody EmployeeUserDTO employeeUserDTO){
        Response response = new Response();
        try{
            if(employeeUserDTO != null && employeeUserDTO.getEmployeeId() != null){

                employeeUserDTO.setModifiedBy(super.getUserName());
                employeeUserDTO.setModifiedAccount(super.getLoginName());
                response = userRemote.modifyEmployeeUser(employeeUserDTO);

            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            log.error("modifyEmployeeUser-controller error!",e);
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
                requestMap.put("modifiedBy", super.getUserName());
                response = userRemote.modifyUserPwd(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            log.error("modifyUserPwd-controller error!",e);
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
                requestMap.put("modifiedBy", super.getUserName());
                requestMap.put("modifiedAccount", super.getLoginName());
                response = userRemote.deleteEmployeeUser(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            log.error("deleteEmployeeUser-controller error!",e);
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
                requestMap.put("user", super.getLoginName());
               response =  userRemote.getEmployeeUserDetail(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            log.error("getEmployeeUserDetail-controller error!",e);
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
            if(requestMap != null && StringUtil.isValidString("adminAccount")){
                response = userRemote.examineUserAccount(requestMap);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e){
            log.error("examineUserAccount-controller error!",e);
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
    public Response login(@RequestBody UserDTO userDTO) {
        Response response = new Response();
        try {
            response = ebkUserRemote.login(userDTO);

            if(null != response && 1 == response.getResult()){
                //登录成功，缓存登录状态
                SecurityCache.putLoginStatusToCache(userDTO.getLoginAccount());
                //生成token给前端
                Jwt jwt = new Jwt();
                jwt.setUserAccount(userDTO.getLoginAccount());
                jwt.setUserName(((LinkedHashMap<String,String>) response.getModel()).get("loginName"));
                jwt.setExpiredMillisecond(JwtUtil.JWT_TTL);
                jwt.setOrgCode(((LinkedHashMap<String,String>) response.getModel()).get("companyCode"));
                if(StringUtil.isValidString(((LinkedHashMap<String,String>) response.getModel()).get("hotelInfoPermission"))){
                    jwt.setHotelInfoPermission(Integer.valueOf(((LinkedHashMap<String,String>) response.getModel()).get("hotelInfoPermission")));
                }


                jwt.setDomainName(((LinkedHashMap<String, String>) response.getModel()).get("companyDomain"));

                String token = JwtUtil.createJWT(jwt);
                //初始化用户权限缓存
                UserDTO userDB = BeanUtil.transformBean(response.getModel(),UserDTO.class);
                userDB.setToken(token);
                if(!CollectionUtils.isEmpty(userDB.getMenus())){
                    Set<String> menuUrlDevList = new HashSet<String>();
                    for (FirstMenuDTO menuDTO : userDB.getMenus()) {
                        menuUrlDevList.add(menuDTO.getBackEndUrl());
                    }
                    SecurityCache.putMenuListToCache(userDTO.getLoginAccount(),menuUrlDevList);
                }
                response.setModel(userDB);
            }

            //重新登录时，用户可能修改了菜单权限，故重新加载系统一二级菜单缓存
            initMenu(userRemote);

        }catch (Exception e){
            log.error("login-controller error!",e);
            response.setResult(0);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }


        return response;
    }

    //查询所有菜单
    public void initMenu(UserRemote userRemote){
        Response response = userRemote.queryMenuListByLoginName(new HashMap<>());
        if(null != response && 1 == response.getResult() && null != response.getModel()){
            List<FirstMenuDTO> menuDTOList = BeanUtil.transformList(response.getModel(),FirstMenuDTO.class);

            if(!CollectionUtils.isEmpty(menuDTOList)){
                Set<String> oneTwoLevelMenuUrlList = new HashSet<String>();
                menuDTOList.forEach(menuDTO -> {
                    if(!oneTwoLevelMenuUrlList.contains(menuDTO.getBackEndUrl())){
                        oneTwoLevelMenuUrlList.add(menuDTO.getBackEndUrl());
                    }
                });

                if(!CollectionUtils.isEmpty(oneTwoLevelMenuUrlList)){
                    SecurityCache.putMenuListToCache(SecurityCache.ONE_TWO_LEVEL_NENU_URL,oneTwoLevelMenuUrlList);
                }
            }
        }
    }


}
