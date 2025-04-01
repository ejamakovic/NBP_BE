
package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Role> roleRowMapper = (rs, rowNum) -> new Role(
            rs.getInt("id"),
            rs.getString("name")
    );

    public List<Role> findAll() {
        return jdbcTemplate.query("SELECT * FROM role", roleRowMapper);
    }

    public Role findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM role WHERE id = ?", roleRowMapper, id);
    }

    public int save(Role role) {
        return jdbcTemplate.update("INSERT INTO role (name) VALUES (?)",
                role.getName());
    }

    public int update(Role role) {
        return jdbcTemplate.update("UPDATE role SET name = ? WHERE id = ?",
                role.getName(), role.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM role WHERE id = ?", id);
    }
}