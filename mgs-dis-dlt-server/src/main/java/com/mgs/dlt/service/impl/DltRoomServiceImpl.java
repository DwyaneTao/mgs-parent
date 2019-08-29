package com.mgs.dlt.service.impl;

import com.alibaba.fastjson.JSON;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.remote.DisMappingRemote;
import com.mgs.dlt.domain.DltMasterRoomPO;
import com.mgs.dlt.mapper.DltMasterRoomMapper;
import com.mgs.dlt.mapper.ProductMapper;
import com.mgs.dlt.request.dto.CreateBasicRoomRequest;
import com.mgs.dlt.request.dto.CreateSaleRoomRequest;
import com.mgs.dlt.response.dto.CreateBasicRoomResponse;
import com.mgs.dlt.response.dto.CreateSaleRoomResponse;
import com.mgs.dlt.response.dto.DltNeedPushSaleRoomDTO;
import com.mgs.dlt.service.DltRoomService;
import com.mgs.dlt.utils.DltInterfaceInvoker;
import com.mgs.keys.ChannelCodeKey;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/8/15 21:55
 * @Description: 代理通房型操作
 */
@Service
@Slf4j
public class DltRoomServiceImpl implements DltRoomService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DltMasterRoomMapper dltMasterRoomMapper;

    @Autowired
    private DisMappingRemote disMappingRemote;

    @Override
    public Integer queryNeedPushSaleRoomCount(String companyCode) {
        if (StringUtil.isValidString(companyCode)) {
            return productMapper.queryNeedPushSaleRoomCount(companyCode);
        }
        return 0;
    }

    @Override
    @Async
    public void pushSaleRoom(String companyCode) {
        synchronized (this) {
            System.out.println("开始执行推送房型，时间："+ DateUtil.getCurrentDateStr(1));
            if (StringUtil.isValidString(companyCode)) {
                //1、查询需要推送的子房型
                Example example = new Example(DltMasterRoomPO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andIsNotNull("companyRoomId");
                List<DltMasterRoomPO> dltMasterRoomPOList = dltMasterRoomMapper.selectByExample(example);
                Map<Integer,DltMasterRoomPO> masterRoomPOMap = null;
                //2、推送子房型
                if (!CollectionUtils.isEmpty(dltMasterRoomPOList)) {
                    masterRoomPOMap = new HashMap<>();
                    CreateBasicRoomRequest createBasicRoomRequest;
                    CreateBasicRoomResponse createBasicRoomResponse;
                    for (DltMasterRoomPO dltMasterRoomPO : dltMasterRoomPOList) {
                        if (null == dltMasterRoomPO.getBasicRoomTypeID()) {
                            createBasicRoomRequest = new CreateBasicRoomRequest();
                            createBasicRoomRequest.setMasterHotelId(dltMasterRoomPO.getMasterHotelID());
                            createBasicRoomRequest.setChildHotelId(dltMasterRoomPO.getHotelID());
                            createBasicRoomRequest.setUpdator(companyCode);
                            String ip = null;
                            try {
                                InetAddress ia = InetAddress.getLocalHost();
                                ip = ia.toString().split("/")[1];
                                Thread.sleep(6000);//代理通新增子物理房型每分钟只能调用10次，每次休眠6s
                            }catch (Exception e) {
                                log.error("获取本机ip失败",e);
                            }
                            createBasicRoomRequest.setOpClientIP(null == ip ? "127.0.0.1" : ip);
                            List<Integer> masterBasicRoomIds = new ArrayList<>();
                            masterBasicRoomIds.add(dltMasterRoomPO.getMasterBasicRoomId());
                            createBasicRoomRequest.setMasterBasicRoomIds(masterBasicRoomIds);
                            createBasicRoomRequest.setCurrency("CNY");
                            log.info("推送子房型param："+ JSON.toJSONString(createBasicRoomRequest));
                            createBasicRoomResponse = DltInterfaceInvoker.invoke(createBasicRoomRequest,companyCode);
                            log.info("推送子房型response："+ JSON.toJSONString(createBasicRoomResponse));
                            if (null != createBasicRoomResponse) {
                                dltMasterRoomPO.setBasicRoomTypeID(createBasicRoomResponse.getBasicRoomIds().get(0));
                                DltMasterRoomPO dltMasterRoomPO1 = new DltMasterRoomPO();
                                dltMasterRoomPO1.setId(dltMasterRoomPO.getId());
                                dltMasterRoomPO1.setBasicRoomTypeID(createBasicRoomResponse.getBasicRoomIds().get(0));
                                dltMasterRoomPO1.setModifiedBy("system");
                                dltMasterRoomPO1.setModifiedDt(DateUtil.getCurrentDateStr(1));
                                dltMasterRoomMapper.updateByPrimaryKeySelective(dltMasterRoomPO1);//更新子物理房型到代理通房型表
                            }
                        }
                        //将代理通房型信息存放在map中，方便后面推送售卖房型
                        masterRoomPOMap.put(dltMasterRoomPO.getCompanyRoomId(),dltMasterRoomPO);
                    }
                }
                //3、查询需要推送的产品(售卖房型)，推送售卖房型并新增产品映射
                List<DltNeedPushSaleRoomDTO> dltNeedPushSaleRoomDTOList = productMapper.queryNeedPushSaleRoom(companyCode);
                if (!CollectionUtils.isEmpty(dltNeedPushSaleRoomDTOList) && null != masterRoomPOMap) {
                    CreateSaleRoomRequest createSaleRoomRequest;
                    CreateSaleRoomResponse createSaleRoomResponse;
                    List<DisProductMappingDTO> disProductMappingDTOList = new ArrayList<>();
                    for (DltNeedPushSaleRoomDTO dltNeedPushSaleRoomDTO : dltNeedPushSaleRoomDTOList) {
                        if (null != masterRoomPOMap.get(dltNeedPushSaleRoomDTO.getRoomId())
                                && null != masterRoomPOMap.get(dltNeedPushSaleRoomDTO.getRoomId()).getBasicRoomTypeID()) {
                            createSaleRoomRequest = new CreateSaleRoomRequest();
                            createSaleRoomRequest.setBasicRoomTypeId(masterRoomPOMap.get(dltNeedPushSaleRoomDTO.getRoomId()).getBasicRoomTypeID());//物理房型
                            createSaleRoomRequest.setHotelId(masterRoomPOMap.get(dltNeedPushSaleRoomDTO.getRoomId()).getHotelID());//酒店
                            createSaleRoomRequest.setPurchaseSourceID(0);//采购来源Id
                            createSaleRoomRequest.setRelationRoomId(0);//关联Id
                            createSaleRoomRequest.setRoomTypeId(0);
                            createSaleRoomRequest.setPriceType(2);
                            List<Integer> applicabilityList = new ArrayList<>();
                            applicabilityList.add(11);
                            createSaleRoomRequest.setApplicabilityList(applicabilityList);//国内酒店使用此节点，可限制入住人群，例如只允许中国人入住。不限制就固定传"11",与属性配合使用
                            List<Integer> rateCodePropertyValueIDList = new ArrayList<>();
                            rateCodePropertyValueIDList.add(5);//默认双人入住
                            if (null != dltNeedPushSaleRoomDTO.getBreakfastQty()) {
                                if (dltNeedPushSaleRoomDTO.getBreakfastQty().equals(0)) {
                                    rateCodePropertyValueIDList.add(498160);
                                }else if (dltNeedPushSaleRoomDTO.getBreakfastQty().equals(1)) {
                                    rateCodePropertyValueIDList.add(7);
                                }else {
                                    rateCodePropertyValueIDList.add(640292);//大于2份早，默认双早，因为默认双人入住，早餐数不能大于入住人数
                                }
                            }
                            createSaleRoomRequest.setRateCodePropertyValueIDList(rateCodePropertyValueIDList);
                            //推送售卖房型到代理通
                            log.info("推送售卖房型param："+ JSON.toJSONString(createSaleRoomRequest));
                            createSaleRoomResponse = DltInterfaceInvoker.invoke(createSaleRoomRequest,companyCode);
                            log.info("推送售卖房型response："+ JSON.toJSONString(createSaleRoomResponse));
                            if (null != createSaleRoomResponse) {
                                //组装产品映射数据
                                DisProductMappingDTO disProductMappingDTO = new DisProductMappingDTO();
                                disProductMappingDTO.setCompanyCode(companyCode);
                                disProductMappingDTO.setDisHotelId(masterRoomPOMap.get(dltNeedPushSaleRoomDTO.getRoomId()).getHotelID().toString());
                                disProductMappingDTO.setDisRoomId(createSaleRoomResponse.getRoomTypeId().toString());
                                disProductMappingDTO.setDistributor(ChannelCodeKey.ctripCode);
                                disProductMappingDTO.setHotelId(dltNeedPushSaleRoomDTO.getHotelId());
                                disProductMappingDTO.setRoomId(dltNeedPushSaleRoomDTO.getRoomId());
                                disProductMappingDTO.setRoomName(dltNeedPushSaleRoomDTO.getRoomName());
                                disProductMappingDTO.setProductId(dltNeedPushSaleRoomDTO.getProductId());
                                disProductMappingDTO.setProductName(dltNeedPushSaleRoomDTO.getProductName());
                                disProductMappingDTO.setCreatedBy("system");
                                disProductMappingDTO.setCreatedDt(DateUtil.getCurrentDateStr(1));
                                disProductMappingDTOList.add(disProductMappingDTO);
                            }
                            try {
                                Thread.sleep(6000);//每推送一次，休眠6s，因为代理通限制每分钟只能推送10次
                            }catch (Exception e) {
                                log.error("推送售卖房型休眠异常",e);
                            }
                        }
                    }

                    //新增产品映射
                    if (disProductMappingDTOList.size() > 0) {
                        disMappingRemote.batchAddProductMapping(disProductMappingDTOList);
                    }
                }
            }
        }
    }
}
