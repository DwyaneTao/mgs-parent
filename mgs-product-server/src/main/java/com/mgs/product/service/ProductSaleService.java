package com.mgs.product.service;

import com.github.pagehelper.PageInfo;
import com.mgs.common.Response;
import com.mgs.product.dto.BatchSaleDTO;
import com.mgs.product.dto.ProductPriceQueryRequestDTO;
import com.mgs.product.dto.ProductSaleLogDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:20
 * @Description: 产品销售
 */
public interface ProductSaleService {

    /**
     * 查询酒店列表
     * @param queryProductRequestDTO
     * @return
     */
    Response queryHotelList(QueryProductRequestDTO queryProductRequestDTO);

    /**
     * 查询
     * @param queryProductRequestDTO
     * @return
     */
    Response querySalePriceList(QueryProductRequestDTO queryProductRequestDTO);

    /**
     * 单产品上下架
     * @param requestMap
     * @return
     */
    Response singleProductModifySaleStatus(@RequestBody Map<String,String> requestMap);

    /**
     * 单天调整售价
     * @param requestMap
     * @return
     */
    Response dailyModifySalePrice(@RequestBody Map<String,String> requestMap);

    /**
     * 批量调整售价
     * @param batchSaleDTO
     * @return
     */
    Response batchModifySalePrice(@RequestBody BatchSaleDTO batchSaleDTO);

    /**
     * 批量调整售卖状态
     * @param batchSaleDTO
     * @return
     */
    Response batchModifySaleStatus(@RequestBody BatchSaleDTO batchSaleDTO);


    /**
     * 查询产品详情供订单使用
     */
    Response queryOrderProductPrice(@RequestBody QueryProductRequestDTO queryProductRequestDTO);

    /**
     * 查询产品销售日志
     * @param requestMap
     * @return
     */
    PageInfo<ProductSaleLogDTO> queryProductSaleLogList(Map<String, String> requestMap);

    /**
     * 分解每日加幅数据
     * @param startDate
     * @param endDate
     * @return
     */
    Response resolveDayIncrease(String startDate,String endDate);
}
