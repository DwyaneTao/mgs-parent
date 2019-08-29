package com.mgs.dlt.utils;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


/**
 *  m_ 2018/6/18.
 */
public class OrderInterfaceInvoker {

    private static volatile String createOrderUrl;

    private static volatile String queryOrderUrl;

    private static volatile String addOrderRequestUrl;

    private static volatile String changeOrderStatusUrl;

    private static final Logger LOG = LoggerFactory.getLogger(OrderInterfaceInvoker.class);

    private static void initInterfaceInfo() {
//        if (StringUtils.isEmpty(createOrderUrl) || StringUtils.isEmpty(queryOrderUrl) || StringUtils.isEmpty(addOrderRequestUrl) || StringUtils.isEmpty(changeOrderStatusUrl)) {
//            synchronized (OrderInterfaceInvoker.class) {
//                if (StringUtils.isEmpty(createOrderUrl) || StringUtils.isEmpty(queryOrderUrl) || StringUtils.isEmpty(addOrderRequestUrl) || StringUtils.isEmpty(changeOrderStatusUrl)) {
//                    if (CollectionUtils.isEmpty(InitData.allList)) {
//                        DictionaryService dictionaryService = (DictionaryService) SpringContextUtil.getBean("dictionaryService");
//                        InitData.allList = dictionaryService.queryAll();
//                    }
//                    for(DictionaryPO dictionaryPO : InitData.allList) {
//                        if(dictionaryPO.getDataCode().equals("create_order_url")) {
//                            createOrderUrl = dictionaryPO.getDataValue();
//                        }else if(dictionaryPO.getDataCode().equals("add_order_request_url")) {
//                            addOrderRequestUrl = dictionaryPO.getDataValue();
//                        }else if(dictionaryPO.getDataCode().equals("query_order_url")) {
//                            queryOrderUrl = dictionaryPO.getDataValue();
//                        }else if(dictionaryPO.getDataCode().equals("change_order_status_url")) {
//                            changeOrderStatusUrl = dictionaryPO.getDataValue();
//                        }
//                    }
//                }
//            }
//        }
    }

    public static Response addManualOrder(String requestJson) {
        initInterfaceInfo();
        if(StringUtil.isValidString(createOrderUrl)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            LOG.info("url:"+createOrderUrl+";requestJson:"+requestJson);
            return restTemplate.postForObject(createOrderUrl, entity, Response.class);
        }else {
            return new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INTERFACENOTEXISTS.errorCode, ErrorCodeEnum.INTERFACENOTEXISTS.errorDesc);
        }
    }

    public static Response queryOrder(String requestJson) {
        initInterfaceInfo();
        if(StringUtil.isValidString(queryOrderUrl)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            LOG.info("url:"+queryOrderUrl+";requestJson:"+requestJson);
            return restTemplate.postForObject(queryOrderUrl,entity,Response.class);
        }else {
            return new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INTERFACENOTEXISTS.errorCode, ErrorCodeEnum.INTERFACENOTEXISTS.errorDesc);
        }
    }

    public static Response addOrderRequest(String requestJson) {
        initInterfaceInfo();
        if(StringUtil.isValidString(addOrderRequestUrl)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            LOG.info("url:"+addOrderRequestUrl+";requestJson:"+requestJson);
            return restTemplate.postForObject(addOrderRequestUrl,entity,Response.class);
        }else {
            return new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INTERFACENOTEXISTS.errorCode, ErrorCodeEnum.INTERFACENOTEXISTS.errorDesc);
        }
    }

    public static Response changeOrderStatus(String requestJson) {
        initInterfaceInfo();
        if(StringUtil.isValidString(changeOrderStatusUrl)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
            LOG.info("url:"+changeOrderStatusUrl+";requestJson:"+requestJson);
            return restTemplate.postForObject(changeOrderStatusUrl,entity,Response.class);
        }else {
            return new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INTERFACENOTEXISTS.errorCode, ErrorCodeEnum.INTERFACENOTEXISTS.errorDesc);
        }
    }
}
