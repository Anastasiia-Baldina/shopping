package ru.vse.shop.pay.boot.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vse.shop.kafka.KafkaListener;
import org.vse.shop.kafka.KafkaPublisher;
import org.vse.shop.kafka.MessageHandler;
import org.vse.shop.kafka.properties.KafkaListenerProperties;
import org.vse.shop.kafka.properties.KafkaResponderProperties;
import ru.vse.shop.dto.PayRequestDto;
import ru.vse.shop.dto.PayResponseDto;
import ru.vse.shop.pay.controller.PaymentController;
import ru.vse.shop.pay.kafka.PaymentHandler;
import ru.vse.shop.pay.properties.DbProperties;
import ru.vse.shop.pay.repository.AccountRepository;
import ru.vse.shop.pay.repository.PaymentRepository;
import ru.vse.shop.pay.repository.PostgresAccountRepository;
import ru.vse.shop.pay.repository.PostgresPaymentRepository;
import ru.vse.shop.pay.service.AccountService;
import ru.vse.shop.pay.service.AccountServiceImpl;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration {

    @ConfigurationProperties(prefix = "db")
    @Bean
    DbProperties dbProperties() {
        return new DbProperties();
    }

    @ConfigurationProperties(prefix = "kafka.request")
    @Bean
    KafkaListenerProperties kafkaListenerProperties() {
        return new KafkaListenerProperties();
    }

    @ConfigurationProperties(prefix = "kafka.response")
    @Bean
    KafkaResponderProperties kafkaResponderProperties() {
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
    PaymentRepository paymentRepository() {
        return new PostgresPaymentRepository(namedParameterJdbcTemplate());
    }

    @Bean
    AccountRepository accountRepository() {
        return new PostgresAccountRepository(namedParameterJdbcTemplate());
    }

    @Bean
    AccountService accountService() {
        return new AccountServiceImpl(accountRepository(), paymentRepository());
    }

    @Bean
    PaymentController paymentController() {
        return new PaymentController(accountService());
    }

    @Bean
    @Profile("!test")
    KafkaPublisher<PayResponseDto> paymentDtoKafkaResponder() {
        return new KafkaPublisher<>(kafkaResponderProperties());
    }

    @Bean
    @Profile("!test")
    MessageHandler<PayRequestDto> paymentDtoMessageHandler() {
        return new PaymentHandler(paymentDtoKafkaResponder(), accountService());
    }

    @Bean
    @Profile("!test")
    KafkaListener<PayRequestDto> paymentDtoKafkaListener() {
        return new KafkaListener<>(
                kafkaListenerProperties(),
                paymentDtoMessageHandler(),
                PayRequestDto.class);
    }
}
