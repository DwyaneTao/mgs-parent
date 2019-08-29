package com.mgs.dis.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单操作请求数据传输响应对象
 *   2018/4/26.
 */
@Data
public class HotelOrderOperateResponseDTO implements Serializable {


    private static final long serialVersionUID = -3639941065561509933L;
    /** 是否操作成功(1 成功，0 失败） */
    private Integer isSuccess;

    /** 失败原因 */
    private String failureReason;

    /** 订单编号 */
    private String orderCode;

    /** 订单状态 */
    private String orderState;

}
