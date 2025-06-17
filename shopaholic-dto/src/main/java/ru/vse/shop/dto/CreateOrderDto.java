package ru.vse.shop.dto;

public class CreateOrderDto {
    private UserIdDto userId;
    private String description;
    private int amount;

    public UserIdDto getUserId() {
        return userId;
    }

    public CreateOrderDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateOrderDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public CreateOrderDto setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
