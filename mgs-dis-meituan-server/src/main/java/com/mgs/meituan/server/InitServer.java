package com.mgs.meituan.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mgs.common.Response;
import com.mgs.meituan.dto.product.request.ProductInfoDTO;
import com.mgs.meituan.dto.product.request.EntireProductSyncDTO;
import com.mgs.meituan.service.InitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class InitServer {

    @Autowired
    private InitService initService;

    @PostMapping("/init/initCityMapping")
    @ResponseBody
    public String initCityMapping(){
        Response response = new Response();
        try {
            initService.initCityMapping();
        }catch (Exception e){
            log.error(e.getMessage());
            return "ERROR";
        }
        return "SUCCESS";
    }

    public static void main(String[] args) {
        String xx = "{\n" +
                "  \"activeCode\": \"partnerActiveCode\",\n" +
                "  \"needChangeRules\": \"1\",\n" +
                "  \"belongPartnerId\": null,\n" +
                "  \"detail\": {\n" +
                "    \"106\": [\n" +
                "      {\n" +
                "        \"roomName\": \"标准大床房\",\n" +
                "        \"roomType\": \"106_7788118\",\n" +
                "        \"breakfastNum\": 0,\n" +
                "        \"inventoryPrice\": [\n" +
                "          {\n" +
                "            \"basePrice\": 15800,\n" +
                "            \"marketPrice\": 26600,\n" +
                "            \"roomDate\": \"2016-08-18\",\n" +
                "            \"roomNum\": 25\n" +
                "          },\n" +
                "          {\n" +
                "            \"basePrice\": 7000,\n" +
                "            \"marketPrice\": 11200,\n" +
                "            \"roomDate\": \"2016-08-19\",\n" +
                "            \"roomNum\": 22\n" +
                "          }\n" +
                "        ],\n" +
                "        \"rule\": [\n" +
                "          {\n" +
                "            \"allowCancel\": 1,\n" +
                "            \"earliestBookingDays\": 0,\n" +
                "            \"latestBookingDays\": 0,\n" +
                "            \"latestBookingHours\": 17,\n" +
                "            \"moveupCancelDays\": 4,\n" +
                "            \"moveupCancelHour\": 16,\n" +
                "            \"roomCountMax\": 10,\n" +
                "            \"roomCountMin\": 0,\n" +
                "            \"ruleEndDate\": \"2016-09-16\",\n" +
                "            \"ruleStartDate\": \"2016-08-17\",\n" +
                "            \"serialCheckinMax\": 0,\n" +
                "            \"serialCheckinMin\": 0\n" +
                "          },\n" +
                "          {\n" +
                "            \"allowCancel\": 0,\n" +
                "            \"earliestBookingDays\": 0,\n" +
                "            \"latestBookingDays\": 0,\n" +
                "            \"latestBookingHours\": 10,\n" +
                "            \"moveupCancelDays\": 0,\n" +
                "            \"moveupCancelHour\": 14,\n" +
                "            \"roomCountMax\": 2,\n" +
                "            \"roomCountMin\": 0,\n" +
                "            \"ruleEndDate\": \"2016-10-02\",\n" +
                "            \"ruleStartDate\": \"2016-09-17\",\n" +
                "            \"serialCheckinMax\": 3,\n" +
                "            \"serialCheckinMin\": 0\n" +
                "          }\n" +
                "        ],\n" +
                "        \"gifts\": [\n" +
                "          {\n" +
                "            \"serviceName\": \"亲子娱乐礼包\",\n" +
                "            \"itemType\": 3,\n" +
                "            \"itemNum\": 0,\n" +
                "            \"itemUnit\": \"份\",\n" +
                "            \"servicePrice\": 10000,\n" +
                "            \"ordinaryValue\": 20000,\n" +
                "            \"weekendValue\": 30000,\n" +
                "            \"specialValue\": 40000,\n" +
                "            \"remark\": \"含100元万象水上乐园门票2张+2位58元的游泳池门票+2位28元的健身房门票\",\n" +
                "            \"availableDate\": [\n" +
                "              {\n" +
                "                \"startDate\": \"2016-08-17\",\n" +
                "                \"endDate\": \"2016-09-16\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"availableDateType\": 2,\n" +
                "            \"serviceStatus\": 1\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        EntireProductSyncDTO productSyncDTO = JSON.parseObject(xx, EntireProductSyncDTO.class);
        JSONObject object = productSyncDTO.getDetail();
        List<ProductInfoDTO> productDetails = JSONArray.parseArray(JSON.toJSONString(object.get("106")), ProductInfoDTO.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("107", productDetails);
        jsonObject.put("106", productDetails);
        productSyncDTO.setDetail(jsonObject);

        System.out.println(JSON.toJSONString(productSyncDTO));

    }
}
