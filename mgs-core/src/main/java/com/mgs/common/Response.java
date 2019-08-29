package com.mgs.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 23:06
 * @Description:
 */
@Data
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回結果(1:成功 0:失敗)
     */
    protected Integer result;

    /**
     * 错误编码
     */
    protected String failCode;

    /**
     * 错误原因
     */
    protected String failReason;

    /**
     * 返回对象
     */
    protected Object model;

    public Response() {};

    public Response(Integer result) {
        this.result = result;
    }

    public Response(Integer result,String failCode,String failReason) {
        this.result = result;
        this.failCode = failCode;
        this.failReason = failReason;
    }
}
