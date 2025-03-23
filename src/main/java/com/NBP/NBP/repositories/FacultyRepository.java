package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Faculty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FacultyRepository {
    private final JdbcTemplate jdbcTemplate;

    public FacultyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Faculty> facultyRowMapper = (rs, rowNum) -> new Faculty(
            rs.getInt("id"),
            rs.getString("name"));

    public List<Faculty> findAll() {
        return jdbcTemplate.query("SELECT * FROM faculty", facultyRowMapper);
    }

    public Faculty findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM faculty WHERE id = ?", facultyRowMapper, id);
    }

    public int save(Faculty faculty) {
        return jdbcTemplate.update("INSERT INTO faculty (name) VALUES (?)",
                faculty.getName());
    }

    public int update(Faculty faculty) {
        return jdbcTemplate.update("UPDATE faculty SET name = ? WHERE id = ?",
                faculty.getName(), faculty.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM faculty WHERE id = ?", id);
    }
}
