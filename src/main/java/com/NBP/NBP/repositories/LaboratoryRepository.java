package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Laboratory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LaboratoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public LaboratoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Laboratory> labRowMapper = (rs, rowNum) -> new Laboratory(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("department_id"));

    public List<Laboratory> findAll() {
        return jdbcTemplate.query("SELECT * FROM laboratory", labRowMapper);
    }

    public Laboratory findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM laboratory WHERE id = ?", labRowMapper, id);
    }

    public int save(Laboratory lab) {
        return jdbcTemplate.update("INSERT INTO laboratory (name, department_id) VALUES (?, ?)",
                lab.getName(), lab.getDepartmentId());
    }

    public int update(Laboratory lab) {
        return jdbcTemplate.update("UPDATE laboratory SET name = ?, department_id = ? WHERE id = ?",
                lab.getName(), lab.getDepartmentId(), lab.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM laboratory WHERE id = ?", id);
    }
}
