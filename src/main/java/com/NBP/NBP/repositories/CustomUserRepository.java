package com.NBP.NBP.repositories;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.Department;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.CustomUserWithDepartments;
import com.NBP.NBP.models.enums.UserType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
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
        user.setYear(resultSet.getInt("year"));
        return user;
    };

    private String getSelectQuery() {
        return String.format("SELECT * FROM %s", TABLE_NAME);
    }

    private String getInsertQuery() {
        return String.format("""
                INSERT INTO %s (user_id, year)
                VALUES (?, ?)
                """, TABLE_NAME);
    }

    private String getUpdateQuery() {
        return String.format("""
                UPDATE %s
                SET user_id = ?, year = ?
                WHERE id = ?
                """, TABLE_NAME);
    }

    private String getDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);
    }

    public List<CustomUser> findAll() {
        return jdbcTemplate.query(getSelectQuery(), customUserRowMapper);
    }

    public List<CustomUserWithDepartments> findAllWithDepartments() {
        String sql = """
                SELECT
                    cu.id AS id,
                    cu.user_id,
                    cu.year,
                    d.id AS department_id,
                    d.name AS department_name
                FROM
                    NBP08.CUSTOM_USER cu
                LEFT JOIN
                    NBP08.CUSTOM_USER_DEPARTMENTS cud ON cu.id = cud.custom_user_id
                LEFT JOIN
                    NBP08.DEPARTMENT d ON cud.department_id = d.id
                ORDER BY cu.id
                """;

        return jdbcTemplate.query(sql, rs -> {
            List<CustomUserWithDepartments> users = new ArrayList<>();
            CustomUserWithDepartments current = null;
            int lastId = -1;

            while (rs.next()) {
                int id = rs.getInt("id");

                if (current == null || id != lastId) {
                    current = new CustomUserWithDepartments();
                    current.setId(id);
                    current.setUserId(rs.getInt("user_id"));
                    current.setYear(rs.getInt("year"));
                    current.setDepartments(new ArrayList<>());
                    users.add(current);
                    lastId = id;
                }

                int departmentId = rs.getInt("department_id");
                if (!rs.wasNull()) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString("department_name"));
                    current.getDepartments().add(department);
                }
            }

            return users;
        });
    }

    public List<CustomUserWithDepartments> findAllWithDepartmentsPaginated(int page, int size, String sortKey, String sortDirection) {
        String validSortKey = switch (sortKey) {
            case "userId", "year", "id" -> sortKey;
            default -> "id";
        };
        String orderBy = "ORDER BY cu." + validSortKey + " " + sortDirection + " ";

        String sql = """
            SELECT
                cu.id AS id,
                cu.user_id,
                cu.year,
                d.id AS department_id,
                d.name AS department_name,
                u.first_name,
                u.last_name,
                u.email,
                u.username,
                u.phone_number,
                u.birth_date,
                r.id AS role_id,
                r.name AS role_name
            FROM
                NBP08.CUSTOM_USER cu
            LEFT JOIN
                NBP08.CUSTOM_USER_DEPARTMENTS cud ON cu.id = cud.custom_user_id
            LEFT JOIN
                NBP08.DEPARTMENT d ON cud.department_id = d.id
            LEFT JOIN NBP.NBP_USER u ON cu.user_id = u.id
            LEFT JOIN NBP.NBP_ROLE r ON u.role_id = r.id
            """ + orderBy + """
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;

        return jdbcTemplate.query(sql, ps -> {
            ps.setInt(1, page * size);
            ps.setInt(2, size);
        }, rs -> {
            List<CustomUserWithDepartments> users = new ArrayList<>();
            CustomUserWithDepartments current = null;
            int lastId = -1;

            while (rs.next()) {
                int id = rs.getInt("id");

                if (current == null || id != lastId) {
                    current = new CustomUserWithDepartments();
                    current.setId(id);
                    current.setUserId(rs.getInt("user_id"));
                    current.setYear(rs.getInt("year"));
                    current.setDepartments(new ArrayList<>());
                    current.setFirstName(rs.getString("first_name"));
                    current.setLastName(rs.getString("last_name"));
                    current.setEmail(rs.getString("email"));
                    current.setUsername(rs.getString("username"));
                    current.setPhoneNumber(rs.getString("phone_number"));
                    current.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                    current.setRoleId(rs.getInt("role_id"));
                    current.setRoleName(rs.getString("role_name"));
                    users.add(current);
                    lastId = id;
                }

                int departmentId = rs.getInt("department_id");
                if (!rs.wasNull()) {
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString("department_name"));
                    current.getDepartments().add(department);
                }
            }

            System.out.println(users);
            return users;
        });
    }

    public long countAllUsers() {
        String sql = "SELECT COUNT(*) FROM NBP08.CUSTOM_USER";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return (count != null) ? count : 0L;
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

    public Optional<CustomUserWithDepartments> findByUserIdWithDepartments(Integer userId) {
        String sql = """
                SELECT
                    cu.id AS id,
                    cu.user_id,
                    cu.year,
                    d.id AS department_id,
                    d.name AS department_name
                FROM
                    NBP08.CUSTOM_USER cu
                JOIN
                    NBP08.CUSTOM_USER_DEPARTMENTS cud ON cu.id = cud.custom_user_id
                JOIN
                    NBP08.DEPARTMENT d ON cud.department_id = d.id
                WHERE
                    cu.user_id = ?
                """;

        try {
            CustomUserWithDepartments user = jdbcTemplate.query(sql, rs -> {
                CustomUserWithDepartments cuwd = null;
                List<Department> departments = new ArrayList<>();

                while (rs.next()) {
                    if (cuwd == null) {
                        cuwd = new CustomUserWithDepartments();
                        cuwd.setId(rs.getInt("id"));
                        cuwd.setUserId(rs.getInt("user_id"));
                        cuwd.setYear(rs.getInt("year"));
                    }

                    Department department = new Department();
                    department.setId(rs.getInt("department_id"));
                    department.setName(rs.getString("department_name"));
                    departments.add(department);
                }

                if (cuwd != null) {
                    cuwd.setDepartments(departments);
                }

                return cuwd;
            }, userId);

            return Optional.ofNullable(user);
        } catch (Exception e) {
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
        String sql = getInsertQuery();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setInt(1, user.getUserId());
            ps.setInt(2, user.getYear());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            user.setId(key.intValue());
            return user.getId();
        } else {
            throw new RuntimeException("Failed to retrieve generated id for CustomUser");
        }
    }

    @Transactional
    public int update(CustomUser user) {
        return jdbcTemplate.update(getUpdateQuery(),
                user.getUserId(),
                user.getYear(),
                user.getId());
    }

    @Transactional
    public void saveCustomUserDepartments(Integer userId, List<Integer> departmentIds) {
        if (departmentIds == null || departmentIds.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO NBP08.CUSTOM_USER_DEPARTMENTS (custom_user_id, department_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, departmentIds, departmentIds.size(),
                (ps, departmentId) -> {
                    ps.setInt(1, userId);
                    ps.setInt(2, departmentId);
                });
    }

    @Transactional
    public int delete(int id) {
        return jdbcTemplate.update(getDeleteQuery(), id);
    }

    @Transactional
    public void deleteUser(Integer id) {
        String deleteDepartmentsSql = "DELETE FROM NBP08.CUSTOM_USER_DEPARTMENTS WHERE custom_user_id = ?";
        jdbcTemplate.update(deleteDepartmentsSql, id);

        jdbcTemplate.update(getDeleteQuery(), id);
    }

}
