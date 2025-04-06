package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LogRepository {

    private static final String TABLE_NAME = "nbp.nbp_log";
    private final JdbcTemplate jdbcTemplate;

    public LogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Log> logRowMapper = (rs, rowNum) -> new Log(
            rs.getInt("id"),
            rs.getString("action_name"),
            rs.getString("table_name"),
            rs.getTimestamp("date_time"),
            rs.getString("db_user")
    );

    public List<Log> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, logRowMapper);
    }

    public int save(Log log) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (action_name, table_name, date_time, db_user) VALUES (?, ?, ?, ?)",
                log.getActionName(), log.getTableName(), log.getDateTime(), log.getDbUser());
    }

    public List<Log> findByTableName(String tableName) {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME + " WHERE table_name = ?", logRowMapper, tableName);
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }
}
