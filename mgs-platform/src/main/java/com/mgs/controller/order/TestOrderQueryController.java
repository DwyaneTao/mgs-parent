package com.mgs.controller.order;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.response.PriceResponseDTO;
import com.mgs.order.remote.request.OrderCodeDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.request.QueryConfirmOrderInfoDTO;
import com.mgs.order.remote.request.QueryOrderListDTO;
import com.mgs.order.remote.request.QueryOrderRemarkDTO;
import com.mgs.order.remote.request.SupplyOrderIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.ChannelOrderQtyDTO;
import com.mgs.order.remote.response.OrderAttachmentDTO;
import com.mgs.order.remote.response.OrderDTO;
import com.mgs.order.remote.response.OrderLogDTO;
import com.mgs.order.remote.response.OrderRemarkDTO;
import com.mgs.order.remote.response.OrderRequestDTO;
import com.mgs.order.remote.response.OrderSimpleDTO;
import com.mgs.order.remote.response.OrderStatisticsDTO;
import com.mgs.order.remote.response.SupplyAttachmentDTO;
import com.mgs.order.remote.response.SupplyGuestDTO;
import com.mgs.order.remote.response.SupplyOrderDTO;
import com.mgs.order.remote.response.SupplyOrderPreviewDTO;
import com.mgs.order.remote.response.SupplyProductDTO;
import com.mgs.order.remote.response.SupplyProductDetailDTO;
import com.mgs.order.remote.response.SupplyProductPreviewDTO;
import com.mgs.order.remote.response.SupplyProductPriceDTO;
import com.mgs.order.remote.response.SupplyResultDTO;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/test/order")
public class TestOrderQueryController {

    @RequestMapping(value = "/queryOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderList(@RequestBody QueryOrderListDTO request) {
        List<OrderSimpleDTO> orderSimpleDTOList=new ArrayList<>();
        for (int i=0;i<35;i++){
            OrderSimpleDTO orderSimpleDTO=new OrderSimpleDTO(
                    i,
                    "H20190426111"+i,
                    DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"),
                    "B2B",
                    "aa旅行社",
                    "深圳喜马拉雅大酒店",
                    "大床房",
                    "标准",
                    "2019-05-01",
                    "2019-05-02",
                    "李雷、韩梅梅",
                    2,
                    BigDecimal.valueOf(288l),
                    0,
                    0,
                    1,
                    1,
                    "李雷",
                    Arrays.asList("今"),
                    "4663333",
                    "李雷",
                    i%2,
                    0,
                    BigDecimal.valueOf(0)
                    );
            orderSimpleDTOList.add(orderSimpleDTO);
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(35);
        paginationSupportDTO.setTotalPage(4);
        paginationSupportDTO.setItemList(orderSimpleDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    @RequestMapping(value = "/queryOrderStatistics",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderStatistics() {
        OrderStatisticsDTO orderStatisticsDTO=new OrderStatisticsDTO(
                19,
                15,
                5,
                5,
                5,
                5,
                12,
                34,
                Arrays.asList(new ChannelOrderQtyDTO("B2B",302,0),
                        new ChannelOrderQtyDTO("B2C",20,0),
                        new ChannelOrderQtyDTO("CTRIP",45,0),
                        new ChannelOrderQtyDTO("QUNAR",33,0),
                        new ChannelOrderQtyDTO("ELONG",325,0))
        );
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderStatisticsDTO);
        return response;
    }

    @RequestMapping(value = "/queryOrderDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderDetail(@RequestBody OrderCodeDTO request) {
        /*OrderDTO orderDTO=new OrderDTO(
                0,
                1,
                "H18052400001",
                0,
                111,
                "麗枫酒店（清远金碧湾店）",
                "标准大床房",
                "优惠大酬宾订房就送欢乐谷门票",
                "2019-05-01",
                "2019-05-02",
                1,
                2,
                "李雷、韩梅梅、王大明",
                "尽量无烟房",
                "李雷",
                DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"),
                "4578985、4568753、5784521、94648646",
                "B2B",
                "深圳环球旅行",
                "李雷",
                "13245677894",
                "x103411378",
                BigDecimal.valueOf(1800l),
                BigDecimal.valueOf(1900l),
                BigDecimal.valueOf(100l),
                0,
                0,
                0,
                BigDecimal.valueOf(900l),
                BigDecimal.valueOf(900l),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0,
                0,
                BigDecimal.valueOf(0l),
                BigDecimal.valueOf(0l),
                BigDecimal.valueOf(0l),
                BigDecimal.valueOf(0l),
                "李雷",
                1,
                "李雷",
                DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"),
                1
                       );
        for (int i=0;i<5;i++){
            orderDTO.getSalePriceList().add(new PriceResponseDTO(
                    "2019-05-0"+i,
                    BigDecimal.valueOf(400l),
                    null
            ));
        }
        for (int i=0;i<5;i++){
            orderDTO.getOrderAttachmentList().add(new OrderAttachmentDTO(
                    1,
                    "ConfirmationStatusEnum"+i,
                    "http://www.aa.com/ConfirmationStatusEnum"+i+".jpg"
            ));
        }
        SupplyOrderDTO supplyOrderDTO=new SupplyOrderDTO(
                0,
                1,
                "S18052400001",
                0,
                0,
                "港澳国旅",
                BigDecimal.valueOf(100l),
                BigDecimal.valueOf(100l),
                BigDecimal.valueOf(0l),
                1,
                new ArrayList<>(),
                0,
                0,
                new ArrayList<>(),
                BigDecimal.valueOf(0l),
                BigDecimal.valueOf(0l),
                BigDecimal.valueOf(0l),
                BigDecimal.valueOf(0l),
                "x103411378"
        );
        for (int i=0;i<5;i++){
            supplyOrderDTO.getBasePriceList().add(new PriceResponseDTO(
                    "2019-05-0"+i,
                    null,
                    BigDecimal.valueOf(300l)
            ));
        }
        supplyOrderDTO.getProductList().add(new SupplyProductDTO(
                1,
                "大床房",
                1,
                1,
                "优惠大酬宾订房就送欢乐谷门票",
                "2019-05-01",
                "2019-05-02",
                1,
                "李明",
                BigDecimal.valueOf(400l)
        ));
        supplyOrderDTO.getProductList().add(new SupplyProductDTO(
                1,
                "大床房",
                1,
                1,
                "限量特惠",
                "2019-05-01",
                "2019-05-02",
                1,
                "李明",
                BigDecimal.valueOf(300l)
        ));
        orderDTO.getSupplyOrderList().add(supplyOrderDTO);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderDTO);*/
        Response response = new Response();
        return response;
    }

    @RequestMapping(value = "/queryOrderRemark",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderRemark(@RequestBody QueryOrderRemarkDTO request) {
        List<OrderRemarkDTO> orderRemarkDTOList=new ArrayList<>();
        for (int i=0;i<2;i++){
            orderRemarkDTOList.add(new OrderRemarkDTO(
                    "此单已确认，保证入住全段，不可更改或取消，NO SHOW房费照付，谢谢。",
                    "客人",
                    "李雷",
                    DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")
            ));
            orderRemarkDTOList.add(new OrderRemarkDTO(
                    "我不想改订单了",
                    "商家",
                    "李雷",
                    DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")
            ));
        }
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderRemarkDTOList);
        return response;
    }

    @RequestMapping(value = "/queryOrderLog",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderLog(@RequestBody OrderIdDTO request) {
        List<OrderLogDTO> orderLogDTOList=new ArrayList<>();
        for (int i=0;i<5;i++){
            orderLogDTOList.add(new OrderLogDTO(
                    "修改了入住 人，将小明改成小黄",
                    "H18052400001",
                    "李雷",
                    DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")
            ));
            orderLogDTOList.add(new OrderLogDTO(
                    "修改了售价，从150改成160",
                    "S18052400002",
                    "李雷",
                    DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss")
            ));
        }
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderLogDTOList);
        return response;
    }

    @RequestMapping(value = "/queryOrderRequest",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderRequest(@RequestBody OrderIdDTO request) {
        List<OrderRequestDTO> orderRequestDTOList=new ArrayList<>();
        orderRequestDTOList.add(new OrderRequestDTO(
                1,
                1,
                "",
                0,
                "李雷",
                DateUtil.dateToString(new Date()),
                null,
                null,
                "深圳新天地假期"
        ));
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderRequestDTOList);
        return response;
    }

    @RequestMapping(value = "/queryConfirmOrderInfo",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryConfirmOrderInfo(@RequestBody QueryConfirmOrderInfoDTO request) {
        String content="您的订单号：H18052400003，2017-04-18入住,2017-04-19离店的广州长隆酒店大酒店的1间标准房，已为您预订成功！真诚祝您入住愉快！酒店地址：广州市番禺区迎宾路；酒店电话：020-84786838。";
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(content);
        return response;
    }

    @RequestMapping(value = "/queryOrderPriceItem",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderPriceItem(@RequestBody OrderIdDTO request) {
        List<PriceResponseDTO> priceDTOList=new ArrayList<>();
        for (int i=0;i<3;i++){
            priceDTOList.add(new PriceResponseDTO(
                    "2019-05-0"+(i+1),
                    BigDecimal.valueOf(400l),
                    null
            ));
            priceDTOList.add(new PriceResponseDTO(
                   "2019-05-0"+(i+2),
                    BigDecimal.valueOf(500l),
                    null
            ));
        }
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(priceDTOList);
        return response;
    }

    @RequestMapping(value = "/previewSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response previewSupplyOrder(@RequestBody SupplyOrderIdDTO request) {
        Response response = new Response();
       /*SupplyOrderPreviewDTO supplyOrderPreviewDTO=new SupplyOrderPreviewDTO(
                1,
                "S18052400001",
                0,
                0,
                "港澳国旅",
                "麗枫酒店（清远金碧湾店）",
                "阳区东四环十里堡北里28号，近朝阳北路农民日报社往南",
                "0737-1548-5789",
                "李雷、韩梅梅、王大明",
                "尽量无烟房",
                BigDecimal.valueOf(2456l),
                0,
                new ArrayList<>()
        );
        supplyOrderPreviewDTO.getProductList().add(new SupplyProductPreviewDTO(
                "大床房",
                "优惠大酬宾订房就送欢乐谷门票",
                "2019-05-01",
                "2019-05-02",
                1,
                1,
                BigDecimal.valueOf(400l),
                BigDecimal.valueOf(400l)
        ));
        supplyOrderPreviewDTO.getProductList().add(new SupplyProductPreviewDTO(
                "大床房",
                "限量特惠",
                "2019-05-01",
                "2019-05-02",
                1,
                1,
                BigDecimal.valueOf(300l),
                BigDecimal.valueOf(300l)
        ));
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplyOrderPreviewDTO);*/
        return response;
    }

    @RequestMapping(value = "/querySupplyOrderResult",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderResult(@RequestBody SupplyOrderIdDTO request) {
        SupplyResultDTO supplyOrderResultDTO=new SupplyResultDTO(
                1,
                0,
                "李明",
                "5664898777",
                "",
                "aaaa",
                BigDecimal.valueOf(0l),
                "李雷",
                DateUtil.dateToString(new Date()),
                new ArrayList<>()
        );
        for (int i=0;i<5;i++){
            supplyOrderResultDTO.getSupplyAttachmentList().add(new SupplyAttachmentDTO(
                    1,
                    "ConfirmationStatusEnum"+i,
                    "http://www.aa.com/ConfirmationStatusEnum"+i+".jpg"
            ));
        }
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplyOrderResultDTO);
        return response;
    }

    @RequestMapping(value = "/querySupplyProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderProduct(@RequestBody SupplyProductIdDTO request) {
        SupplyProductDetailDTO supplyProductDetailDTO=new SupplyProductDetailDTO(
                1,
                "大床房",
                "优惠大酬宾订房就送欢乐谷门票",
                "港澳国旅",
                0,
                2,
                0,
                "2019-05-01",
                "2019-05-02",
                1,
                new ArrayList<>(),
                BigDecimal.valueOf(600l),
                1,
                BigDecimal.valueOf(500l),
                new ArrayList<>()
        );
        supplyProductDetailDTO.getGuestList().add(new SupplyGuestDTO(
                "李雷",
                1
        ));
        supplyProductDetailDTO.getGuestList().add(new SupplyGuestDTO(
                "韩梅梅",
                0
        ));
        supplyProductDetailDTO.getGuestList().add(new SupplyGuestDTO(
                "王明",
                0
        ));
        for (int i=0;i<3;i++){
            supplyProductDetailDTO.getPriceList().add(new SupplyProductPriceDTO(
                    "2019-05-0"+(i+1),
                    BigDecimal.valueOf(400l),
                    1,
                    5
            ));
            supplyProductDetailDTO.getPriceList().add(new SupplyProductPriceDTO(
                    "2019-05-0"+(i+1),
                    BigDecimal.valueOf(400l),
                    1,
                    10
            ));
        }
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplyProductDetailDTO);
        return response;
    }
}
