package ru.vse.shop.pay.model;

import org.jetbrains.annotations.NotNull;
import ru.vse.shop.utils.Asserts;

public class Account {
    @NotNull
    private final String userId;
    private final int balance;

    private Account(Builder b) {
        userId = Asserts.notEmpty(b.userId, "userId");
        balance = b.balance;
    }

    @NotNull
    public String userId() {
        return userId;
    }

    public int balance() {
        return balance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private int balance;

        public Account build() {
            return new Account(this);
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setBalance(int balance) {
            this.balance = balance;
            return this;
        }
    }
}
