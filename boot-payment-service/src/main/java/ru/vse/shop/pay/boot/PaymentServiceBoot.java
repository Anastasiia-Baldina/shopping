package ru.vse.shop.pay.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class PaymentServiceBoot {

    public static void main(String[] args) {
        new SpringApplicationBuilder(PaymentServiceBoot.class)
                .run(args);
    }
}
