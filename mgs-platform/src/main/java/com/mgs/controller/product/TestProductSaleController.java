package com.mgs.controller.product;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.product.dto.*;
import com.mgs.product.remote.ProductSaleRemote;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 20:40
 * @Description:
 */
@RestController
@RequestMapping("/test/sale")
@Slf4j
public class TestProductSaleController {

//    @Autowired
//    private ProductSaleRemote productSaleRemote;
//
//    @PostMapping("/queryHotelList")
//    public Response queryHotelList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
//        Response response = new Response(1);
//        PaginationSupportDTO page = new PaginationSupportDTO();
//        List<HotelProductsDTO> hotelProductsDTOList = new ArrayList<HotelProductsDTO>();
//        HotelProductsDTO hotelProductsDTO = new HotelProductsDTO(1,"深圳喜马拉雅大酒店",1,new ArrayList<ProductRoomDTO>());
//        ProductRoomDTO productRoomDTO = new ProductRoomDTO(1,"高级房",1,new ArrayList<ProductForShowDTO>());
//        ProductForShowDTO productForShowDTO1 = new ProductForShowDTO(1,"限时优惠+门票",2,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10001","深圳美丽华旅行社",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        ProductForShowDTO productForShowDTO2 = new ProductForShowDTO(2,"限时优惠+门票",2,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10002","美团",0,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        ProductForShowDTO productForShowDTO3 = new ProductForShowDTO(3,"限时优惠+门票",2,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10003","深圳集散",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        productRoomDTO.getProductList().add(productForShowDTO1);
//        productRoomDTO.getProductList().add(productForShowDTO2);
//        productRoomDTO.getProductList().add(productForShowDTO3);
//        hotelProductsDTO.getRoomList().add(productRoomDTO);
//
//        ProductRoomDTO productRoomDTO1 = new ProductRoomDTO(2,"豪华房",1,new ArrayList<ProductForShowDTO>());
//        hotelProductsDTO.getRoomList().add(productRoomDTO1);
//
//        ProductRoomDTO productRoomDTO2 = new ProductRoomDTO(3,"行政房",1,new ArrayList<ProductForShowDTO>());
//        ProductForShowDTO productForShowDTO4 = new ProductForShowDTO(1,"限时优惠",2,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10001","深圳美丽华旅行社",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        ProductForShowDTO productForShowDTO5 = new ProductForShowDTO(2,"限时优惠+门票",2,"携程，飞猪店铺1，飞猪店铺2","同程艺龙",1,"2019年4月20包5000间房",0,"S10001","深圳春秋",1,1,0,"0,1",1,new ArrayList<ProductSaleItemDTO>());
//        productRoomDTO2.getProductList().add(productForShowDTO4);
//        productRoomDTO2.getProductList().add(productForShowDTO5);
//        hotelProductsDTO.getRoomList().add(productRoomDTO2);
//
//        ProductRoomDTO productRoomDTO3 = new ProductRoomDTO(4,"套房",1,new ArrayList<ProductForShowDTO>());
//        hotelProductsDTO.getRoomList().add(productRoomDTO3);
//
//        ProductRoomDTO productRoomDTO4 = new ProductRoomDTO(5,"奇妙客房",1,new ArrayList<ProductForShowDTO>());
//        hotelProductsDTO.getRoomList().add(productRoomDTO4);
//
//        ProductRoomDTO productRoomDTO5 = new ProductRoomDTO(6,"豪华套房",1,new ArrayList<ProductForShowDTO>());
//        hotelProductsDTO.getRoomList().add(productRoomDTO5);
//        hotelProductsDTOList.add(hotelProductsDTO);
//
//        hotelProductsDTOList.add(new HotelProductsDTO(2,"桔子酒店（深圳罗湖店）",1,new ArrayList<ProductRoomDTO>()));
//        hotelProductsDTOList.add(new HotelProductsDTO(3,"深圳大梅沙京基喜来登酒店",1,new ArrayList<ProductRoomDTO>()));
//        hotelProductsDTOList.add(new HotelProductsDTO(4,"深圳维也纳大酒店",1,new ArrayList<ProductRoomDTO>()));
//        hotelProductsDTOList.add(new HotelProductsDTO(5,"昆明泰丽国际酒店",1,new ArrayList<ProductRoomDTO>()));
//        hotelProductsDTOList.add(new HotelProductsDTO(6,"维也纳酒店（深圳爱榕路店）",1,new ArrayList<ProductRoomDTO>()));
//        page.setTotalPage(20);
//        page.setTotalCount(118);
//        page.setPageSize(6);
//        page.setCurrentPage(1);
//        page.setItemList(hotelProductsDTOList);
//        response.setModel(page);
//        return response;
//
//    }
//
//    @PostMapping("/querySalePriceList")
//    public Response querySalePriceList(@RequestBody QueryProductRequestDTO queryProductRequestDTO) {
//        Response response = new Response(1);
//        ProductSalePriceDTO productSalePriceDTO = new ProductSalePriceDTO(1,"深圳喜马拉雅大酒店",1,"大床房",1,"限时优惠",new ArrayList<ProductSalePriceItemDTO>());
//        Map<Date,Date> dateMap = DateUtil.getDateMap(DateUtil.stringToDate(DateUtil.getFirstDayOfThisMonth()),DateUtil.stringToDate(DateUtil.getLastDayOfThisMonth()));
//        for (Date date : dateMap.keySet()) {
//            if (DateUtil.compare(DateUtil.getDate(date,-3,0),DateUtil.getCurrentDate()) == 0) {
//                productSalePriceDTO.getPriceList().add(new ProductSalePriceItemDTO(DateUtil.dateToString(date),null,null,null,20,10,1,1));
//            }else {
//                productSalePriceDTO.getPriceList().add(new ProductSalePriceItemDTO(DateUtil.dateToString(date),new BigDecimal("100"),new BigDecimal("110"),new BigDecimal("10"),20,10,1,1));
//            }
//        }
//        response.setModel(productSalePriceDTO);
//        return response;
//    }
//
//    @PostMapping("/singleProductModifySaleStatus")
//    public Response singleProductModifySaleStatus(@RequestBody Map<String,String> requestMap) {
//        Response response = new Response(1);
//        return response;
//    }
//
//    @PostMapping("/dailyModifySalePrice")
//    public Response dailyModifySalePrice(@RequestBody Map<String,String> requestMap) {
//        Response response = new Response(1);
//        return response;
//    }
//
//    @PostMapping("/batchModifySalePrice")
//    public Response batchModifySalePrice(@RequestBody List<BatchSaleItemDTO> batchSaleItemDTOList) {
//        Response response = new Response(1);
//        return response;
//    }
//
//    @PostMapping("/batchModifySaleStatus")
//    public Response batchModifySaleStatus(@RequestBody Map<Object,Object> requestMap) {
//        Response response = new Response(1);
//        return response;
//    }
}

