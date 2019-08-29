package com.mgs.dlt.request.base;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:47
 * @Description:
 */
@Data
public class PagingSettings {

    private Integer pageSize;
    private Integer currentPageIndex;
}
