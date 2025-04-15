package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceRepository {
    private static final String TABLE_NAME = "NBP08.SERVICE";

    private final JdbcTemplate jdbcTemplate;

    public ServiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Service> serviceRowMapper = (rs, rowNum) -> new Service(
            rs.getInt("id"),
            rs.getInt("equipment_id"),
            rs.getString("description")
    );

    public List<Service> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, serviceRowMapper);
    }

    public Service findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", serviceRowMapper, id);
    }

    public int save(Service service) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (equipment_id, description) VALUES (?, ?)",
                service.getEquipementId(), service.getDescription());
    }

    public int update(Service service) {
        return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET equipment_id = ?, description = ? WHERE id = ?",
                service.getEquipementId(), service.getDescription(), service.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }
}
