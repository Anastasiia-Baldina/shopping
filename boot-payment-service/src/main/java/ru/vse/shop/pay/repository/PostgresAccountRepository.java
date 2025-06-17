package ru.vse.shop.pay.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.vse.shop.pay.model.Account;
import ru.vse.shop.pay.model.Deposit;
import ru.vse.shop.pay.model.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresAccountRepository implements AccountRepository {
    private static final String sqlCreate =
            "insert into account" +
                    "(" +
                    "   user_id" +
                    "   ,balance" +
                    ")" +
                    " values" +
                    "(" +
                    "   :user_id" +
                    "   ,:balance" +
                    ")";
    private static final String sqlFindByUserId =
            "select " +
                    "   user_id" +
                    "   ,balance" +
                    " from" +
                    "   account" +
                    " where" +
                    "   user_id = :user_id";
    private static final String sqlWithdrawal =
            "update account" +
                    " set" +
                    "   balance = balance - :amount" +
                    " where" +
                    "   user_id = :user_id" +
                    "   and balance > :amount";
    private static final String sqlDeposit =
            "update account" +
                    " set" +
                    "   balance = balance + :amount" +
                    " where" +
                    "   user_id = :user_id";
    private final NamedParameterJdbcTemplate jdbc;

    public PostgresAccountRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(@NotNull Account account) {
        var pSrc = new MapSqlParameterSource()
                .addValue("user_id", account.userId())
                .addValue("balance", account.balance());
        jdbc.update(sqlCreate, pSrc);
    }

    @Override
    public boolean makeDeposit(@NotNull Deposit deposit) {
        var pSrc = new MapSqlParameterSource()
                .addValue("user_id", deposit.userId())
                .addValue("amount", deposit.amount());
        return jdbc.update(sqlDeposit, pSrc) > 0;
    }

    @Override
    public boolean makePayment(@NotNull Payment payment) {
        var pSrc = new MapSqlParameterSource()
                .addValue("user_id", payment.userId())
                .addValue("amount", payment.amount());
        return jdbc.update(sqlWithdrawal, pSrc) > 0;
    }

    @Override
    @Nullable
    public Account findByUserId(@NotNull String userId) {
        var pSrc = new MapSqlParameterSource("user_id", userId);
        var res = jdbc.query(sqlFindByUserId, pSrc, AccountRowMapper.INSTANCE);

        return res.isEmpty() ? null : res.getFirst();
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        static final AccountRowMapper INSTANCE = new AccountRowMapper();

        @Override
        public Account mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
            return Account.builder()
                    .setUserId(rs.getString("user_id"))
                    .setBalance(rs.getInt("balance"))
                    .build();
        }
    }
}
