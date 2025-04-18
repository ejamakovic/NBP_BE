package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.enums.EquipmentStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EquipmentRepository {
    private static final String TABLE_NAME = "NBP08.EQUIPMENT";

    private final JdbcTemplate jdbcTemplate;

    public EquipmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Equipment> equipmentRowMapper = (rs, rowNum) -> new Equipment(
            rs.getInt("id"),
            rs.getString("description"),
            rs.getString("name"),
            rs.getInt("category_id"),
            rs.getInt("laboratory_id"),
            EquipmentStatus.valueOf(rs.getString("status"))
    );

    public List<Equipment> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, equipmentRowMapper);
    }

    public Equipment findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", equipmentRowMapper, id);
    }

    public int save(Equipment equipment) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (description, name, category_id, laboratory_id, status) VALUES (?, ?, ?, ?, ?)",
                equipment.getDescription(), equipment.getName(), equipment.getCategoryId(), equipment.getLaboratoryId(), equipment.getStatus().name());
    }

    public int update(Equipment equipment) {
        return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET description = ?, name = ?, category_id = ?, laboratory_id = ?, status = ? WHERE id = ?",
                equipment.getDescription(), equipment.getName(), equipment.getCategoryId(), equipment.getLaboratoryId(), equipment.getStatus().name(), equipment.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public Optional<Equipment> findByName(String name) {
        try {
            Equipment equipment = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    equipmentRowMapper,
                    name
            );
            return Optional.of(equipment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
