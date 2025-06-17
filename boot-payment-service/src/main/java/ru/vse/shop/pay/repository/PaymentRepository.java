package ru.vse.shop.pay.repository;

import ru.vse.shop.pay.model.Payment;

public interface PaymentRepository {

    boolean insert(Payment payment);
}
