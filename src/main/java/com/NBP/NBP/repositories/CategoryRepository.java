package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Category;
import com.NBP.NBP.models.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Category> categoryRowMapper = (rs, rowNum) -> new Category(
            rs.getInt("id"),
            rs.getString("description"),
            rs.getString("name")
    );

    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM category", categoryRowMapper);
    }

    public Category findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM category WHERE id = ?", categoryRowMapper, id);
    }

    public int save(Category category) {
        return jdbcTemplate.update("INSERT INTO category (description, name) VALUES (?, ?)",
                category.getDescription(), category.getName());
    }

    public int update(Category category) {
        return jdbcTemplate.update("UPDATE category SET description = ?, name = ? WHERE id = ?",
                category.getDescription(), category.getName(), category.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM category WHERE id = ?", id);
    }

    public Optional<Category> findByName(String name) {
        try {
            Category category = jdbcTemplate.queryForObject(
                    "SELECT * FROM category WHERE name = ?",
                    categoryRowMapper,
                    name
            );
            return Optional.of(category);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}