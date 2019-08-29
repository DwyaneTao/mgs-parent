package com.mgs.common.enums;

/**
 * 代理通渠道订单状态，不同渠道有不同的状态，大体分为Ctrip、Qunar、B2B、ChannelA
 *   2018/5/22.
 */
public enum DltChannelOrderStateEnum {

    /** 代理通——携程渠道订单状态 */
    CTRIP_UNREAD_UNPROCESSED(DltChannelEnum.CTRIP, "0", "未读未处理"),
    CTRIP_READ_UNPROCESSED(DltChannelEnum.CTRIP, "01", "已读未处理"),
    CTRIP_PROCESSED(DltChannelEnum.CTRIP, "100", "已处理"),
    CTRIP_REFUSED(DltChannelEnum.CTRIP, "101", "已拒绝"),

    /** 代理通——携程B2B渠道订单状态 */
    CTRIP_B2B_NEW_ORDER(DltChannelEnum.CTRIP_B2B, "600", "新订单"),
    CTRIP_B2B_UNPROCESSED(DltChannelEnum.CTRIP_B2B, "1", "新订单"),
    CTRIP_B2B_PROCESSED(DltChannelEnum.CTRIP_B2B, "100", "新订单"),
    CTRIP_B2B_REFUSED(DltChannelEnum.CTRIP_B2B, "101", "新订单"),
    CTRIP_B2B_CANCELED(DltChannelEnum.CTRIP_B2B, "500", "新订单"),

    /** 代理通——携程ChannelA（代分销）渠道订单状态 */
    CTRIP_CHANNEL_A_TO_BE_CONFIRM(DltChannelEnum.CTRIP_CHANNEL_A, "1", "待确认"),
    CTRIP_CHANNEL_A_CONFIRMED_AND_UNPROCESSED(DltChannelEnum.CTRIP_CHANNEL_A, "2", "已确认-待处理"),
    CTRIP_CHANNEL_A_CONFIRMED_AND_PROCESSED(DltChannelEnum.CTRIP_CHANNEL_A, "3", "已确认-已处理"),
    CTRIP_CHANNEL_A_CANCELING_UNPROCESSED(DltChannelEnum.CTRIP_CHANNEL_A, "4", "取消中-待处理"),
    CTRIP_CHANNEL_A_CANCELED_PROCESSED(DltChannelEnum.CTRIP_CHANNEL_A, "5", "已取消-已处理"),
    CTRIP_CHANNEL_A_UNPROCESSED(DltChannelEnum.CTRIP_CHANNEL_A, "6", "未处理"),

    /** 代理通——Qunar渠道订单状态 */
    QUNAR_TO_BE_RESERVED_AND_NOT_PAID(DltChannelEnum.QUNAR, "1", "待预留（待支付）"),
    QUNAR_TO_BE_ARRANGED(DltChannelEnum.QUNAR, "2", "待安排"),
    QUNAR_CONFIRMED(DltChannelEnum.QUNAR, "3", "已确认"),
    QUNAR_CHECKED_IN(DltChannelEnum.QUNAR, "4", "已入住"),
    QUNAR_TO_BE_RESERVED_AND_PAID(DltChannelEnum.QUNAR, "5", "待预留（已支付）"),
    QUNAR_TO_BE_PAID(DltChannelEnum.QUNAR, "6", "待支付"),
    QUNAR_CANCELED_OTA_REFUSED(DltChannelEnum.QUNAR, "7", "已取消（OTA拒绝）"),
    QUNAR_TO_BE_REFUND(DltChannelEnum.QUNAR, "8", "待退款"),
    QUNAR_CANCELED_PROCESSED(DltChannelEnum.QUNAR, "9", "已取消-已处理"),
    QUNAR_REFUSED_CANCEL(DltChannelEnum.QUNAR, "10", "拒绝退订"),
    QUNAR_CANCELED_UNPROCESSED(DltChannelEnum.QUNAR, "11", "已取消-未处理"),
    QUNAR_UNPROCESSED(DltChannelEnum.QUNAR, "13", "未处理");

    public DltChannelEnum channel;
    public String orderStateCode;
    public String orderStateName;

    DltChannelOrderStateEnum(DltChannelEnum channel, String orderStateCode, String orderStateName) {
        this.channel = channel;
        this.orderStateCode = orderStateCode;
        this.orderStateName = orderStateName;
    }

    public static DltChannelOrderStateEnum getChannelOrderStateEnum(String channelCode, String orderStateCode) {
        DltChannelOrderStateEnum[] values = values();
        for (DltChannelOrderStateEnum channelOrderState : values) {
            if (channelOrderState.channel.key.equals(channelCode)
                    && channelOrderState.orderStateCode.equals(orderStateCode)) {
                return channelOrderState;
            }
        }
        return null;
    }

    public static String getOrderStateName(String channelCode, String orderStateCode) {
        DltChannelOrderStateEnum channelOrderStateEnum = getChannelOrderStateEnum(channelCode, orderStateCode);
        return null == channelOrderStateEnum ? null : channelOrderStateEnum.orderStateName;
    }
}
