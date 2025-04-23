package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
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
            rs.getString("description"),
            rs.getDate("service_date").toLocalDate()
    );

    public List<Service> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, serviceRowMapper);
    }

    public Service findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", serviceRowMapper, id);
    }

    @Transactional
    public int save(Service service) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (equipment_id, service_date, description) VALUES (?, ?, ?)",
                service.getEquipmentId(), service.getServiceDate(), service.getDescription());
    }

    @Transactional
    public int update(Service service) {
        return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET equipment_id = ?, service_date = ?, description = ? WHERE id = ?",
                service.getEquipmentId(), service.getServiceDate(), service.getDescription(), service.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public List<Service> findByEquipment(Equipment equipment) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM " + TABLE_NAME + " WHERE equipment_id = ?",
                    serviceRowMapper,
                    equipment.getId()
            );
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
