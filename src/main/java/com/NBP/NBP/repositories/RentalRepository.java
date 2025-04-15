package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Rental;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class RentalRepository {

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
        return jdbcTemplate.query("SELECT * FROM rental", rentalRowMapper);
    }

    public Rental findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM rental WHERE id = ?", rentalRowMapper, id);
    }

    public int save(Rental rental) {
        return jdbcTemplate.update(
                "INSERT INTO rental (equipment_id, rent_date, return_date) VALUES (?, ?, ?)",
                rental.getEquipmentId(), rental.getRentDate(), rental.getReturnDate());
    }

    public int update(Rental rental) {
        return jdbcTemplate.update(
                "UPDATE rental SET equipment_id = ?, rent_date = ?, return_date = ? WHERE id = ?",
                rental.getEquipmentId(), rental.getRentDate(), rental.getReturnDate(), rental.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM rental WHERE id = ?", id);
    }
}
