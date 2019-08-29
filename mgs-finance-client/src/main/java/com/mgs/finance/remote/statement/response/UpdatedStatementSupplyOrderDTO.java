package com.mgs.finance.remote.statement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedStatementSupplyOrderDTO implements Serializable{

    /**
     * 供货单编码
     */
    private String supplyOrderCode;

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
