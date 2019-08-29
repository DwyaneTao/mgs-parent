package com.mgs.dis.server;


import com.mgs.common.Response;
import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;
import com.mgs.dis.service.InitService;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 初始化redis数据
 */
@Slf4j
@RestController
@RequestMapping("/init")
public class InitServer {

    @Autowired
    private InitService initService;

    /**
     * 初始化价格
     * @return
     */
    @RequestMapping("/initBasePriceAndRoomStatus")
    public Response initBasePriceAndRoomStatus(@RequestBody(required = false) List<ProductDayQuotationDTO> productDayQuotationDTOList){
        Response response = new Response();
        try{
            int i = initService.initBasePriceAndRoomStatus(productDayQuotationDTOList);
            response.setResult(1);
            response.setModel("成功初始化价格数：" + i);
        }catch (Exception e){
            response.setResult(0);
            response.setFailReason("初始化价格失败，系统异常");
        }
        return response;
    }



    /**
     * 初始化售价信息
     * @return
     */
    @RequestMapping("/initSalePrice")
    public Response initSalePrice(@RequestBody(required = false) List<ProductSaleIncreaseDTO> productSaleIncreaseList){
        Response response = new Response();
        try{
            int i = initService.initSalePrice(productSaleIncreaseList);
            response.setResult(1);
            response.setModel("成功初始化售价信息数：" + i);
        }catch (Exception e){
            response.setResult(0);
            response.setFailReason("初始化售价信息失败，系统异常");
        }
        return response;
    }

    /**
     * 初始化上下架信息
     * @return
     */
    @RequestMapping("/initSaleStatus")
    public Response initSaleStatus(@RequestBody(required = false) List<ProductSaleStatusDTO> productSaleStatusList){
        Response response = new Response();
        try{
            int i =initService.initSaleStatus(productSaleStatusList);
            response.setResult(1);
            response.setModel("成功初始化上下架信息数：" + i);
        }catch (Exception e){
            response.setResult(0);
            response.setFailReason("初始化上下架信息失败，系统异常");
        }
        return response;
    }

    /**
     * 初始化条款
     * @return
     */
    @RequestMapping("/initRestrict")
    public Response initRestrict(@RequestBody ProductDTO product){
        Response response = new Response();
        try{
            int i = initService.initRestrict(product);
            response.setResult(1);
            response.setModel("成功初始化条款数：" + i);
        }catch (Exception e) {
            response.setResult(0);
            response.setFailReason("初始化条款失败，系统异常");
        }
        return response;
    }

    /**
     * 初始化产品商家关系
     * @return
     */
    @RequestMapping("/initProductCompanyRelation")
    public Response initProductCompanyRelation(){
        Response response = new Response();
        try{
            int i = initService.initProductCompanyRelation();
            response.setResult(1);
            response.setModel("成功初始化产品商家关系数：" + i);
        }catch (Exception e) {
            response.setResult(0);
            response.setFailReason("初始化产品商家关系失败，系统异常");
        }
        return response;
    }



}
