package ru.vse.shop.dto;

public class OrderRequestDto {
    private UserIdDto userId;
    private String description;
    private int amount;

    public UserIdDto getUserId() {
        return userId;
    }

    public OrderRequestDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrderRequestDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public OrderRequestDto setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
