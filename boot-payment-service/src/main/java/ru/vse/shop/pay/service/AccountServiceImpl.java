package ru.vse.shop.pay.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;
import ru.vse.shop.dto.AccountDto;
import ru.vse.shop.dto.DepositDto;
import ru.vse.shop.dto.PayRequestDto;
import ru.vse.shop.dto.PayResponseDto;
import ru.vse.shop.dto.PaymentStatusDto;
import ru.vse.shop.dto.UserIdDto;
import ru.vse.shop.pay.model.Account;
import ru.vse.shop.pay.model.Deposit;
import ru.vse.shop.pay.model.Payment;
import ru.vse.shop.pay.repository.AccountRepository;
import ru.vse.shop.pay.repository.PaymentRepository;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              PaymentRepository paymentRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    @NotNull
    @Override
    public AccountDto createAccount(UserIdDto userIdDto) {
        var usrId = userIdDto.getValue();
        var account = accountRepository.findByUserId(usrId);
        if (account == null) {
            account = Account.builder()
                    .setUserId(usrId)
                    .setBalance(0)
                    .build();
            accountRepository.create(account);
        }
        return new AccountDto()
                .setUserId(userIdDto)
                .setBalance(account.balance());
    }

    @Nullable
    @Override
    public AccountDto findByUserId(UserIdDto userIdDto) {
        return toDto(accountRepository.findByUserId(userIdDto.getValue()));
    }

    @Nullable
    @Override
    public AccountDto makeDeposit(DepositDto depositDto) {
        var usrId = depositDto.getUserId().getValue();
        var deposit = Deposit.builder()
                .setUserId(usrId)
                .setAmount(depositDto.getAmount())
                .build();
        if (accountRepository.makeDeposit(deposit)) {
            return toDto(accountRepository.findByUserId(usrId));
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    @Transactional
    public PayResponseDto makePayment(PayRequestDto payRequestDto) {
        var usrId = payRequestDto.getUserId().getValue();
        var payment = Payment.builder()
                .setUserId(usrId)
                .setOrderId(payRequestDto.getOrderId())
                .setAmount(payRequestDto.getAmount())
                .build();
        PaymentStatusDto status;
        if (accountRepository.findByUserId(usrId) == null) {
            status = PaymentStatusDto.NO_ACCOUNT;
        } else if (!paymentRepository.insert(payment)) {
            status = PaymentStatusDto.DUPLICATED_PAYMENT;
        } else if(!accountRepository.makePayment(payment)) {
            status = PaymentStatusDto.NOT_ENOUGH_MONEY;
        } else {
            status = PaymentStatusDto.COMPLETED;
        }

        return new PayResponseDto()
                .setOrderId(payRequestDto.getOrderId())
                .setStatus(status)
                .setUserId(payRequestDto.getUserId());

    }

    @Nullable
    private static AccountDto toDto(@Nullable Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDto()
                .setUserId(new UserIdDto().setValue(account.userId()))
                .setBalance(account.balance());
    }
}
