package com.mgs.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.hotel.domain.BasicHotelLogPO;
import com.mgs.hotel.domain.BasicPhotoPO;
import com.mgs.hotel.domain.BasicRoomInfoPO;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.dto.BasicHotelLogDTO;
import com.mgs.hotel.dto.BasicPhotoDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.dto.HotelInfoDTO;
import com.mgs.hotel.domain.BasicHotelInfoPO;
import com.mgs.hotel.domain.BasicHotelSortPO;
import com.mgs.enums.HotelPhotoEnum;
import com.mgs.hotel.mapper.*;
import com.mgs.hotel.service.HotelService;
import com.mgs.user.dto.UserDTO;
import com.mgs.user.mapper.UserMapper;
import com.mgs.util.BeanUtil;
import com.mgs.util.ComparedUtil;
import com.mgs.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Basic;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelServiceImpl implements HotelService {

    private static final Integer photoRank = 500;

    @Autowired
    private BasicHotelInfoMapper basicHotelInfoMapper;

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

    @Autowired
    private BasicHotelSortMapper basicHotelSortMapper;

    @Autowired
    private BasicPhotoMapper basicPhotoMapper;

    @Autowired
    private BasicRoomInfoMapper basicRoomInfoMapper;

    @Autowired
    private BasicHotelLogMapper basicHotelLogMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 增加酒店基本信息
     * @param basicHotelInfoDTO
     * @return
     */
    @Override
    @Transactional
    public int addHotelInfo(BasicHotelInfoDTO basicHotelInfoDTO) {
        BasicHotelInfoPO existHotelInfo = new BasicHotelInfoPO();
        existHotelInfo.setHotelName(basicHotelInfoDTO.getHotelName());
        existHotelInfo.setActive(1);
        existHotelInfo = basicHotelInfoMapper.selectOne(existHotelInfo);

        if(existHotelInfo != null){
            return -1;
        }

        BasicHotelInfoPO basicHotelInfoPO = BeanUtil.transformBean(basicHotelInfoDTO, BasicHotelInfoPO.class);
        basicHotelInfoPO.setActive(1);
        basicHotelInfoPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelInfoMapper.insert(basicHotelInfoPO);

        BasicPhotoPO basicPhotoPO = new BasicPhotoPO();
        basicPhotoPO.setHotelId(basicHotelInfoPO.getHotelId());
        basicPhotoPO.setPhotoType(HotelPhotoEnum.OUTSIDE.no);
        basicPhotoPO.setCreatedBy(basicHotelInfoDTO.getCreatedBy());
        basicPhotoPO.setActive(1);
        basicPhotoPO.setPhotoRank(photoRank);
        basicPhotoPO.setPhotoUrl(basicHotelInfoPO.getMainPhotoUrl());
        basicPhotoPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicPhotoMapper.insert(basicPhotoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelLogPO.setContent("新增了酒店");
        basicHotelLogPO.setTarget("酒店");
        basicHotelLogPO.setHotelId(basicHotelInfoPO.getHotelId());
        basicHotelLogPO.setCreatedBy(combinationOperator(basicHotelInfoDTO.getOperatorAccount()));
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 修改酒店信息
     * @param basicHotelInfoDTO
     * @return
     */
    @Override
    public int modifyHotelInfo(BasicHotelInfoDTO basicHotelInfoDTO) throws Exception {
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("hotelId", String.valueOf(basicHotelInfoDTO.getHotelId()));
        BasicHotelInfoDTO before = hotelInfoMapper.queryHotelDetail(requestMap);

        BasicHotelInfoPO basicHotelInfoPO = BeanUtil.transformBean(basicHotelInfoDTO, BasicHotelInfoPO.class);
        basicHotelInfoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelInfoPO.setMainPhotoUrl(null);
        basicHotelInfoPO.setHotelName(null);
        basicHotelInfoPO.setCreatedDt(null);
        basicHotelInfoMapper.updateByPrimaryKeySelective(basicHotelInfoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelLogPO.setContent(ComparedUtil.getComparedContent(before, basicHotelInfoDTO));
        basicHotelLogPO.setTarget("酒店");
        basicHotelLogPO.setHotelId(basicHotelInfoDTO.getHotelId());
        basicHotelLogPO.setCreatedBy(combinationOperator(basicHotelInfoDTO.getOperatorAccount()));
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 删除酒店信息
     * @param requestMap
     * @return
     */
    @Override
    public int deleteHotelInfo(Map<String, String> requestMap) {

        List<Integer> productIds = hotelInfoMapper.queryExistProduct(requestMap);

        if(CollectionUtils.isNotEmpty(productIds)){
            return -1;
        }

        BasicHotelInfoPO basicHotelInfoPO = new BasicHotelInfoPO();
        basicHotelInfoPO.setHotelId(Integer.valueOf(requestMap.get("hotelId")));
        basicHotelInfoPO.setActive(0);
        basicHotelInfoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelInfoMapper.updateByPrimaryKeySelective(basicHotelInfoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedBy(combinationOperator(requestMap.get("operatorAccount")));
        basicHotelLogPO.setTarget("酒店");
        basicHotelLogPO.setHotelId(Integer.valueOf(requestMap.get("hotelId")));
        basicHotelLogPO.setContent("删除了酒店");
        basicHotelLogPO.setCreatedBy(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 修改酒店排序
     * @param requestMap
     * @return
     */
    @Override
    @Transactional
    public int modifyHotelSort(Map<String, String> requestMap) {
       /* BasicHotelInfoPO basicHotelInfoPO = new BasicHotelInfoPO();
        basicHotelInfoPO.setHotelId(Integer.valueOf(requestMap.get("hotelId")));
        basicHotelInfoPO.setActive(1);
        basicHotelInfoPO = basicHotelInfoMapper.selectOne(basicHotelInfoPO);

        if(basicHotelInfoPO == null){
            return -1;
        }*/

        BasicHotelSortPO basicHotelSortPO = new BasicHotelSortPO();
        basicHotelSortPO.setActive(1);
        basicHotelSortPO.setHotelId(Integer.valueOf(requestMap.get("hotelId")));
        basicHotelSortPO.setOrgCode(requestMap.get("orgCode"));

        BasicHotelSortPO existBasicHotelSort = basicHotelSortMapper.selectOne(basicHotelSortPO);
        basicHotelSortPO.setSortRank(Integer.valueOf(requestMap.get("sortRank")));
        if(existBasicHotelSort != null){
            existBasicHotelSort.setSortRank(basicHotelSortPO.getSortRank());
            existBasicHotelSort.setModifiedBy(requestMap.get("modifiedBy"));
            existBasicHotelSort.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            return basicHotelSortMapper.updateByPrimaryKeySelective(existBasicHotelSort);
        }

        basicHotelSortPO.setCreatedBy(requestMap.get("modifiedBy"));
        basicHotelSortPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return basicHotelSortMapper.insert(basicHotelSortPO);
    }


    /**
     * 查询酒店列表
     * @param requestMap
     * @return
     */
    @Override
    public PageInfo<BasicHotelInfoDTO> queryHotelListBySort(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<BasicHotelInfoDTO> basicHotelInfoDTOList = hotelInfoMapper.queryHotelListBySort(requestMap);
        return new PageInfo<BasicHotelInfoDTO>(basicHotelInfoDTOList);
    }

    /**
     * 查询酒店详情
     * @param requestMap
     * @return
     */
    @Override
    public BasicHotelInfoDTO queryHotelDetail(Map<String, String> requestMap) {
        return hotelInfoMapper.queryHotelDetail(requestMap);
    }

    /**
     * 查询酒店列表
     * @param requestMap
     * @return
     */
    @Override
    public PageInfo<BasicHotelInfoDTO> queryHotelList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<BasicHotelInfoDTO> hotelInfoList = hotelInfoMapper.queryHotelList(requestMap);
        return new PageInfo<BasicHotelInfoDTO>(hotelInfoList);
    }

    /**
     * 增加房型
     * @param basicRoomInfoDTO
     * @return
     */
    @Override
    @Transactional
    public int addRoomInfo(BasicRoomInfoDTO basicRoomInfoDTO) {
        List<BasicRoomInfoDTO> roomInfoDTO = hotelInfoMapper.queryExistHotelOrExistRoomName(basicRoomInfoDTO);

        if(CollectionUtils.isNotEmpty(roomInfoDTO)){
            return -1;
        }

        BasicRoomInfoPO basicRoomInfoPO = BeanUtil.transformBean(basicRoomInfoDTO, BasicRoomInfoPO.class);
        basicRoomInfoPO.setActive(1);
        basicRoomInfoPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicRoomInfoMapper.insert(basicRoomInfoPO);

        BasicPhotoPO basicPhotoPO = new BasicPhotoPO();
        basicPhotoPO.setHotelId(basicRoomInfoDTO.getHotelId());
        basicPhotoPO.setPhotoType(HotelPhotoEnum.ROOM.no);
        basicPhotoPO.setPhotoUrl(basicRoomInfoDTO.getMainPhotoUrl());
        basicPhotoPO.setRoomId(basicRoomInfoPO.getRoomId());
        basicPhotoPO.setPhotoRank(photoRank);
        basicPhotoPO.setActive(1);
        basicPhotoPO.setCreatedBy(basicRoomInfoDTO.getCreatedBy());
        basicPhotoPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicPhotoMapper.insert(basicPhotoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelLogPO.setContent("新增了房型\"" + basicRoomInfoDTO.getRoomName() +"\"");
        basicHotelLogPO.setTarget("房型");
        basicHotelLogPO.setHotelId(basicRoomInfoDTO.getHotelId());
        basicHotelLogPO.setCreatedBy(combinationOperator(basicRoomInfoDTO.getOperatorAccount()));
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 修改房型信息
     * @param basicRoomInfoDTO
     * @return
     */
    @Override
    public int modifyRoomInfo(BasicRoomInfoDTO basicRoomInfoDTO) throws Exception {

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("roomId", String.valueOf(basicRoomInfoDTO.getRoomId()));
        BasicRoomInfoDTO before =hotelInfoMapper.queryRoomDetail(requestMap);

        BasicRoomInfoPO basicRoomInfoPO = BeanUtil.transformBean(basicRoomInfoDTO, BasicRoomInfoPO.class);
        basicRoomInfoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicRoomInfoPO.setMainPhotoUrl(null);
        basicRoomInfoPO.setRoomName(null);
        basicRoomInfoPO.setHotelId(null);
        basicRoomInfoPO.setCreatedDt(null);
        basicRoomInfoMapper.updateByPrimaryKeySelective(basicRoomInfoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelLogPO.setContent(ComparedUtil.getComparedContent(before, basicRoomInfoDTO));
        basicHotelLogPO.setCreatedBy(combinationOperator(basicRoomInfoDTO.getOperatorAccount()));
        basicHotelLogPO.setTarget("房型");
        basicHotelLogPO.setHotelId(basicRoomInfoDTO.getHotelId());
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 删除房型信息
     * @param requestMap
     * @return
     */
    @Override
    @Transactional
    public int deleteRoomInfo(Map<String, String> requestMap) {

        List<Integer> productIds = hotelInfoMapper.queryExistProduct(requestMap);

        if(CollectionUtils.isNotEmpty(productIds)){
            return -1;
        }

        BasicRoomInfoPO existRoomInfo = new BasicRoomInfoPO();
        existRoomInfo.setRoomId(Integer.valueOf(requestMap.get("roomId")));
        existRoomInfo.setActive(0);
        existRoomInfo.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        existRoomInfo.setModifiedBy(requestMap.get("modifiedBy"));
        basicRoomInfoMapper.updateByPrimaryKeySelective(existRoomInfo);

        BasicRoomInfoPO basicRoomInfoPO = new BasicRoomInfoPO();
        basicRoomInfoPO.setRoomId(Integer.valueOf(requestMap.get("roomId")));
        basicRoomInfoPO = basicRoomInfoMapper.selectOne(basicRoomInfoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicHotelLogPO.setContent("删除了房型\""+ basicRoomInfoPO.getRoomName()+ "\"");
        basicHotelLogPO.setCreatedBy(combinationOperator(requestMap.get("operatorAccount")));
        basicHotelLogPO.setTarget("房型");
        basicHotelLogPO.setHotelId(basicRoomInfoPO.getHotelId());
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 增加图片信息
     * @param basicPhotoDTO
     * @return
     */
    @Override
    public int addHotelPhoto(BasicPhotoDTO basicPhotoDTO) {
        BasicPhotoPO basicPhotoPO = BeanUtil.transformBean(basicPhotoDTO, BasicPhotoPO.class);
        basicPhotoPO.setPhotoRank(photoRank);
        basicPhotoPO.setActive(1);
        basicPhotoPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicPhotoMapper.insert(basicPhotoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Map<String, String> requestMap = new HashMap<String, String>();
        if(basicPhotoDTO.getRoomId() != null){
            requestMap.put("roomId", String.valueOf(basicPhotoDTO.getRoomId()));
        }
                 basicHotelLogPO.setContent("新增了一张\""+ (basicPhotoDTO.getPhotoType() == 3 ? hotelInfoMapper.queryRoomDetail(requestMap).getRoomName() : HotelPhotoEnum.getHotelPhotoDesc(basicPhotoDTO.getPhotoType()))+ "\"图片");
        basicHotelLogPO.setCreatedBy(combinationOperator(basicPhotoDTO.getOperatorAccount()));
        basicHotelLogPO.setTarget("图片");
        basicHotelLogPO.setHotelId(basicPhotoDTO.getHotelId());
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }

    /**
     * 修改图片信息
     * @param basicPhotoDTO
     * @return
     */
    @Override
    public int moveHotelPhoto(BasicPhotoDTO basicPhotoDTO) {

        BasicPhotoPO basicPhoto = new BasicPhotoPO();
        basicPhoto.setPhotoId(basicPhotoDTO.getPhotoId());
        basicPhoto = basicPhotoMapper.selectOne(basicPhoto);

        BasicPhotoPO basicPhotoPO = BeanUtil.transformBean(basicPhotoDTO, BasicPhotoPO.class);
        basicPhotoPO.setModifiedBy(basicPhotoDTO.getModifiedBy());
        basicPhotoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicPhotoPO.setPhotoRank(photoRank);
        basicPhotoPO.setHotelId(null);
        basicPhotoMapper.updateByPrimaryKeySelective(basicPhotoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //修改前的,若是有房型的话
        Map<String, String> beforeMap = new HashMap<String, String>();
        if(basicPhoto.getRoomId() != null){
            beforeMap.put("roomId", String.valueOf(basicPhoto.getRoomId()));
        }
        //修改后的map
        Map<String, String> requestMap = new HashMap<String, String>();
        if(basicPhotoDTO.getRoomId() != null){
            requestMap.put("roomId", String.valueOf(basicPhotoDTO.getRoomId()));
        }
        basicHotelLogPO.setContent("将\""+(basicPhoto.getPhotoType() == 3 ? hotelInfoMapper.queryRoomDetail(beforeMap).getRoomName() : HotelPhotoEnum.getHotelPhotoDesc(basicPhotoDTO.getPhotoType()))+ "移动到"+(basicPhotoDTO.getPhotoType() == 3 ? hotelInfoMapper.queryRoomDetail(requestMap).getRoomName() : HotelPhotoEnum.getHotelPhotoDesc(basicPhotoDTO.getPhotoType()))+ "\"");
        basicHotelLogPO.setCreatedBy(combinationOperator(basicPhotoDTO.getOperatorAccount()));
        basicHotelLogPO.setTarget("图片");
        basicHotelLogPO.setHotelId(basicPhotoDTO.getHotelId());
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }



    /**
     * 删除图片信息
     * @param basicPhotoDTO
     * @return
     */
    @Override
    public int deleteHotelPhoto(BasicPhotoDTO basicPhotoDTO) {
        BasicPhotoPO basicPhoto = new BasicPhotoPO();
        basicPhoto.setPhotoId(basicPhotoDTO.getPhotoId());

        BasicPhotoPO basicPhotoPO = BeanUtil.transformBean(basicPhotoDTO, BasicPhotoPO.class);
        basicPhotoPO.setActive(0);
        basicPhotoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
         basicPhotoMapper.updateByPrimaryKeySelective(basicPhotoPO);


        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Map<String, String> requestMap = new HashMap<String, String>();
        if(basicPhotoDTO.getRoomId() != null){
            requestMap.put("roomId", String.valueOf(basicPhotoDTO.getRoomId()));
        }

        basicHotelLogPO.setContent("删除一张"+(basicPhotoDTO.getPhotoType() == 3 ? hotelInfoMapper.queryRoomDetail(requestMap).getRoomName() : HotelPhotoEnum.getHotelPhotoDesc(basicPhotoDTO.getPhotoType()))+ "\"图片");
        basicHotelLogPO.setCreatedBy(combinationOperator(basicPhotoDTO.getOperatorAccount()));
        basicHotelLogPO.setTarget("图片");
        basicHotelLogPO.setHotelId(basicPhotoDTO.getHotelId());
        return basicHotelLogMapper.insert(basicHotelLogPO);
    }



    /**
     * 设置为默认值
     * @param basicPhotoDTO
     * @return
     */
    @Override
    @Transactional
    public int setDefaultPhoto(BasicPhotoDTO basicPhotoDTO) {
        //设置为默认值
        Integer lowestHotelRank = hotelInfoMapper.queryLowestPhotoRank(basicPhotoDTO);
        if(lowestHotelRank == null){
            lowestHotelRank = new Integer(500);
        }
        lowestHotelRank = lowestHotelRank - 1;

        //若是为外景图以及房间图的话，把对应的主图换上
        if(basicPhotoDTO.getPhotoType() == HotelPhotoEnum.OUTSIDE.no){
            BasicHotelInfoPO basicHotelInfoPO = new BasicHotelInfoPO();
            basicHotelInfoPO.setHotelId(basicPhotoDTO.getHotelId());
            basicHotelInfoPO.setMainPhotoUrl(basicPhotoDTO.getPhotoUrl());
            basicHotelInfoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            basicHotelInfoPO.setModifiedBy(basicPhotoDTO.getModifiedBy());
            basicHotelInfoMapper.updateByPrimaryKeySelective(basicHotelInfoPO);
        }else if(basicPhotoDTO.getPhotoType() == HotelPhotoEnum.ROOM.no && basicPhotoDTO.getRoomId() != null){
            BasicRoomInfoPO basicRoomInfoPO = new BasicRoomInfoPO();
            basicRoomInfoPO.setRoomId(basicPhotoDTO.getRoomId());
            basicRoomInfoPO.setMainPhotoUrl(basicPhotoDTO.getPhotoUrl());
            basicRoomInfoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            basicRoomInfoPO.setModifiedBy(basicPhotoDTO.getModifiedBy());
            basicRoomInfoMapper.updateByPrimaryKeySelective(basicRoomInfoPO);
        }
        BasicPhotoPO basicPhotoPO = BeanUtil.transformBean(basicPhotoDTO, BasicPhotoPO.class);
        basicPhotoPO.setPhotoRank(lowestHotelRank);
        basicPhotoPO.setModifiedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        basicPhotoPO.setHotelId(null);
        basicPhotoMapper.updateByPrimaryKeySelective(basicPhotoPO);

        BasicHotelLogPO basicHotelLogPO = new BasicHotelLogPO();
        basicHotelLogPO.setCreatedDt(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Map<String, String> requestMap = new HashMap<String, String>();
        if(basicPhotoDTO.getRoomId() != null){
            requestMap.put("roomId", String.valueOf(basicPhotoDTO.getRoomId()));
        }
        basicHotelLogPO.setContent("设置\""+ (basicPhotoDTO.getPhotoType() == 3 ? hotelInfoMapper.queryRoomDetail(requestMap).getRoomName() : HotelPhotoEnum.getHotelPhotoDesc(basicPhotoDTO.getPhotoType()))+ "\"为默认");
        basicHotelLogPO.setCreatedBy(combinationOperator(basicPhotoDTO.getOperatorAccount()));
        basicHotelLogPO.setTarget("图片");
        basicHotelLogPO.setHotelId(basicPhotoDTO.getHotelId());
        return basicHotelLogMapper.insert(basicHotelLogPO);

    }

    @Override
    public PageInfo<BasicPhotoDTO> queryHotelPhotoList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<BasicPhotoDTO> basicPhotoDTOS = hotelInfoMapper.queryHotelPhotoList(requestMap);
        return new PageInfo<BasicPhotoDTO>(basicPhotoDTOS);
    }

    /**
     * 检查酒店名称是否存在
     * @param requestMap
     * @return
     */
    @Override
    public int examineHotelName(Map<String, String> requestMap) {
        BasicHotelInfoPO basicHotelInfoPO = new BasicHotelInfoPO();
        basicHotelInfoPO.setHotelName(requestMap.get("hotelName"));
        basicHotelInfoPO.setActive(1);

        basicHotelInfoPO = basicHotelInfoMapper.selectOne(basicHotelInfoPO);
        if(basicHotelInfoPO == null){
            return  0;
        }
        return 1;
    }

    /**
     * 检查房型名称是否存在
     * @param requestMap
     * @return
     */
    @Override
    public int examineRoomName(Map<String, String> requestMap) {
        BasicRoomInfoPO basicRoomInfoPO = new BasicRoomInfoPO();
        basicRoomInfoPO.setActive(1);
        basicRoomInfoPO.setRoomName(requestMap.get("roomName"));
        basicRoomInfoPO.setHotelId(Integer.valueOf(requestMap.get("hotelId")));

        basicRoomInfoPO = basicRoomInfoMapper.selectOne(basicRoomInfoPO);
        if(basicRoomInfoPO == null){
            return 0;
        }
        return 1;
    }

    /**
     * 查询酒店操作日志
     * @param requestMap
     * @return
     */
    @Override
    public PageInfo<BasicHotelLogDTO> queryHotelLogList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<BasicHotelLogDTO> hotelLogDTOS = hotelInfoMapper.queryHotelLogList(requestMap);
        return new PageInfo<BasicHotelLogDTO>(hotelLogDTOS);
    }

    /**
     * 查询房型信息列表
     * @param requestMap
     * @return
     */
    @Override
    public PageInfo<BasicRoomInfoDTO> queryRoomList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<BasicRoomInfoDTO> basicRoomInfoDTOS = hotelInfoMapper.queryRoomList(requestMap);
        return new PageInfo<BasicRoomInfoDTO>(basicRoomInfoDTOS);
    }

    /**
     * 查询房型基本信息详情
     * @param requestMap
     * @return
     */
    @Override
    public BasicRoomInfoDTO queryRoomDetail(Map<String, String> requestMap) {
        return hotelInfoMapper.queryRoomDetail(requestMap);
    }

    /**
     * 组装日志中的操作人
     * @param userAccount
     * @return
     */
    private String combinationOperator(String userAccount){
        UserDTO userDTO =  userMapper.queryLoginUser(userAccount);
        return new String(userDTO.getCompanyName() + "-"+ userDTO.getLoginName());
    }


}
