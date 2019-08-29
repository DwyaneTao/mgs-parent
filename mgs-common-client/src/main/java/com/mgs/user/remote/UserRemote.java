package com.mgs.user.remote;

import com.mgs.common.Response;
import com.mgs.user.dto.EmployeeUserDTO;
import com.mgs.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient(value = "mgs-common-server")
public interface UserRemote {

    @PostMapping(value = "/user/resetAdminPwd",produces = { "application/json;charset=UTF-8" })
    Response resetAdminPwd(@RequestBody Map<String,String> requestMap);

    @PostMapping(value = "/user/queryPurchaseManagerList", produces = {"application/json;charset=UTF-8"})
    Response getPurchaseManagerList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/querySaleManagerList", produces = {"application/json;charset=UTF-8"})
    Response getSaleManagerList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/queryRoleAndDomain", produces = {"application/json;charset=UTF-8"})
    Response getRoleAndDomain(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/queryEmployeeList", produces = {"application/json;charset=UTF-8"})
    Response getEmployeeList(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/addEmployee", produces = {"application/json;charset=UTF-8"})
    Response addEmployeeUser(@RequestBody EmployeeUserDTO employeeUserDTO);

    @PostMapping(value = "/user/modifyEmployee", produces = {"application/json;charset=UTF-8"})
    Response modifyEmployeeUser(@RequestBody EmployeeUserDTO employeeUserDTO);

    @PostMapping(value = "/user/modifyEmployeePwd",produces = {"application/json;charset=UTF-8"})
    Response modifyUserPwd(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/deleteEmployee",produces = {"application/json;charset=UTF-8"})
    Response deleteEmployeeUser(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/queryEmployeeDetail", produces = {"application/json;charset=UTF-8"})
    Response getEmployeeUserDetail(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/examineUserAccount", produces = {"application/json;charset=UTF-8"})
    Response examineUserAccount(@RequestBody Map<String, String> requestMap);

    @PostMapping(value = "/user/login", produces = {"application/json;charset=UTF-8"})
    Response login(@RequestBody UserDTO userDTO);

    @PostMapping(value = "/user/queryMenuListByLoginName", produces = {"application/json;charset=UTF-8"})
    Response queryMenuListByLoginName(@RequestBody Map<String, String> requestMap);
}
