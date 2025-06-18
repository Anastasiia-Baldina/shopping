package ru.vse.shop.gateway.boot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestTemplate;
import ru.vse.shop.gateway.controller.ErrorHandlerController;
import ru.vse.shop.gateway.controller.OrderController;
import ru.vse.shop.gateway.controller.PaymentController;
import ru.vse.shop.gateway.kafka.PushDtoMessageHandler;
import ru.vse.shop.gateway.properties.RouterProperties;
import ru.vse.shop.gateway.service.EndpointBalancer;
import ru.vse.shop.gateway.service.RoundRobinBalancer;
import org.vse.shop.kafka.KafkaListener;
import org.vse.shop.kafka.MessageHandler;
import org.vse.shop.kafka.properties.KafkaListenerProperties;
import ru.vse.shop.dto.PushDto;

@Configuration
public class ApplicationConfiguration {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @ConfigurationProperties(prefix = "router.order-service")
    @Bean
    RouterProperties orderRouterProperties() {
        return new RouterProperties();
    }

    @ConfigurationProperties(prefix = "router.payment-service")
    @Bean
    RouterProperties paymentRouterProperties() {
        return new RouterProperties();
    }

    @ConfigurationProperties(prefix = "kafka.order-push")
    @Bean
    KafkaListenerProperties orderPushKafkaListenerProperties() {
        return new KafkaListenerProperties();
    }

    @Bean
    RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    @Bean
    EndpointBalancer orderEndpointBalancer() {
        return new RoundRobinBalancer(orderRouterProperties());
    }

    @Bean
    EndpointBalancer paymentEndpointBalancer() {
        return new RoundRobinBalancer(paymentRouterProperties());
    }

    @Bean
    OrderController orderController() {
        return new OrderController(orderEndpointBalancer(), restTemplate());
    }

    @Bean
    PaymentController paymentController() {
        return new PaymentController(paymentEndpointBalancer(), restTemplate());
    }

    @Bean
    MessageHandler<PushDto> pushDtoMessageHandler() {
        return new PushDtoMessageHandler(simpMessagingTemplate);
    }

    @Bean
    @Profile("!test")
    KafkaListener<PushDto> pushDtoKafkaListener() {
        return new KafkaListener<>(
                orderPushKafkaListenerProperties(),
                pushDtoMessageHandler(),
                PushDto.class);
    }

    @Bean
    ErrorHandlerController errorHandlerController() {
        return new ErrorHandlerController();
    }
}
