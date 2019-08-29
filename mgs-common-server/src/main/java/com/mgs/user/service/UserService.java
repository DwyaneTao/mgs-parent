package com.mgs.user.service;


import com.github.pagehelper.PageInfo;
import com.mgs.user.dto.*;
import com.mgs.organization.domain.UserPO;

import java.util.List;
import java.util.Map;


public interface UserService {

    /**
     * 增加超级管理员
     * @param userPO
     * @return
     */
   int addAdminUser(UserPO userPO, int type);

    /**
     * 修改超级管理员信息（除密码）
     * @param userPO
     * @return
     */
   int modifyAdminUser(UserPO userPO);

    /**
     * 重置超级管理员密码
     * @param userPO
     * @return
     */
    int modifyAdminUserPwd(UserPO userPO);

    /**
     * 获取采购管理员列表
     * @return
     */
    List<PurchaseManagerDTO> getPurchaseManagerList(Map<String, String> requestMap);

    /**
     * 获取销售经理列表
     * @return
     */
    List<SaleManagerDTO> getSaleManagerList(Map<String, String> requestMap);

    /**
     * 获取角色和域名
     * @param requestMap
     * @return
     */
    RoleAndDomainDTO getRoleAndDomain(Map<String, String> requestMap);

    /**
     * 获取企业员工列表
     * @param requestMap
     * @return
     */
    PageInfo<EmployeeUserDTO> getEmployeeList(Map<String, String> requestMap);

    /**
     * 增加企业员工
     * @param employeeUserDTO
     * @return
     */
    int addEmployeeUser(EmployeeUserDTO employeeUserDTO);

    /**
     * 修改企业员工信息
     * @param employeeUserDTO
     * @return
     */
    int modifyEmployeeUser(EmployeeUserDTO employeeUserDTO);

    /**
     * 重置员工密码
     * @param userPO
     * @return
     */
    int modifyEmployeePwd(UserPO userPO);

    /**
     * 删除员工
     * @param userPO
     * @return
     */
    int deleteEmployeeUser(UserPO userPO, String modifiedAccount);

    /**
     * 获取企业员工详情
     * @return
     */
    EmployeeUserDTO getEmployeeUserDetail(Map<String, String> requestMap);

    /**
     * 个人信息
     * @return
     */
    EmployeeUserDTO getCurrentUserDetail(Map<String, String> requestMap);

    /**
    * 检验是否存在该账号名
    * @param userAccount
    * @return
    */
   int examineUserAccount(String userAccount);

   /**
    * 通过登陆名查询目录
    * @param requestMap
    * @return
    */
    List<MenuDTO> queryMenuListByLoginName(Map<String, String> requestMap);

    /**
     * 登录
     * @param userDTO
     * @return
     */
    UserDTO login(UserDTO userDTO) throws Exception;

}
