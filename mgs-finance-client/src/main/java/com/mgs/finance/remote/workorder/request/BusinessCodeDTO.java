package com.mgs.finance.remote.workorder.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessCodeDTO implements Serializable{

    /**
     * 业务单号
     */
    private String businessCode;
}
