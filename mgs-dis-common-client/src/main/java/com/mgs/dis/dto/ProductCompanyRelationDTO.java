package com.mgs.dis.dto;

import lombok.Data;

import java.util.List;

/**
 * 产品商家关系DTO
 */
@Data
public class ProductCompanyRelationDTO {

    /**
     * 产品id
     */
    private String productId;

    /**
     * 产品对应的商家信息
     */
    List<CompanySaleStatusDTO> companySaleStatusList;


}
