package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 18:15
 * @Description:
 */
@Data
@Table(name = "t_dis_dlt_order_detail")
public class DltOrderDetailPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代理通订单Id
     */
    private String dltOrderId;

    private String orderId;

    private String lastDltOrderId;

    private String nextDltOrderId;

    private String channel;

    private String childChannel;

    private Date updateTime;

    private Date orderDate;

    private String orderCurrency;

    private BigDecimal orderPrice;

    private String formType;

    private String orderStatus;

    private String paymentType;

    private String confirmNo;

    private Date checkInDate;

    private Date checkOutDate;

    private Integer cityId;

    private String cityName;

    private String cityEname;

    private String hotelId;

    private String hotelName;

    private String hotelEname;

    private String roomId;

    private String roomName;

    private String roomEname;

    private Integer roomNum;

    private String bedType;

    private String customerName;

    private String customerDid;

    private String specialMemo;

    private String orderMemo;

    private String mOrderCode;

    private String isHoldRoom;

}
