package com.mgs.dis.service.impl;

import com.mgs.common.Response;
import com.mgs.dis.domain.DisAgentPO;
import com.mgs.dis.domain.DisConfigPO;
import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisConfigDTO;
import com.mgs.dis.mapper.DisConfigMapper;
import com.mgs.dis.service.DisConfigService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:43
 * @Description:
 */
@Slf4j
@Service
public class DisConfigServiceImpl implements DisConfigService {

    @Autowired
    private DisConfigMapper disConfigMapper;

    public List<DisConfigDTO> queryConfigList(String channelCode) {
        try {
            DisConfigPO disConfigPO = new DisConfigPO();
            disConfigPO.setChannelCode(channelCode);
            List<DisConfigPO> disConfigPOList = disConfigMapper.select(disConfigPO);

            //数据转换
            List<DisConfigDTO> disConfigDTOList = BeanUtil.transformList(disConfigPOList,DisConfigDTO.class);

            return disConfigDTOList;
        }catch (Exception e) {
            log.error("queryConfig-service error!",e);
        }
        return null;
    }
}
