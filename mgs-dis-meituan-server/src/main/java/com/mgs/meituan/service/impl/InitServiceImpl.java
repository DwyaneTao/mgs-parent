package com.mgs.meituan.service.impl;

import com.mgs.meituan.config.RequestConfig;
import com.mgs.meituan.domain.MeituanCityPO;
import com.mgs.meituan.dto.city.response.CityDTO;
import com.mgs.meituan.mapper.CityMapper;
import com.mgs.meituan.service.InitService;
import com.mgs.meituan.util.MeituanCityUtil;
import com.mgs.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class InitServiceImpl implements InitService {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private RequestConfig requestConfig;

    @Resource(name = "cityMappingServiceExecutor")
    private ThreadPoolTaskExecutor executor;

    @Override
    public void initCityMapping() throws Exception{
        List<CityDTO> cityList = cityMapper.queryCityList();

        Map<String, Integer> meituanCityMap = MeituanCityUtil.getCityMap();

        List<Future> futures = new ArrayList<Future>();

        //分成1000个城市一组
        int loop = cityList.size() / requestConfig.getThreadSize() + (cityList.size() % requestConfig.getThreadSize() != 0? 1: 0);
        for(int i = 0; i < loop; i++){
            List<CityDTO> partCityList = cityList.subList(i * requestConfig.getThreadSize(), (i + 1) * requestConfig.getThreadSize() > cityList.size()? cityList.size(): (i + 1) * requestConfig.getThreadSize());
            Future<?> future = executor.submit(()->{
                handlerCityMapping(partCityList, meituanCityMap);
            });
            futures.add(future);
        }

        //TODO 将线程的结果写入日志
    }

    private void handlerCityMapping(List<CityDTO> cityList, Map<String, Integer > meituanCityMap){
        List<MeituanCityPO> meituanCityList = new ArrayList<MeituanCityPO>();
        for(CityDTO cityDTO: cityList){
            MeituanCityPO meituanCityPO = BeanUtil.transformBean(cityDTO, MeituanCityPO.class);
            meituanCityPO.setActive(1);
            //若是没有一模一样的名称
            if(meituanCityMap.get(cityDTO.getCityName()) == null) {
                if (cityDTO.getCityName().endsWith("市") || cityDTO.getCityName().endsWith("县")) { //若是该城市名称以市或者县结尾的
                    if(meituanCityMap.get(cityDTO.getCityName().substring(0 ,cityDTO.getCityName().length() - 1)) != null){
                        meituanCityPO.setMeituanCityName(cityDTO.getCityName().substring(0 ,cityDTO.getCityName().length() - 1));
                        meituanCityPO.setMeituanCityId(meituanCityMap.get(cityDTO.getCityName().substring(0 ,cityDTO.getCityName().length() - 1)));
                    }else {
                        continue;
                    }
                }else { //若是没有匹配的
                    continue;
                }
            }else {
                meituanCityPO.setMeituanCityName(cityDTO.getCityName());
                meituanCityPO.setMeituanCityId(meituanCityMap.get(cityDTO.getCityName()));
            }
            meituanCityList.add(meituanCityPO);
        }
        cityMapper.insertList(meituanCityList);
    }

}
