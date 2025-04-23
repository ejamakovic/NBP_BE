package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.Order;
import com.NBP.NBP.models.enums.OrderStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private static final String TABLE_NAME = "NBP08.ORDERS";

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Order> orderRowMapper = (rs, rowNum) -> new Order(
            rs.getInt("id"),
            rs.getInt("custom_user_id"),
            rs.getInt("equipment_id"),
            rs.getInt("price"),
            rs.getInt("supplier_id"),
            rs.getString("invoice_number"),
            OrderStatus.valueOf(rs.getString("order_status"))
    );

    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, orderRowMapper);
    }

    public Order findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", orderRowMapper, id);
    }

    @Transactional
    public int save(Order order) {
        return jdbcTemplate.update(
                "INSERT INTO " + TABLE_NAME + " (custom_user_id, equipment_id, price, supplier_id, invoice_number, order_status) VALUES (?, ?, ?, ?, ?, ?)",
                order.getCustomUserId(), order.getEquipmentId(), order.getPrice(),
                order.getSupplierId(), order.getInvoiceNumber(), order.getOrderStatus().name());
    }

    @Transactional
    public int update(Order order) {
        return jdbcTemplate.update(
                "UPDATE " + TABLE_NAME + " SET custom_user_id = ?, equipment_id = ?, price = ?, supplier_id = ?, invoice_number = ?, order_status = ? WHERE id = ?",
                order.getCustomUserId(), order.getEquipmentId(), order.getPrice(),
                order.getSupplierId(), order.getInvoiceNumber(), order.getOrderStatus().name(), order.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public List<Order> findByEquipment(Equipment equipment) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM " + TABLE_NAME + " WHERE equipment_id = ?",
                    orderRowMapper,
                    equipment.getId()
            );
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
