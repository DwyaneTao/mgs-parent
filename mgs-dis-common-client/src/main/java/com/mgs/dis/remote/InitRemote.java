package com.mgs.dis.remote;

import com.mgs.common.Response;
import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mgs-dis-common-server")
public interface InitRemote {

    /**
     * 初始化价格
     * @return
     */
    @RequestMapping("/init/initBasePriceAndRoomStatus")
    Response initBasePriceAndRoomStatus(@RequestBody(required = false) List<ProductDayQuotationDTO> productDayQuotationDTOList);

    /**
     * 初始化售价信息
     * @return
     */
    @RequestMapping("/init/initSalePrice")
    Response initSalePrice(@RequestBody(required = false) List<ProductSaleIncreaseDTO> productSaleIncreaseList);

    /**
     * 初始化上下架信息
     * @return
     */
    @GetMapping("/init/initSaleStatus")
    Response initSaleStatus(@RequestBody(required = false) List<ProductSaleStatusDTO> productSaleStatusPOList);

    /**
     * 初始化条款
     * @return
     */
    @RequestMapping("/init/initRestrict")
    Response initRestrict(@RequestBody(required = false) ProductDTO productDTO);

    /**
     * 初始化产品商家关系
     * @return
     */
    @RequestMapping("/init/initProductCompanyRelation")
    Response initProductCompanyRelation();


}
