package com.mgs.dis.service.impl;

import com.mgs.common.Response;
import com.mgs.dis.domain.DisProductMappingPO;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.mapper.DisProductMappingMapper;
import com.mgs.dis.service.DisMappingService;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.util.BeanUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 11:58
 * @Description: 映射
 */
@Slf4j
@Service
public class DisMappingServiceImpl implements DisMappingService {

    @Autowired
    private DisProductMappingMapper disProductMappingMapper;

    @Override
    public List<DisProductMappingDTO> queryProductMapping(DisMappingQueryDTO disMappingQueryDTO) {
        try {
            //查询映射
            Example example = new Example(DisProductMappingPO.class);
            example.setOrderByClause("hotel_id ");//默认按酒店Id排序
            Example.Criteria criteria = example.createCriteria();
            if (null != disMappingQueryDTO) {
                if (StringUtil.isValidString(disMappingQueryDTO.getDistributor())) {
                    criteria.andEqualTo("distributor",disMappingQueryDTO.getDistributor());
                }

                if (StringUtil.isValidString(disMappingQueryDTO.getCompanyCode())) {
                    criteria.andEqualTo("companyCode",disMappingQueryDTO.getCompanyCode());
                }

                if (StringUtil.isValidString(disMappingQueryDTO.getDisHotelId())) {
                    criteria.andEqualTo("disHotelId",disMappingQueryDTO.getDisHotelId());
                }

                if (StringUtil.isValidString(disMappingQueryDTO.getDisRoomId())) {
                    criteria.andEqualTo("disRoomId",disMappingQueryDTO.getDisRoomId());
                }

                if (StringUtil.isValidString(disMappingQueryDTO.getDisProductId())) {
                    criteria.andEqualTo("disProductId",disMappingQueryDTO.getDisProductId());
                }

                if (null != disMappingQueryDTO.getHotelId()) {
                    criteria.andEqualTo("hotelId",disMappingQueryDTO.getHotelId());
                }

                if (null != disMappingQueryDTO.getRoomId()) {
                    criteria.andEqualTo("roomId",disMappingQueryDTO.getRoomId());
                }

                if (null != disMappingQueryDTO.getProductId()) {
                    criteria.andEqualTo("productId",disMappingQueryDTO.getProductId());
                }
            }
            List<DisProductMappingPO> disProductMappingPOList = disProductMappingMapper.selectByExample(example);

            //数据转换
            List<DisProductMappingDTO> disProductMappingDTOList = BeanUtil.transformList(disProductMappingPOList,DisProductMappingDTO.class);

            return disProductMappingDTOList;
        }catch (Exception e) {
            log.error("queryProductMapping-service error!",e);
        }
        return null;
    }

    @Override
    public Integer batchAddProductMapping(@RequestBody List<DisProductMappingDTO> disProductMappingDTOList){
        Integer returnFlag = 0;
        if (!CollectionUtils.isEmpty(disProductMappingDTOList)) {
            List<DisProductMappingPO> disProductMappingPOList = BeanUtil.transformList(disProductMappingDTOList,DisProductMappingPO.class);
            returnFlag = disProductMappingMapper.insertList(disProductMappingPOList);
        }
        return returnFlag;
    }
}
