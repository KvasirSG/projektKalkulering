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
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            task.setCreationDate(rs.getDate("creation_date"));
            task.setEstimate(rs.getInt("estimate"));
            task.setStartDate(rs.getDate("start_date"));
            task.setEndDate(rs.getDate("end_date"));
            task.setStatus(rs.getString("status"));
            task.setIsSubTask(rs.getBoolean("is_sub_task"));
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

    public int save(Task task) {
        String sql = "INSERT INTO tasks (name, description, creation_date, estimate, start_date, end_date, status, is_sub_task, project_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                task.getParentId()
        );
    }

    public int update(Task task) {
        String sql = "UPDATE tasks SET name = ?, description = ?, creation_date = ?, estimate = ?, start_date = ?, end_date = ?, status = ?, is_sub_task = ? " +
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
