package com.mgs.meituan.init;

import com.alibaba.fastjson.JSONArray;
import com.mgs.dis.dto.DisConfigDTO;
import com.mgs.dis.remote.DisConfigRemote;
import com.mgs.keys.ChannelCodeKey;
import com.mgs.meituan.key.InitData;
import com.mgs.meituan.util.MeituanCityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitRunner implements ApplicationRunner {

    @Autowired
    private DisConfigRemote disConfigRemote;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始初始化渠道信息");
        List<DisConfigDTO> disConfigDTOList = disConfigRemote.queryConfigList(ChannelCodeKey.meituanCode);

        if (!CollectionUtils.isEmpty(disConfigDTOList)) {
            Map<String, Map<String,String>> channelConfigMap = new HashMap<String,Map<String,String>>();
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
