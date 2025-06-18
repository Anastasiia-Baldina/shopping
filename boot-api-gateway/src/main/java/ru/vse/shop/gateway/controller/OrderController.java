package ru.vse.shop.gateway.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vse.shop.kafka.utils.Json;
import ru.vse.shop.gateway.service.EndpointBalancer;
import ru.vse.shop.dto.CreateOrderDto;
import ru.vse.shop.dto.OrderDto;
import ru.vse.shop.dto.OrderListDto;
import ru.vse.shop.dto.UserIdDto;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final EndpointBalancer endpointBalancer;
    private final RestTemplate restTemplate;

    public OrderController(EndpointBalancer endpointBalancer, RestTemplate restTemplate) {
        this.endpointBalancer = endpointBalancer;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseBody
    public OrderDto create(@RequestBody @Valid CreateOrderDto createOrderDto) {
        log.info("Request /order/create: {}", Json.toJson(createOrderDto));
        var url = endpointBalancer.nextEndpoint() + "/order/create";
        var res = restTemplate.postForObject(url, createOrderDto, OrderDto.class);
        log.info("Response /order/create: {}", Json.toJson(res));

        return res;
    }

    @PostMapping(value = "/findByUserId", produces = "application/json")
    @ResponseBody
    public OrderListDto findByUserId(@RequestBody @Valid UserIdDto userIdDto) {
        log.info("Request /order/findByUserId: {}", Json.toJson(userIdDto));
        var url = endpointBalancer.nextEndpoint() + "/order/findByUserId";
        var res = restTemplate.postForObject(url, userIdDto, OrderListDto.class);
        log.info("Response /order/findByUserId: {}", Json.toJson(res));

        return res;
    }
}
