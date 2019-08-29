package com.mgs.controller.product;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.product.dto.*;
import com.mgs.product.remote.ProductRemote;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 20:40
 * @Description:
 */
@RestController
@RequestMapping("/test/product")
@Slf4j
public class TestProductController {

//    @Autowired
//    private ProductRemote productRemote;
//
//    @PostMapping("/queryHotelList")
//    public Response queryProductList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
//        Response response = new Response(1);
//        PaginationSupportDTO page = new PaginationSupportDTO();
//        List<ProductHotelDTO> productHotelDTOList = new ArrayList<ProductHotelDTO>();
//        ProductHotelDTO productHotelDTO1 = new ProductHotelDTO();
//        productHotelDTO1.setHotelId(1);
//        productHotelDTO1.setHotelName("深圳喜马拉雅大酒店");
//        productHotelDTOList.add(productHotelDTO1);
//
//        ProductHotelDTO productHotelDTO2 = new ProductHotelDTO();
//        productHotelDTO2.setHotelId(2);
//        productHotelDTO2.setHotelName("桔子酒店·精选（深圳罗湖店）");
//        productHotelDTOList.add(productHotelDTO2);
//
//        ProductHotelDTO productHotelDTO3 = new ProductHotelDTO();
//        productHotelDTO3.setHotelId(3);
//        productHotelDTO3.setHotelName("柏高颂酒店（深圳东门步行街地铁…");
//        productHotelDTOList.add(productHotelDTO3);
//
//        ProductHotelDTO productHotelDTO4 = new ProductHotelDTO();
//        productHotelDTO4.setHotelId(4);
//        productHotelDTO4.setHotelName("深圳大梅沙京基喜来登酒店");
//        productHotelDTOList.add(productHotelDTO4);
//
//        ProductHotelDTO productHotelDTO5 = new ProductHotelDTO();
//        productHotelDTO5.setHotelId(5);
//        productHotelDTO5.setHotelName("深圳途家斯维登度假公寓（东部华…");
//        productHotelDTOList.add(productHotelDTO5);
//
//        ProductHotelDTO productHotelDTO6 = new ProductHotelDTO();
//        productHotelDTO6.setHotelId(6);
//        productHotelDTO6.setHotelName("深圳大梅沙京基喜来登酒店");
//        productHotelDTOList.add(productHotelDTO6);
//
//        ProductHotelDTO productHotelDTO7 = new ProductHotelDTO();
//        productHotelDTO7.setHotelId(7);
//        productHotelDTO7.setHotelName("曼谷寓米酒店公寓素拉翁分店…");
//        productHotelDTOList.add(productHotelDTO7);
//
//        ProductHotelDTO productHotelDTO8 = new ProductHotelDTO();
//        productHotelDTO8.setHotelId(1);
//        productHotelDTO8.setHotelName("维也纳酒店（深圳爱榕路店）");
//        productHotelDTOList.add(productHotelDTO8);
//
//        ProductHotelDTO productHotelDTO9 = new ProductHotelDTO();
//        productHotelDTO9.setHotelId(1);
//        productHotelDTO9.setHotelName("维也纳酒店（深圳爱榕路店）");
//        productHotelDTOList.add(productHotelDTO9);
//
//        ProductHotelDTO productHotelDTO10 = new ProductHotelDTO();
//        productHotelDTO10.setHotelId(10);
//        productHotelDTO10.setHotelName("北京东方君悦大酒店");
//        productHotelDTOList.add(productHotelDTO10);
//
//        ProductHotelDTO productHotelDTO11 = new ProductHotelDTO();
//        productHotelDTO11.setHotelId(11);
//        productHotelDTO11.setHotelName("昆明泰丽国际酒店");
//        productHotelDTOList.add(productHotelDTO11);
//
//        ProductHotelDTO productHotelDTO12 = new ProductHotelDTO();
//        productHotelDTO12.setHotelId(12);
//        productHotelDTO12.setHotelName("深圳途家斯维登度假公寓（东部华…");
//        productHotelDTOList.add(productHotelDTO12);
//
//        ProductHotelDTO productHotelDTO13 = new ProductHotelDTO();
//        productHotelDTO13.setHotelId(13);
//        productHotelDTO13.setHotelName("深圳喜马拉雅大酒店");
//        productHotelDTOList.add(productHotelDTO13);
//
//        ProductHotelDTO productHotelDTO14 = new ProductHotelDTO();
//        productHotelDTO14.setHotelId(14);
//        productHotelDTO14.setHotelName("新加坡香格里拉东陵今旅酒店…");
//        productHotelDTOList.add(productHotelDTO14);
//
//        ProductHotelDTO productHotelDTO15 = new ProductHotelDTO();
//        productHotelDTO15.setHotelId(15);
//        productHotelDTO15.setHotelName("昆明泰丽国际酒店");
//        productHotelDTOList.add(productHotelDTO15);
//
//        ProductHotelDTO productHotelDTO16 = new ProductHotelDTO();
//        productHotelDTO16.setHotelId(16);
//        productHotelDTO16.setHotelName("深圳维也纳大酒店");
//        productHotelDTOList.add(productHotelDTO16);
//
//        page.setItemList(productHotelDTOList);
//        page.setCurrentPage(1);
//        page.setPageSize(16);
//        page.setTotalCount(33);
//        page.setTotalPage(3);
//        response.setModel(page);
//
//        return response;
//    }
//
//    @PostMapping("/queryHotelProducts")
//    public Response queryHotelProducts(@RequestBody QueryProductRequestDTO queryProductRequestDTO){
//        Response response = new Response(1);
//        HotelProductsDTO hotelProductsDTO = new HotelProductsDTO(1,"深圳喜马拉雅大酒店",1,new ArrayList<ProductRoomDTO>());
//        ProductRoomDTO roomDTO = new ProductRoomDTO(1,"标准大床房",1,new ArrayList<ProductForShowDTO>());
//        ProductForShowDTO productForShowDTO = new ProductForShowDTO(1,"限时优惠+门票",2,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10001","深圳美丽华旅行社",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        for (int i = 0; i < 7; i++) {
//            if (i == 0) {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),null,null,null,null,null);
//                productForShowDTO.getSaleItemList().add(productSaleItemDTO);
//            }else {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)), BigDecimal.TEN,20,10,1,1);
//                productForShowDTO.getSaleItemList().add(productSaleItemDTO);
//            }
//        }
//        roomDTO.getProductList().add(productForShowDTO);
//
//        ProductForShowDTO productForShowDTO1 = new ProductForShowDTO(2,"限时优惠",1,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",2,"2019年4月20包5000间房",0,"S10001","深圳美丽华旅行社",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        for (int i = 0; i < 7; i++) {
//            if (i == 2) {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),null,null,null,null,null);
//                productForShowDTO1.getSaleItemList().add(productSaleItemDTO);
//            }else {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),BigDecimal.TEN,20,10,1,0);
//                productForShowDTO1.getSaleItemList().add(productSaleItemDTO);
//            }
//        }
//        roomDTO.getProductList().add(productForShowDTO1);
//
//        ProductForShowDTO productForShowDTO2 = new ProductForShowDTO(3,"免费双早+游泳池",1,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",3,"2019年4月20包5000间房",0,"S10001","深圳美丽华旅行社",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        for (int i = 0; i < 7; i++) {
//            if (i == 3) {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),null,null,null,null,null);
//                productForShowDTO2.getSaleItemList().add(productSaleItemDTO);
//            }else {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),BigDecimal.TEN,20,10,1,0);
//                productForShowDTO2.getSaleItemList().add(productSaleItemDTO);
//            }
//        }
//        roomDTO.getProductList().add(productForShowDTO2);
//        hotelProductsDTO.getRoomList().add(roomDTO);
//
//        ProductRoomDTO roomDTO1 = new ProductRoomDTO(2,"特级豪华单人客房",1,new ArrayList<ProductForShowDTO>());
//        ProductForShowDTO productForShowDTO4 = new ProductForShowDTO(4,"限时优惠",0,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10002","美团",1,1,2,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        for (int i = 0; i < 7; i++) {
//            if (i == 1) {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),BigDecimal.TEN,20,10,0,1);
//                productForShowDTO4.getSaleItemList().add(productSaleItemDTO);
//            }else {
//                ProductSaleItemDTO productSaleItemDTO = new ProductSaleItemDTO(DateUtil.dateToString(DateUtil.getDate(DateUtil.getCurrentDate(),i,0)),BigDecimal.TEN,20,10,1,1);
//                productForShowDTO4.getSaleItemList().add(productSaleItemDTO);
//            }
//        }
//        roomDTO1.getProductList().add(productForShowDTO4);
//        hotelProductsDTO.getRoomList().add(roomDTO1);
//
//        ProductRoomDTO roomDTO3 = new ProductRoomDTO(3,"特级豪华双人人客房",1,new ArrayList<ProductForShowDTO>());
//        hotelProductsDTO.getRoomList().add(roomDTO3);
//
//        response.setModel(hotelProductsDTO);
//
//        return response;
//    }
//
//    @PostMapping("/addProduct")
//    public Response addProduct(@RequestBody ProductDTO productDTO){
//        Response response = new Response(1);
//        response.setModel(1);
//        return response;
//    }
//
//    @PostMapping("/queryProduct")
//    public Response queryProduct(@RequestBody Map<String,String> requestMap){
//        Response response = new Response(1);
//        ProductDTO productDTO = new ProductDTO(1,"限时优惠","S10001","深圳环球旅行",1,"深圳喜马拉雅大酒店",1,"豪华房","0,1",1,1,"产品备注",1,null,0,1,"F001",1,3,"18:00","扣费20%",0,3,"18:00",3,2);
//        response.setModel(productDTO);
//        return response;
//    }
//
//    @PostMapping("/modifyProduct")
//    public Response modifyProduct(@RequestBody ProductDTO productDTO){
//        Response response = new Response(1);
//        response.setModel(1);
//        return response;
//    }
//
//    @PostMapping("/deleteProduct")
//    public Response deleteProduct(@RequestBody Map<String,String> requestMap){
//        Response response = new Response(1);
//        response.setModel(1);
//        return response;
//    }
//
//    @PostMapping("/dailyModifyProductInfo")
//    public Response dailyModifyProductInfo(@RequestBody ProductDayQuotationDTO productDayQuotationDTO){
//        Response response = new Response(1);
//        response.setModel(1);
//        return response;
//    }
//
//    @PostMapping("/batchModityPrice")
//    public Response batchModityPrice(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList){
//        Response response = new Response(1);
//        response.setModel(1);
//        return response;
//    }
//
//    @PostMapping("/batchModifyRoomStatus")
//    public Response batchModifyRoomStatus(@RequestBody List<BatchQuotationDTO> batchQuotationDTOList){
//        Response response = new Response(1);
//        response.setModel(1);
//        return response;
//    }
}

