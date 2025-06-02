package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {

    private static final String TABLE_NAME = "NBP08.CATEGORY";

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Category> categoryRowMapper = (rs, rowNum) -> new Category(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"));

    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, categoryRowMapper);
    }

    public List<Category> findPaginated(Integer offset, Integer limit, String sortKey, String sortDirection) {
        if (!List.of("name", "description", "id").contains(sortKey.toLowerCase())) {
            sortKey = "name";
        }
        if (!List.of("asc", "desc").contains(sortDirection.toLowerCase())) {
            sortDirection = "asc";
        }

        String baseSql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + sortKey + " " + sortDirection;

        if (offset == null || limit == null) {
            return jdbcTemplate.query(baseSql, categoryRowMapper);
        }

        String paginatedSql = baseSql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(paginatedSql, ps -> {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
        }, categoryRowMapper);
    }

    public Integer countAll() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + TABLE_NAME, Integer.class);
    }


    public Category findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", categoryRowMapper, id);
    }

    @Transactional
    public int save(Category category) {
        return jdbcTemplate.update("INSERT INTO " + TABLE_NAME + " (description, name) VALUES (?, ?)",
                category.getDescription(), category.getName());
    }

    @Transactional
    public int update(Category category) {
        return jdbcTemplate.update("UPDATE " + TABLE_NAME + " SET description = ?, name = ? WHERE id = ?",
                category.getDescription(), category.getName(), category.getId());
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM " + TABLE_NAME + " WHERE id = ?", id);
    }

    public Optional<Category> findByName(String name) {
        try {
            Category category = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + TABLE_NAME + " WHERE name = ?",
                    categoryRowMapper,
                    name);
            return Optional.of(category);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
