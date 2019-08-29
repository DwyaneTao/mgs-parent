package com.mgs.ebk.service;

import com.mgs.user.dto.UserDTO;

public interface EbkUserService{

    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    UserDTO login(UserDTO userDTO) throws Exception;
}
