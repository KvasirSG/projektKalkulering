/*
 * ===================================================================================
 * File:        UserRepository.java
 * Description: Repository for managing User entities in the database using
 *              Spring's JdbcTemplate. Supports CRUD operations and queries
 *              based on the User interface.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - Uses a factory method (createUserInstance) for dynamic creation
 *                of User objects. Replace this method with an actual implementation
 *                when a concrete class (e.g., UserImpl) is available.
 *              - Handles SQL mappings using a RowMapper for decoupled, reusable
 *                database access logic.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.User;
import f24c2c1.projektkalkulering.model.UserImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Factory method to create a new User instance.
     * Replace this with a concrete implementation when available.
     */
    private User createUserInstance() {
        return new UserImpl();
    }

    /**
     * RowMapper implementation for mapping database rows to User instances.
     */
    private final RowMapper<User> USER_ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        User user = createUserInstance();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    };

    /**
     * Finds all users.
     *
     * @return a list of all users
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user
     * @return the user
     */
    public User findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, id);
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return the user
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, email);
    }

    /**
     * Saves a new user to the database.
     *
     * @param user the user to save
     * @return the number of rows affected
     */
    public int save(User user) {
        String sql = "INSERT INTO users (email, name, password, role) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getRole());
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the user to update
     * @return the number of rows affected
     */
    public int update(User user) {
        String sql = "UPDATE users SET email = ?, name = ?, password = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getRole(),
                user.getId());
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user
     * @return the number of rows affected
     */
    public int deleteById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
