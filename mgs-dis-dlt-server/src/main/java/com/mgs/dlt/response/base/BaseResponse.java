package com.mgs.dlt.response.base;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class BaseResponse {

    private ResponseStatus ResponseStatus;
    private ResultStatus resultStatus;

}
