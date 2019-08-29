package com.mgs.dlt.request.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *   2018/2/28.
 */
@Data
public class QueryHotelProductDetailRequest implements Serializable {

    private static final long serialVersionUID = 588214080851408901L;
    private Integer pricePlanId;

    private Date checkInDate;

    private Date checkOutDate;

    private String channelCode;

    private String companyCode;

}
