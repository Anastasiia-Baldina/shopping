package ru.vse.shop.order.kafka;

import org.vse.shop.kafka.KafkaPublisher;
import org.vse.shop.kafka.MessageHandler;
import ru.vse.shop.dto.PayResponseDto;
import ru.vse.shop.dto.PaymentStatusDto;
import ru.vse.shop.dto.PushDto;
import ru.vse.shop.order.model.Dto;
import ru.vse.shop.order.model.OrderStatus;
import ru.vse.shop.order.repository.OrderRepository;

public class PaymentResponseHandler implements MessageHandler<PayResponseDto> {
    private final OrderRepository orderRepository;
    private final KafkaPublisher<PushDto> kafkaPublisher;

    public PaymentResponseHandler(OrderRepository orderRepository, KafkaPublisher<PushDto> kafkaPublisher) {
        this.orderRepository = orderRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @Override
    public void onMessage(PayResponseDto msg) {
        var orderId = msg.getOrderId();
        var status = fromDto(msg.getStatus());
        if (orderRepository.updateStatus(orderId, status)) {
            var order = orderRepository.findById(orderId);
            if (order != null) {
                var pushDto = new PushDto()
                        .setOrderId(order.id())
                        .setUserId(order.userId())
                        .setOrderStatus(Dto.toDto(order.status()));
                kafkaPublisher.apply(order.userId(), pushDto);
            }
        }
    }

    private static OrderStatus fromDto(PaymentStatusDto status) {
        return switch (status) {
            case NO_ACCOUNT -> OrderStatus.NO_ACCOUNT;
            case NOT_ENOUGH_MONEY -> OrderStatus.NOT_ENOUGH_MONEY;
            default -> OrderStatus.DONE;
        };
    }
}
