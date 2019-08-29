package com.mgs.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:13
 * @Description:
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createdBy;

    private String createdDt;

    private String modifiedBy;

    private String modifiedDt;

}
