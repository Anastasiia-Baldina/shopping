package ru.vse.shop.dto;

public class PayRequestDto {
    private UserIdDto userId;
    private String orderId;
    private int amount;

    public UserIdDto getUserId() {
        return userId;
    }

    public PayRequestDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public PayRequestDto setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public PayRequestDto setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
}
