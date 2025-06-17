package ru.vse.shop.order.model;

import ru.vse.shop.dto.CreateOrderDto;
import ru.vse.shop.dto.OrderDto;
import ru.vse.shop.dto.OrderStatusDto;
import ru.vse.shop.dto.UserIdDto;

import java.util.UUID;

public final class Dto {
    private Dto() {
    }

    public static OrderStatusDto toDto(OrderStatus status) {
        return switch (status) {
            case DONE -> OrderStatusDto.DONE;
            case CREATED -> OrderStatusDto.CREATED;
            case NO_ACCOUNT -> OrderStatusDto.NO_ACCOUNT;
            case NOT_ENOUGH_MONEY -> OrderStatusDto.NOT_ENOUGH_MONEY;
        };
    }

    public static OrderDto toDto(Order order) {
        return new OrderDto()
                .setOrderId(order.id())
                .setUserId(new UserIdDto().setValue(order.userId()))
                .setAmount(order.amount())
                .setDescription(order.description())
                .setStatus(toDto(order.status()));
    }

    public static Order fromDto(CreateOrderDto dto) {
        return Order.builder()
                .setId(UUID.randomUUID().toString())
                .setUserId(dto.getUserId().getValue())
                .setDescription(dto.getDescription())
                .setAmount(dto.getAmount())
                .setStatus(OrderStatus.CREATED)
                .build();
    }
}
