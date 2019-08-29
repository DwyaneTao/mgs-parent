package com.mgs.dlt.response.base;

import lombok.Data;

/**
 *   2018/4/11.
 */
@Data
public class DltHttpResponse {

    /**
     * 响应内容
     */
    private String response = "";

    /**
     * 请求状态码,初始化一个非200的数字
     */
    private int code = -1;

    private String msg;

    public DltHttpResponse() {}

    public DltHttpResponse(int code, String msg, String response) {
        this.code = code;
        this.msg = msg;
        this.response = response;
    }
}
