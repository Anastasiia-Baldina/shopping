package ru.vse.shop.order.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vse.shop.utils.Asserts;

public class Order {
    @NotNull
    private final String id;
    @NotNull
    private final String userId;
    @Nullable
    private final String description;
    @NotNull
    private final OrderStatus status;
    private final int amount;

    private Order(Builder b) {
        id = Asserts.notEmpty(b.id, "id");
        userId = Asserts.notEmpty(b.userId, "userId");
        description = b.description;
        amount = b.amount;
        status = Asserts.notNull(b.status, "status");
    }

    @NotNull
    public String id() {
        return id;
    }

    @NotNull
    public String userId() {
        return userId;
    }

    @Nullable
    public String description() {
        return description;
    }

    public int amount() {
        return amount;
    }

    @NotNull
    public OrderStatus status() {
        return status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String userId;
        private String description;
        private int amount;
        private OrderStatus status = OrderStatus.CREATED;

        public Order build() {
            return new Order(this);
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }
    }
}
