package ru.vse.shop.pay.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.vse.shop.pay.model.Payment;

public class PostgresPaymentRepository implements PaymentRepository {
    private static final String sqlInsert =
            "merge into payment p" +
                    " using " +
                    "   (values(:order_id)) as params(order_id)" +
                    " on " +
                    "   p.order_id = params.order_id" +
                    " when not matched then" +
                    "    insert (order_id, user_id, amount)" +
                    "    values (:order_id, :user_id, :amount)";
    private final NamedParameterJdbcTemplate jdbc;

    public PostgresPaymentRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean insert(Payment payment) {
        var pSrc = new MapSqlParameterSource()
                .addValue("order_id", payment.orderId())
                .addValue("user_id", payment.userId())
                .addValue("amount", payment.amount());

        return jdbc.update(sqlInsert, pSrc) > 0;
    }
}
