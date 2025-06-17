package ru.vse.shop.pay.model;

import org.jetbrains.annotations.NotNull;
import ru.vse.shop.utils.Asserts;

public class Payment {
    @NotNull
    private final String userId;
    @NotNull
    private final String orderId;
    private final int amount;

    private Payment(Builder b) {
        userId = Asserts.notEmpty(b.userId, "userId");
        orderId = Asserts.notEmpty(b.orderId, "orderId");
        amount = b.amount;
    }

    @NotNull
    public String userId() {
        return userId;
    }

    @NotNull
    public String orderId() {
        return orderId;
    }

    public int amount() {
        return amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String orderId;
        private int amount;

        public Payment build() {
            return new Payment(this);
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }
    }
}
