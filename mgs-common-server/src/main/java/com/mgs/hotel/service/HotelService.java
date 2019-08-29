package com.mgs.hotel.service;

import com.github.pagehelper.PageInfo;
import com.mgs.hotel.domain.BasicPhotoPO;
import com.mgs.hotel.dto.BasicHotelInfoDTO;
import com.mgs.hotel.dto.BasicHotelLogDTO;
import com.mgs.hotel.dto.BasicPhotoDTO;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.dto.HotelInfoDTO;

import java.util.Map;

public interface HotelService {

    /**
     * 增加酒店信息
     * @param basicHotelInfoDTO
     * @return
     */
    int addHotelInfo(BasicHotelInfoDTO basicHotelInfoDTO);

    /**
     * 修改酒店信息
     * @param basicHotelInfoDTO
     * @return
     */
    int modifyHotelInfo(BasicHotelInfoDTO basicHotelInfoDTO) throws Exception;

    /**
     * 删除酒店信息
     * @param requestMap
     * @return
     */
    int deleteHotelInfo(Map<String, String> requestMap);

    /**
     * 修改酒店排序
     * @param requestMap
     * @return
     */
    int modifyHotelSort(Map<String, String> requestMap);

    /**
     * 查询酒店基本信息列表（有排序）
     * @param requestMap
     * @return
     */
    PageInfo<BasicHotelInfoDTO> queryHotelListBySort(Map<String, String> requestMap);

    /**
     * 查询酒店详情
     * @param requestMap
     * @return
     */
     BasicHotelInfoDTO queryHotelDetail(Map<String, String> requestMap);

    /**
     * 查询酒店列表（无排序）
     * @param requestMap
     * @return
     */
    PageInfo<BasicHotelInfoDTO> queryHotelList(Map<String, String> requestMap);

    /**
     * 增加房型
     * @param basicRoomInfoDTO
     * @return
     */
    int addRoomInfo(BasicRoomInfoDTO basicRoomInfoDTO);

    /**
     * 修改房型
     * @param basicRoomInfoDTO
     * @return
     */
    int modifyRoomInfo(BasicRoomInfoDTO basicRoomInfoDTO) throws Exception;

    /**
     * 删除房型
     * @param requestMap
     * @return
     */
    int deleteRoomInfo(Map<String, String> requestMap);

    /**
     * 查询房型基本信息列表
     * @param requestMap
     * @return
     */
    PageInfo<BasicRoomInfoDTO> queryRoomList(Map<String, String> requestMap);

    /**
     * 查询房型信息详情
     * @param requestMap
     * @return
     */
    BasicRoomInfoDTO queryRoomDetail(Map<String, String> requestMap);

    /**
     * 增加图片
     * @param basicPhotoDTO
     * @return
     */
    int addHotelPhoto(BasicPhotoDTO basicPhotoDTO);

    /**
     * 移动图片
     * @param basicPhotoDTO
     * @return
     */
    int moveHotelPhoto(BasicPhotoDTO basicPhotoDTO);

    /**
     * 删除图片
     * @param basicPhotoDTO
     * @return
     */
    int deleteHotelPhoto(BasicPhotoDTO basicPhotoDTO);

    /**
     * 设置默认
     * @param basicPhotoDTO
     * @return
     */
    int setDefaultPhoto(BasicPhotoDTO basicPhotoDTO);

    /**
     * 查询图片列表
     * @param requestMap
     * @return
     */
    PageInfo<BasicPhotoDTO> queryHotelPhotoList(Map<String, String> requestMap);

    /**
     * 检查是否有重复的酒店名称
     * @param requestMap
     * @return
     */
    int examineHotelName(Map<String, String> requestMap);

    /**
     * 检查是否有重复的房型名称
     * @param requestMap
     * @return
     */
    int examineRoomName(Map<String, String> requestMap);

    /**
     * 查询酒店操作日志
     * @param requestMap
     * @return
     */
    PageInfo<BasicHotelLogDTO> queryHotelLogList(Map<String, String> requestMap);
}
