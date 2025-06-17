package ru.vse.shop.dto;

public class PayResponseDto {
    private UserIdDto userId;
    private String orderId;
    private PaymentStatusDto status;

    public UserIdDto getUserId() {
        return userId;
    }

    public PayResponseDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public PayResponseDto setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentStatusDto getStatus() {
        return status;
    }

    public PayResponseDto setStatus(PaymentStatusDto status) {
        this.status = status;
        return this;
    }
}
