package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelOrderQtyDTO implements Serializable,Comparable<ChannelOrderQtyDTO> {

    /**
     * 渠道编码
     */
    private String channelCode;
    /**
     * 渠道序号
     */
    private  Integer channelNo;

    /**
     * 订单数
     */
    private Integer orderQty;

    @Override
    public int compareTo(ChannelOrderQtyDTO o) {
        return this.channelNo -o.channelNo;
    }
}
