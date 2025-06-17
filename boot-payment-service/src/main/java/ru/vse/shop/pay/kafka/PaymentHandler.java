package ru.vse.shop.pay.kafka;

import org.vse.shop.kafka.KafkaPublisher;
import org.vse.shop.kafka.MessageHandler;
import ru.vse.shop.dto.PayRequestDto;
import ru.vse.shop.dto.PayResponseDto;
import ru.vse.shop.pay.service.AccountService;

public class PaymentHandler implements MessageHandler<PayRequestDto> {
    private final KafkaPublisher<PayResponseDto> kafkaPublisher;
    private final AccountService accountService;

    public PaymentHandler(KafkaPublisher<PayResponseDto> kafkaPublisher, AccountService accountService) {
        this.kafkaPublisher = kafkaPublisher;
        this.accountService = accountService;
    }

    @Override
    public void onMessage(PayRequestDto payRequestDto) {
        PayResponseDto res = accountService.makePayment(payRequestDto);
        kafkaPublisher.apply(payRequestDto.getUserId().getValue(), res);
    }
}
