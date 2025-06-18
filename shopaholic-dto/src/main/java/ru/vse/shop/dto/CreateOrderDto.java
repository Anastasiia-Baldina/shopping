package ru.vse.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateOrderDto {
    @NotNull
    private UserIdDto userId;
    @NotEmpty
    private String description;
    @Min(value = 1)
    @Max(value = 1_000_000)
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
