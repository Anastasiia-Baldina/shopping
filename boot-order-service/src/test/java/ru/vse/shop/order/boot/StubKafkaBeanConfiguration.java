package ru.vse.shop.order.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.vse.shop.kafka.KafkaPublisher;
import ru.vse.shop.dto.PayRequestDto;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("test")
public class StubKafkaBeanConfiguration {
    @Bean

    KafkaPublisher<PayRequestDto> payRequestDtoKafkaPublisher() {
        return mock(KafkaPublisher.class);
    }
}
