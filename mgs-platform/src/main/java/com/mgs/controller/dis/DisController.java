package com.mgs.controller.dis;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.dis.remote.DisRoomRemote;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Owen
 * @Date: 2019/4/23 20:40
 * @Description: 产品
 */
@RestController
@RequestMapping("/dis")
@Slf4j
public class DisController extends BaseController {

    @Autowired
    private DisRoomRemote disRoomRemote;

    @PostMapping("/queryNeedPushSaleRoomCount")
    public Response queryNeedPushSaleRoomCount() {
        Response response = null;
        try {
            response = disRoomRemote.queryNeedPushSaleRoomCount(getCompanyCode());
        }catch (Exception e) {
            log.error("queryNeedPushSaleRoomCount-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @PostMapping("/pushSaleRoom")
    public Response pushSaleRoom() {
        Response response = null;
        try {
            response = disRoomRemote.pushSaleRoom(getCompanyCode());
        }catch (Exception e) {
            log.error("pushSaleRoom-controller error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}

