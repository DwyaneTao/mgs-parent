package com.mgs.ebk.mapper;

import com.mgs.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EbkUserMapper {

    UserDTO queryEbkLoginUser(@Param("userAccount") String userAccount);
}
