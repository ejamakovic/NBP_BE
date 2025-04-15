package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.models.Supplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierRepository {
    private final JdbcTemplate jdbcTemplate;

    public SupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Supplier> supplierRowMapper = (rs, rowNum) -> new Supplier(
            rs.getInt("id"),
            rs.getInt("equipment_id"),
            rs.getString("name")
    );

    public List<Supplier> findAll() {
        return jdbcTemplate.query("SELECT * FROM supplier", supplierRowMapper);
    }

    public Supplier findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM supplier WHERE id = ?", supplierRowMapper, id);
    }

    public int save(Supplier supplier) {
        return jdbcTemplate.update("INSERT INTO supplier (equipment_id, name) VALUES (?, ?)",
                supplier.getEquipmentId(), supplier.getName());
    }

    public int update(Supplier supplier) {
        return jdbcTemplate.update("UPDATE supplier SET equipment_id = ?, name = ? WHERE id = ?",
                supplier.getEquipmentId(), supplier.getName(), supplier.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM supplier WHERE id = ?", id);
    }

    public Optional<Supplier> findByName(String name) {
        try {
            Supplier supplier = jdbcTemplate.queryForObject(
                    "SELECT * FROM supplier WHERE name = ?",
                    supplierRowMapper,
                    name
            );
            return Optional.of(supplier);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}