package ru.vse.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AccountDto {
    @NotNull
    private UserIdDto userId;
    private int balance;

    public UserIdDto getUserId() {
        return userId;
    }

    public AccountDto setUserId(UserIdDto userId) {
        this.userId = userId;
        return this;
    }

    public int getBalance() {
        return balance;
    }

    public AccountDto setBalance(int balance) {
        this.balance = balance;
        return this;
    }
}
