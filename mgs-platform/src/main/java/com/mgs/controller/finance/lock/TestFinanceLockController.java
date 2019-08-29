package com.mgs.controller.finance.lock;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.response.OrderFinanceLockDTO;
import com.mgs.finance.remote.lock.response.SupplyOrderFinanceLockDTO;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/test/finance/lock")
public class TestFinanceLockController {

    /**
     * 财务订单锁查询
     */
    @RequestMapping(value = "/queryOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderList(@RequestBody QueryOrderFinanceLockListDTO request) {
        List<OrderFinanceLockDTO> orderFinanceLockDTOList=new ArrayList<>();
        for (int i=0;i<15;){
            orderFinanceLockDTOList.add(new OrderFinanceLockDTO(
                    i,
                    "H1805240000"+i,
                    new Date(),
                    "深圳环球旅行",
                    "深圳喜马拉雅大酒店",
                    "大床房",
                    DateUtil.stringToDate("2019-06-06"),
                    DateUtil.stringToDate("2019-06-07"),
                    "李雷、韩梅梅",
                    2,
                    (i+1)%2
            ));
            orderFinanceLockDTOList.add(new OrderFinanceLockDTO(
                    i,
                    "H1805240000"+i,
                    new Date(),
                    "深圳新天地假期",
                    "桔子酒店·精选（深圳罗湖店）",
                    "大床房",
                    DateUtil.stringToDate("2019-06-20"),
                    DateUtil.stringToDate("2019-06-21"),
                    "王大明",
                    1,
                    (i+1)%2
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(orderFinanceLockDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 财务订单加锁
     */
    @RequestMapping(value = "/lockOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response lockOrder(@RequestBody FinanceLockOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 财务供货单锁查询
     */
    @RequestMapping(value = "/querySupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderList(@RequestBody QuerySupplyOrderFinanceLockListDTO request) {
        List<SupplyOrderFinanceLockDTO> supplyOrderFinanceLockDTOList=new ArrayList<>();
        for (int i=0;i<15;){
            supplyOrderFinanceLockDTOList.add(new SupplyOrderFinanceLockDTO(
                    i,
                    "S1805240000"+i,
                    new Date(),
                    "深圳环球旅行",
                    "深圳喜马拉雅大酒店",
                    "大床房",
                    DateUtil.stringToDate("2019-06-06"),
                    DateUtil.stringToDate("2019-06-07"),
                    "李雷、韩梅梅",
                    2,
                    (i+1)%2
            ));
            supplyOrderFinanceLockDTOList.add(new SupplyOrderFinanceLockDTO(
                    i,
                    "S1805240000"+i,
                    new Date(),
                    "深圳新天地假期",
                    "桔子酒店·精选（深圳罗湖店）",
                    "大床房",
                    DateUtil.stringToDate("2019-06-20"),
                    DateUtil.stringToDate("2019-06-21"),
                    "王大明",
                    1,
                    (i+1)%2
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(supplyOrderFinanceLockDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 财务订单加锁
     */
    @RequestMapping(value = "/lockSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response lockSupplyOrder(@RequestBody FinanceLockSupplyOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
