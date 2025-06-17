package ru.vse.shop.dto;

public class AccountDto {
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
