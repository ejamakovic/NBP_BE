package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.models.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepository {
    private static final String TABLE_NAME = "nbp.nbp_role";

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Pomoćna metoda za mapiranje rezultata
    private final RowMapper<Role> roleRowMapper = (rs, rowNum) -> new Role(
            rs.getInt("id"),
            rs.getString("name")
    );

    // Generalizovane metode za SQL upite
    private String getSelectQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }

    private String getInsertQuery() {
        return "INSERT INTO " + TABLE_NAME + " (name) VALUES (?)";
    }

    private String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET name = ? WHERE id = ?";
    }

    private String getDeleteQuery() {
        return "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
    }

    public List<Role> findAll() {
        return jdbcTemplate.query(getSelectQuery(), roleRowMapper);
    }

    public List<Role> findAllStartingWithNBP08() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE 'NBP08%'";
        return jdbcTemplate.query(sql, roleRowMapper);
    }


    public Role findById(int id) {
        String sql = getSelectQuery() + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, roleRowMapper, id);
    }

    @Transactional
    public int save(Role role) {
        return jdbcTemplate.update(getInsertQuery(),
                role.getName());
    }

    @Transactional
    public int update(Role role) {
        return jdbcTemplate.update(getUpdateQuery(),
                role.getName(), role.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update(getDeleteQuery(), id);
    }

    public Optional<Role> findByName(String name) {
        try {
            Role role = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    roleRowMapper,
                    name
            );
            return Optional.of(role);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
