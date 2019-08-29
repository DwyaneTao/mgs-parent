package com.mgs.fuzzyquery.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:44
 * @Description:
 */
@Data
public class FuzzyProductDTO implements Serializable {

    private Integer hotelId;

    private Integer roomId;

    private Integer productId;

    private String productName;
}
