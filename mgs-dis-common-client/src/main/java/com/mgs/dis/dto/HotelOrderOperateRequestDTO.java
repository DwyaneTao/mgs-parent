package com.mgs.dis.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单操作请求数据传输对象
 *   2018/4/26.
 */
@Data
public class HotelOrderOperateRequestDTO implements Serializable {


    private static final long serialVersionUID = 8195599645224684707L;
    /** 商家编码  */
    private String merchantCode;

    /** 订单所属渠道编码 */
    private String channelCode;

    /** 订单编号 */
    private String orderCode;

    /** 客户订单编号 */
    private String customerOrderCode;

    /** 订单状态 */
    private String orderState;

    /** 操作类型 */
    private String operateType;

    /** 确认方式(：按入住人姓名；2：按确认号)  */
    private Integer confirmType;

    /** 订单确认号 */
    private String confirmNo;

    /** 拒绝类型(1：满房；2:房价不对;3:其他原因） */
    private Integer refuseType;

    /** 拒绝原因 */
    private String refuseReason;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 备注信息 */
    private String remark;

    /** 签名(MD5(toString(this)+MD5(secret key)), 用于安全验证，secret key(秘钥)定义在字典表里面  */
    private String signature;

    /** 操作人 */
    private String operator;
}
