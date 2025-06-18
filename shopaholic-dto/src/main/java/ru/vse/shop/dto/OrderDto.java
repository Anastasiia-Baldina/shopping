package ru.vse.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderDto {
    @NotEmpty
    private String orderId;
    @NotNull
    private UserIdDto userId;
    @NotEmpty
    private String description;
    private int amount;
    @NotNull
    private OrderStatusDto status;

    public String getOrderId() {
        return orderId;
    }

    public OrderDto setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public UserIdDto getUserId() {
        return userId;
    }

    public OrderDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrderDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public OrderDto setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public OrderStatusDto getStatus() {
        return status;
    }

    public OrderDto setStatus(OrderStatusDto status) {
        this.status = status;
        return this;
    }
}
