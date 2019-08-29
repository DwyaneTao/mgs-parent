package com.mgs.meituan.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

@Data
public class Response {

    /**
     * 是否成功；true为成功
     */
    private Boolean success;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回说明
     */
    private String message;

    /**
     * 校验失败说明
     */
    private JSONObject data;

}
