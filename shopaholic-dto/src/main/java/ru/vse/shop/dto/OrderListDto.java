package ru.vse.shop.dto;

import java.util.List;

public class OrderListDto {
    private List<OrderDto> orders;

    public List<OrderDto> getOrders() {
        return orders;
    }

    public OrderListDto setOrders(List<OrderDto> orders) {
        this.orders = orders;
        return this;
    }
}
