package com.mgs.dlt.server;

import com.mgs.dis.dto.HotelOrderOperateRequestDTO;
import com.mgs.dis.dto.HotelOrderOperateResponseDTO;
import com.mgs.dlt.service.DltHotelOrderOperateService;
import com.mgs.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:38
 * @Description: 代理通操作订单
 */
@RestController
@Slf4j
@RequestMapping("/dis/dlt")
public class DltOrderOperateServer {

    @Autowired
    private DltHotelOrderOperateService dltHotelOrderOperateService;

    @RequestMapping("/order/operateOrder")
    public HotelOrderOperateResponseDTO operateOrder(@RequestBody HotelOrderOperateRequestDTO requestDTO) {
        HotelOrderOperateResponseDTO hotelOrderOperateResponseDTO = new HotelOrderOperateResponseDTO();
        hotelOrderOperateResponseDTO.setIsSuccess(ResultCodeEnum.SUCCESS.code);
        if (null == requestDTO || null == requestDTO.getOrderCode()
                || null == requestDTO.getChannelCode() || null == requestDTO.getOperateType()
                || null == requestDTO.getMerchantCode()) {
            hotelOrderOperateResponseDTO.setFailureReason("操作代理通订单参数为空，请检查");
            log.error("操作代理通订单参数为空, requestDTO=" + requestDTO);
            return hotelOrderOperateResponseDTO;
        }

        if (null == requestDTO.getOrderCode() || null == requestDTO.getCustomerOrderCode()
                || null == requestDTO.getOrderState()) {
            hotelOrderOperateResponseDTO.setFailureReason("订单传入参数有误！订单ID，渠道订单编码，订单状态为必传！");
            log.error("订单传入参数有误！订单ID，渠道订单编码，订单状态为必传！, requestDTO=" + requestDTO);
            return hotelOrderOperateResponseDTO;
        }
        return dltHotelOrderOperateService.operateOrder(requestDTO);
    }
}
