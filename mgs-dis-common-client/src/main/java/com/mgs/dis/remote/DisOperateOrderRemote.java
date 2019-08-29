package com.mgs.dis.remote;

import com.mgs.dis.dto.HotelOrderOperateRequestDTO;
import com.mgs.dis.dto.HotelOrderOperateResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: Owen
 * @Date: 2019/8/13 18:09
 * @Description: 分销对接
 */
@FeignClient(value = "mgs-dis-dlt-server")
public interface DisOperateOrderRemote {

    @PostMapping("/dis/dlt/order/operateOrder")
    public HotelOrderOperateResponseDTO operateOrder(@RequestBody HotelOrderOperateRequestDTO requestDTO);
}
