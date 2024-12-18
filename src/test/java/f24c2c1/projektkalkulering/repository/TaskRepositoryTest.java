package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.model.TaskImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(TaskRepository.class)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDatabase() {
        // Create tables matching what the repository expects
        // Adjust the schema if needed to match all columns referenced in code
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS projects (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    description TEXT,
                    creation_date DATE,
                    start_date DATE,
                    end_date DATE,
                    creator_id BIGINT,
                    client_id BIGINT,
                    is_subproject BOOLEAN DEFAULT FALSE
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tasks (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    description VARCHAR(255),
                    creation_date DATE NOT NULL,
                    estimate INT,
                    start_date DATE,
                    end_date DATE,
                    status VARCHAR(50),
                    is_subtask BOOLEAN,
                    project_id BIGINT,
                    FOREIGN KEY (project_id) REFERENCES projects(id)
                );
                """);

        // Clean up existing data
        jdbcTemplate.execute("DELETE FROM tasks");
        jdbcTemplate.execute("DELETE FROM projects");

        // Insert a dummy project to associate tasks with
        // Ensure we have at least one project to reference by ID=1
        jdbcTemplate.update("INSERT INTO projects (id, name, creation_date) VALUES (1, 'Test Project', ?)",
                java.sql.Date.valueOf(LocalDate.now()));
    }

    @Test
    void testSaveAndFindById() {
        // Create a task that references the existing project_id=1
        TaskImpl task = new TaskImpl();
        task.setName("Sample Task");
        task.setDescription("A test task");
        // Set required dates to avoid NullPointerExceptions
        task.setCreationDate(LocalDate.now());
        task.setEstimate(5);
        task.setStartDate(LocalDate.now().plusDays(1));
        task.setEndDate(LocalDate.now().plusDays(2));
        task.setStatus("NEW");
        task.setIsSubTask(false);
        task.setParentId(1L); // Reference the existing project with id=1

        long taskId = taskRepository.save(task);

        Task savedTask = taskRepository.findById(taskId);
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getName()).isEqualTo("Sample Task");
        assertThat(savedTask.getParentId()).isEqualTo(1L);
    }

    @Test
    void testFindAll() {
        // Insert multiple tasks
        insertTask("Task 1", 1L);
        insertTask("Task 2", 1L);

        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(2);
    }

    @Test
    void testUpdate() {
        long taskId = insertTask("Old Task", 1L);
        Task taskToUpdate = taskRepository.findById(taskId);
        taskToUpdate.setName("Updated Task");
        taskToUpdate.setDescription("Updated Description");
        taskToUpdate.setStatus("IN_PROGRESS");
        int rowsAffected = taskRepository.update(taskToUpdate);
        assertThat(rowsAffected).isEqualTo(1);

        Task updatedTask = taskRepository.findById(taskId);
        assertThat(updatedTask.getName()).isEqualTo("Updated Task");
        assertThat(updatedTask.getDescription()).isEqualTo("Updated Description");
        assertThat(updatedTask.getStatus()).isEqualTo("IN_PROGRESS");
    }

    @Test
    void testDeleteById() {
        long taskId = insertTask("Task to Delete", 1L);
        int rowsDeleted = taskRepository.deleteById(taskId);
        assertThat(rowsDeleted).isEqualTo(1);

        // Now ensure it's gone
        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).isEmpty();
    }

    // Helper method to insert a task into the database using the repository
    private long insertTask(String taskName, long projectId) {
        TaskImpl task = new TaskImpl();
        task.setName(taskName);
        task.setDescription("Description");
        task.setCreationDate(LocalDate.now()); // required date to avoid NPE
        task.setEstimate(3);
        task.setStartDate(LocalDate.now().plusDays(1));
        task.setEndDate(LocalDate.now().plusDays(2));
        task.setStatus("NEW");
        task.setIsSubTask(false);
        task.setParentId(projectId);
        return taskRepository.save(task);
    }
}
