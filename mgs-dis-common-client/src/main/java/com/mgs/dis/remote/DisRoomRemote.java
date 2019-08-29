package com.mgs.dis.remote;

import com.mgs.common.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: Owen
 * @Date: 2019/8/17 10:36
 * @Description:
 */
@FeignClient(value = "mgs-dis-dlt-server")
public interface DisRoomRemote {

    @RequestMapping("/dlt/room/queryNeedPushSaleRoomCount")
    public Response queryNeedPushSaleRoomCount(@RequestParam("companyCode") String companyCode);

    @RequestMapping("/dlt/room/pushSaleRoom")
    public Response pushSaleRoom(@RequestParam("companyCode") String companyCode);
}
