package ru.vse.shop.pay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vse.shop.dto.AccountDto;
import ru.vse.shop.dto.DepositDto;
import ru.vse.shop.dto.UserIdDto;
import ru.vse.shop.pay.exception.AccountNotFoundException;
import ru.vse.shop.pay.service.AccountService;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    private final AccountService accountService;

    public PaymentController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseBody
    @PostMapping(value = "/newAccount", produces = "application/json")
    public AccountDto newAccount(@RequestBody UserIdDto userIdDto) {
        return accountService.createAccount(userIdDto);
    }

    @ResponseBody
    @PostMapping(value = "/deposit", produces = "application/json")
    public AccountDto deposit(@RequestBody DepositDto depositDto) {
        var res = accountService.makeDeposit(depositDto);
        if (res == null) {
            throw new AccountNotFoundException(depositDto.getUserId().getValue());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/findByUserId", produces = "application/json")
    public AccountDto findByUserId(@RequestBody UserIdDto userIdDto) {
        return accountService.findByUserId(userIdDto);
    }
}
