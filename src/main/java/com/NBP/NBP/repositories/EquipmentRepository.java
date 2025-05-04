package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.dtos.EquipmentWithDetailsDTO;
import com.NBP.NBP.models.enums.EquipmentStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            EquipmentStatus.valueOf(rs.getString("status")));

    // RowMapper for EquipmentWithDetailsDTO
    private final RowMapper<EquipmentWithDetailsDTO> equipmentWithDetailsRowMapper = (rs, rowNum) -> {
        EquipmentWithDetailsDTO dto = new EquipmentWithDetailsDTO();
        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setDescription(rs.getString("description"));
        dto.setStatus(EquipmentStatus.valueOf(rs.getString("status")));
        dto.setCategoryId(rs.getInt("category_id"));
        dto.setLaboratoryId(rs.getInt("laboratory_id"));
        dto.setCategoryName(rs.getString("category_name"));
        dto.setLaboratoryName(rs.getString("laboratory_name"));
        return dto;
    };

    public List<Equipment> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, equipmentRowMapper);
    }

    private static final Set<String> ALLOWED_SORT_KEYS = Set.of("name", "status", "laboratory_id", "category_id");
    private static final Set<String> ALLOWED_SORT_DIRECTIONS = Set.of("asc", "desc");

    public List<EquipmentWithDetailsDTO> findPaginated(int offset, int limit, String sortKey, String sortDirection) {

        if (!ALLOWED_SORT_KEYS.contains(sortKey.toLowerCase())) {
            sortKey = "name";
        }
        if (!ALLOWED_SORT_DIRECTIONS.contains(sortDirection.toLowerCase())) {
            sortDirection = "asc";
        }

        String sql = "SELECT e.id, e.name, e.description, e.status, " +
                "e.category_id, e.laboratory_id, " +
                "c.name AS category_name, " +
                "l.name AS laboratory_name " +
                "FROM " + TABLE_NAME + " e " +
                "JOIN CATEGORY c ON e.category_id = c.id " +
                "JOIN LABORATORY l ON e.laboratory_id = l.id " +
                "ORDER BY " + sortKey + " " + sortDirection +
                " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<EquipmentWithDetailsDTO> res = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, offset);
                    preparedStatement.setInt(2, limit);
                },
                equipmentWithDetailsRowMapper);
        System.out.println(res);
        return res;
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public Equipment findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", equipmentRowMapper, id);
    }

    @Transactional
    public int save(Equipment equipment) {
        return jdbcTemplate.update(
                "INSERT INTO " + TABLE_NAME
                        + " (description, name, category_id, laboratory_id, status) VALUES (?, ?, ?, ?, ?)",
                equipment.getDescription(), equipment.getName(), equipment.getCategoryId(), equipment.getLaboratoryId(),
                equipment.getStatus().name());
    }

    @Transactional
    public int update(Equipment equipment) {
        return jdbcTemplate.update(
                "UPDATE " + TABLE_NAME
                        + " SET description = ?, name = ?, category_id = ?, laboratory_id = ?, status = ? WHERE id = ?",
                equipment.getDescription(), equipment.getName(), equipment.getCategoryId(), equipment.getLaboratoryId(),
                equipment.getStatus().name(), equipment.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public Optional<Equipment> findByName(String name) {
        try {
            Equipment equipment = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    equipmentRowMapper,
                    name);
            return Optional.of(equipment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
