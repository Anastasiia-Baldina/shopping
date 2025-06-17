package ru.vse.shop.order.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vse.shop.order.model.Order;
import ru.vse.shop.order.model.OrderStatus;

import java.util.List;

public interface OrderRepository {
    void create(Order order);

    boolean updateStatus(@NotNull String orderId, @NotNull OrderStatus status);

    @Nullable
    Order findById(String orderId);

    List<Order> findByUserId(String userId);

    List<Order> fetchAll();
}
