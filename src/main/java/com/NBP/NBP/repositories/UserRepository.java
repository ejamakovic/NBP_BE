package com.NBP.NBP.repositories;

import com.NBP.NBP.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final String TABLE_NAME = "nbp.nbp_user";

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map result set to User
    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setUsername(resultSet.getString("username"));
        user.setPhoneNumber(resultSet.getString("phone_number"));

        Optional.ofNullable(resultSet.getDate("birth_date"))
                .ifPresent(birthDate -> user.setBirthDate(birthDate.toLocalDate()));

        Optional.ofNullable(resultSet.getObject("address_id", Integer.class))
                .ifPresent(user::setAddressId);

        user.setRoleId(resultSet.getInt("role_id"));
        return user;
    };

    // SQL queries as formatted strings
    private String getSelectQuery() {
        return String.format("SELECT * FROM %s", TABLE_NAME);
    }

    private String getInsertQuery() {
        return String.format("""
                INSERT INTO %s (first_name, last_name, email, password, username, phone_number, birth_date, address_id, role_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, TABLE_NAME);
    }

    private String getUpdateQuery() {
        return String.format("""
                UPDATE %s 
                SET first_name = ?, last_name = ?, email = ?, password = ?, username = ?, phone_number = ?, 
                    birth_date = ?, address_id = ?, role_id = ? 
                WHERE id = ?
                """, TABLE_NAME);
    }

    private String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);
    }

    // Retrieve all users
    public List<User> findAll() {
        return jdbcTemplate.query(getSelectQuery(), userRowMapper);
    }

    // Find a user by ID with error handling
    public User findById(int id) {
        try {
            String sql = getSelectQuery() + " WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    // Save a new user
    public int save(User user) {
        return jdbcTemplate.update(getInsertQuery(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getAddressId(),
                user.getRoleId()
        );
    }

    // Update an existing user
    public int update(User user) {
        return jdbcTemplate.update(getUpdateQuery(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getAddressId(),
                user.getRoleId(),
                user.getId()
        );
    }

    // Delete a user by ID
    public int delete(int id) {
        return jdbcTemplate.update(getDeleteQuery(), id);
    }

    // Custom exception class for user not found scenario
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public User findByUsername(String username) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE username = ?", TABLE_NAME);
            return jdbcTemplate.queryForObject(
                    sql,
                    userRowMapper,
                    username
            );
        } catch (Exception e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE email = ?", TABLE_NAME);
            return jdbcTemplate.queryForObject(sql, userRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean existsByUsernameExcludingId(String username, int id) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE username = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, id);
        return count != null && count > 0;
    }

    public boolean existsByEmailExcludingId(String email, int id) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE email = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, id);
        return count != null && count > 0;
    }


}
