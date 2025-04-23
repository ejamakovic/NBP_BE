package com.NBP.NBP.repositories;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.enums.UserType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomUserRepository {

    private static final String TABLE_NAME = "NBP08.CUSTOM_USER";

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public CustomUserRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    private final RowMapper<CustomUser> customUserRowMapper = (resultSet, rowNum) -> {
        CustomUser user = new CustomUser();
        user.setId(resultSet.getInt("id"));
        user.setUserId(resultSet.getInt("user_id"));
        user.setUserType(UserType.valueOf(resultSet.getString("user_type")));
        user.setYear(resultSet.getInt("year"));
        user.setDepartmentId(resultSet.getInt("department_id"));
        return user;
    };

    private String getSelectQuery() {
        return String.format("SELECT * FROM %s", TABLE_NAME);
    }

    private String getInsertQuery() {
        return String.format("""
                INSERT INTO %s (user_id, user_type, year, department_id)
                VALUES (?, ?, ?, ?)
                """, TABLE_NAME);
    }

    private String getUpdateQuery() {
        return String.format("""
                UPDATE %s 
                SET user_id = ?, user_type = ?, year = ?, department_id = ? 
                WHERE id = ?
                """, TABLE_NAME);
    }

    private String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);
    }

    public List<CustomUser> findAll() {
        return jdbcTemplate.query(getSelectQuery(), customUserRowMapper);
    }

    public Optional<CustomUser> findById(int id) {
        try {
            String sql = getSelectQuery() + " WHERE id = ?";
            CustomUser user = jdbcTemplate.queryForObject(sql, customUserRowMapper, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public CustomUser findByUserId(int id) {
        try {
            String sql = getSelectQuery() + " WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, customUserRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public CustomUser findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return findByUserId(user.getId());
        }
        return null;
    }

    @Transactional
    public int save(CustomUser user) {
        return jdbcTemplate.update(getInsertQuery(),
                user.getUserId(),
                user.getUserType().toString(),
                user.getYear(),
                user.getDepartmentId()
        );
    }

    @Transactional
    public int update(CustomUser user) {
        return jdbcTemplate.update(getUpdateQuery(),
                user.getUserId(),
                user.getUserType().toString(),
                user.getYear(),
                user.getDepartmentId(),
                user.getId()
        );
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update(getDeleteQuery(), id);
    }
}
