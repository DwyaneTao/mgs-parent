package com.mgs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 00:04
 * @Description: 产品房型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRoomDTO implements Serializable {

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 房型床型
     */
    private String bedTypes;

    /**
     * 是否选中
     */
    private Integer selected;

    /**
     * 产品列表
     */
    private List<ProductForShowDTO> productList;
}
