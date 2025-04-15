package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Rental;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
            rs.getDate("rent_date"),
            rs.getDate("return_date")
    );

    public List<Rental> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, rentalRowMapper);
    }

    public Rental findById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rentalRowMapper, id);
    }

    public int save(Rental rental) {
        String sql = "INSERT INTO " + TABLE_NAME + " (equipment_id, rent_date, return_date) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, rental.getEquipmentId(), rental.getRentDate(), rental.getReturnDate());
    }

    public int update(Rental rental) {
        String sql = "UPDATE " + TABLE_NAME + " SET equipment_id = ?, rent_date = ?, return_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, rental.getEquipmentId(), rental.getRentDate(), rental.getReturnDate(), rental.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
