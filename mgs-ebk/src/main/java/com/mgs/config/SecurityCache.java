package com.mgs.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mgs.common.Response;
import com.mgs.user.dto.MenuDTO;
import com.mgs.user.remote.UserRemote;
import com.mgs.util.BeanUtil;
import com.mgs.util.SpringContextUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SecurityCache {

    /***********login cache start************/

    static LoadingCache<String, Boolean> loginCache = CacheBuilder.newBuilder()
            .maximumSize(2000)
//            .expireAfterWrite(10L, TimeUnit.SECONDS)
            .build(createLoginCacheLoader());


    private static CacheLoader<String, Boolean> createLoginCacheLoader() {
        return new CacheLoader<String, Boolean>() {
            @Override
            public Boolean load(String key) throws Exception {
                return false;
            }
        };
    }

    public static boolean getLoginStatusByLoginName(String loginName) throws Exception{
        return loginCache.get(loginName);
    }

    public static void putLoginStatusToCache(String loginName){
        loginCache.put(loginName,true);
    }

    public static void removeLoginStatus(String loginName){
        loginCache.invalidate(loginName);
    }

    /***********login cache end************/

    /***********menu cache start************/


    public static final String ONE_TWO_LEVEL_NENU_URL = "oneTwoLevelMenuUrl";

    static LoadingCache<String, Set<String>> menuCache = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .expireAfterWrite(3L, TimeUnit.DAYS)
            .build(createMenuCacheLoader());


    private static CacheLoader<String,Set<String>> createMenuCacheLoader() {
        return new CacheLoader<String, Set<String>>() {
            @Override
            public Set<String> load(String userAccount) throws Exception {

                UserRemote userRemote = (UserRemote) SpringContextUtil.getBean(UserRemote.class);
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("userAccount", userAccount);
                Response response = userRemote.queryMenuListByLoginName(paramMap);
                if (null != response && 1 == response.getResult() && null != response.getModel()) {
                    List<MenuDTO> menuDTOList = BeanUtil.transformList(response.getModel(), MenuDTO.class);
                    if (!CollectionUtils.isEmpty(menuDTOList)) {
                        Set<String> menuUrlDevList = new HashSet<>();
                        for (MenuDTO menuDTO : menuDTOList) {
                            if (!menuUrlDevList.contains(menuDTO.getBackEndUrl())) {
                                menuUrlDevList.add(menuDTO.getBackEndUrl());
                            }
                        }
                        return menuUrlDevList;
                    }
                }
                return null;

            }
        };
    }

    public static Set<String> getMenuListByLoginName(String userAccount) throws Exception{
        return menuCache.get(userAccount);
    }

    public static void putMenuListToCache(String userAccount,Set<String> menuUrlDevList){
        menuCache.put(userAccount,menuUrlDevList);
    }

    /***********menu cache end************/
}
