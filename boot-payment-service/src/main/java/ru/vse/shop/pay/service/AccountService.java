package ru.vse.shop.pay.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vse.shop.dto.*;

public interface AccountService {
    @NotNull
    AccountDto createAccount(UserIdDto userIdDto);

    @Nullable
    AccountDto findByUserId(UserIdDto userIdDto);

    @Nullable
    AccountDto makeDeposit(DepositDto depositDto);

    @Nullable
    PayResponseDto makePayment(PayRequestDto payRequestDto);
}
