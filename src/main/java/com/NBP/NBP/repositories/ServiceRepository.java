package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceRepository {
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
        return jdbcTemplate.query("SELECT * FROM service", serviceRowMapper);
    }

    public Service findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM service WHERE id = ?", serviceRowMapper, id);
    }

    public int save(Service service) {
        return jdbcTemplate.update("INSERT INTO service (equipment_id, description) VALUES (?, ?)",
                service.getEquipementId(), service.getDescription());
    }

    public int update(Service service) {
        return jdbcTemplate.update("UPDATE service SET equipment_id = ?, description = ? WHERE id = ?",
                service.getEquipementId(), service.getDescription(), service.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM service WHERE id = ?", id);
    }
}