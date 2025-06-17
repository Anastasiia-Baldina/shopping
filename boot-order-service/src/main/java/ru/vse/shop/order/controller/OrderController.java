package ru.vse.shop.order.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.vse.shop.kafka.utils.Json;
import ru.vse.shop.dto.CreateOrderDto;
import ru.vse.shop.dto.OrderDto;
import ru.vse.shop.dto.OrderListDto;
import ru.vse.shop.dto.UserIdDto;
import ru.vse.shop.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseBody
    public OrderDto create(@RequestBody @Valid CreateOrderDto createOrderDto) {
        log.info("Request /order/create: {}", Json.toJson(createOrderDto));
        var res = orderService.create(createOrderDto);
        log.info("Response /order/create: {}", Json.toJson(res));

        return res;
    }

    @PostMapping(value = "/findByUserId", produces = "application/json")
    @ResponseBody
    public OrderListDto findByUserId(@RequestBody UserIdDto userIdDto) {
        return orderService.findByUserId(userIdDto);
    }
}
