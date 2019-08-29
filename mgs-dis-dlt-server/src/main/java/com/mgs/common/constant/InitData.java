package com.mgs.common.constant;

import java.util.*;


public class InitData {


    /**
     * 城市
     * key=MAC
     * value=澳門
     */
    public static Map<String,String> cityMap = new LinkedHashMap<String,String>();

    /**
     * 星級
     * key=19
     * value=五星級
     */
    public static Map<String,String> starMap = new LinkedHashMap<String,String>();

    /**
     * 星級
     * key=10
     * value=大床
     */
    public static Map<String,String> bedTypeMap = new LinkedHashMap<String,String>();


    /**
     * 币种
     * key=CNY
     * value=CNY
     */
    public static Map<String,String> currencyMap = new LinkedHashMap<String,String>();

    /**
     * 配额类型的下拉框
     * key=buyout1
     * value=包房一
     */
    public static Map<String,String> quotaTypeMap = new LinkedHashMap<String,String>();

    /**
     *
     */
    public static List<String> weekendList = new LinkedList<String>();

    /**
     * 附加费类型
     * key=bed
     * value=加早
     */
    public static Map<String,String> chargeMap = new LinkedHashMap<String,String>();

    /**
     * 渠道
     * key=bed
     * value=加早
     */
    public static Map<String,String> channleMap = new LinkedHashMap<String,String>();

    /**
     * 商家配置
     */
    public static Map<String, String> merchantMap = new LinkedHashMap<String,String>();

    /**
     * 商家渠道对接配置信息
     */
    public static Map<String,Map<String,String>> channelConfigMap = new HashMap<String,Map<String,String>>();

    /**
     * 代理通接口信息
     */
    public static Map<String, String> dltInterfaceInfoMap = new LinkedHashMap<String,String>();

    /**
     * 预留配额的时间：默认15分钟
     */
    public static Integer RESERVE_QUOTA_TIME = 15;

    /**
     * 商家编码
     * FIXME-DLT
     */
    public static String merchantCode = "SC10049";

    /**
     * 银行账号
     */
    public static Map<String,String> bankMap = new LinkedHashMap<>();

}
