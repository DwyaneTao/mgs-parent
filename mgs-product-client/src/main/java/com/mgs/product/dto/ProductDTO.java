package com.mgs.product.dto;

import com.mgs.common.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:12
 * @Description: 产品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends BaseDTO implements Serializable{

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 床型
     */
    private String bedTypes;

    /**
     * 早餐数
     */
    private Integer breakfastQty;

    /**
     * 采购方式（0自签协议房 1自签包房）
     */
    private Integer purchaseType;

    /**
     * 产品备注
     */
    private String remark;

    /**
     * 是否有效
     */
    private Integer active;

    /**
     * 配额账号Id
     */
    private Integer quotaAccountId;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 包房款Id
     */
    private Integer advancePaymentId;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * 取消条款类型（0一经预订不能取消 1可以取消）
     */
    private Integer cancellationType;

    /**
     * 取消条款提前天数
     */
    private Integer cancellationAdvanceDays;

    /**
     * 取消条款提前时间点
     */
    private String cancellationDueTime;

    /**
     * 取消条款扣费说明
     */
    private String cancellationDeductionTerm;

    /**
     * 预订天数条款类型（0大于等于 1等于 2小于等于）
     */
    private Integer comparisonType;

    /**
     * 预订天数条款天数
     */
    private Integer reservationLimitNights;

    /**
     * 预订提前时间点
     */
    private String reservationDueTime;

    /**
     * 预订间数条款间数
     */
    private Integer reservationLimitRooms;

    /**
     * 预订提前天数
     */
    private Integer reservationAdvanceDays;
}
