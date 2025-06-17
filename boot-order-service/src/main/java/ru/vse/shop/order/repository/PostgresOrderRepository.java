package ru.vse.shop.order.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.vse.shop.order.model.Order;
import ru.vse.shop.order.model.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresOrderRepository implements OrderRepository {
    private static final String sqlCreate =
            "insert into shop_order" +
                    "(" +
                    "   user_id" +
                    "   ,order_id" +
                    "   ,description" +
                    "   ,amount" +
                    "   ,status" +
                    ")" +
                    "values" +
                    "(" +
                    "   :user_id" +
                    "   ,:order_id" +
                    "   ,:description" +
                    "   ,:amount" +
                    "   ,:status" +
                    ")";
    private static final String sqlUpdateStatus =
            "update shop_order" +
                    " set" +
                    "   status = :status" +
                    " where" +
                    "   order_id = :order_id";
    private static final String sqlFindById =
            "select" +
                    "   *" +
                    " from" +
                    "   shop_order" +
                    " where" +
                    "   order_id = :order_id";
    private static final String sqlFindByUserId =
            "select" +
                    "   *" +
                    " from" +
                    "   shop_order" +
                    " where" +
                    "   user_id = :user_id";
    private static final String sqlFetchAll =
            "select * from shop_order order by user_id";

    private final NamedParameterJdbcTemplate jdbc;

    public PostgresOrderRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(Order order) {
        var pSrc = new MapSqlParameterSource()
                .addValue("order_id", order.id())
                .addValue("user_id", order.userId())
                .addValue("description", order.description())
                .addValue("amount", order.amount())
                .addValue("status", order.status().name());
        jdbc.update(sqlCreate, pSrc);
    }

    @Override
    public boolean updateStatus(@NotNull String orderId, @NotNull OrderStatus status) {
        var pSrc = new MapSqlParameterSource()
                .addValue("order_id", orderId)
                .addValue("status", status.name());
        return jdbc.update(sqlUpdateStatus, pSrc) > 0;
    }

    @Nullable
    @Override
    public Order findById(String orderId) {
        var pSrc = new MapSqlParameterSource("order_id", orderId);
        var res = jdbc.query(sqlFindById, pSrc, OrderRowMapper.INSTANCE);

        return res.isEmpty() ? null : res.getFirst();
    }

    @Override
    public List<Order> findByUserId(String userId) {
        var pSrc = new MapSqlParameterSource("user_id", userId);
        return jdbc.query(sqlFindByUserId, pSrc, OrderRowMapper.INSTANCE);
    }

    @Override
    public List<Order> fetchAll() {
        var pSrc = EmptySqlParameterSource.INSTANCE;
        return jdbc.query(sqlFetchAll, pSrc, OrderRowMapper.INSTANCE);
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        static final OrderRowMapper INSTANCE = new OrderRowMapper();

        @Override
        public Order mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
            return Order.builder()
                    .setId(rs.getString("order_id"))
                    .setUserId(rs.getString("user_id"))
                    .setDescription(rs.getString("description"))
                    .setAmount(rs.getInt("amount"))
                    .setStatus(OrderStatus.valueOf(rs.getString("status")))
                    .build();
        }
    }
}
