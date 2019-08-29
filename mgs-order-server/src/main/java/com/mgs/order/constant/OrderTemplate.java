package com.mgs.order.constant;

public interface OrderTemplate {

    String ConfirmedTemplate="您的订单号：${orderCode}，${startDate}入住,${endDate}离店的" +
            "${hotelName}的${roomQty}间${roomName}，已为您预订成功！真诚祝您入住愉快！" +
            "酒店地址：${hotelAddress}；" +
            "酒店电话：${hotelPhone}。";

    String CanceledTemplate="您的订单号：${orderCode}，${startDate}入住,${endDate}离店的" +
            "${hotelName}的${roomQty}间${roomName}，已为您取消！欢迎再次预订！";
}
