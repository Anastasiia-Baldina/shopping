package ru.vse.shop.gateway.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.vse.shop.kafka.utils.Json;
import ru.vse.shop.gateway.service.EndpointBalancer;
import ru.vse.shop.dto.AccountDto;
import ru.vse.shop.dto.DepositDto;
import ru.vse.shop.dto.UserIdDto;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final EndpointBalancer endpointBalancer;
    private final RestTemplate restTemplate;

    public PaymentController(EndpointBalancer endpointBalancer, RestTemplate restTemplate) {
        this.endpointBalancer = endpointBalancer;
        this.restTemplate = restTemplate;
    }

    @ResponseBody
    @PostMapping(value = "/newAccount", produces = "application/json")
    public AccountDto newAccount(@RequestBody @Valid UserIdDto userIdDto) {
        log.info("Request /pay/newAccount: {}", Json.toJson(userIdDto));
        var url = endpointBalancer.nextEndpoint() + "/pay/newAccount";
        var res = restTemplate.postForObject(url, userIdDto, AccountDto.class);
        log.info("Response /pay/newAccount: {}", Json.toJson(res));

        return res;
    }

    @ResponseBody
    @PostMapping(value = "/deposit", produces = "application/json")
    public AccountDto deposit(@RequestBody DepositDto depositDto) {
        log.info("Request /pay/deposit: {}", Json.toJson(depositDto));
        var url = endpointBalancer.nextEndpoint() + "/pay/deposit";
        var res = restTemplate.postForObject(url, depositDto, AccountDto.class);
        log.info("Response /pay/deposit: {}", Json.toJson(res));

        return res;
    }

    @ResponseBody
    @PostMapping(value = "/findByUserId", produces = "application/json")
    public AccountDto findByUserId(@RequestBody UserIdDto userIdDto) {
        log.info("Request /pay/findByUserId: {}", Json.toJson(userIdDto));
        var url = endpointBalancer.nextEndpoint() + "/pay/findByUserId";
        var res = restTemplate.postForObject(url, userIdDto, AccountDto.class);
        log.info("Response /pay/findByUserId: {}", Json.toJson(res));

        return res;
    }
}
