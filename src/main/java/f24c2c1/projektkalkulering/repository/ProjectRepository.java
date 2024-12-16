/*
 * ===================================================================================
 * File:        ProjectRepository.java
 * Description: Repository for managing Project entities and their relationships
 *              with Tasks, Subprojects, and Users in the database using Spring's
 *              JdbcTemplate.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-12-15
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This repository provides CRUD operations for Project entities,
 *                including related tasks and subprojects.
 *              - The ProjectRowMapper maps database rows to Project instances.
 *              - Factory methods (createProjectInstance, createUserInstance,
 *                createTaskInstance) are used for flexibility in handling
 *                interface-based entity models.
 *              - Ensure that database transactions are properly managed for
 *                complex operations involving multiple entities.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Project project) {
        String sql = "INSERT INTO projects (name, description, creation_date, start_date, end_date, creator_id, client_id, is_subproject) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getCreationDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getCreator() != null ? project.getCreator().getId() : null,
                project.getClient() != null ? project.getClient().getId() : null,
                project.isSubProject());

        // Save tasks and subprojects
        saveTasks(project.getId(), project.getTasks());
        saveSubprojects(project.getId(), project.getSubProjects());
    }

    public Project findById(long id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), id);
    }

    public List<Project> findAll() {
        String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET name = ?, description = ?, creation_date = ?, start_date = ?, end_date = ?, creator_id = ?,client_id = ?, is_subproject = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getCreationDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getCreator() != null ? project.getCreator().getId() : null,
                project.getClient() != null ? project.getClient().getId() : null,
                project.isSubProject(),
                project.getId());

        // Update tasks and subprojects
        saveTasks(project.getId(), project.getTasks());
        saveSubprojects(project.getId(), project.getSubProjects());
    }

    public void deleteById(long id) {
        // Delete tasks, subprojects, and the main project
        deleteTasksByProjectId(id);
        deleteSubprojectsByProjectId(id);

        String sql = "DELETE FROM projects WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private void saveTasks(long projectId, List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return;

        String sql = "INSERT INTO tasks (name, description, creation_date, estimate, start_date, end_date, status, is_subtask, project_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        for (Task task : tasks) {
            jdbcTemplate.update(sql,
                    task.getName(),
                    task.getDescription(),
                    task.getCreationDate(),
                    task.getEstimate(),
                    task.getStartDate(),
                    task.getEndDate(),
                    task.getStatus(),
                    task.getIsSubTask(),
                    projectId);
        }
    }

    private void saveSubprojects(long projectId, List<Project> subprojects) {
        if (subprojects == null || subprojects.isEmpty()) return;

        String sql = "INSERT INTO subprojects (project_id, subproject_id) VALUES (?, ?)";
        for (Project subproject : subprojects) {
            jdbcTemplate.update(sql, projectId, subproject.getId());
        }
    }

    private void deleteTasksByProjectId(long projectId) {
        String sql = "DELETE FROM tasks WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }

    private void deleteSubprojectsByProjectId(long projectId) {
        String sql = "DELETE FROM subprojects WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }

    // Inner class for mapping rows to Project objects
    private class ProjectRowMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Dynamically create a Project implementation (e.g., ProjectImpl or another class in the future)
            Project project = createProjectInstance();
            project.setId(rs.getLong("id"));
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));
            project.setCreationDate(rs.getDate("creation_date"));
            project.setStartDate(rs.getDate("start_date"));
            project.setEndDate(rs.getDate("end_date"));
            project.setSubProject(rs.getBoolean("is_subproject"));

            // Fetch related entities
            long creatorId = rs.getLong("creator_id");
            if (creatorId > 0) {
                project.setCreator(fetchCreator(creatorId));
            }

            long clientId = rs.getLong("client_id");
            if (clientId > 0) {
                project.setClient(fetchClient(clientId));
            }

            project.setTasks(fetchTasks(project.getId()));
            project.setSubProjects(fetchSubprojects(project.getId()));

            return project;
        }

        private Client fetchClient(long clientId) {
            String sql = "SELECT * FROM clients WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Client client = createClientInstance();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setEmail(rs.getString("email"));
                client.setAddress(rs.getString("address"));
                client.setCity(rs.getString("city"));
                client.setZip(rs.getString("zip"));
                client.setContactName(rs.getString("contact_name"));
                client.setPhone(rs.getString("phone"));
                return client;
            }, clientId);
        }

        private User fetchCreator(long creatorId) {
            String sql = "SELECT * FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                User user = createUserInstance();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }, creatorId);
        }

        private List<Task> fetchTasks(long projectId) {
            String sql = "SELECT * FROM tasks WHERE project_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Task task = createTaskInstance();
                task.setId(rs.getLong("id"));
                task.setName(rs.getString("name"));
                task.setDescription(rs.getString("description"));
                task.setCreationDate(rs.getDate("creation_date"));
                task.setEstimate(rs.getInt("estimate"));
                task.setStartDate(rs.getDate("start_date"));
                task.setEndDate(rs.getDate("end_date"));
                task.setStatus(rs.getString("status"));
                task.setIsSubTask(rs.getBoolean("is_subtask"));
                return task;
            }, projectId);
        }

        private List<Project> fetchSubprojects(long projectId) {
            String sql = "SELECT subproject_id FROM subprojects WHERE project_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> findById(rs.getLong("subproject_id")));
        }
    }

    // Factory methods to dynamically create instances of the interfaces
    private Project createProjectInstance() {
        return new ProjectImpl();
    }

    private User createUserInstance() {
        return new UserImpl();
    }

    private Task createTaskInstance() {
       return new TaskImpl();
    }

    private Client createClientInstance() {
        return new ClientImpl();
    }

    public Map<String, Date> getProjectDates(long projectId) {
        String sql = "SELECT start_date, end_date FROM projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Map<String, Date> dates = new HashMap<>();
            dates.put("startDate", rs.getDate("start_date"));
            dates.put("endDate", rs.getDate("end_date"));
            return dates;
        }, projectId);
    }

}


