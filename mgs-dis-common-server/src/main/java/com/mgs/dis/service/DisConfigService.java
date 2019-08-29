package com.mgs.dis.service;

import com.mgs.dis.dto.DisConfigDTO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:42
 * @Description:
 */
public interface DisConfigService {

    List<DisConfigDTO> queryConfigList(String channelCode);
}
