package ru.vse.shop.order.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OrderServiceBoot {

    public static void main(String[] args) {
        new SpringApplicationBuilder(OrderServiceBoot.class)
                .run(args);
    }
}
