package com.mgs.user.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.user.domain.RolePO;
import com.mgs.user.domain.UserRolePO;
import com.mgs.user.dto.*;
import com.mgs.user.mapper.RoleMapper;
import com.mgs.organization.domain.UserPO;
import com.mgs.user.dto.PurchaseManagerDTO;
import com.mgs.user.dto.SaleManagerDTO;
import com.mgs.user.mapper.UserMapper;
import com.mgs.user.mapper.UserRoleMapper;
import com.mgs.user.service.UserService;
import com.mgs.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String PWD = "666666";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 增加供应商、客户、运营商所需要的调用的增加超级管理员接口
     *  以下为所需要封装为AdminPO里面的参数
     *  姓名、账号、手机号、企业编号、创建人
     *  type指哪个端的用户 type类型见com.mgs.user.enums
     * @return  返回为-1，则该企业编码下有超级管理员或有该账号名
     */
    @Override
    @Transactional
    public int addAdminUser(UserPO userPO, int type) {

        UserPO queryUserPO = new UserPO();
        queryUserPO.setOrgCode(userPO.getOrgCode());
        queryUserPO.setIsSuperAdmin(1);
        queryUserPO.setActive(1);
        queryUserPO.setUserAccount(userPO.getUserAccount());
        List<UserPO> existPO = userMapper.queryExistAdminUser(queryUserPO);

        if(CollectionUtils.isNotEmpty(existPO)){
            return -1;
        }

        userPO.setIsSuperAdmin(1);
        userPO.setActive(1);
        userPO.setAvailableStatus(1);
        userPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));

        try {
            String salt = NumberUtil.generateNumber(4);
            userPO.setUserPwd(Md5Util.md5Encode(PWD + salt));
            userPO.setSalt(salt);
        } catch (Exception e) {
            log.error("新增用户密码加密失败");
        }
        userMapper.insert(userPO);

        // 增加总管理员的时候，把所有的权限都加上
        RolePO rolePO = new RolePO();
        rolePO.setActive(1);
        rolePO.setType(type);
        List<RolePO> rolePOS = roleMapper.select(rolePO);

        if(CollectionUtils.isNotEmpty(rolePOS)) {
            List<UserRolePO> userRolePOs = new ArrayList<UserRolePO>();

            for(RolePO role: rolePOS) {
                UserRolePO userRolePO = new UserRolePO();
                userRolePO.setRoleId(role.getRoleId());
                userRolePO.setUserId(userPO.getUserId());
                userRolePO.setCreatedBy(userPO.getCreatedBy());
                userRolePO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));

                userRolePOs.add(userRolePO);
            }

            userRoleMapper.insertList(userRolePOs);
        }
        return 1;
    }

    /**
     * 修改超级管理员信息
     * 必传userId ，修改人，以及修改的信息
     * @param userPO
     * @return 返回 == 1，则不为超级管理员,返回 > 0，则操作成功
     */
    @Override
    public int modifyAdminUser(UserPO userPO) {

        UserPO existUser = new UserPO();
        existUser.setUserId(userPO.getUserId());
        existUser.setActive(1);
        existUser.setIsSuperAdmin(1);
        existUser = userMapper.selectOne(existUser);

        if(existUser == null){
            return -1;
        }

        if(userPO.getAvailableStatus() != null){
            existUser.setAvailableStatus(userPO.getAvailableStatus());
        }

        if(StringUtil.isValidString(userPO.getUserName())){
            existUser.setUserName(userPO.getUserName());
        }

        if(StringUtil.isValidString(userPO.getUserTel())){
            existUser.setUserTel(userPO.getUserTel());
        }
        if(StringUtil.isValidString(userPO.getModifiedBy())){
            existUser.setModifiedBy(userPO.getModifiedBy());
        }

        existUser.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return userMapper.updateByPrimaryKeySelective(existUser);
    }

    /**
     * 重置超级管理员密码
     * 必传userId ，修改人
     * @param userPO
     * @return 返回 == 1，则不为超级管理员,返回 > 0，则操作成功
     */
    @Override
    public int modifyAdminUserPwd(UserPO userPO) {

        UserPO existUser = new UserPO();
        existUser.setUserAccount(userPO.getUserAccount());
        existUser.setActive(1);
        existUser.setIsSuperAdmin(1);
        existUser = userMapper.selectOne(existUser);


        if(existUser == null){
            return -1;
        }

        try{
            existUser.setUserPwd(Md5Util.md5Encode(existUser.getSalt() + PWD));
        }catch (Exception e) {
            log.error("新增用户密码加密失败");
        }
        existUser.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        existUser.setModifiedBy(userPO.getModifiedBy());

        return userMapper.updateByPrimaryKeySelective(existUser);
    }

    /**
     * 获取采购经理列表
     * @return
     */
    @Override
    public List<PurchaseManagerDTO> getPurchaseManagerList(Map<String, String> requestMap) {
       return userMapper.queryPurchaseManagerList(requestMap);
    }

    /**
     * 获取销售经理列表
     * @return
     */
    @Override
    public List<SaleManagerDTO> getSaleManagerList(Map<String, String> requestMap) {
        return userMapper.getSaleManagerList(requestMap);
    }

    /**
     * 获取角色和域名
     * @param requestMap
     * @return
     */
    @Override
    public RoleAndDomainDTO getRoleAndDomain(Map<String, String> requestMap) {
        return userMapper.getRoleAndDomain(requestMap);
    }

    /**
     * 获取企业员工列表
     * @param requestMap
     * @return
     */
    @Override
    public PageInfo<EmployeeUserDTO> getEmployeeList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<EmployeeUserDTO> employeeUserDTOS = userMapper.getEmployeeList(requestMap);
        if(CollectionUtils.isNotEmpty(employeeUserDTOS)) {
            List<RoleDTO> roleList = userMapper.getRoleList(employeeUserDTOS);
            assemblyEmployee(employeeUserDTOS, roleList);
        }
        PageInfo<EmployeeUserDTO> pageInfo = new PageInfo<EmployeeUserDTO>(employeeUserDTOS);
        return pageInfo;
    }

    /**
     * 增加企业员工
     * @param employeeUserDTO
     * @return -1,已存在该账号；-2，不能增加总管理员身份
     */
    @Override
    @Transactional
    public int addEmployeeUser(EmployeeUserDTO employeeUserDTO) {

        UserPO existPO = new UserPO();
        existPO.setUserAccount(employeeUserDTO.getEmployeeAccount());
        existPO.setActive(1);
        existPO = userMapper.selectOne(existPO);

        if(existPO != null){
            return -1;
        }

        UserPO userPO = new UserPO();
        userPO.setUserAccount(employeeUserDTO.getEmployeeAccount());
        userPO.setUserTel(employeeUserDTO.getEmployeeTel());
        userPO.setUserName(employeeUserDTO.getEmployeeName());
        userPO.setOrgCode(employeeUserDTO.getOrgCode());
        userPO.setCreatedBy(employeeUserDTO.getCreatedBy());
        userPO.setActive(1);
        userPO.setAvailableStatus(1);
        userPO.setCreatedDt(DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));

        try{
            String salt = NumberUtil.generateNumber(4);
            userPO.setUserPwd(Md5Util.md5Encode(PWD + salt));
            userPO.setSalt(salt);
        }catch (Exception e){
            log.error("新增用户密码加密失败");
        }
         userMapper.insert(userPO);

        if(CollectionUtils.isNotEmpty(employeeUserDTO.getEmployeeRoleList())){
            List<UserRolePO> userRolePOList = new ArrayList<UserRolePO>();
            for(RoleDTO roleDTO: employeeUserDTO.getEmployeeRoleList()){
                UserRolePO userRolePO = new UserRolePO();
                userRolePO.setUserId(userPO.getUserId());
                userRolePO.setRoleId(roleDTO.getEmployeeRoleId());
                userRolePO.setCreatedBy(employeeUserDTO.getCreatedBy());
                userRolePO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                userRolePOList.add(userRolePO);
            }

            userRoleMapper.insertList(userRolePOList);
        }
        return 1;
    }

    /**
     * 修改员工信息
     * （只包括修改用户基本信息，启用性，以及权限）（不包括密码修改）
     * @param employeeUserDTO
     * @return
     */
    @Override
    @Transactional
    public int modifyEmployeeUser(EmployeeUserDTO employeeUserDTO) {
        //查询非管理员的用户，因为管理员不能被修改
        UserPO existPO = userMapper.getUserWithoutAdmin(employeeUserDTO.getEmployeeId());

        if(existPO == null) {
            return -1;
        }

        /**
         * 若是当前用户的话，则不能修改
         */
        if(existPO.getUserAccount().equals(employeeUserDTO.getModifiedAccount())){
            return -2;
        }

        UserPO userPO = new UserPO();
        userPO.setUserId(employeeUserDTO.getEmployeeId());

        if(StringUtil.isValidString(employeeUserDTO.getEmployeeName())){
            userPO.setUserName(employeeUserDTO.getEmployeeName());
        }

        if(StringUtil.isValidString(employeeUserDTO.getEmployeeTel())){
            userPO.setUserTel(employeeUserDTO.getEmployeeTel());
        }

        if(employeeUserDTO.getAvailableStatus() != null){
            userPO.setAvailableStatus(employeeUserDTO.getAvailableStatus());
        }

        userPO.setModifiedBy(employeeUserDTO.getModifiedBy());
        userPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));

        userMapper.updateByPrimaryKeySelective(userPO);

        if(!CollectionUtils.isEmpty(employeeUserDTO.getEmployeeRoleList())){
            List<UserPO> userPOS = Arrays.asList(new UserPO(employeeUserDTO.getEmployeeId()));
            List<RoleDTO> roleDTOS = userMapper.getRoleListByUserId(userPOS);

            Map<Integer, RoleDTO> roleMap = roleDTOS.stream().collect(Collectors.toMap(RoleDTO::getEmployeeRoleId, Function.identity(), (key1, key2) -> key2));

            List<UserRolePO> addEmployeeUserRolePOS = new ArrayList<UserRolePO>();
            List<UserRolePO> delEmployeeUserRolePOS = new ArrayList<UserRolePO>();

            for(RoleDTO roleDTO: employeeUserDTO.getEmployeeRoleList()){
                if(roleMap.containsKey(roleDTO.getEmployeeRoleId())){
                    roleMap.remove(roleDTO.getEmployeeRoleId());
                }else{
                    UserRolePO userRolePO = new UserRolePO();
                    userRolePO.setUserId(employeeUserDTO.getEmployeeId());
                    userRolePO.setRoleId(roleDTO.getEmployeeRoleId());
                    userRolePO.setCreatedBy(employeeUserDTO.getModifiedBy());
                    userRolePO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    addEmployeeUserRolePOS.add(userRolePO);
                }
            }

            if(!CollectionUtils.isEmpty(addEmployeeUserRolePOS)) {
                userRoleMapper.insertList(addEmployeeUserRolePOS);
            }

            if((roleMap != null) && (roleMap.size() > 0)) {
                for (RoleDTO roleDTO : roleMap.values()) {
                    UserRolePO deleteUserRole = new UserRolePO();
                    deleteUserRole.setId(Integer.valueOf(roleDTO.getUserRoleId()));

                    delEmployeeUserRolePOS.add(deleteUserRole);
                }
                userMapper.deleteUserRole(delEmployeeUserRolePOS);
            }
        }
        return 1;
    }

    /**
     * 重置密码
     * @param userPO
     * @return
     */
    @Override
    public int modifyEmployeePwd(UserPO userPO) {

        UserPO existUser = userMapper.selectByPrimaryKey(userPO.getUserId());

        if(existUser == null){
            return -1;
        }

        String pwd = PWD;
        if(StringUtil.isValidString(userPO.getUserPwd())){
            pwd = userPO.getUserPwd();
        }

        try {
            existUser.setUserPwd(Md5Util.md5Encode(pwd + existUser.getSalt()));
        }catch (Exception e){
            log.error("用户密码加密失败");
        }

        existUser.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));

        return userMapper.updateByPrimaryKeySelective(existUser);
    }

    /**
     * 删除员工
     * @param userPO
     * @return
     */
    @Override
    public int deleteEmployeeUser(UserPO userPO, String modifiedAccount) {
        UserPO existUser = userMapper.getUserWithoutAdmin(userPO.getUserId());

        if(existUser == null){
            return -1;
        }

        if(existUser.getUserAccount().equals(modifiedAccount)){
            return -2;
        }

        userPO.setActive(0);
        userPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return userMapper.updateByPrimaryKeySelective(userPO);
    }

    @Override
    public EmployeeUserDTO getEmployeeUserDetail(Map<String, String> requestMap) {
       EmployeeUserDTO employeeUserDTO = userMapper.queryEmployeeDetail(requestMap);
        if(employeeUserDTO != null) {
            List<RoleDTO> roleList = userMapper.getRoleList(Arrays.asList(employeeUserDTO));
            assemblyEmployee(Arrays.asList(employeeUserDTO), roleList);
        }
        return employeeUserDTO;
    }

    @Override
    public EmployeeUserDTO getCurrentUserDetail(Map<String, String> requestMap) {
        EmployeeUserDTO employeeUserDTO = userMapper.getCurrentUserDetail(requestMap);
        if(employeeUserDTO != null) {
            List<RoleDTO> roleList = userMapper.getRoleList(Arrays.asList(employeeUserDTO));
            assemblyEmployee(Arrays.asList(employeeUserDTO), roleList);
        }
        return employeeUserDTO;
    }

    /**
     * 检查是否存在该用户账号
     * @param userAccount
     * @return 1为存在，-1为不存在
     */
    @Override
    public int examineUserAccount(String userAccount) {
        UserPO userPO = new UserPO();
        userPO.setUserAccount(userAccount);
        userPO.setAvailableStatus(1);
        userPO = userMapper.selectOne(userPO);

        if(userPO == null){
            return 0;
        }
        return 1;
    }

    /**
     * 通过登录名查询目录
     * @param requestMap
     * @return
     */
    @Override
    public List<MenuDTO> queryMenuListByLoginName(Map<String, String> requestMap) {
        return userMapper.queryMenuListByLoginName(requestMap);
    }

    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO login(UserDTO userDTO) throws Exception{

        UserDTO user = userMapper.queryLoginUser(userDTO.getLoginAccount());

        if(user == null){
            throw new Exception(ErrorCodeEnum.NOT_EXIST_USER.errorCode);
        }
        if(user.getAvailableStatus().equals(0)){
            throw new Exception(ErrorCodeEnum.USER_IS_DISABLED.errorCode);
        }
        String pwd = null;
        try{
            pwd = Md5Util.md5Encode(userDTO.getLoginPwd() + user.getSalt());
        }catch (Exception e){
            log.error("md5加密密码失败");
        }

        if(!pwd.equals(user.getLoginPwd())){
            throw new Exception(ErrorCodeEnum.PASSWORD_ERROR.errorCode);
        }

        //角色
        EmployeeUserDTO employeeUserDTO = new EmployeeUserDTO();
        employeeUserDTO.setEmployeeId(user.getLoginId());
        List<RoleDTO> roleList = userMapper.getRoleList(Arrays.asList(employeeUserDTO));
        assemblyEmployee(Arrays.asList(employeeUserDTO), roleList);

        //菜单
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("userAccount", userDTO.getLoginAccount());
        List<MenuDTO> menuDTOS = userMapper.queryMenuListByLoginName(requestMap);
        List<FirstMenuDTO> firstMenuDTOS = combinationMenu(menuDTOS);
        userDTO = user;
        userDTO.setRoleList(employeeUserDTO.getEmployeeRoleList());
        userDTO.setLoginPwd(null);
        userDTO.setSalt(null);
        userDTO.setMenus(firstMenuDTOS);
        return userDTO;
    }


    /**
     * 组合用户以及对应的角色
     * @param employeeUserDTOS
     * @param roleDTOS
     */
    private void assemblyEmployee(List<EmployeeUserDTO> employeeUserDTOS, List<RoleDTO> roleDTOS){
        Map<Integer , EmployeeUserDTO> employeeUserDTOMap = employeeUserDTOS.stream().collect(Collectors.toMap(EmployeeUserDTO::getEmployeeId, Function.identity(), (key1, key2) -> key2));
        for(RoleDTO roleDTO: roleDTOS){
            List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
            EmployeeUserDTO employeeUserDTO = employeeUserDTOMap.get(roleDTO.getEmployeeId());
            if(CollectionUtils.isEmpty(employeeUserDTO.getEmployeeRoleList())){
                roleDTOList  = Arrays.asList(roleDTO);
            }else{
                roleDTOList = new ArrayList<RoleDTO>(employeeUserDTO.getEmployeeRoleList());
                roleDTOList.add(roleDTO);
            }

            if(CollectionUtils.isNotEmpty(roleDTOList)) {
                employeeUserDTO.setEmployeeRoleList(roleDTOList);
            }
        }
    }


    /**
     * 组装目录
     * @return
     */
    private List<FirstMenuDTO> combinationMenu(List<MenuDTO> menuDTOS){
        List<FirstMenuDTO> firstMenuDTOS = new ArrayList<FirstMenuDTO>();

        for(MenuDTO menuDTO : menuDTOS){
            if(menuDTO.getMenuLevel() == 1){
                FirstMenuDTO firstMenuDTO =  BeanUtil.transformBean(menuDTO, FirstMenuDTO.class);
                firstMenuDTO.setPath(menuDTO.getFrontEndUrl() == null? null:menuDTO.getFrontEndUrl());
                firstMenuDTO.setFirstMenu(menuDTO.getMenuName());
                firstMenuDTOS.add(firstMenuDTO);
            }
        }

       Collections.sort(firstMenuDTOS, new Comparator<FirstMenuDTO>() {
           @Override
           public int compare(FirstMenuDTO o1, FirstMenuDTO o2) {
                return o1.getMenuRank() - o2.getMenuRank();
           }
       });


        Map<String, FirstMenuDTO> menuMap = firstMenuDTOS.stream().collect(Collectors.toMap(FirstMenuDTO::getMenuCode, Function.identity(), (key1, key2) -> key2));

        for(MenuDTO menuDTO : menuDTOS){
            if(menuDTO.getMenuLevel() == 2){
                List<MenuLevelDTO> secondMenuDTOs = new ArrayList<MenuLevelDTO>();
                if(!CollectionUtils.isEmpty(menuMap.get(menuDTO.getParentCode()).getSecondMenuList())) {
                    secondMenuDTOs = menuMap.get(menuDTO.getParentCode()).getSecondMenuList();
                }
                MenuLevelDTO menuLevelDTO = BeanUtil.transformBean(menuDTO, MenuLevelDTO.class);
                menuLevelDTO.setPath(menuDTO.getFrontEndUrl() == null?null:menuDTO.getFrontEndUrl());
                menuLevelDTO.setSecondMenu(menuDTO.getMenuName());
                secondMenuDTOs.add(menuLevelDTO);
                menuMap.get(menuDTO.getParentCode()).setSecondMenuList(secondMenuDTOs);
            }
        }


        for(FirstMenuDTO firstMenuDTO: firstMenuDTOS){
            if(CollectionUtils.isNotEmpty(firstMenuDTO.getSecondMenuList())) {
                Collections.sort(firstMenuDTO.getSecondMenuList(), new Comparator<MenuLevelDTO>() {
                    @Override
                    public int compare(MenuLevelDTO o1, MenuLevelDTO o2) {
                        return o1.getMenuRank() - o2.getMenuRank();
                    }
                });
            }
        }

        return firstMenuDTOS;
    }
}
