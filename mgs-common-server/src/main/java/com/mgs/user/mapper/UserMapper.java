package com.mgs.user.mapper;

import com.mgs.common.MyMapper;

import com.mgs.user.domain.UserRolePO;
import com.mgs.user.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.mgs.organization.domain.UserPO;
import com.mgs.user.dto.PurchaseManagerDTO;
import com.mgs.user.dto.SaleManagerDTO;


import java.util.List;
import java.util.Map;

public interface UserMapper extends MyMapper<UserPO> {

    /**
     * 查询是否符合增加管理员的要求
     * @param userPO
     * @return
     */
    List<UserPO> queryExistAdminUser(UserPO userPO);

    /**
     * 查询采购经理List
     * @return
     */
    List<PurchaseManagerDTO> queryPurchaseManagerList(Map<String, String> requestMap);

    /**
     * 获取销售经理列表
     * @return
     */
    List<SaleManagerDTO> getSaleManagerList(Map<String, String> requestMap);

    /**
     * 获取域名和角色
     * @param requestMap
     * @return
     */
    RoleAndDomainDTO getRoleAndDomain(Map<String, String> requestMap);

    /**
     * 获取企业员工列表
     * @param requestMap
     * @return
     */
    List<EmployeeUserDTO> getEmployeeList(Map<String, String> requestMap);

    /**
     * 获取角色列表
     * @param employeeUserDTOS
     * @return
     */
    List<RoleDTO> getRoleList(List<EmployeeUserDTO> employeeUserDTOS);

    /**
     * 通过用户id获取角色信息
     * @param userPOs
     * @return
     */
    List<RoleDTO> getRoleListByUserId(List<UserPO> userPOs);

    /**
     * 删除用户-角色
     * @param userRolePOS
     * @return
     */
    void deleteUserRole(List<UserRolePO> userRolePOS);

    /**
     * 查询不是超级管理员的用户
     * @param userId
     * @return
     */
    UserPO getUserWithoutAdmin(@Param("userId") Integer userId);

    /**
     * 获取企业员工详情
     * @param requestMap
     * @return
     */
    EmployeeUserDTO queryEmployeeDetail(Map<String, String> requestMap);

    /**
     * 获取当前用户信息
     * @param requestMap
     * @return
     */
    EmployeeUserDTO getCurrentUserDetail(Map<String, String> requestMap);

    /**
     * 通过登录名查询目录
     * @param requestMap
     * @return
     */
    List<MenuDTO> queryMenuListByLoginName(Map<String, String> requestMap);

    /**
     * 查询登录的信息
     * @param userAccount
     * @return
     */
    UserDTO queryLoginUser(@Param(value = "userAccount") String userAccount);

}
