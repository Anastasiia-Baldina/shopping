package ru.vse.shop.order.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.vse.shop.kafka.KafkaPublisher;
import ru.vse.shop.dto.*;
import ru.vse.shop.order.model.Dto;
import ru.vse.shop.order.repository.OrderRepository;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaPublisher<PayRequestDto> kafkaPublisher;

    public OrderServiceImpl(OrderRepository orderRepository,
                            KafkaPublisher<PayRequestDto> kafkaPublisher) {
        this.orderRepository = orderRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @Nullable
    @Override
    @Transactional
    public OrderDto create(CreateOrderDto createOrderDto) {
        var order = Dto.fromDto(createOrderDto);
        orderRepository.create(order);
        var payRq = new PayRequestDto()
                .setOrderId(order.id())
                .setUserId(new UserIdDto().setValue(order.userId()))
                .setAmount(order.amount());
        kafkaPublisher.apply(order.userId(), payRq);

        return Dto.toDto(order);
    }

    @Override
    public OrderListDto findByUserId(UserIdDto userIdDto) {
        var userId = userIdDto.getValue();
        var orders = orderRepository.findByUserId(userId).stream()
                .map(Dto::toDto)
                .toList();
        return new OrderListDto().setOrders(orders);
    }
}
