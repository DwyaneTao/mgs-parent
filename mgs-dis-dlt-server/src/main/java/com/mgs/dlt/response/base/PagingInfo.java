package com.mgs.dlt.response.base;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 23:00
 * @Description:
 */
@Data
public class PagingInfo {

    private Integer currentPageIndex;
    private Integer totalPages;
    private Integer totalRecords;
}
