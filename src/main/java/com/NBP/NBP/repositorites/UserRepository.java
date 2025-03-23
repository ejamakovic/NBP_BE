package com.NBP.NBP.repositorites;

import org.springframework.stereotype.Repository;

import com.NBP.NBP.models.User;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("id"),
            rs.getString("name"));

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    public User findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", userRowMapper, id);
    }

    public int save(User user) {
        return jdbcTemplate.update("INSERT INTO users (name) VALUES (?)",
                user.getName());
    }

    public int update(User user) {
        return jdbcTemplate.update("UPDATE users SET name = ? WHERE id = ?",
                user.getName(), user.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }
}