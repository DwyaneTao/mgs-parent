package com.mgs.fuzzyquery.service;

import com.mgs.common.Response;
import com.mgs.fuzzyquery.dto.FuzzyQueryDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:35
 * @Description:模糊查询service
 */
public interface FuzzyQueryService {

    /**
     * 模糊查询城市
     * @param fuzzyQueryDTO
     * @return
     */
    Response queryCity(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    /**
     * 模糊查询供应商
     * @param fuzzyQueryDTO
     * @return
     */
    Response querySupplier(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    /**
     * 模糊查询分销商
     * @param fuzzyQueryDTO
     * @return
     */
    Response queryAgent(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    /**
     * 模糊查询酒店
     * @param fuzzyQueryDTO
     * @return
     */
    Response queryHotel(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    /**
     * 模糊查询房型
     * @param fuzzyQueryDTO
     * @return
     */
    Response queryRoom(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);

    /**
     * 模糊查询产品
     * @param fuzzyQueryDTO
     * @return
     */
    Response queryProduct(@RequestBody FuzzyQueryDTO fuzzyQueryDTO);
}
