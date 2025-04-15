package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Faculty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FacultyRepository {
    private static final String TABLE_NAME = "NBP08.FACULTY";

    private final JdbcTemplate jdbcTemplate;

    public FacultyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Faculty> facultyRowMapper = (rs, rowNum) -> new Faculty(
            rs.getInt("id"),
            rs.getString("name"));

    public List<Faculty> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, facultyRowMapper);
    }

    public Faculty findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", facultyRowMapper, id);
    }

    public int save(Faculty faculty) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (name) VALUES (?)",
                faculty.getName());
    }

    public int update(Faculty faculty) {
        return jdbcTemplate.update("UPDATE "+ TABLE_NAME + " SET name = ? WHERE id = ?",
                faculty.getName(), faculty.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public Optional<Faculty> findByName(String name) {
        try {
            Faculty faculty = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    facultyRowMapper,
                    name
            );
            return Optional.of(faculty);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
