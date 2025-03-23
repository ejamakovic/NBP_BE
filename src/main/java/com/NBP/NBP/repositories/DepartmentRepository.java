package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Department> departmentRowMapper = (rs, rowNum) -> new Department(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("faculty_id"));

    public List<Department> findAll() {
        return jdbcTemplate.query("SELECT * FROM department", departmentRowMapper);
    }

    public Department findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM department WHERE id = ?", departmentRowMapper, id);
    }

    public int save(Department department) {
        return jdbcTemplate.update("INSERT INTO department (name, faculty_id) VALUES (?, ?)",
                department.getName(), department.getFacultyId());
    }

    public int update(Department department) {
        return jdbcTemplate.update("UPDATE department SET name = ?, faculty_id = ? WHERE id = ?",
                department.getName(), department.getFacultyId(), department.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM department WHERE id = ?", id);
    }
}
