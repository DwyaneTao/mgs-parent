package com.mgs.dis.service.impl;

import com.mgs.common.Response;
import com.mgs.dis.domain.DisAgentPO;
import com.mgs.dis.dto.DisAgentDTO;
import com.mgs.dis.dto.DisBaseQueryDTO;
import com.mgs.dis.mapper.DisAgentMapper;
import com.mgs.dis.service.DisAgentService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.util.BeanUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:42
 * @Description:
 */
@Slf4j
@Service
public class DisAgentServiceImpl implements DisAgentService {

    @Autowired
    private DisAgentMapper disAgentMapper;

    public List<DisAgentDTO> queryAgentList() {
        try {
            List<DisAgentPO> disAgentPOList = disAgentMapper.selectAll();

            //数据转换
            List<DisAgentDTO> disAgentDTOList = BeanUtil.transformList(disAgentPOList,DisAgentDTO.class);

            return disAgentDTOList;
        }catch (Exception e) {
            log.error("queryAgent-service error!",e);
        }
        return null;
    }

    public List<DisAgentDTO> queryAgentListByParam(DisBaseQueryDTO disBaseQueryDTO) {
        try {
            Example example = new Example(DisAgentPO.class);
            Example.Criteria criteria = example.createCriteria();
            if (null != disBaseQueryDTO) {
                if (StringUtil.isValidString(disBaseQueryDTO.getCompanyCode())) {
                    criteria.andEqualTo("companyCode",disBaseQueryDTO.getCompanyCode());
                }
                if (StringUtil.isValidString(disBaseQueryDTO.getChannelCode())) {
                    criteria.andEqualTo("channelCode",disBaseQueryDTO.getChannelCode());
                }
            }
            List<DisAgentPO> disAgentPOList = disAgentMapper.selectByExample(example);

            //数据转换
            List<DisAgentDTO> disAgentDTOList = BeanUtil.transformList(disAgentPOList,DisAgentDTO.class);

            return disAgentDTOList;
        }catch (Exception e) {
            log.error("queryAgent-service error!",e);
        }
        return null;
    }
}
