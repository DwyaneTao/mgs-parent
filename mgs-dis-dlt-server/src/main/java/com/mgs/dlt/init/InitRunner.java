package com.mgs.dlt.init;

import com.alibaba.fastjson.JSONArray;
import com.mgs.common.Response;
import com.mgs.common.constant.InitData;
import com.mgs.dis.dto.DisConfigDTO;
import com.mgs.dis.remote.DisConfigRemote;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.keys.ChannelCodeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/8/5 22:09
 * @Description:
 */
@Component
public class InitRunner implements ApplicationRunner {

    @Autowired
    private DisConfigRemote disConfigRemote;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始初始化数据");
        List<DisConfigDTO> disConfigDTOList = disConfigRemote.queryConfigList(ChannelCodeKey.ctripCode);

        if (!CollectionUtils.isEmpty(disConfigDTOList)) {
            Map<String,Map<String,String>> channelConfigMap = new HashMap<String,Map<String,String>>();
            for (DisConfigDTO disConfigDTO : disConfigDTOList) {
                if (null != disConfigDTO.getCompanyCode()) {
                    if (null == channelConfigMap.get(disConfigDTO.getCompanyCode())) {
                        Map<String,String> configMap = new HashMap<String,String>();
                        configMap.put(disConfigDTO.getFieldName(),disConfigDTO.getFieldValue());
                        channelConfigMap.put(disConfigDTO.getCompanyCode(),configMap);
                    }else {
                        channelConfigMap.get(disConfigDTO.getCompanyCode()).put(disConfigDTO.getFieldName(),disConfigDTO.getFieldValue());
                    }
                }
            }
            InitData.channelConfigMap = channelConfigMap;
            System.out.println("结束初始化数据，数据："+ JSONArray.toJSONString(channelConfigMap));
        }
    }
}
