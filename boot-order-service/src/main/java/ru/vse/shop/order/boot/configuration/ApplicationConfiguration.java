package ru.vse.shop.order.boot.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.vse.shop.kafka.KafkaListener;
import org.vse.shop.kafka.KafkaPublisher;
import org.vse.shop.kafka.properties.KafkaListenerProperties;
import org.vse.shop.kafka.properties.KafkaResponderProperties;
import ru.vse.shop.dto.OrderDto;
import ru.vse.shop.dto.PayRequestDto;
import ru.vse.shop.dto.PayResponseDto;
import ru.vse.shop.dto.PushDto;
import ru.vse.shop.order.controller.OrderController;
import ru.vse.shop.order.kafka.PaymentResponseHandler;
import ru.vse.shop.order.properties.DbProperties;
import ru.vse.shop.order.repository.OrderRepository;
import ru.vse.shop.order.repository.PostgresOrderRepository;
import ru.vse.shop.order.service.OrderService;
import ru.vse.shop.order.service.OrderServiceImpl;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class ApplicationConfiguration {

    @ConfigurationProperties(prefix = "db")
    @Bean
    DbProperties dbProperties() {
        return new DbProperties();
    }

    @ConfigurationProperties(prefix = "kafka.pay-response")
    @Bean
    KafkaListenerProperties payResponseKafkaListenerProperties() {
        return new KafkaListenerProperties();
    }

    @ConfigurationProperties(prefix = "kafka.order-push")
    @Bean
    KafkaResponderProperties orderPushKafkaPublisherProperties() {
        return new KafkaResponderProperties();
    }

    @ConfigurationProperties(prefix = "kafka.pay-request")
    @Bean
    KafkaResponderProperties payRequestKafkaPublisherProperties() {
        return new KafkaResponderProperties();
    }

    @Bean
    DataSource dataSource() {
        var cfg = dbProperties();
        var dSrc = new HikariDataSource();
        dSrc.setJdbcUrl(cfg.getUrl());
        dSrc.setUsername(cfg.getUser());
        dSrc.setPassword(cfg.getPassword());
        dSrc.setSchema(cfg.getSchema());
        dSrc.setMaximumPoolSize(cfg.getMaxPoolSize());

        return dSrc;
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    OrderRepository orderRepository() {
        return new PostgresOrderRepository(namedParameterJdbcTemplate());
    }

    @Bean
    @Profile("!test")
    KafkaPublisher<PayRequestDto> payRequestDtoKafkaPublisher() {
        return new KafkaPublisher<>(payRequestKafkaPublisherProperties());
    }

    @Bean
    OrderService orderService() {
        return new OrderServiceImpl(orderRepository(), payRequestDtoKafkaPublisher());
    }

    @Bean
    OrderController orderController() {
        return new OrderController(orderService());
    }

    @Bean
    @Profile("!test")
    KafkaPublisher<PushDto> orderDtoKafkaResponder() {
        return new KafkaPublisher<>(orderPushKafkaPublisherProperties());
    }

    @Bean
    @Profile("!test")
    PaymentResponseHandler paymentResponseHandler() {
        return new PaymentResponseHandler(orderRepository(), orderDtoKafkaResponder());
    }

    @Bean
    @Profile("!test")
    KafkaListener<PayResponseDto> payResponseDtoKafkaListener() {
        return new KafkaListener<>(
                payResponseKafkaListenerProperties(),
                paymentResponseHandler(),
                PayResponseDto.class);
    }
}
