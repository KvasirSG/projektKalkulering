package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.User;
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

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User(
            rs.getInt("id"),
            rs.getString("email"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getString("role")
    );

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, id);
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, email);
    }

    public int save(User user) {
        String sql = "INSERT INTO users (email, name, password, role) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPassword(), user.getRole());
    }

    public int update(User user) {
        String sql = "UPDATE users SET email = ?, name = ?, password = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPassword(), user.getRole(), user.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
