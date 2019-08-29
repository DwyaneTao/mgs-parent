package com.mgs.dlt.service;


import com.mgs.dis.dto.HotelOrderOperateRequestDTO;
import com.mgs.dis.dto.HotelOrderOperateResponseDTO;

/**
 *   2018/2/28.
 */
public interface DltHotelOrderOperateService {

    HotelOrderOperateResponseDTO operateOrder(HotelOrderOperateRequestDTO requestDTO);
}
