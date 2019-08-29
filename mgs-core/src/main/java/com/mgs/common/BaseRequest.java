package com.mgs.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 14:54
 * @Description:
 */
@Data
public class BaseRequest implements Serializable {

    /**
     * 当前页 默认1
     */
    private int currentPage = 1;

    /**
     * 页面记录数
     */
    private int pageSize = 20;

}
