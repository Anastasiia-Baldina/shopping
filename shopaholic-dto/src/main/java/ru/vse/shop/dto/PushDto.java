package ru.vse.shop.dto;

public class PushDto {
    private String orderId;
    private String userId;
    private OrderStatusDto orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public PushDto setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderStatusDto getOrderStatus() {
        return orderStatus;
    }

    public PushDto setOrderStatus(OrderStatusDto orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PushDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
