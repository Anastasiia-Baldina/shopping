package ru.vse.shop.pay.model;

import org.jetbrains.annotations.NotNull;
import ru.vse.shop.utils.Asserts;

public class Deposit {
    @NotNull
    private final String userId;
    private final int amount;

    private Deposit(Builder b) {
        userId = Asserts.notEmpty(b.userId, "userId");
        amount = b.amount;
    }

    @NotNull
    public String userId() {
        return userId;
    }

    public int amount() {
        return amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private int amount;

        public Deposit build() {
            return new Deposit(this);
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }
    }
}
