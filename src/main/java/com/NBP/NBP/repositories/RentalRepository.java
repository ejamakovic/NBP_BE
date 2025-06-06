package com.NBP.NBP.repositories;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.dtos.RentalDetailsDTO;
import com.NBP.NBP.models.enums.EquipmentStatus;
import com.NBP.NBP.models.enums.RentalStatus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RentalRepository {
    private static final String TABLE_NAME = "NBP08.RENTAL";

    private final JdbcTemplate jdbcTemplate;

    public RentalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Rental> rentalRowMapper = (rs, rowNum) -> new Rental(
            rs.getInt("id"),
            rs.getInt("equipment_id"),
            rs.getInt("custom_user_id"),
            rs.getDate("rent_date"),
            rs.getDate("return_date"),
            RentalStatus.valueOf(rs.getString("status")));

    private final RowMapper<RentalDetailsDTO> rentalDetailsRowMapper = (rs, rowNum) -> {
        RentalDetailsDTO dto = new RentalDetailsDTO();
        dto.setRentalId(rs.getInt("rental_id"));
        dto.setRentalStatus(RentalStatus.valueOf(rs.getString("rental_status")));
        dto.setRentDate(rs.getDate("rent_date"));
        dto.setReturnDate(rs.getDate("return_date"));

        dto.setEquipmentId(rs.getInt("equipment_id"));
        dto.setEquipmentName(rs.getString("equipment_name"));
        dto.setEquipmentDescription(rs.getString("equipment_description"));
        dto.setEquipmentStatus(EquipmentStatus.valueOf(rs.getString("equipment_status")));

        dto.setCategoryName(rs.getString("category_name"));
        dto.setCategoryDescription(rs.getString("category_description"));

        dto.setLaboratoryName(rs.getString("laboratory_name"));
        dto.setDepartmentName(rs.getString("department_name"));

        dto.setCustomUserId(rs.getInt("custom_user_id"));
        dto.setUserId(rs.getInt("user_id"));
        dto.setUsername(rs.getString("username"));
        dto.setFirstName(rs.getString("first_name"));
        dto.setLastName(rs.getString("last_name"));
        return dto;
    };

    public List<Rental> findAllPending(int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE status = 'PENDING' OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, offset, limit);
    }

    public List<RentalDetailsDTO> findAllPendingDetails(int offset, int limit) {
        if (offset < 0)
            offset = 0;
        if (limit <= 0)
            limit = 10;

        String sql = "SELECT " +
                "r.id AS rental_id, " +
                "r.status AS rental_status, " +
                "r.rent_date, " +
                "r.return_date, " +
                "e.id AS equipment_id, " +
                "e.name AS equipment_name, " +
                "e.description AS equipment_description, " +
                "e.status AS equipment_status, " +
                "c.name AS category_name, " +
                "c.description AS category_description, " +
                "l.name AS laboratory_name, " +
                "d.name AS department_name, " +
                "u.id AS user_id, " +
                "cu.id AS custom_user_id, " +
                "u.username AS username, " +
                "u.first_name AS first_name, " +
                "u.last_name AS last_name " +
                "FROM " + TABLE_NAME + " r " +
                "JOIN NBP08.EQUIPMENT e ON r.equipment_id = e.id " +
                "JOIN NBP08.CATEGORY c ON e.category_id = c.id " +
                "JOIN NBP08.LABORATORY l ON e.laboratory_id = l.id " +
                "JOIN NBP08.DEPARTMENT d ON l.department_id = d.id " +
                "JOIN NBP08.CUSTOM_USER cu ON r.custom_user_id = cu.id " +
                "JOIN NBP.NBP_USER u ON cu.user_id = u.id " +
                "WHERE r.status = 'PENDING' " +
                "OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY";

        return jdbcTemplate.query(sql, rentalDetailsRowMapper);
    }

    public List<Rental> findByUserId(Integer customUserId, int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE custom_user_id = ? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, customUserId, offset, limit);
    }

    public List<RentalDetailsDTO> findRentalDetailsByUserId(Integer customUserId, int offset, int limit) {
        String sql = """
                SELECT
                    r.id AS rental_id,
                    r.status AS rental_status,
                    r.rent_date,
                    r.return_date,
                    e.id AS equipment_id,
                    e.name AS equipment_name,
                    e.description AS equipment_description,
                    e.status AS equipment_status,
                    c.name AS category_name,
                    c.description AS category_description,
                    l.name AS laboratory_name,
                    d.name AS department_name,
                    u.id AS user_id,
                    cu.id AS custom_user_id,
                    u.username AS username,
                    u.first_name AS first_name,
                    u.last_name AS last_name
                FROM NBP08.RENTAL r
                JOIN NBP08.EQUIPMENT e ON r.equipment_id = e.id
                JOIN NBP08.CATEGORY c ON e.category_id = c.id
                JOIN NBP08.LABORATORY l ON e.laboratory_id = l.id
                JOIN NBP08.DEPARTMENT d ON l.department_id = d.id
                JOIN NBP08.CUSTOM_USER cu ON r.custom_user_id = cu.id
                JOIN NBP.NBP_USER u ON cu.user_id = u.id
                WHERE r.custom_user_id = ?
                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;
        return jdbcTemplate.query(sql, rentalDetailsRowMapper, customUserId, offset, limit);
    }

    public List<Rental> findPendingByUserId(Integer customUserId, int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE custom_user_id = ? AND status = 'PENDING' OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, customUserId, offset, limit);
    }

    public List<RentalDetailsDTO> findPendingRentalDetailsByUserId(Integer customUserId, int offset, int limit) {
        if (offset < 0)
            offset = 0;
        if (limit <= 0)
            limit = 10;

        String sql = "SELECT " +
                "r.id AS rental_id, " +
                "r.status AS rental_status, " +
                "r.rent_date, " +
                "r.return_date, " +
                "e.id AS equipment_id, " +
                "e.name AS equipment_name, " +
                "e.description AS equipment_description, " +
                "e.status AS equipment_status, " +
                "c.name AS category_name, " +
                "c.description AS category_description, " +
                "l.name AS laboratory_name, " +
                "d.name AS department_name, " +
                "u.id AS user_id, " +
                "cu.id AS custom_user_id, " +
                "u.username AS username, " +
                "u.first_name AS first_name, " +
                "u.last_name AS last_name " +
                "FROM " + TABLE_NAME + " r " +
                "JOIN NBP08.EQUIPMENT e ON r.equipment_id = e.id " +
                "JOIN NBP08.CATEGORY c ON e.category_id = c.id " +
                "JOIN NBP08.LABORATORY l ON e.laboratory_id = l.id " +
                "JOIN NBP08.DEPARTMENT d ON l.department_id = d.id " +
                "JOIN NBP08.CUSTOM_USER cu ON r.custom_user_id = cu.id " +
                "JOIN NBP.NBP_USER u ON cu.user_id = u.id " +
                "WHERE r.custom_user_id = ? AND r.status = 'PENDING' " +
                "OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY";

        return jdbcTemplate.query(sql, rentalDetailsRowMapper, customUserId);
    }

    public List<Rental> findAll(int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, offset, limit);
    }

    public List<RentalDetailsDTO> findAllDetailed(int offset, int limit) {
        String sql = """
                SELECT
                    r.id AS rental_id,
                    r.status AS rental_status,
                    r.rent_date,
                    r.return_date,
                    e.id AS equipment_id,
                    e.name AS equipment_name,
                    e.description AS equipment_description,
                    e.status AS equipment_status,
                    c.name AS category_name,
                    c.description AS category_description,
                    l.name AS laboratory_name,
                    d.name AS department_name,
                    u.id AS user_id,
                    cu.id AS custom_user_id,
                    u.username AS username,
                    u.first_name AS first_name,
                    u.last_name AS last_name
                FROM NBP08.RENTAL r
                JOIN NBP08.EQUIPMENT e ON r.equipment_id = e.id
                JOIN NBP08.CATEGORY c ON e.category_id = c.id
                JOIN NBP08.LABORATORY l ON e.laboratory_id = l.id
                JOIN NBP08.DEPARTMENT d ON l.department_id = d.id
                JOIN NBP08.CUSTOM_USER cu ON r.custom_user_id = cu.id
                JOIN NBP.NBP_USER u ON cu.user_id = u.id
                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;

        return jdbcTemplate.query(sql, rentalDetailsRowMapper, offset, limit);
    }

    public List<Rental> findByEquipmentId(Integer equipmentId, int offset, int limit) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE equipment_id = ? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return jdbcTemplate.query(sql, rentalRowMapper, equipmentId, offset, limit);
    }

    public List<RentalDetailsDTO> findRentalDetailsByEquipmentId(Integer equipmentId, int offset, int limit) {
        String sql = "SELECT r.id AS rental_id, r.status AS rental_status, r.rent_date AS rent_date, r.return_date AS return_date, "
                +
                "e.id AS equipment_id, e.name AS equipment_name, e.description AS equipment_description, e.status AS equipment_status, "
                +
                "c.name AS category_name, c.description AS category_description, " +
                "l.name AS laboratory_name, d.name AS department_name, " +
                "u.id AS user_id, " +
                "cu.id AS custom_user_id, " +
                "u.username AS username, " +
                "u.first_name AS first_name, " +
                "u.last_name AS last_name " +
                "FROM " + TABLE_NAME + " r " +
                "JOIN NBP08.EQUIPMENT e ON r.equipment_id = e.id " +
                "JOIN NBP08.CATEGORY c ON e.category_id = c.id " +
                "JOIN NBP08.LABORATORY l ON e.laboratory_id = l.id " +
                "JOIN NBP08.DEPARTMENT d ON l.department_id = d.id " +
                "JOIN NBP08.CUSTOM_USER cu ON r.custom_user_id = cu.id " +
                "JOIN NBP.NBP_USER u ON cu.user_id = u.id " +
                "WHERE r.equipment_id = ? " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        return jdbcTemplate.query(sql, rentalDetailsRowMapper, equipmentId, offset, limit);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public int countAllPending() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE status = 'PENDING'";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    public int countByUserId(Integer customUserId) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE custom_user_id = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, customUserId);
        return result != null ? result : 0;
    }

    public int countPendingByUserId(Integer customUserId) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE custom_user_id = ? AND status = 'PENDING'";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, customUserId);
        return result != null ? result : 0;
    }

    public int countByEquipmentId(Integer equipmentId) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE equipment_id = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, equipmentId);
        return result != null ? result : 0;
    }

    public Rental findById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rentalRowMapper, id);
    }

    @Transactional
    public int updateStatus(Integer rentalId, RentalStatus newStatus) {
        String sql = "UPDATE " + TABLE_NAME + " SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newStatus.name(), rentalId);
    }

    public boolean equipmentExists(int equipmentId) {
        String sql = "SELECT COUNT(*) FROM NBP08.EQUIPMENT WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, equipmentId);
        return count != null && count > 0;
    }

    @Transactional
    public int update(Rental rental) {
        if (!equipmentExists(rental.getEquipmentId())) {
            throw new IllegalArgumentException("Invalid equipment_id: " + rental.getEquipmentId());
        }
        String sql = "UPDATE " + TABLE_NAME
                + " SET equipment_id = ?, custom_user_id = ?, rent_date = ?, return_date = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, rental.getEquipmentId(), rental.getCustomUserId(), rental.getRentDate(),
                rental.getReturnDate(), rental.getStatus().name(), rental.getId());
    }

    @Transactional
    public int save(Rental rental) {
        if (!equipmentExists(rental.getEquipmentId())) {
            throw new IllegalArgumentException("Invalid equipment_id: " + rental.getEquipmentId());
        }
        String sql = "INSERT INTO " + TABLE_NAME
                + " (equipment_id, custom_user_id, rent_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, rental.getEquipmentId(), rental.getCustomUserId(), rental.getRentDate(),
                rental.getReturnDate(), rental.getStatus().name());
    }

    @Transactional
    public int delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
