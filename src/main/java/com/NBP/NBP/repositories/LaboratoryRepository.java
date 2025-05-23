package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.models.dtos.LaboratoryWithDepartmentDTO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class LaboratoryRepository {
    private static final String TABLE_NAME = "NBP08.LABORATORY";

    private final JdbcTemplate jdbcTemplate;
    private static final Set<String> ALLOWED_SORT_KEYS = Set.of(
            "name",
            "department_name");

    private static final Set<String> ALLOWED_SORT_DIRECTIONS = Set.of(
            "asc",
            "desc");

    private final RowMapper<LaboratoryWithDepartmentDTO> laboratoryWithDepartmentRowMapper = (rs, rowNum) -> {
        LaboratoryWithDepartmentDTO dto = new LaboratoryWithDepartmentDTO();
        dto.setLaboratoryId(rs.getInt("id"));
        dto.setLaboratoryName(rs.getString("name"));
        dto.setDepartmentId(rs.getInt("department_id"));
        dto.setDepartmentName(rs.getString("department_name"));
        return dto;
    };

    public LaboratoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Laboratory> labRowMapper = (rs, rowNum) -> new Laboratory(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("department_id"));

    public List<Laboratory> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, labRowMapper);
    }

    public List<LaboratoryWithDepartmentDTO> findPaginated(Integer offset, Integer limit, String sortKey,
            String sortDirection) {

        if (!ALLOWED_SORT_KEYS.contains(sortKey.toLowerCase())) {
            sortKey = "name";
        }
        if (!ALLOWED_SORT_DIRECTIONS.contains(sortDirection.toLowerCase())) {
            sortDirection = "asc";
        }

        String baseSql = "SELECT l.id AS id, l.name AS name, " +
                "d.id AS department_id, d.name AS department_name " +
                "FROM LABORATORY l " +
                "JOIN DEPARTMENT d ON l.department_id = d.id " +
                "ORDER BY " + sortKey + " " + sortDirection;

        if (offset == null || limit == null) {
            return jdbcTemplate.query(baseSql, laboratoryWithDepartmentRowMapper);
        }

        String paginatedSql = baseSql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(paginatedSql, ps -> {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
        }, laboratoryWithDepartmentRowMapper);
    }

    public List<LaboratoryWithDepartmentDTO> findPaginatedForUser(Integer userId, Integer offset, Integer limit,
            String sortKey,
            String sortDirection) {
        if (!ALLOWED_SORT_KEYS.contains(sortKey.toLowerCase())) {
            sortKey = "name";
        }
        if (!ALLOWED_SORT_DIRECTIONS.contains(sortDirection.toLowerCase())) {
            sortDirection = "asc";
        }

        String baseSql = "SELECT l.id AS id, l.name AS name, " +
                "d.id AS department_id, d.name AS department_name " +
                "FROM LABORATORY l " +
                "JOIN DEPARTMENT d ON l.department_id = d.id " +
                "JOIN CUSTOM_USER_DEPARTMENTS ud ON ud.department_id = d.id " +
                "WHERE ud.custom_user_id = (SELECT cu.id FROM CUSTOM_USER cu WHERE cu.user_id = ?) " +
                "ORDER BY " + sortKey + " " + sortDirection;

        if (offset == null || limit == null) {
            return jdbcTemplate.query(baseSql, ps -> {
                ps.setInt(1, userId);
            }, laboratoryWithDepartmentRowMapper);
        }

        String paginatedSql = baseSql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(paginatedSql, ps -> {
            ps.setInt(1, userId);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
        }, laboratoryWithDepartmentRowMapper);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public int countAllForUser(Integer userId) {
        String sql = "SELECT COUNT(DISTINCT l.id) FROM LABORATORY l " +
                "JOIN DEPARTMENT d ON l.department_id = d.id " +
                "JOIN CUSTOM_USER_DEPARTMENTS ud ON ud.department_id = d.id AND ud.custom_user_id = (SELECT cu.id FROM CUSTOM_USER cu WHERE cu.user_id = ?) ";

        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return result != null ? result : 0;
    }

    public Laboratory findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", labRowMapper, id);
    }

    public int save(Laboratory lab) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (name, department_id) VALUES (?, ?)",
                lab.getName(), lab.getDepartmentId());
    }

    @Transactional
    public int update(Laboratory lab) {
        return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET name = ?, department_id = ? WHERE id = ?",
                lab.getName(), lab.getDepartmentId(), lab.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    @Transactional
    public Optional<Laboratory> findByName(String name) {
        try {
            Laboratory laboratory = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    labRowMapper,
                    name);
            return Optional.of(laboratory);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
