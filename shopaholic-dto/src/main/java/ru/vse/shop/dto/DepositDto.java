package ru.vse.shop.dto;

public class DepositDto {
    private UserIdDto userId;
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
