package ru.vse.shop.order.service;

import org.jetbrains.annotations.Nullable;
import ru.vse.shop.dto.CreateOrderDto;
import ru.vse.shop.dto.OrderDto;
import ru.vse.shop.dto.OrderListDto;
import ru.vse.shop.dto.UserIdDto;

import java.util.List;

public interface OrderService {
    @Nullable
    OrderDto create(CreateOrderDto createOrderDto);

    OrderListDto findByUserId(UserIdDto userIdDto);
}
