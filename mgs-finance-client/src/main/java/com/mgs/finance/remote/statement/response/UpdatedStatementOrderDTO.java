package com.mgs.finance.remote.statement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedStatementOrderDTO implements Serializable{

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 操作人
     */
    private String operatedBy;

    /**
     * 操作内容
     */
    private String content;
}
