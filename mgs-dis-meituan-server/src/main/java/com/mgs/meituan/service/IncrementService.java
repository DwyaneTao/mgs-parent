package com.mgs.meituan.service;

import java.util.List;

public interface IncrementService {

    /**
     * 推送增量
     * @param incrementList
     */
    void pullIncrement(List<Object> incrementList);
}
