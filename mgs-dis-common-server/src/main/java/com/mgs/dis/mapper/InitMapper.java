package com.mgs.dis.mapper;

import com.mgs.dis.dto.ProductBasePriceAndRoomStatusDTO;
import com.mgs.dis.dto.ProductCompanyRelationDTO;
import com.mgs.dis.dto.ProductRestrictDTO;
import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;

import java.util.List;

public interface InitMapper {

    /**
     * 查询条款信息
     * @return
     */
    List<ProductRestrictDTO> queryRestrictList();

    /**
     * 查询底价和房态
     * @return
     */
    List<ProductBasePriceAndRoomStatusDTO> queryBasePriceAndRoomStatusList();

    /**
     * 查询销售状态
     * @return
     */
    List<ProductSaleStatusDTO> querySaleStatusList();

    /**
     * 查询销售价格
     * @return
     */
    List<ProductSaleIncreaseDTO> querySalePriceList();

    /**
     * 查询产品与商家关系
     * @return
     */
    List<ProductCompanyRelationDTO> queryProductCompanyRelationList();

}
