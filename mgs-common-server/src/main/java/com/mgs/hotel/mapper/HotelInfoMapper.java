package com.mgs.hotel.mapper;

import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.dto.BasicHotelLogDTO;
import com.mgs.hotel.dto.BasicPhotoDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HotelInfoMapper {

    /**
     * 查询酒店列表信息(有排序)
     * @param requestMap
     * @return
     */
    List<BasicHotelInfoDTO> queryHotelListBySort(Map<String, String> requestMap);

    /**
     * 查询酒店列表（无排序）
     * @param requestMap
     * @return
     */
    List<BasicHotelInfoDTO> queryHotelList(Map<String, String> requestMap);

    /**
     * 查询最低的分数
     * @param basicPhotoDTO
     * @return
     */
    Integer queryLowestPhotoRank(BasicPhotoDTO basicPhotoDTO);

    /**
     * 查询酒店基本信息详情
     * @param requestMap
     * @return
     */
    BasicHotelInfoDTO queryHotelDetail(Map<String ,String> requestMap);

    /**
     * 查询房型基本信息列表
     * @param requestMap
     * @return
     */
    List<BasicRoomInfoDTO> queryRoomList(Map<String, String> requestMap);

    /**
     * 查询房型详情
     * @param requestMap
     * @return
     */
    BasicRoomInfoDTO queryRoomDetail(Map<String, String> requestMap);

    /**
     * 查询是否存在该酒店或者房型名称
     * (暂时不用，供以后扩展)
     * @param basicRoomInfoDTO
     * @return
     */
    List<BasicRoomInfoDTO> queryExistHotelOrExistRoomName(BasicRoomInfoDTO basicRoomInfoDTO);

    /**
     * 查询是否存在该酒店和房型id
     * (暂时不用，供以后扩展)
     * @param basicRoomInfoDTO
     * @return
     */
    BasicRoomInfoDTO queryExistHotelAndExistRoomId(BasicRoomInfoDTO basicRoomInfoDTO);

    /**
     * 查询是否存在产品
     * @param requestMap
     * @return
     */
    List<Integer> queryExistProduct(Map<String, String> requestMap);

    /**
     * 查询酒店图片列表
     * @param requestMap
     * @return
     */
    List<BasicPhotoDTO> queryHotelPhotoList(Map<String, String> requestMap);

    /**
     * 查询酒店操作日志
     * @param requestMap
     * @return
     */
    List<BasicHotelLogDTO> queryHotelLogList(Map<String, String> requestMap);



}
