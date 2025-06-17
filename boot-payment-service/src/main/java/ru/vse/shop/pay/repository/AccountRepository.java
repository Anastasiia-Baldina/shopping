package ru.vse.shop.pay.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vse.shop.pay.model.Account;
import ru.vse.shop.pay.model.Deposit;
import ru.vse.shop.pay.model.Payment;

public interface AccountRepository {

    void create(@NotNull Account account);

    boolean makeDeposit(@NotNull Deposit deposit);

    boolean makePayment(@NotNull Payment payment);

    @Nullable
    Account findByUserId(@NotNull String userId);
}
