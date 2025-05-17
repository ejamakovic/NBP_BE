package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepository {
    private static final String TABLE_NAME = "NBP08.DEPARTMENT";

    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Department> departmentRowMapper = (rs, rowNum) -> new Department(
            rs.getInt("id"),
            rs.getString("name"));

    public List<Department> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, departmentRowMapper);
    }

    public Department findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", departmentRowMapper, id);
    }

    @Transactional
    public int save(Department department) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (name) VALUES (?)",
                department.getName());
    }

    @Transactional
    public int update(Department department) {
        return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET name = ? WHERE id = ?",
                department.getName(), department.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public Optional<Department> findByName(String name) {
        try {
            Department department = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    departmentRowMapper,
                    name);
            return Optional.of(department);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
