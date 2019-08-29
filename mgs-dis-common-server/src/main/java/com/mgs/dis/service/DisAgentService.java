package com.mgs.dis.service;

import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisBaseQueryDTO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:42
 * @Description:
 */
public interface DisAgentService {

    List<DisAgentDTO> queryAgentList();

    List<DisAgentDTO> queryAgentListByParam(DisBaseQueryDTO disBaseQueryDTO);
}
