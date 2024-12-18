/*
 * ===================================================================================
 * File:        TaskRepository.java
 * Description: Repository for managing Task entities in the database using Spring's
 *              JdbcTemplate. Provides CRUD operations and SQL mappings for Task.
 *
 * Author:      Sebastian (Duofour)
 * Contributor: Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-12-13
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This repository supports operations for the Task interface.
 *              - The TaskRowMapper maps SQL query results to Task implementations.
 *              - SQL statements assume a database schema matching the Task model
 *                with fields like `id`, `name`, `description`, `status`, etc.
 *              - Ensure database transactions are handled appropriately for batch
 *                or multi-step operations.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.model.TaskImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for TaskImpl
    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            TaskImpl task = new TaskImpl();
            task.setId(rs.getLong("id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setCreationDate(rs.getDate("creation_date").toLocalDate());
            task.setEstimate(rs.getInt("estimate"));
            task.setStartDate(rs.getDate("start_date").toLocalDate());
            task.setEndDate(rs.getDate("end_date").toLocalDate());
            task.setStatus(rs.getString("status"));
            task.setIsSubTask(rs.getBoolean("is_subtask"));
            task.setParentId(rs.getLong("project_id"));
            return task;
        }
    }

    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    public Task findById(long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

    public long save(Task task) {
        String sql = "INSERT INTO tasks (name, description, creation_date, estimate, start_date, end_date, status, is_subtask, project_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(task.getCreationDate()));
            ps.setInt(4, task.getEstimate());
            ps.setDate(5, task.getStartDate() != null ? java.sql.Date.valueOf(task.getStartDate()) : null);
            ps.setDate(6, task.getEndDate() != null ? java.sql.Date.valueOf(task.getEndDate()) : null);
            ps.setString(7, task.getStatus());
            ps.setBoolean(8, task.getIsSubTask());
            ps.setLong(9, task.getParentId());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        task.setId(generatedId);
        return generatedId;
    }


    public int update(Task task) {
        String sql = "UPDATE tasks SET name = ?, description = ?, creation_date = ?, estimate = ?, start_date = ?, end_date = ?, status = ?, is_subtask = ? " +
                "WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                task.getName(),
                task.getDescription(),
                task.getCreationDate(),
                task.getEstimate(),
                task.getStartDate(),
                task.getEndDate(),
                task.getStatus(),
                task.getIsSubTask(),
                task.getId()
        );
    }

    public int deleteById(long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
