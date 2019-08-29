package com.mgs.ebk.service.impl;

import com.mgs.ebk.mapper.EbkUserMapper;
import com.mgs.ebk.service.EbkUserService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.user.dto.EmployeeUserDTO;
import com.mgs.user.dto.FirstMenuDTO;
import com.mgs.user.dto.MenuDTO;
import com.mgs.user.dto.MenuLevelDTO;
import com.mgs.user.dto.RoleDTO;
import com.mgs.user.dto.UserDTO;
import com.mgs.user.mapper.UserMapper;
import com.mgs.util.BeanUtil;
import com.mgs.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EbkUserServiceImpl implements EbkUserService {

    @Autowired
    private EbkUserMapper ebkUserMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO login(UserDTO userDTO) throws Exception{

        UserDTO user = ebkUserMapper.queryEbkLoginUser(userDTO.getLoginAccount());

        if(user == null){
            throw new Exception(ErrorCodeEnum.NOT_EXIST_USER.errorCode);
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
