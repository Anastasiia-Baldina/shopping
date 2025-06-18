package ru.vse.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DepositDto {
    @NotNull
    private UserIdDto userId;
    @Min(value = 1)
    @Max(value = 1_000_000)
    private int amount;

    public UserIdDto getUserId() {
        return userId;
    }

    public DepositDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public DepositDto setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
