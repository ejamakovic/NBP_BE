package com.NBP.NBP.repositories;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.enums.UserType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CustomUser> rowMapper = (rs, rowNum) -> new CustomUser(
            rs.getInt("id"),
            rs.getInt("user_id"),
            UserType.valueOf(rs.getString("user_type")),
            rs.getInt("year"),
            rs.getInt("department_id"),
            rs.getString("email"),
            rs.getString("username"),
            rs.getString("password")
    );

    public List<CustomUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM custom_users", rowMapper);
    }

    public CustomUser findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM custom_users WHERE id = ?", rowMapper, id);
    }

    public int save(CustomUser user) {
        return jdbcTemplate.update(
                "INSERT INTO custom_users (user_id, user_type, year, department_id, email, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getUserId(), user.getUserType().name(), user.getYear(), user.getDepartmentId(),
                user.getEmail(), user.getUsername(), user.getPassword()
        );
    }

    public int update(CustomUser user) {
        return jdbcTemplate.update(
                "UPDATE custom_users SET user_id = ?, user_type = ?, year = ?, department_id = ?, email = ?, username = ?, password = ? WHERE id = ?",
                user.getUserId(), user.getUserType().name(), user.getYear(), user.getDepartmentId(),
                user.getEmail(), user.getUsername(), user.getPassword(), user.getId()
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM custom_users WHERE id = ?", id);
    }

    public CustomUser findByUsernameAndPassword(String username, String password) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM custom_users WHERE username = ? AND password = ?",
                rowMapper,
                username, password
        );
    }

    public CustomUser findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM custom_users WHERE username = ?",
                    rowMapper,
                    username
            );
        } catch (Exception e) {
            // Handle no user found (e.g., return null or throw custom exception)
            return null;
        }
    }

    public CustomUser findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM custom_users WHERE email = ?",
                    rowMapper,
                    email
            );
        } catch (Exception e) {
            // Handle no user found (e.g., return null or throw custom exception)
            return null;
        }
    }
}
