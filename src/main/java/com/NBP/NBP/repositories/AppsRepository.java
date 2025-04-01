package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Apps;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppsRepository {
    private final JdbcTemplate jdbcTemplate;

    public AppsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Apps> appsRowMapper = (rs, rowNum) -> new Apps(
            rs.getInt("id"),
            rs.getString("app_id"),
            rs.getObject("manager_id") != null ? rs.getInt("manager_id") : null,
            rs.getDate("expiry_date")
    );

    public List<Apps> findAll() {
        return jdbcTemplate.query("SELECT * FROM apps", appsRowMapper);
    }

    public Apps findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM apps WHERE id = ?", appsRowMapper, id);
    }

    public int save(Apps app) {
        return jdbcTemplate.update("INSERT INTO apps (app_id, manager_id, expiry_date) VALUES (?, ?, ?)",
                app.getAppId(), app.getManagerId(), app.getExpiryDate());
    }

    public int update(Apps app) {
        return jdbcTemplate.update("UPDATE apps SET app_id = ?, manager_id = ?, expiry_date = ? WHERE id = ?",
                app.getAppId(), app.getManagerId(), app.getExpiryDate(), app.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM apps WHERE id = ?", id);
    }
}
