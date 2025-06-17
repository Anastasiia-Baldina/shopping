package ru.vse.shop.web.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebBoot {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebBoot.class)
                .run(args);
    }
}
