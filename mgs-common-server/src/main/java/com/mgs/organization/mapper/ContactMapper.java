package com.mgs.organization.mapper;

import com.mgs.common.MyMapper;
import com.mgs.organization.domain.ContactPO;
import com.mgs.organization.remote.dto.ContactAddDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author py
 * @date 2019/6/22 11:41
 **/
@Mapper
public interface ContactMapper extends MyMapper<ContactPO> {

}
