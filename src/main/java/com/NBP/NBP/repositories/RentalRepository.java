package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.enums.RentalStatus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RentalRepository {
    private static final String TABLE_NAME = "NBP08.RENTAL";

    private final JdbcTemplate jdbcTemplate;

    public RentalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Rental> rentalRowMapper = (rs, rowNum) -> new Rental(
            rs.getInt("id"),
            rs.getInt("equipment_id"),
            rs.getInt("user_id"),
            rs.getDate("rent_date"),
            rs.getDate("return_date"),
            RentalStatus.valueOf(rs.getString("status")));

    public List<Rental> findAllPending(int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE status = 'PENDING' OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, offset, limit);
    }

    public List<Rental> findByUserId(Integer userId, int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, userId, offset, limit);
    }

    public List<Rental> findPendingByUserId(Integer userId, int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE user_id = ? AND status = 'PENDING' OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, userId, offset, limit);
    }

    public List<Rental> findAll(int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, offset, limit);
    }

    public List<Rental> findByEquipmentId(Integer equipmentId, int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE equipment_id = ? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, equipmentId, offset, limit);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public int countAllPending() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE status = 'PENDING'";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public int countByUserId(Integer userId) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE user_id = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return result != null ? result : 0;
    }

    public int countPendingByUserId(Integer userId) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE user_id = ? AND status = 'PENDING'";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return result != null ? result : 0;
    }

    public int countByEquipmentId(Integer equipmentId) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE equipment_id = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, equipmentId);
        return result != null ? result : 0;
    }

    public Rental findById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rentalRowMapper, id);
    }

    @Transactional
    public int updateStatus(Integer rentalId, RentalStatus newStatus) {
        String sql = "UPDATE " + TABLE_NAME + " SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newStatus.name(), rentalId);
    }

    @Transactional
    public int save(Rental rental) {
        String sql = "INSERT INTO " + TABLE_NAME
                + " (equipment_id, user_id, rent_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, rental.getEquipmentId(), rental.getUserId(), rental.getRentDate(),
                rental.getReturnDate(), rental.getStatus().name());
    }

    @Transactional
    public int update(Rental rental) {
        String sql = "UPDATE " + TABLE_NAME
                + " SET equipment_id = ?, user_id = ?, rent_date = ?, return_date = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, rental.getEquipmentId(), rental.getUserId(), rental.getRentDate(),
                rental.getReturnDate(), rental.getStatus().name(), rental.getId());
    }

    @Transactional
    public int delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
