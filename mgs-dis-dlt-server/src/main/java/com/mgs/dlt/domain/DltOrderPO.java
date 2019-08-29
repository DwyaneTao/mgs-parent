package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 17:26
 * @Description: 代理通订单
 */
@Data
@Table(name = "t_dis_dlt_order")
public class DltOrderPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代理通订单Id
     */
    private String dltOrderId;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * 是否处理
     */
    private Integer isHandled;

    /**
     * 处理时间
     */
    private String handleDate;

    /**
     * 处理失败原因
     */
    private String handleRemark;

    /**
     * 处理结果success/failure
     */
    private String handleResult;

    /**
     * 运营商订单Id
     */
    private String orderId;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 子渠道
     */
    private String childChannel;

    private String orderDate;

    private String formType;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 入住日期
     */
    private String checkinDate;

    /**
     * 离店日期
     */
    private String checkoutDate;

}
