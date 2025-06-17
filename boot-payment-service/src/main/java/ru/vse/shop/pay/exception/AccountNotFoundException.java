package ru.vse.shop.pay.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String userId) {
        super("Account with userId=%s not found".formatted(userId));
    }
}
