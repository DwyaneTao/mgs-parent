package com.mgs.order.remote;

import com.mgs.common.Response;
import com.mgs.order.remote.request.AddOrderRequestDTO;
import com.mgs.order.remote.request.AddRemarkDTO;
import com.mgs.order.remote.request.CancelOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderDTO;
import com.mgs.order.remote.request.HandleOrderRequestDTO;
import com.mgs.order.remote.request.LockOrderDTO;
import com.mgs.order.remote.request.MarkOrderDTO;
import com.mgs.order.remote.request.ModifyGuestDTO;
import com.mgs.order.remote.request.ModifyOrderRoomDTO;
import com.mgs.order.remote.request.ModifyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifyRoomDTO;
import com.mgs.order.remote.request.ModifySalePriceDTO;
import com.mgs.order.remote.request.ModifySpecialRequirementDTO;
import com.mgs.order.remote.request.OrderAttachmentIdDTO;
import com.mgs.order.remote.request.SaveOrderAttachmentDTO;
import com.mgs.order.remote.response.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "mgs-order-server")
public interface OrderRemote {

    /**
     * 确认订单
     * @param request
     * @return
     */
    @PostMapping("/order/confirmOrder")
    Response confirmOrder(@RequestBody ConfirmOrderDTO request);

    /**
     * 取消订单
     * @param request
     * @return
     */
    @PostMapping("/order/cancelOrder")
    Response cancelOrder(@RequestBody CancelOrderDTO request);

    /**
     * 修改入住人
     * @param request
     * @return
     */
    @PostMapping("/order/modifyGuest")
    Response modifyGuest(@RequestBody ModifyGuestDTO request);

    /**
     * 修改房型
     * @param request
     * @return
     */
    @PostMapping("/order/modifyRoom")
    Response modifyRoom(@RequestBody ModifyRoomDTO request);

    /**
     * 修改特殊要求
     * @param request
     * @return
     */
    @PostMapping("/order/modifySpecialRequirement")
    Response modifySpecialRequirement(@RequestBody ModifySpecialRequirementDTO request);

    /**
     * 修改售价
     * @param request
     * @return
     */
    @PostMapping("/order/modifySalePrice")
    Response modifySalePrice(@RequestBody ModifySalePriceDTO request);

    /**
     * 添加备注
     * @param request
     * @return
     */
    @PostMapping("/order/addRemark")
    Response addRemark(@RequestBody AddRemarkDTO request);

    /**
     * 上传附件
     * @param request
     * @return
     */
    @PostMapping("/order/saveOrderAttachment")
    Response saveOrderAttachment(@RequestBody SaveOrderAttachmentDTO request);

    /**
     * 删除附件
     * @param request
     * @return
     */
    @PostMapping("/order/deleteOrderAttachment")
    Response deleteOrderAttachment(@RequestBody OrderAttachmentIdDTO request);

    /**
     * 处理订单请求
     * @param request
     * @return
     */
    @PostMapping("/order/handleOrderRequest")
    Response handleOrderRequest(@RequestBody HandleOrderRequestDTO request);

    /**
     * 修改订单房型
     * @param request
     * @return
     */
    @PostMapping("/order/modifyOrderRoom")
    Response modifyOrderRoom(@RequestBody ModifyOrderRoomDTO request);

    /**
     * 修改订单结算方式
     * @param request
     * @return
     */
    @PostMapping("/order/modifyOrderSettlementType")
    Response modifyOrderSettlementType(@RequestBody ModifyOrderSettlementTypeDTO request);

    /**
     * 解锁订单
     * @param request
     * @return
     */
    @PostMapping("/order/lockOrder")
    Response lockOrder(@RequestBody LockOrderDTO request);

    /**
     * 标记订单
     * @param request
     * @return
     */
    @PostMapping("/order/markOrder")
    Response markOrder(@RequestBody MarkOrderDTO request);

    /**
     * 订单取消申请
     * @param request
     * @return
     */
    @PostMapping("/order/addOrderRequest")
    Response addOrderRequest(@RequestBody AddOrderRequestDTO request);


    /**
     * 修改渠道号
     * @param request
     * @return
     */
    @PostMapping("/order/modifyChannelOrderCode")
    Response modifyChannelOrderCode(@RequestBody Map<String,String> request);
}
