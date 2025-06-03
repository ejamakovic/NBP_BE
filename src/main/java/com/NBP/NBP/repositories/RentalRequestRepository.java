package com.NBP.NBP.repositories;

import com.NBP.NBP.models.RentalRequest;
import com.NBP.NBP.models.enums.RentalStatus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public class RentalRequestRepository {
    private static final String TABLE_NAME = "NBP08.RENTAL_REQUEST";

    private final JdbcTemplate jdbcTemplate;

    public RentalRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<RentalRequest> rentalRequestRowMapper = (rs, rowNum) -> new RentalRequest(
            rs.getInt("id"),
            rs.getInt("equipment_id"),
            rs.getInt("custom_user_id"),
            rs.getDate("request_date").toLocalDate(),
            RentalStatus.valueOf(rs.getString("status").trim().toUpperCase()));

    public List<RentalRequest> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, rentalRequestRowMapper);
    }

    public RentalRequest findById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rentalRequestRowMapper, id);
    }

    @Transactional
    public int save(RentalRequest request) {
        String sql = "INSERT INTO " + TABLE_NAME
                + " (equipment_id, custom_user_id, request_date, status) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, request.getEquipmentId(), request.getCustomUserId(),
                Date.valueOf(request.getRequestDate()), request.getStatus());
    }

    @Transactional
    public int update(RentalRequest request) {
        String sql = "UPDATE " + TABLE_NAME
                + " SET equipment_id = ?, custom_user_id = ?, request_date = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, request.getEquipmentId(), request.getCustomUserId(),
                Date.valueOf(request.getRequestDate()), request.getStatus(), request.getId());
    }

    @Transactional
    public int delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}