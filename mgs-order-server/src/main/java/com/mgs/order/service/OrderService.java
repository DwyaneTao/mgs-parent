package com.mgs.order.service;

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

import java.util.Map;

public interface OrderService {

    /**
     * 确认订单
     * @param request
     * @return
     */
    Response confirmOrder(ConfirmOrderDTO request);

    /**
     * 取消订单
     * @param request
     * @return
     */
    Response cancelOrder(CancelOrderDTO request);

    /**
     * 修改入住人
     * @param request
     * @return
     */
    Response modifyGuest(ModifyGuestDTO request);

    /**
     * 修改房型
     * @param request
     * @return
     */
    int modifyRoom(ModifyRoomDTO request);

    /**
     * 修改特殊要求
     * @param request
     * @return
     */
    Response modifySpecialRequirement(ModifySpecialRequirementDTO request);

    /**
     * 修改售价
     * @param request
     * @return
     */
    Response modifySalePrice(ModifySalePriceDTO request);

    /**
     * 添加备注
     * @param request
     * @return
     */
    Response addRemark(AddRemarkDTO request);

    /**
     * 保存附件
     * @param request
     * @return
     */
    Response saveOrderAttachment(SaveOrderAttachmentDTO request);

    /**
     * 删除附件
     * @param request
     * @return
     */
    Response deleteOrderAttachment(OrderAttachmentIdDTO request);

    /**
     * 添加订单申请
     * @param request
     * @return
     */
    Response addOrderRequest(AddOrderRequestDTO request);

    /**
     * 处理订单请求
     * @param request
     * @return
     */
    Response handleOrderRequest(HandleOrderRequestDTO request);

    /**
     * 修改订单房型
     * @param request
     * @return
     */
    Response modifyOrderRoom(ModifyOrderRoomDTO request);

    /**
     * 修改订单结算方式
     * @param request
     * @return
     */
    Response modifyOrderSettlementType(ModifyOrderSettlementTypeDTO request);

    /**
     * 解锁订单
     * @param request
     * @return
     */
    Response lockOrder(LockOrderDTO request);

    /**
     * 标记订单
     * @param request
     * @return
     */
    Response markOrder(MarkOrderDTO request);


    /**
     * 修改渠道订单号
     * @param request
     * @return
     */
    Response modifyChannelOrderCode(Map<String,String> request);
}
